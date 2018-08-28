package br.cefetmg.inf.organizer.proxy;

import br.cefetmg.inf.organizer.dist.ClientDistribution;
import br.cefetmg.inf.organizer.model.domain.ItemTag;
import br.cefetmg.inf.organizer.model.domain.Tag;
import br.cefetmg.inf.organizer.model.service.IKeepItemTag;
import br.cefetmg.inf.util.PseudoPackage;
import br.cefetmg.inf.util.RequestType;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeepItemTagProxy implements IKeepItemTag{
    
    private ClientDistribution client;

    public KeepItemTagProxy() throws SocketException, UnknownHostException {
        client = ClientDistribution.getInstance();
    }

    @Override
    public boolean createTagInItem(ItemTag itemTag) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteTagInItem(ArrayList<Tag> itemTag, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }

    @Override
    public ArrayList<Tag> listAllTagInItem(Long seqItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteTagByItemId(Long idItem) {
       
        PseudoPackage contentPackage;
        Gson json = new Gson();
        
        List<String> jsonContent;
        jsonContent = new ArrayList();
        jsonContent.add(json.toJson(idItem));
        
        RequestType requestType = RequestType.DELETETAGBYITEMID;
        contentPackage = new PseudoPackage(requestType, jsonContent);
        
        try {
            PseudoPackage receivedPackage = client.request(contentPackage);
            return Boolean.valueOf(receivedPackage.getContent().get(0));
           
        } catch (IOException ex) {
            Logger.getLogger(KeepUserProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return false;
    }    
}
