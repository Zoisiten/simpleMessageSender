package com.messagesender;

import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    //Server Info Saving Bank
    public static class ServerInfo {
        private static int ServerPort;
        private static InetAddress ClientIP;
        private static int ClientPort;
        private static boolean ExitFlag;
        private static String GetMessage;
        private static boolean TableFlag;
        private static DatagramSocket UDPSocket;

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

        public static boolean getExitFlag() {
            return ExitFlag;
        }

        public static void setExitFlag(boolean ExitFlags) {
            ExitFlag = ExitFlags;
        }

        public static String getGetMessage() {
            return GetMessage;
        }

        public static void setGetMessage(String GetMessages) {
            GetMessage = GetMessages;
        }

        public static boolean getTableFlag() {
            return TableFlag;
        }

        public static void setTableFlag(boolean TableFlags) {
            TableFlag = TableFlags;
        }

        public static DatagramSocket getUDPSocket() {
            return UDPSocket;
        }

        public static void setUDPSocket(DatagramSocket UDPSockets) {
            UDPSocket = UDPSockets;
        }

    }

    public static void FromSender(String SenderMessage) throws IOException {
        DatagramSocket UDPSocket = UDPServer.ServerInfo.getUDPSocket();
        UDPServerSender(UDPSocket, SenderMessage);
        System.out.println(SenderMessage);
    }

    public static void FormGet() throws IOException {
        ServerInfo.setServerPort(SandAndGet.InfoSet.getSetPort());//Setting Connection Port
        DatagramSocket UDPSocket = UDPServerLinkStart();// Create Message Port
        //New Thread
        Thread WaitingLine = new Thread(() -> {
            try {
                System.out.println("IN");
                while (!ServerInfo.getExitFlag()) {
                    String MessageBack = UDPServerGet(UDPSocket);
                    ServerInfo.setGetMessage(MessageBack);
                    System.out.println(ServerInfo.getGetMessage() + "GETtl");
                    ServerInfo.setTableFlag(true);
                    if (ServerInfo.getTableFlag()) {
                        System.out.println("The Flags is True");
                    }
                }
            } catch (IOException ignored) {

            }
        });
        WaitingLine.start();
    }

    //Create a Base UDPLinkSockets
    public static DatagramSocket UDPServerLinkStart() throws IOException {
        //UDPClient.ServerInfo.setClientPort(UDPClient.ServerInfo.getServerPort());
        int ServerPorts = ServerInfo.getServerPort();
        DatagramSocket Sockets = new DatagramSocket(ServerPorts);
        System.out.println("System Opening");
        ServerInfo.setUDPSocket(Sockets);
        return Sockets;
    }

    //The Message Send Function For Server
    public static void UDPServerSender(DatagramSocket UDPSocket, String SendMessage) throws IOException {
        InetAddress SendClientIP = ServerInfo.getClientIP();
        int SendClientPort = ServerInfo.getClientPort();
        //String ServerMessage = "TestMessage,Hello World!";
        byte[] SendToClient = SendMessage.getBytes(StandardCharsets.UTF_8);
        DatagramPacket SendDP = new DatagramPacket(SendToClient, SendToClient.length, SendClientIP, SendClientPort);
        UDPSocket.send(SendDP);
        System.out.println("Replay OK" + "\n" + SendClientPort);
    }

    //The Message Get Function For Server
    public static String UDPServerGet(DatagramSocket UDPSocket) throws IOException {
        byte[] Message = new byte[500];
        DatagramPacket ReceiveDP = new DatagramPacket(Message, Message.length);
        UDPSocket.receive(ReceiveDP);
        ServerInfo.setClientIP(ReceiveDP.getAddress());
        ServerInfo.setClientPort(ReceiveDP.getPort());
        return new String(Message, 0, ReceiveDP.getLength());
    }

    public static void GetKeyInput(DatagramSocket UDPSocket) throws IOException {
        System.out.println("Input Key to Continue");
        char CharBuff = (char) System.in.read();
        if (CharBuff == 'g') {
            //UDPServerSender(UDPSocket);
            System.out.println("Send SuExec");
        }
        GetKeyInput(UDPSocket);
    }

    //Main Function
    public static void main(String[] args) throws IOException {
        ServerInfo.setServerPort(SandAndGet.InfoSet.getSetPort());//Setting Connection Port
        DatagramSocket UDPSocket = UDPServerLinkStart();// Create Message Port

        //New Thread
        Thread WaitingLine = new Thread(() -> {
            try {
                while (true) {
                    System.out.println(UDPServerGet(UDPSocket));
                }
            } catch (IOException ignored) {

            }
        });

        WaitingLine.start();
        GetKeyInput(UDPSocket);
    }
}



