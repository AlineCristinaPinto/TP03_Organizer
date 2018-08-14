package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.dao.impl.ItemDAO;
import br.cefetmg.inf.organizer.model.dao.impl.TagDAO;
import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.util.db.ConnectionManager;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadMax implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String pageJSP = "";
        
        // Iniciando sessão
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        // Obtendo dados
        Gson gson = new Gson();
        
        // item
        ArrayList<Item> listAllItem = null;
        ItemDAO itemControl = new ItemDAO();
        if(itemControl.listAllItem( user ) != null)
            listAllItem = itemControl.listAllItem( user );
        String jsonItem = gson.toJson(listAllItem); 
        
        // tag
        ArrayList<Tag> listAlltag = null;
        TagDAO tagControl = new TagDAO();
        listAlltag = tagControl.listAlltag( user );
        String jsonTag = gson.toJson(listAlltag); 
        
        //item_tag
        
        ArrayList<String> listAllTagsItems = new ArrayList<>();
        ArrayList<String> listAllItemsTags = new ArrayList<>();
        
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            
            String sql = 
            "SELECT A.nom_item, C.nom_tag FROM item A JOIN item_tag B ON (A.seq_item = B.seq_item) "
                    + "JOIN tag C ON (B.seq_tag = C.seq_tag) WHERE A.cod_email = ? AND C.cod_email = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                preparedStatement.setString(1, user.getCodEmail());
                preparedStatement.setString(2, user.getCodEmail());
                
                try (ResultSet result = preparedStatement.executeQuery()) {
                    if (result.next()) {
                        do {
                            listAllTagsItems.add( result.getString("nom_item") );
                            listAllItemsTags.add( result.getString("nom_tag") );
                        } while (result.next());
                    }
                }
            }
            /*
            sql = "SELECT * FROM item_TAG";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet result = preparedStatement.executeQuery()) {
                    
                    if (result.next()) {
                        do {
                            System.out.println("Oi"+result.getString("seq_item"));
                            System.out.println("Oi"+result.getString("seq_tag"));
                        } while (result.next());
                    }
                }
            }
            */
        } catch (Exception ex) {
            //exception
        }
        String jsonTagsItems = gson.toJson(listAllTagsItems);
        String jsonItemsTags = gson.toJson(listAllItemsTags);
                
        // Carregando dados na sessão
        session.setAttribute("jsonItem", jsonItem);
        session.setAttribute("jsonTag", jsonTag);
        session.setAttribute("jsonTagsItems", jsonTagsItems);
        session.setAttribute("jsonItemsTags", jsonItemsTags);
        
        pageJSP = "/max.jsp";
        
        return pageJSP;
    }
    
}
