package com.messagesender;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class MessageSenderGUI {
    public static void LoginAndRegisterGUI() {
        LoginAndRegisterGUI.ShowAndCreateGUI();
    }


}

class CommonFunctionValue {
    private static boolean ModeFlag;

    //This Flag for Working Mode;if true,Working in Server mode;if false Working in Client Mode
    public static boolean getModeFlag() {
        return ModeFlag;
    }

    public static void setModeFlag(boolean ModeFlags) {
        ModeFlag = ModeFlags;
    }
}

//公共组件
class CommonGUI {
    public static void MessageBox(String MessageBoxText, String MessageTitle, int MessageLevel) {
        JOptionPane.showMessageDialog(null, MessageBoxText, MessageTitle, MessageLevel);
    }
}

//主页面
class MainSenderGUI {
    public static void ShowAndCreateGUI() throws IOException {
        JFrame JFrame1 = new JFrame("消息传输 ｜ MessageSender");
        JPanel JPanel1 = new JPanel();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame1.setSize(800, 600);
        JFrame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JFrame1.add(JPanel1);
        //SetComponents
        GUIComponents(JPanel1);
        JFrame1.setResizable(false);
        JFrame1.setVisible(true);
    }

    public static DefaultTableModel TableCreate() {
        DefaultTableModel MessageTableModel = new DefaultTableModel();
        Vector Row = new Vector<>();
        Vector Data = new Vector<>();
        Vector Names = new Vector<>();
        Row.add("Success");
        Row.add("登陆成功，欢迎使用");
        Data.add(Row);
        Names.add(0, "来源");
        Names.add(1, "信息详情");
        MessageTableModel.setDataVector(Data, Names);
        return MessageTableModel;
    }

    public static void ModelUpdate(DefaultTableModel GUIModel) {
        if (UDPServer.ServerInfo.getTableFlag()) {
            Vector VAdd = new Vector();
            VAdd.add(0, UDPServer.ServerInfo.getClientIP());
            VAdd.add(1, UDPServer.ServerInfo.getGetMessage());
            GUIModel.addRow(VAdd);
            UDPServer.ServerInfo.setTableFlag(false);
        }
    }

    public static void ClientModelUpdate(DefaultTableModel GUIModel) {
        if (UDPServer.ServerInfo.getTableFlag()) {
            Vector VAdd = new Vector();
            VAdd.add(0, UDPClient.ServerInfo.getServerIP());
            VAdd.add(1, UDPClient.ServerInfo.getClientMessageGet());
            GUIModel.addRow(VAdd);
            UDPServer.ServerInfo.setTableFlag(false);
        }
    }

    public static void SendModelUpdate(DefaultTableModel GUIModel) {
        if (UDPServer.ServerInfo.getTableFlag()) {
            Vector VAdd = new Vector();
            VAdd.add(0, "本机");
            VAdd.add(1, SandAndGet.InfoSet.getServerSendMessage());
            GUIModel.addRow(VAdd);
            UDPServer.ServerInfo.setTableFlag(false);
        }
    }


