/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localchatwithsockets;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author daniel
 */
public class RunUser {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter username:");
            String username = in.nextLine();
            System.out.println("Enter Server's ip:");
            String ip = in.nextLine();
            System.out.println("Enter Server's port:");
            int port = Integer.parseInt(in.nextLine());
            Socket socket = new Socket(ip, port);
            User user = new User(socket);
            Thread t = new Thread(user);
            t.start();
            boolean listen = true;
            String line;
            System.out.println("connecting to server, type quit to quit.");
            while(listen) {
                if((line = in.nextLine()) != null) {
                    if(line.equalsIgnoreCase("quit")) {
                        listen = false;
                        user.sendMessage("User " + username + " left the chat.");
                        t.stop();
                        System.out.println("ready to quit");
                    } else {
                        LocalDateTime time = LocalDateTime.now();
                        Message msg = new Message(time, line, username);
                        user.sendMessage(msg.toString());                        
                    }   
                }
            }
        } catch (IOException ex) {
            System.out.println("failed to make a connection.");
        }
    }
}
