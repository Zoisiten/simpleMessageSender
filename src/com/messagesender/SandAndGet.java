package com.messagesender;

import java.net.*;
import java.io.IOException;


public class SandAndGet {
    public static class InfoSet {
        private static int SetPort;
        private static InetAddress SetIP;
        private static boolean SoftwaraType; //True is Server & False is client;
        private static String ServerSendMessage;
        private static String ClientSendMessage;

        public static int getSetPort() {
            return SetPort;
        }

        public static void setSetPort(int SetPorts) {
            SetPort = SetPorts;
        }

        public static InetAddress getSetIP() {
            return SetIP;
        }

        ;

        public static void setSetIP(InetAddress SetIPs) {
            SetIP = SetIPs;
        }

        public static boolean getSoftwaraType() {
            return SoftwaraType;
        }

        public static void setSoftwaraType(boolean SoftwaraTpyes) {
            SoftwaraType = SoftwaraTpyes;
        }

        public static String getServerSendMessage() {
            return ServerSendMessage;
        }

        public static void setServerSendMessage(String SendMessages) {
            ServerSendMessage = SendMessages;
        }

        public static String getClientSendMessage() {
            return ClientSendMessage;
        }

        public static void setClientSendMessage(String ClientSendMessages) {
            ClientSendMessage = ClientSendMessages;
        }
    }


    //This part for Server Mode USeing
    public static class ServerPoint {
        public static void Connect() throws IOException {
            //UDPServer.UDPServerLinkStart();
            UDPServer.ServerInfo.setServerPort(InfoSet.getSetPort());
            UDPServer.FormGet();
        }

        public static void SendMessage() throws IOException {
            UDPServer.FromSender(SandAndGet.InfoSet.getServerSendMessage());
        }


    }

    public static class ClinetPoint {
        //This part fou Client Mode Using
        public static class ClientPoint {
            public static void ClientConnect() throws IOException {
                UDPClient.ClientGET();
            }

            public static void SendMessageClient() throws IOException {
                UDPClient.ClientSend();
            }
        }
    }
}


