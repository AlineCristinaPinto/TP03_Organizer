/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepItemTag;
import br.cefetmg.inf.organizer.model.service.IKeepTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepItemTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepTag;
import br.cefetmg.inf.util.ErrorObject;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ChangeTarefaStatus implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String pageJSP = "";
        List<Item> itemList;
        
        // Pegando usuário
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
      
        String idItemString = req.getParameter("takeId");
        Long idItem = Long.parseLong(idItemString); 
        
        IKeepItem keepItem = new KeepItem();        
        Item item = keepItem.searchItemById(idItem);
        
        item.setIdentifierStatus("A");
        item.setUser(user);
        
        boolean result = keepItem.updateItem(item);
        
        if(result == false){
            ErrorObject error = new ErrorObject();
            error.setErrorName("Tente novamente");
            error.setErrorDescription("Erro ao reativar tarefa");
            error.setErrorSubtext("Não foi possível reativar a tarefa.");
            req.getSession().setAttribute("error", error);
            pageJSP = "/error.jsp";
        } else {
            IKeepTag keepTag = new KeepTag();
            Long idConclude = keepTag.searchTagByName("Concluidos", user);            
            Tag concludeTag = keepTag.searchTagById(idConclude);
            
            ArrayList<Tag> tag = new ArrayList();
            tag.add(concludeTag);
           
            IKeepItemTag keepItemTag = new KeepItemTag();
            result = keepItemTag.deleteTagInItem(tag, idItem);
            
            if(result==false){
                ErrorObject error = new ErrorObject();
                error.setErrorName("Tente novamente");
                error.setErrorDescription("Erro ao reativar tarefa");
                error.setErrorSubtext("Não foi possível reativar a tarefa.");
                req.getSession().setAttribute("error", error);
                pageJSP = "/error.jsp";
            } else {
                itemList = keepItem.listAllItem(user);
                if(itemList == null){
                    req.setAttribute("itemList", new ArrayList());
                }else{
                    req.setAttribute("itemList", itemList);
                }
                pageJSP = "/index.jsp";
            }
        }
       
        return pageJSP;
    }
    
}
*/