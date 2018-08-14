package br.cefetmg.inf.organizer.controller;

import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.util.db.ConnectionManager;
import br.cefetmg.inf.util.exception.PersistenceException;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ruan Bertuce
 */
public class UpdateBD implements GenericProcess{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        
        //Pegando usuário
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        
        //Obtendo dados
        Gson gson = new Gson();
        
        String[] itemsID = gson.fromJson( req.getParameter("itemsID"), String[].class );
        String[] itemsType = gson.fromJson( req.getParameter("itemsType"), String[].class );
        String[] itemsName = gson.fromJson( req.getParameter("itemsName"), String[].class );
        String[] itemsDescription = gson.fromJson( req.getParameter("itemsDescription"), String[].class );
        String[] itemsDate = gson.fromJson( req.getParameter("itemsDate"), String[].class );
        String[] itemsStatus = gson.fromJson( req.getParameter("itemsStatus"), String[].class );
        
        String[] tagsID = gson.fromJson( req.getParameter("tagsID"), String[].class );
        String[] tagsName = gson.fromJson( req.getParameter("tagsName"), String[].class );
        
        String[] tagsItems = gson.fromJson( req.getParameter("tagsItems"), String[].class );
        String[] itemsTags = gson.fromJson( req.getParameter("itemsTags"), String[].class );
        
        Date date;
        java.sql.Date itemDate;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        //Passando dados para o BD
        
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            String sql = null;
            if(itemsID.length > 0){
                sql = "DELETE FROM item";
            
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.execute();
                }
            
            
                sql = "INSERT INTO item ( nom_item, des_item, dat_item, idt_item, idt_estado, cod_email)"
                       + " VALUES ( ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    for(int i = 0; i < itemsID.length; i++){
                        if(itemsDate[i] == null || itemsDate[i].equals("") || itemsDate[i].isEmpty()){
                            itemDate = null;
                        } else {            
                            date = formatter.parse(itemsDate[i]);
                            itemDate = new java.sql.Date( date.getTime() );
                        }
                                               
                        //preparedStatement.setString(1, itemsID[i]);
                        preparedStatement.setString(1, itemsName[i]);
                        preparedStatement.setString(2, itemsDescription[i]);
                        preparedStatement.setDate(3, itemDate);
                        preparedStatement.setString(4, itemsType[i]);
                        preparedStatement.setString(5, itemsStatus[i]);
                        preparedStatement.setString(6, user.getCodEmail());

                        preparedStatement.execute();
                    }               
                }
            }
            
            if(tagsID.length > 0){
                sql = "DELETE FROM tag";
            
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.execute();
                }
            
                sql = "INSERT INTO tag (nom_tag, cod_email)"
                    + " VALUES (?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    for(int i = 0; i < tagsID.length; i++){
                        //preparedStatement.setString(1, tagsID[i]);
                        preparedStatement.setString(1, tagsName[i]);
                        preparedStatement.setString(2, user.getCodEmail());

                        preparedStatement.execute();
                    }               
                }
            }
            if(tagsItems.length > 0){
                sql = "DELETE FROM item_tag";
            
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.execute();
                }
            
                sql = "INSERT INTO item_tag (seq_item, seq_tag)"
                    + " VALUES (?, ?)";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    for(int i = 0; i < tagsID.length; i++){
                        //preparedStatement.setString(1, tagsID[i]);
                        preparedStatement.setInt(1, Integer.valueOf( tagsItems[i]) );
                        preparedStatement.setInt(2, Integer.valueOf( itemsTags[i]) );

                        preparedStatement.execute();
                    }               
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex.getMessage(), ex);
        }
        
        return "index.jsp";
    }
    
}
