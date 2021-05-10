package com.Server;

import com.CollectionManager;
import com.Commands.Execute;
import com.Data.Report;
import com.Data.Request;
import com.Data.ExecuteRequest;
import com.Exceptions.ExitException;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class Server {
    private int PORT;
    private InetAddress address;
    private DatagramSocket socket;
    private DataBase dataBase;

    private BufferedReader br;
    private CollectionManager collectionManager;

    public Server(int port, BufferedReader br) {
        PORT = port;
        this.br = br;
    }

    public void run() {
        try {
            socket = new DatagramSocket(5458);
            connectToDatabase(br);
            Runnable userInput = () -> {
                try {
                    while (true) {
                        String[] userCommand = (br.readLine()).split(" ");
                        System.out.println(Arrays.toString(userCommand));
                        if (userCommand[0].equals("save") || userCommand[0].equals("exit")) {
                            if (userCommand[0].equals("save") || userCommand.length == 2) {
                                Execute.execute(new BufferedReader(new StringReader("save\n" + userCommand[1] + "\nexit")), null, dataBase);
                            }
                            if (userCommand[0].equals("exit")) {
                                Execute.execute(new BufferedReader(new StringReader("exit")), null, dataBase);
                            }
                        } else {
                            System.out.println("Server has command save and command exit as well!");
                        }

                    }
                } catch (Exception e) {
                }
            };
            Thread thread = new Thread(userInput);
            thread.start();

            while (true) {
                clientRequest();
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void connectToDatabase(BufferedReader br) throws IOException {
        String name = br.readLine().trim();
        String password = br.readLine().trim();
        String jdbcURL = "jdbc:postgresql://localhost:7654/studs";
        dataBase = new DataBase(jdbcURL, name, password);
        dataBase.connectToDatabase();
    }

    public void clientRequest() throws ExitException {
        Request request = null;
        Report report = null;

        try {
            byte[] accept = new byte[16384];
            DatagramPacket getPacket = new DatagramPacket(accept, accept.length);

            socket.receive(getPacket);

            address = getPacket.getAddress();
            PORT = getPacket.getPort();

            request = deserialize(getPacket);
            report = ExecuteRequest.doingRequest(request, dataBase);


        } catch (ExitException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            byte[] sendBuffer = new byte[0];
            try {
                sendBuffer = serialize(report);
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, PORT);
                socket.send(sendPacket);
                System.out.println("Sending to " + sendPacket.getAddress() + ", message: " +
                        (report == null ? "ERROR" : report.getReportBody()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private <T> T deserialize(DatagramPacket getPacket) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        T request = (T) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    private <T> byte[] serialize(T toSerialize) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(toSerialize);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
}
