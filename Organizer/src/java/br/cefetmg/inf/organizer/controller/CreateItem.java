/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.ItemTag;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepItemTag;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepItemTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.ErrorObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aline
 */
public class CreateItem implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    
        String pageJSP = "";
        
        // Pegando usuário
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
                
       
        // Pega os dados dos inputs
        String selectType = req.getParameter("selectType");
        String name = req.getParameter("nameItem");
        String description = req.getParameter("descriptionItem");
        
        // Tratamento de data
        String datItem = req.getParameter("dateItem");
        Date dateItem;
        if(datItem == null || datItem.equals("") || datItem.isEmpty()){
            dateItem = null;
        } else {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateItem = formatter.parse(datItem);
        }
        
        // Pega as tags e inserem no arrayList buscando o id delas para
        // conseguir inserir no itemtag
        String tag = req.getParameter("inputTag");
        ArrayList<Tag> tagItem = new ArrayList();
            
        if(!tag.isEmpty()){
            String[] vetTag = tag.split(";");
            
            IKeepTag keepTag = new KeepTag();

            for (String vetTag1 : vetTag) {
                if (keepTag.searchTagByName(vetTag1.trim(), user) == null) {
                    //exceção
                } else {
                    Tag tagOfUser = new Tag();
                    
                    tagOfUser.setSeqTag(keepTag.searchTagByName(vetTag1.trim(), user));
                    tagOfUser.setTagName(vetTag1.trim());
                    tagOfUser.setUser(user);

                    tagItem.add(tagOfUser);
                }
            }
        }
        
        // Instanciando item para inserir
        Item item = new Item();
        
        item.setNameItem(name);
        item.setDescriptionItem(description);
        item.setIdentifierItem(selectType);
        item.setDateItem(dateItem);
        item.setUser(user);
        
        // se o item for uma tarefa o status não pode ser null
        if(selectType.equals("TAR")){
            item.setIdentifierStatus("A");
        } else {
            item.setIdentifierStatus(null);
        }
        
        // Inserção do item mas sem a tag
        IKeepItem keepItem = new KeepItem();
        boolean result = keepItem.createItem(item);
        
        if(result == false){
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Item já existe");
            error.setErrorSubtext("Não é possível inserir um item de mesmo tipo com o mesmo nome.");
            req.getSession().setAttribute("error", error);
            pageJSP = "/error.jsp";
        } else {
            
            if(!tag.isEmpty()){
                // busca a pk de item já que ela é uma seq e necessária para
                // inserir as tags relacionadas ao item em itemtag
                Item itemWithId = keepItem.searchItemByName(name);
            
                if(itemWithId == null){
                    ErrorObject error = new ErrorObject();
                    error.setErrorName("Tente novamente");
                    error.setErrorDescription("Erro Interno 505");
                    req.getSession().setAttribute("error", error);
                    pageJSP = "/error.jsp";

                } else {
                
                    // Adicionando os dados do item e tag a tabela itemtag
                    ItemTag itemTag = new ItemTag();
                
                    itemTag.setItem(itemWithId);
                    // inserindo o array list de tag aqui
                    itemTag.setListTags(tagItem);
                
                    // enfim adicionando as tags do item
                    IKeepItemTag keepItemTag = new KeepItemTag();
                
                    result = keepItemTag.createTagInItem(itemTag);
                
                    if(result == false){
                        ErrorObject error = new ErrorObject();
                        error.setErrorName("Tente novamente");
                        error.setErrorDescription("Erro Interno 505");
                        error.setErrorSubtext("Não foi possível inserir as tags corretamente.");
                        req.getSession().setAttribute("error", error);
                        pageJSP = "/error.jsp";
                    } else {
                        pageJSP = "/index.jsp";
                    }
                }    
            } else {
                pageJSP = "/index.jsp";
            }
                
            }
        
        return pageJSP;
        
    }
}


