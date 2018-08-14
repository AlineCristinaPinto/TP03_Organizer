
package br.cefetmg.inf.organizer.model.service.impl;

import br.cefetmg.inf.organizer.model.dao.IMaxDAO;
import br.cefetmg.inf.organizer.model.dao.impl.MaxDAO;
import br.cefetmg.inf.organizer.model.domain.MaxDataObject;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepMaxData;
import br.cefetmg.inf.util.exception.PersistenceException;



public class KeepMaxData implements IKeepMaxData {

    private final IMaxDAO maxDAO;
    
    public KeepMaxData() {
        maxDAO = new MaxDAO();
    }
    
    //Não existem regras de negócio pois essas funções servem para alterar o BD real baseado no BD simulado do assistente MAX. Em caso de dúvidas contate os membros do grupo;
    
    @Override
    public boolean updateAllItems(MaxDataObject maxDataObject) throws PersistenceException {    
        return maxDAO.updateAllItems(maxDataObject); 
    }

    @Override
    public boolean updateAllTags(MaxDataObject maxDataObject) throws PersistenceException {        
        return maxDAO.updateAllTags(maxDataObject);
    }

    @Override
    public boolean updateAllItemTag(MaxDataObject maxDataObject) throws PersistenceException {
        return maxDAO.updateAllItemTag(maxDataObject);
    }
    
}
