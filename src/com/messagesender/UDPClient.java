package com.messagesender;

import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class UDPClient {

    public static class ServerInfo {
        private static int ServerPort;
        private static InetAddress ClientIP;
        private static int ClientPort;
        private static InetAddress ServerIP;
        private static String ClientMessage;
        private static String ClientMessageGet;
        private static DatagramSocket UDPSocketClient;

        public static int getServerPort() {
            return ServerPort;
        }

        public static void setServerPort(int ServerPorts) {
            ServerPort = ServerPorts;
        }

        public static InetAddress getClientIP() {
            return ClientIP;
        }

        public static void setClientIP(InetAddress ClientIPs) {
            ClientIP = ClientIPs;
        }

        public static int getClientPort() {
            return ClientPort;
        }

        public static void setClientPort(int ClientPorts) {
            ClientPort = ClientPorts;
        }

        public static InetAddress getServerIP() {
            return ServerIP;
        }

        public static void setServerIP(InetAddress ServerIPs) {
            ServerIP = ServerIPs;
        }

        public static String getClientMessage() {
            return ClientMessage;
        }

        public static void setClientMessage(String ClientMessages) {
            ClientMessage = ClientMessages;
        }

        public static String getClientMessageGet() {
            return ClientMessageGet;
        }

        public static void setClientMessageGet(String ClientMessageGets) {
            ClientMessageGet = ClientMessageGets;
        }

        public static DatagramSocket getUDPSocketClient() {
            return UDPSocketClient;
        }

        public static void setUDPSocketClient(DatagramSocket UDPSocketClients) {
            UDPSocketClient = UDPSocketClients;
        }

    }

    public static void ClientGET() throws IOException {
        UDPServer.ServerInfo.setServerPort(ServerInfo.getClientPort());//Setting Connection Port
        DatagramSocket UDPSocket = UDPClientInfoSet();// Create Message Port
        ServerInfo.setUDPSocketClient(UDPSocket);
        Thread ClientGet = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("1");
                    UDPClientGet(UDPSocket);
                }
            } catch (IOException ignored) {

            }
        });

        ClientGet.start();
    }

    public static void ClientSend() throws IOException {
        DatagramSocket UDPSocket = ServerInfo.getUDPSocketClient();
        UDPClientSender(UDPSocket);
    }

    public static DatagramSocket UDPClientInfoSet() throws IOException {
        ServerInfo.setServerPort(49363);
        //ServerInfo.setServerPort(ServerInfo.getClientPort());
        ServerInfo.setServerIP(InetAddress.getLocalHost());
        return new DatagramSocket();
    }

    public static void UDPClientSender(DatagramSocket UDPSocket) throws IOException {
        InetAddress SendServerIP = ServerInfo.getServerIP();
        int SendServerPort = ServerInfo.getServerPort();
        String ServerMessage = "From Client's Message,TestMessage,Hello World!";
        //String ServerMessage = ServerInfo.getClientMessage();
        byte[] SendToClient = ServerMessage.getBytes(StandardCharsets.UTF_8);
        DatagramPacket SendDP = new DatagramPacket(SendToClient, SendToClient.length, SendServerIP, SendServerPort);
        UDPSocket.send(SendDP);
    }

    public static void UDPClientGet(DatagramSocket UDPSocket) throws IOException {
        byte[] Message = new byte[500];
        DatagramPacket ReceiveDP = new DatagramPacket(Message, Message.length);
        UDPSocket.receive(ReceiveDP);
        String MessageToString = new String(Message, 0, ReceiveDP.getLength());
        System.out.println(MessageToString);
        UDPClient.ServerInfo.setClientMessage(MessageToString);
        UDPClientGet(UDPSocket);
    }

    public static void Waiting() {
        try {
            //System.out.println(UDPServer.ServerInfo.getClientIP() + "\n" + UDPServer.ServerInfo.getClientPort());
            Thread.sleep(1000);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public static void main(String[] args) throws IOException {
        DatagramSocket UDPSocket = UDPClientInfoSet();

        Thread ClientGet = new Thread(() -> {
            try {
                int flag = 1;
                while (flag == 1) {
                    System.out.println("1");
                    UDPClientGet(UDPSocket);
                }
            } catch (IOException exception) {

            }
        });

        ClientGet.start();

        int i = 0;
        while (i < 20) {
            UDPClientSender(UDPSocket);
            Waiting();
            i++;
            System.out.println(i);
        }
        //System.out.println(UDPClientGet(UDPSocket));
        UDPSocket.close();
    }
}


