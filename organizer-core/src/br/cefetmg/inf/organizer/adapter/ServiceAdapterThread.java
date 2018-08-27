package br.cefetmg.inf.organizer.adapter;

import br.cefetmg.inf.organizer.dist.ServerDistribution;
import br.cefetmg.inf.organizer.model.domain.User;
import br.cefetmg.inf.organizer.model.service.IKeepUser;
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

    public void evaluateRequest() throws PersistenceException, BusinessException, IOException {
        RequestType requestType = contentPackage.getRequestType();
        byte[][] sendData;
        switch (requestType) {
            case REGISTERUSER:
                
                User user = gson.fromJson(contentPackage.getContent().get(0), User.class);
                IKeepUser keepUser = new KeepUser();
                boolean confirm = keepUser.registerUser(user);

                PseudoPackage responsePackage;
                List<String> jsonContent;
                jsonContent = new ArrayList();
                jsonContent.add(String.valueOf(confirm));
                responsePackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
                
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
                break;
            default:
            //exception
        }
    }

}
