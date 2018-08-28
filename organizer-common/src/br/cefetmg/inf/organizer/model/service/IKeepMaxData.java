
package br.cefetmg.inf.organizer.model.service;

import br.cefetmg.inf.organizer.model.domain.MaxDataObject;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.util.exception.PersistenceException;


public interface IKeepMaxData {
    boolean updateAllItems(MaxDataObject maxDataObject)  throws PersistenceException; 
    boolean updateAllTags(MaxDataObject maxDataObject) throws PersistenceException; //Olhar parametros
    boolean updateAllItemTag(MaxDataObject maxDataObject) throws PersistenceException;
}