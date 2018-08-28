package br.cefetmg.inf.organizer.adapter;

import br.cefetmg.inf.organizer.dist.ServerDistribution;
import br.cefetmg.inf.organizer.model.domain.Item;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepItem;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
import br.cefetmg.inf.organizer.model.service.impl.KeepItem;
import br.cefetmg.inf.organizer.model.service.impl.KeepUser;
import br.cefetmg.inf.util.PackageShredder;
import br.cefetmg.inf.util.PseudoPackage;
import br.cefetmg.inf.util.RequestType;
import br.cefetmg.inf.util.exception.BusinessException;
import br.cefetmg.inf.util.exception.PersistenceException;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceAdapterThread  implements Runnable{

    private InetAddress IPAddress;
    private int clientPort;
    private PseudoPackage contentPackage;
    private Gson gson;

    public ServiceAdapterThread(InetAddress IPAddress, int clientPort, PseudoPackage contentPackage) {
        this.IPAddress = IPAddress;
        this.clientPort = clientPort;
        this.contentPackage = contentPackage;
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            evaluateRequest();
            return;
        } catch (PersistenceException ex) {
            Logger.getLogger(ServiceAdapterThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BusinessException ex) {
            Logger.getLogger(ServiceAdapterThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServiceAdapterThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void prepareToSend(PseudoPackage responsePackage) throws IOException {
        
        byte[][] sendData;
        
        String responseString = gson.toJson(responsePackage);
        PackageShredder packageShredder = new PackageShredder();
        sendData = packageShredder.fragment(responseString);

        PseudoPackage numPackage;
        List<String> jsonContentAux;
        jsonContentAux = new ArrayList();
        jsonContentAux.add(String.valueOf(sendData.length));

        RequestType requestTypeAux = RequestType.NUMPACKAGE;
        numPackage = new PseudoPackage(requestTypeAux, jsonContentAux);

        ServerDistribution.sendData(IPAddress, clientPort, numPackage);
        ServerDistribution.sendData(IPAddress, clientPort, responsePackage);
                
    }

    public void evaluateRequest() throws PersistenceException, BusinessException, IOException {
        RequestType requestType = contentPackage.getRequestType();        
        boolean confirm;
        PseudoPackage responsePackage;
        List<String> jsonContent;
        User user;        
        IKeepUser keepUser;
        Item item;
        IKeepItem keepItem;
        
        switch (requestType) {
            case REGISTERUSER:
                user = gson.fromJson(contentPackage.getContent().get(0), User.class);
                keepUser = new KeepUser();
                confirm = keepUser.registerUser(user);
                
                jsonContent = new ArrayList();
                jsonContent.add(String.valueOf(confirm));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                
                prepareToSend(responsePackage);                    
                break;
            case SEARCHUSER:
                user = gson.fromJson(contentPackage.getContent().get(0), User.class);
                keepUser = new KeepUser();
                
                User foundUser = keepUser.searchUser(user);
                jsonContent = new ArrayList();
                jsonContent.add(gson.toJson(foundUser));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
               
                prepareToSend(responsePackage);    
                break;
            case UPDATEUSER:
                user = gson.fromJson(contentPackage.getContent().get(0), User.class);
                keepUser = new KeepUser();
                confirm = keepUser.updateUser(user);
                
                jsonContent = new ArrayList();
                jsonContent.add(String.valueOf(confirm));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                
                prepareToSend(responsePackage);  
                break;
            case DELETEACCOUNT:
                user = gson.fromJson(contentPackage.getContent().get(0), User.class);
                keepUser = new KeepUser();
                confirm = keepUser.deleteAccount(user);
                
                jsonContent = new ArrayList();
                jsonContent.add(String.valueOf(confirm));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                
                prepareToSend(responsePackage);  
                break;
            case GETUSERLOGIN:    
                String emailUser = contentPackage.getContent().get(0);
                String userPassword = contentPackage.getContent().get(1);
                
                keepUser = new KeepUser();
                user = keepUser.getUserLogin(emailUser, userPassword);
                
                jsonContent = new ArrayList();
                jsonContent.add(gson.toJson(user));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
               
                prepareToSend(responsePackage);    
                break;
                
            case CREATEITEM:
                
                item = gson.fromJson(contentPackage.getContent().get(0), Item.class);
                keepItem = new KeepItem();
                confirm = keepItem.createItem(item);
                
                jsonContent = new ArrayList();
                jsonContent.add(String.valueOf(confirm));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                
                prepareToSend(responsePackage);                    
                break;
                
            case SEARCHITEMBYNAME:
                
                String nameItem = gson.fromJson(contentPackage.getContent().get(0), String.class);
                keepItem = new KeepItem();
                Item itemByName = keepItem.searchItemByName(nameItem); 
                
                jsonContent = new ArrayList();
                jsonContent.add(gson.toJson(itemByName));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                        
                prepareToSend(responsePackage);                    
                break;
                
            case LISTALLITEM:
                
                user = gson.fromJson(contentPackage.getContent().get(0), User.class);
                keepItem = new KeepItem();
                List<Item> itemList  = keepItem.listAllItem(user);
                
                jsonContent = new ArrayList();
                jsonContent.add(gson.toJson(itemList));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                
                prepareToSend(responsePackage);                    
                break;
            default:
            //exception
        }
    }

}
