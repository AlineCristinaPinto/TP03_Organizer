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
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aline
 */
public class ConcludeTarefa implements GenericProcess {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String pageJSP = "";
        
        // Pegando usuário
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
      
        String idItemString = req.getParameter("takeId");
        Long idItem = Long.parseLong(idItemString); 
        
        IKeepItem keepItem = new KeepItem();        
        Item item = keepItem.searchItemById(idItem);
        
        item.setIdentifierStatus("C");
        item.setUser(user);
        
        boolean result = keepItem.updateItem(item);
        
        if(result == false){
            // exceção
        } else {
            IKeepTag keepTag = new KeepTag();
            Long idConclude = keepTag.searchTagByName("Concluidos", user);
            Tag concludeTag = keepTag.searchTagById(idConclude);
            ArrayList<Tag> tag = new ArrayList();
            tag.add(concludeTag);
           
            IKeepItemTag keepItemTag = new KeepItemTag();
            ItemTag itemTag = new ItemTag(); 
            itemTag.setItem(item);
            itemTag.setListTags(tag);
            
            result = keepItemTag.createTagInItem(itemTag);
            
            if(result==false){
                //exceção
            } else {
                pageJSP = "/index.jsp";
            }
           pageJSP = "/index.jsp";
        }
       
        return pageJSP;
    }
    
}