    public static void GUIComponents(JPanel JPanel1) throws IOException {

        DefaultTableModel GUITableModel = TableCreate();
        JTable MessageTable = new JTable(GUITableModel);
        JScrollPane JSP = new JScrollPane(MessageTable);
        MessageTable.getColumnModel().getColumn(0).setMaxWidth(80);
        MessageTable.getColumnModel().getColumn(0).setMinWidth(80);
        MessageTable.setRowHeight(30);
        JTextArea InputMessageText = new JTextArea();
        JButton SetPanelButton = new JButton("设置面板");
        JButton SendMessageButton = new JButton("发送消息");
        JScrollPane TextJSP = new JScrollPane(InputMessageText);
        JButton StartButton = new JButton("开始连接");

        JPanel1.setLayout(null);
        JSP.setBounds(50, 50, 700, 300);
        TextJSP.setBounds(50, 370, 700, 80);
        SendMessageButton.setBounds(620, 460, 130, 60);
        SetPanelButton.setBounds(50, 460, 130, 60);
        StartButton.setBounds(200, 460, 130, 60);
        JPanel1.add(JSP);
        JPanel1.add(TextJSP);
        JPanel1.add(SendMessageButton);
        JPanel1.add(SetPanelButton);
        JPanel1.add(StartButton);

        StartButton.addActionListener(e -> {
            Thread ToTable = new Thread(() -> {
                while (true) {
                    ModelUpdate(GUITableModel);
                    try {
                        //SandAndGet.ServerPoint.Connect();
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            ToTable.start();
        });

        SendMessageButton.addActionListener(e -> {
                    if (CommonFunctionValue.getModeFlag()) {
                        try {
                            SandAndGet.InfoSet.setServerSendMessage(InputMessageText.getText());
                            SandAndGet.ServerPoint.SendMessage();
                            SendModelUpdate(GUITableModel);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        SandAndGet.InfoSet.setClientSendMessage(InputMessageText.getText());
                        try {
                            SandAndGet.ClinetPoint.ClientPoint.SendMessageClient();
                            SendModelUpdate(GUITableModel);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
        );
        SetPanelButton.addActionListener(e -> SetPanelGUI.ShowAndCreateGUI());
    }
}

//登陆界面
class LoginAndRegisterGUI {
    public static void ShowAndCreateGUI() {
        JFrame JFrame1 = new JFrame("登陆与注册 ｜ Login & Register");
        JPanel JPanel1 = new JPanel();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame1.setSize(300, 200);
        JFrame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JFrame1.add(JPanel1);
        //SetComponents
        GUIComponents(JPanel1, JFrame1);
        JFrame1.setResizable(false);
        JFrame1.setVisible(true);
    }

    public static void ClosePanel(JFrame JFFrame1) {
        JFFrame1.setVisible(false);
    }

    public static void GUIComponents(JPanel JPanel1, JFrame JFrame1) {
        JLabel UserLabel = new JLabel("账户:");
        JLabel PasswordLabel = new JLabel("密码:");
        JButton LoginButton = new JButton("登陆");
        JButton RegisterButton = new JButton("注册");
        JPasswordField UserPasswordText = new JPasswordField();
        JTextField UserNameText = new JTextField();

        JPanel1.setLayout(null);

        UserLabel.setBounds(50, 30, 80, 20);
        PasswordLabel.setBounds(50, 60, 80, 20);

        LoginButton.setBounds(60, 100, 80, 40);
        RegisterButton.setBounds(160, 100, 80, 40);

        UserNameText.setBounds(90, 30, 155, 20);
        UserPasswordText.setBounds(90, 60, 155, 20);

        JPanel1.add(UserLabel);
        JPanel1.add(PasswordLabel);
        JPanel1.add(LoginButton);
        JPanel1.add(RegisterButton);
        JPanel1.add(UserNameText);
        JPanel1.add(UserPasswordText);

        LoginButton.addActionListener(e -> {
            String username = UserNameText.getText();
            String password = Arrays.toString(UserPasswordText.getPassword());
            if (!Objects.equals(username, "admin") && !Objects.equals(password, "admin")) {
                CommonGUI.MessageBox("账户或密码错误，请重试", "出现错误", 2);
            } else {
                try {
                    MainSenderGUI.ShowAndCreateGUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ClosePanel(JFrame1);
            }
        });
    }
}

class SetPanelGUI {
    public static void ShowAndCreateGUI() {
        JFrame JFrame1 = new JFrame("设置 ｜ Software Setting");
        JPanel JPanel1 = new JPanel();

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame1.setSize(400, 300);
        JFrame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JFrame1.add(JPanel1);
        //SetComponents
        GUIComponents(JPanel1, JFrame1);
        JFrame1.setResizable(false);
        JFrame1.setVisible(true);
    }

    public static void ClosePanel(JFrame JFFrame1) {
        JFFrame1.setVisible(false);
    }

    public static void GUIComponents(JPanel JPanel1, JFrame JFrame1) {
        JLabel PortLabel = new JLabel("端口:");
        JButton SaveButton = new JButton("保存");
        JTextField PortText = new JTextField();
        JButton RandomPort = new JButton("随机端口");
        JTextField IPText = new JTextField();
        JLabel IPLabel = new JLabel("IP:");
        JButton LocalIP = new JButton("Localhost");


        JRadioButton ServerChick = new JRadioButton("服务端 ｜ Server");
        JRadioButton ClientChick = new JRadioButton("客户端 ｜ Client");
        ButtonGroup RadioGroup = new ButtonGroup();
        RadioGroup.add(ServerChick);
        RadioGroup.add(ClientChick);


        JPanel1.setLayout(null);

        PortLabel.setBounds(30, 50, 30, 30);
        PortText.setBounds(70, 50, 150, 30);
        SaveButton.setBounds(200, 200, 170, 50);
        ServerChick.setBounds(30, 120, 200, 30);
        ClientChick.setBounds(30, 150, 200, 30);
        RandomPort.setBounds(220, 50, 100, 30);
        IPLabel.setBounds(30, 80, 30, 30);
        IPText.setBounds(70, 80, 150, 30);
        LocalIP.setBounds(220, 80, 100, 30);


        JPanel1.add(PortLabel);
        JPanel1.add(PortText);
        JPanel1.add(SaveButton);
        JPanel1.add(ServerChick);
        JPanel1.add(ClientChick);
        JPanel1.add(RandomPort);
        JPanel1.add(IPLabel);
        JPanel1.add(IPText);
        JPanel1.add(LocalIP);

        RandomPort.addActionListener(e -> {
            int RandomPort1 = (int) (Math.random() * 50000) + 15000;
            PortText.setText(String.valueOf(RandomPort1));
        });

        ServerChick.addActionListener(e -> {
            if (ServerChick.isSelected()) {
                SandAndGet.InfoSet.setSoftwaraType(true);
                IPText.setText("本模式IP将自动获取");
                IPText.setEnabled(false);
            }
        });
        ClientChick.addActionListener(e -> {
            if (ClientChick.isSelected()) {
                SandAndGet.InfoSet.setSoftwaraType(false);
                IPText.setEnabled(true);
                IPText.setText("");
            }
        });
        SaveButton.addActionListener(e -> {
            if (ServerChick.isSelected()) {//Server MODE
                CommonGUI.MessageBox("IP：自动协商" + "\n" + "Port:" + PortText.getText() + "\n" + "工作模式：服务器模式", "确认设置", 1);
                try {
                    SandAndGet.InfoSet.setSetPort(Integer.parseInt(PortText.getText()));
                    //SandAndGet.InfoSet.setSetIP(InetAddress.getByName(IPText.getText()));
                    SandAndGet.ServerPoint.Connect();
                    CommonFunctionValue.setModeFlag(true);
                    System.out.println("OK");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (ClientChick.isSelected()) {//Client MODE
                CommonGUI.MessageBox("IP:" + IPText.getText() + "\n" + "Port:" + PortText.getText() + "\n" + "工作模式：客户端模式", "确认设置", 1);
                try {
                    UDPClient.ServerInfo.setClientIP(InetAddress.getByName(IPText.getText()));
                    UDPClient.ServerInfo.setClientPort(Integer.parseInt(PortText.getText()));
                    SandAndGet.ClinetPoint.ClientPoint.ClientConnect();
                    CommonFunctionValue.setModeFlag(true);
                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } else {//Default MODE （NULL）
                CommonGUI.MessageBox("未选择工作模式，请勾选", "ERROE", 2);
            }
            ClosePanel(JFrame1);
        });
        LocalIP.addActionListener(e -> IPText.setText("127.0.0.1"));
    }
}

