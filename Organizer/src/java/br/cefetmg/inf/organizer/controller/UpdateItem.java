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
public class UpdateItem implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String pageJSP = "";
        
        // Pegando usuário
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        // Pega os dados dos inputs
        String id = req.getParameter("getIdItem");
        Long idItem = Long.parseLong(id);
        String name = req.getParameter("nameItem");
        String description = req.getParameter("descriptionItem");
        String identifierItem = req.getParameter("getIdentifierItem");
        
        // Tratamento de data
        String datItem = req.getParameter("dateItem");
        Date dateItem;
        if(datItem == null || datItem.equals("") || datItem.isEmpty()){
            dateItem = null;
        } else {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateItem = formatter.parse(datItem);
        }
        
        // Tratamento de Tag
        String tag = req.getParameter("inputTag");
        
        ArrayList<Tag> tagItem = new ArrayList();
            
        if(!tag.isEmpty()){
            String[] vetTag = tag.split(";");
            
            IKeepTag keepTag = new KeepTag();

            for (String vetTag1 : vetTag) {
                if(vetTag1.equals(" ")){
                    break;
                } else {
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
        }
        
        IKeepItemTag keepItemTag = new KeepItemTag();
        ArrayList<Tag> oldTags;
        
        // Pega as tags adicionadas anteriormente a atualização
        oldTags = keepItemTag.listAllTagInItem(idItem);        
        
        ArrayList<Tag> keepTag = new ArrayList();
        ArrayList<Tag> deleteTag = new ArrayList();
        ArrayList<Tag> newTag = new ArrayList();
        
        if(oldTags == null && !tagItem.isEmpty()){
            
            newTag = tagItem;
        
        } else if (oldTags != null && tagItem.isEmpty()){
        
            deleteTag = oldTags;
        
        } else if (oldTags != null && !tagItem.isEmpty()){      
            
            // Adiciona as tags antigas que permanecem em keepTag
            for(int i=0;i<tagItem.size();i++){
                for(int j=0;j<oldTags.size();j++){
                    if(tagItem.get(i).getTagName().contains(oldTags.get(j).getTagName())){
                        keepTag.add(tagItem.get(i));
                        break;
                    }
                }
            }
            
            // Adiciona as novas tags em newTag
            for(int i=0;i<tagItem.size();i++){
                for(int j=0;j<oldTags.size();j++){
                    if(!tagItem.get(i).getTagName().contains(oldTags.get(j).getTagName())){
                        newTag.add(tagItem.get(i));
                        break;
                    }
                }
            }
            
            if(keepTag.isEmpty()){
        
                deleteTag = oldTags;
            
            } else {

                // Adiciona as tags que não existem mais apos a atualização em deleteTag
                for(int i=0;i<oldTags.size();i++){
                    for(int j=0;j<keepTag.size();j++){
                        if(!(keepTag.get(j).getTagName().contains(oldTags.get(i).getTagName()))){
                            deleteTag.add(oldTags.get(i));
                            break;
                        }
                    }
                }
            }
        
        }
                     
        // Instanciando item para update
        Item item = new Item();
        
        item.setSeqItem(idItem);
        item.setNameItem(name);
        item.setDescriptionItem(description);
        item.setDateItem(dateItem);
        item.setIdentifierItem(identifierItem);
        if(identifierItem.equals("TAR")){
            item.setIdentifierStatus("A");
        }
        item.setUser(user);
        
        IKeepItem keepItem = new KeepItem();
        boolean result = keepItem.updateItem(item);
        
        if(result == false){
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Item já existe");
            error.setErrorSubtext("Não é possível atualizar um item de mesmo tipo com o mesmo nome.");
            req.getSession().setAttribute("error", error);
            pageJSP = "/error.jsp";
        } else {
            
            if(!deleteTag.isEmpty()){
                result = keepItemTag.deleteTagInItem(deleteTag, idItem);
                
                if(result == false){
                    ErrorObject error = new ErrorObject();
                    error.setErrorName("Tente novamente");
                    error.setErrorDescription("Erro Interno 505");
                    error.setErrorSubtext("Não foi possível retirar as tags corretamente");
                    req.getSession().setAttribute("error", error);
                    pageJSP = "/error.jsp";
                } else {
                    
                    if(!newTag.isEmpty()){
                        // Adiciona as novas tags
                        ItemTag itemTag = new ItemTag();
                        itemTag.setItem(item);
                        itemTag.setListTags(newTag);

                        result = keepItemTag.createTagInItem(itemTag);

                        if(result == false){
                            ErrorObject error = new ErrorObject();
                            error.setErrorName("Tente novamente");
                            error.setErrorDescription("Erro interno 505");
                            error.setErrorSubtext("Não foi possível inserir as novas tags corretamente.");
                            req.getSession().setAttribute("error", error);
                            pageJSP = "/error.jsp";
                        } else {
                            pageJSP = "/index.jsp";
                        }
                    } else {
                        pageJSP = "/index.jsp";
                    }
                }
            } else {
                if(!newTag.isEmpty()){
                    // Adiciona as novas tags
                    ItemTag itemTag = new ItemTag();
                    itemTag.setItem(item);
                    itemTag.setListTags(newTag);

                    result = keepItemTag.createTagInItem(itemTag);

                    if(result == false){
                        ErrorObject error = new ErrorObject();
                        error.setErrorName("Tente novamente");
                        error.setErrorDescription("Erro interno 505");
                        error.setErrorSubtext("Não foi possível inserir as novas tags corretamente.");
                        req.getSession().setAttribute("error", error);
                        pageJSP = "/error.jsp";
                    } else {
                        pageJSP = "/index.jsp";
                    }
                } else {
                    pageJSP = "/index.jsp";
                }
            }             
            
        }
        
        return pageJSP;
    }
    
}

