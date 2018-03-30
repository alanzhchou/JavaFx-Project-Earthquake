package service;

import java.io.*;
import java.net.Socket;

/**
 * @author : Alan
 * @version : 1.0
 * @serial : 2018/3/28
 * @since : Java_8
 */
public class MyClient {
    private String serverAddress = "127.0.0.1";
    private int serverPort = 4567;

    public MyClient(){
        try {
            System.out.println("连接到主机：" + serverAddress + " ，端口号：" + serverPort);
            Socket client = new Socket(serverAddress, serverPort);
            System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            System.out.println("服务器响应： " + in.readUTF());
            client.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
