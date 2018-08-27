package br.cefetmg.inf.organizer.dist;

import br.cefetmg.inf.util.RequestType;
import br.cefetmg.inf.util.PackageShredder;
import br.cefetmg.inf.util.PseudoPackage;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ClientDistribution {

    private static ClientDistribution onlyInstance;

    private DatagramSocket clientSocket;
    private String servidor;
    private int porta;
    private InetAddress IPAddress;

    private ClientDistribution() throws SocketException, UnknownHostException {

        clientSocket = new DatagramSocket();
        servidor = "localhost";
        porta = 2233;
        IPAddress = InetAddress.getByName(servidor);
    }

    public static ClientDistribution getInstance() throws SocketException, UnknownHostException {
        if (onlyInstance == null) {
            onlyInstance = new ClientDistribution();
        }

        return onlyInstance;
    }

    public PseudoPackage request(PseudoPackage pseudoPackage) throws IOException {
        JsonReader reader;
        final int BYTE_LENGTH = 1024;
 
        byte[][] sendData;
        byte[][] numPackage;
        byte[] receiveData = new byte[BYTE_LENGTH];
        Gson gson = new Gson();

        String jsonObject = gson.toJson(pseudoPackage);
        PackageShredder packageShredder = new PackageShredder();

        sendData = packageShredder.fragment(jsonObject);

        PseudoPackage contentPackage;
        List<String> jsonContent;
        jsonContent = new ArrayList();
        jsonContent.add(String.valueOf(sendData.length));

        RequestType requestType = RequestType.NUMPACKAGE;
        contentPackage = new PseudoPackage(requestType, jsonContent);
        numPackage = packageShredder.fragment(gson.toJson(contentPackage));

        for (byte[] numPackage1 : numPackage) {
            DatagramPacket sendPacketLenght = new DatagramPacket(numPackage1,
                    numPackage1.length, IPAddress, porta);
            clientSocket.send(sendPacketLenght);
        }

        DatagramPacket receiveConfirmation = new DatagramPacket(receiveData,
                receiveData.length);

        clientSocket.receive(receiveConfirmation);
        
        String receivedPackage = new String(receiveConfirmation.getData());
        reader = new JsonReader(new StringReader(receivedPackage));
        reader.setLenient(true);
        PseudoPackage confirmationPackage = gson.fromJson(reader, PseudoPackage.class);
        reader.close();
        if (Boolean.valueOf(confirmationPackage.getContent().get(0))&& 
                confirmationPackage.getRequestType().equals(RequestType.CONFIRMATIONPACKAGE)) {

            for (byte[] sendData1 : sendData) {
                DatagramPacket sendPackage = new DatagramPacket(sendData1,
                        sendData1.length, IPAddress, porta);
                clientSocket.send(sendPackage);
            }
        } else {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }

        DatagramPacket receivedFromServerLength = new DatagramPacket(receiveData,
                receiveData.length);

        clientSocket.receive(receivedFromServerLength);

        String strReceivedFromServerLength = new String(receivedFromServerLength.getData());
        reader = new JsonReader(new StringReader(strReceivedFromServerLength));
        reader.setLenient(true);
        PseudoPackage receivedFromServerLengthPackage = gson.fromJson(reader, PseudoPackage.class);
        reader.close();
        
        int numPackages = Integer.parseInt(receivedFromServerLengthPackage.getContent().get(0));
        byte [][] fragmentedPackage = new byte [numPackages][BYTE_LENGTH];
        
        for (int i = 0; i < numPackages; i++) {
            DatagramPacket receivedFromServer = new DatagramPacket(receiveData,
                    receiveData.length);

            clientSocket.receive(receivedFromServer);
            fragmentedPackage[i] = receivedFromServer.getData();
        }
        
        String receivedObject = packageShredder.defragment(fragmentedPackage);
        reader = new JsonReader(new StringReader(receivedObject));
        reader.setLenient(true);
        PseudoPackage returnPackage = gson.fromJson(reader, PseudoPackage.class);
        reader.close();
        return returnPackage;
    }

}
