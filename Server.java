/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localchatwithsockets;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author daniel
 */
public class Server implements Runnable{

    private List<User> users;
    private List<String> messages;
    private int port;
    private ServerSocket socket;    
    private boolean run;
    private List<Thread> threads;
    
    public Server(int port) throws IOException { //a simple server
        this.port = port;
        this.messages = new ArrayList();
        this.users = new ArrayList();
        this.socket = new ServerSocket(port);
        this.threads = new ArrayList();
        this.run = true;
    }
    
    public void sendAllMessage(String message) {    //sends a message to everyone
        messages.add(message);
        this.getUsers().stream().forEach((user) -> {
            user.sendMessage(message);
        });
    }
    public void stop() throws IOException {
        this.run = false;
        this.threads.stream().forEach((thread) -> {
            thread.stop();
        });
        this.messages.clear();
        this.users.clear();
        this.threads.clear();
        this.socket.close();        
        System.out.println("server stopped.");
    }
    @Override
    public void run() {
        while(run) {
            try {
                Socket userSocket = socket.accept();               
                User user = new User(userSocket, this);
                Thread u = new Thread(user);
                u.start();
                this.users.add(user);
                this.threads.add(u);
                user.sendMessage("welcome to the server.");
                this.messages.stream().forEach((message) -> {
                    user.sendMessage(message);
                });

                
            } catch (IOException ex) {
                System.out.println("failed to connect user");
            }
            
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getSocket() {
        return socket;
    }

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }
    
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("what port to run on? (pick a four-digit number default is 3000");
            int port = Integer.parseInt(in.nextLine());
            System.out.println("trying to start server...");
            Server server = new Server(port);
            Thread s = new Thread(server);
            s.start();
            System.out.println("Running server on: " + Inet4Address.getLocalHost().getHostAddress());
            System.out.println("type \"quit\" to quit.");
            String line;
            boolean run = true;
            while(run) {
                if(((line = in.nextLine()) != null)) {
                    if(line.equalsIgnoreCase("quit")) {
                        server.stop();
                        s.stop();
                        System.out.println("ready to quit");
                        run = false;
                    }
                }
            }
            
        } catch (IOException ex) {
            System.out.println("couldn't make the server.");
        }
    }
    
}
