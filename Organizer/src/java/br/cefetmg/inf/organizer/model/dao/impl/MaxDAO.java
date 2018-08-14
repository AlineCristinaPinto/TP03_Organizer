package br.cefetmg.inf.organizer.model.dao.impl;

import br.cefetmg.inf.organizer.model.dao.IMaxDAO;
import br.cefetmg.inf.organizer.model.domain.MaxDataObject;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.util.db.ConnectionManager;
import br.cefetmg.inf.util.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaxDAO implements IMaxDAO {

    @Override
    public boolean updateAllItems(MaxDataObject maxDataObject) throws PersistenceException {

        Date date;
        java.sql.Date itemDate;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            String sql;

            sql = "DELETE FROM item WHERE cod_email =?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maxDataObject.getUser().getCodEmail());
                preparedStatement.execute();
            }

            sql = "INSERT INTO item ( nom_item, des_item, dat_item, idt_item, idt_estado, cod_email)"
                    + " VALUES ( ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < maxDataObject.getItemsID().length; i++) {
                    if (maxDataObject.getItemsDate()[i] == null || maxDataObject.getItemsDate()[i].equals("") || maxDataObject.getItemsDate()[i].isEmpty()) {
                        itemDate = null;
                    } else {
                        date = formatter.parse(maxDataObject.getItemsDate()[i]);
                        itemDate = new java.sql.Date(date.getTime());
                    }

                    preparedStatement.setString(1, maxDataObject.getItemsName()[i]);
                    preparedStatement.setString(2, maxDataObject.getItemsDescription()[i]);
                    preparedStatement.setDate(3, itemDate);
                    preparedStatement.setString(4, maxDataObject.getItemsType()[i]);
                    if(maxDataObject.getItemsType()[i].equals("TAR")){
                        preparedStatement.setString(5, "A");
                    } else {
                        preparedStatement.setString(5, null);
                    }
                    preparedStatement.setString(6, maxDataObject.getUser().getCodEmail());

                    preparedStatement.execute();

                }

                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean updateAllTags(MaxDataObject maxDataObject) throws PersistenceException {

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            String sql;

            sql = "DELETE FROM tag WHERE cod_email =?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maxDataObject.getUser().getCodEmail());
                preparedStatement.execute();
            }

            sql = "INSERT INTO tag (nom_tag, cod_email)"
                    + " VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < maxDataObject.getTagsID().length; i++) {
                    //preparedStatement.setString(1, tagsID[i]);
                    preparedStatement.setString(1, maxDataObject.getTagsName()[i]);
                    preparedStatement.setString(2, maxDataObject.getUser().getCodEmail());

                    preparedStatement.execute();
                }

                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex.getMessage(), ex);
        }

    }

    @Override
    public boolean updateAllItemTag(MaxDataObject maxDataObject) throws PersistenceException {

        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            String sql;

            sql = "DELETE FROM item_tag WHERE seq_item IN (SELECT seq_item FROM item WHERE cod_email = ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, maxDataObject.getUser().getCodEmail());
                preparedStatement.execute();
            }

            sql = "INSERT INTO item_tag (seq_item, seq_tag)"
                    + " VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < maxDataObject.getTagsItems().length; i++) {
                    preparedStatement.setInt(1, Integer.valueOf(maxDataObject.getTagsItems()[i]));
                    preparedStatement.setInt(2, Integer.valueOf(maxDataObject.getItemsTags()[i]));

                    preparedStatement.execute();
                }

                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenceException(ex.getMessage(), ex);
        }
    }

}
