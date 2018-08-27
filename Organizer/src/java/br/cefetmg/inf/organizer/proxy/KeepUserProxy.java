package br.cefetmg.inf.organizer.proxy;

import br.cefetmg.inf.organizer.dist.ClientDistribution;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.util.PseudoPackage;
import br.cefetmg.inf.util.RequestType;
import br.cefetmg.inf.util.exception.BusinessException;
import br.cefetmg.inf.util.exception.PersistenceException;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeepUserProxy implements IKeepUser {

    private ClientDistribution client;

    public KeepUserProxy() throws SocketException, UnknownHostException {
        client = ClientDistribution.getInstance();
    }
    
    @Override
    public boolean registerUser(User user) throws PersistenceException, BusinessException {

        PseudoPackage contentPackage;
        Gson json = new Gson();
        
        List<String> jsonContent;
        jsonContent = new ArrayList();
        jsonContent.add(json.toJson(user));
        
        RequestType requestType = RequestType.REGISTERUSER;
        contentPackage = new PseudoPackage(requestType, jsonContent);
        try {
            PseudoPackage receivedPackage = client.request(contentPackage);
          
            return Boolean.valueOf(receivedPackage.getContent().get(0));
            
        } catch (IOException ex) {
            Logger.getLogger(KeepUserProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public User searchUser(User user) throws PersistenceException, BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean updateUser(User user) throws PersistenceException, BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteAccount(User user) throws PersistenceException, BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getUserLogin(String email, String password) throws PersistenceException, BusinessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
