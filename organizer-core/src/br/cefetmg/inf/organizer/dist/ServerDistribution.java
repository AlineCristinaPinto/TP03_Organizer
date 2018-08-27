package br.cefetmg.inf.organizer.dist;

import br.cefetmg.inf.organizer.adapter.ServiceAdapterThread;
import br.cefetmg.inf.util.PackageShredder;
import br.cefetmg.inf.util.RequestType;
import br.cefetmg.inf.util.PseudoPackage;
import br.cefetmg.inf.util.exception.BusinessException;
import br.cefetmg.inf.util.exception.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerDistribution {

    private static DatagramSocket serverSocket;
    private static int porta = 2233;

    public static void main(String args[]) throws SocketException, IOException, PersistenceException, BusinessException {
        serverSocket = new DatagramSocket(porta);
        while (true) {
            receiveData();
        }
    }

    private static synchronized void receiveData() throws IOException, PersistenceException, BusinessException {
        JsonReader reader;
        final int BYTE_LENGTH = 1024;
        byte[] receiveData = new byte[BYTE_LENGTH];
        byte[][] sendData;
        Gson gson = new Gson();
        PackageShredder packageShredder = new PackageShredder();

        DatagramPacket receivePacket = new DatagramPacket(receiveData,
                receiveData.length);
        serverSocket.receive(receivePacket);

        InetAddress IPAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();

        String receivedLength = new String(receivePacket.getData());
        reader = new JsonReader(new StringReader(receivedLength));
        reader.setLenient(true);
        PseudoPackage lengthPackage = gson.fromJson(reader, PseudoPackage.class);
        reader.close();
        if (lengthPackage.getRequestType().equals(RequestType.NUMPACKAGE)) {
            int numPackages = Integer.parseInt(lengthPackage.getContent().get(0));

            PseudoPackage confirmationPackage;
            List<String> jsonContent;
            jsonContent = new ArrayList();
            jsonContent.add("true");

            confirmationPackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
            sendData = packageShredder.fragment(gson.toJson(confirmationPackage));

            DatagramPacket sendPacketConfirmation = new DatagramPacket(sendData[0],
                    sendData[0].length, IPAddress, clientPort);

            serverSocket.send(sendPacketConfirmation);

            byte[][] fragmentedPackage = new byte[numPackages][BYTE_LENGTH];

            for (int i = 0; i < numPackages; i++) {
                DatagramPacket receivedFromClient = new DatagramPacket(receiveData,
                        receiveData.length);

                serverSocket.receive(receivedFromClient);
                fragmentedPackage[i] = receivedFromClient.getData();
            }

            String receivedObject = packageShredder.defragment(fragmentedPackage);
            reader = new JsonReader(new StringReader(receivedObject));
            reader.setLenient(true);
            PseudoPackage returnPackage = gson.fromJson(reader, PseudoPackage.class);
            reader.close();
            ServiceAdapterThread adapter = new ServiceAdapterThread(IPAddress, clientPort, returnPackage);
            adapter.evaluateRequest();
            Thread adapterThread = new Thread(adapter);
            adapterThread.start();
            return;

        } else {
            PseudoPackage confirmationPackage;
            List<String> jsonContent;
            jsonContent = new ArrayList();
            jsonContent.add("false");

            confirmationPackage = new PseudoPackage(RequestType.CONFIRMATIONPACKAGE, jsonContent);
            sendData = packageShredder.fragment(gson.toJson(confirmationPackage));

            DatagramPacket sendPacketConfirmation = new DatagramPacket(sendData[0],
                    sendData[0].length, receivePacket.getAddress(), receivePacket.getPort());

            serverSocket.send(sendPacketConfirmation);

            return;
        }
    }

    public static synchronized void sendData(InetAddress IPAddress, int clientPort, PseudoPackage responsePackage) throws IOException {
        byte[][] sendData;
        PackageShredder packageShredder = new PackageShredder();
        Gson gson = new Gson();

        sendData = packageShredder.fragment(gson.toJson(responsePackage));

        if (sendData.length > 1) {
            for (byte[] sendDataAux : sendData) {
                DatagramPacket sendPacketConfirmation = new DatagramPacket(sendDataAux,
                        sendDataAux.length, IPAddress, clientPort);
                serverSocket.send(sendPacketConfirmation);
            }
        } else {
            DatagramPacket sendPacketConfirmation = new DatagramPacket(sendData[0],
                    sendData[0].length, IPAddress, clientPort);
            serverSocket.send(sendPacketConfirmation);
        }

    }
}
