
package br.cefetmg.inf.organizer.model.dao;

import br.cefetmg.inf.organizer.model.domain.MaxDataObject;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.util.exception.PersistenceException;


public interface IMaxDAO {
    boolean updateAllItems(MaxDataObject maxDataObject) throws PersistenceException;
    boolean updateAllTags(MaxDataObject maxDataObject) throws PersistenceException;
    boolean updateAllItemTag(MaxDataObject maxDataObject) throws PersistenceException;
}
