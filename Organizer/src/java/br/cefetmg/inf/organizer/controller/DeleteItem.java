/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepItemTag;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepItemTag;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aline
 */
public class DeleteItem implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        String pageJSP = "";
        
        // Pegando usuário
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
      
        String idItemString = req.getParameter("takeId");
        Long idItem = Long.parseLong(idItemString); 
       
        IKeepItem keepItem = new KeepItem();
        boolean result = keepItem.deleteItem(idItem, user);
       
        if(result == false){
            //exceção
        } else {
            
            IKeepItemTag keepItemTag = new KeepItemTag();
            result = keepItemTag.deleteTagByItemId(idItem);
            
            if(result == false){
                //exceção
            } else {
            
                pageJSP = "/index.jsp";
            
            }
        }
        
        return pageJSP;
        
    }
    
}
