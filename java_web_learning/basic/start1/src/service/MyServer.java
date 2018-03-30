package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author : Alan
 * @version : 1.0
 * @serial : 2018/3/28
 * @since : Java_8
 */
public class MyServer extends Thread {
    private int port;
    private ServerSocket serverSocket;

    public MyServer(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(this.port);
//        serverSocket.setSoTimeout(5 * 1000);
        System.out.println(new SimpleDateFormat("hh:mm:ss E 'at' yyyy.MM.dd a zzz").format(new Date()));
        System.out.print("Server create succeed! work on: ");
        System.out.println(getLocalHostLANAddress().getHostAddress() + " : " + port);
    }

    private String inetType(InetAddress inetAddress){
        if (inetAddress.isAnyLocalAddress()){
            return "AnyLocalAddress";
        }else if (inetAddress.isLoopbackAddress()){
            return  "LoopbackAddress";
        }else if (inetAddress.isLinkLocalAddress()){
            return  "LinkLocalAddress";
        }else if (inetAddress.isSiteLocalAddress()){
            return  "SiteLocalAddress";
        }else if (inetAddress.isMulticastAddress()){
            return  "MulticastAddress";
        }else {
            return "Unknown";
        }
    }

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException{
        try {
            Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()){
                NetworkInterface networkInterface = (NetworkInterface)interfaces.nextElement();

                String interfaceName = networkInterface.toString();
                boolean Virtual = interfaceName.indexOf("Virtual") > 0 &&
                        (interfaceName.indexOf("Wireless") > 0 || interfaceName.indexOf("eth") > 0);

                if (!Virtual){
                    Enumeration inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()){
                        InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                        if (inetAddress.isSiteLocalAddress() && inetAddress.getHostAddress().indexOf(':') < 0){
                            return inetAddress;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InetAddress.getLocalHost();
    }

    public void run() {
        while(true)
        {
            try {
                Socket server = serverSocket.accept();
                System.out.println("远程主机地址：" + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("谢谢连接我：" + server.getLocalSocketAddress() + "\nGoodbye!");
                server.close();
            }catch(SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
