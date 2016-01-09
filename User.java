/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localchatwithsockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author daniel
 */
public class User implements Runnable {
    
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean inServer;
    private Server server;
    
    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(this.socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(
                this.socket.getInputStream()));                
        this.inServer = false;
    }
    
    public User(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(this.socket.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(
                this.socket.getInputStream()));
        this.server = server;
        this.inServer = true;
    }

    public void sendMessage(String text) {
        this.getOut().println(text);
        this.getOut().flush();
    }
    
    

    @Override
    public void run() {
        try {
            System.out.println("user started running.");            
            String line;
            while(true) {                
                if((line = in.readLine()) != null) {
                    if(!isInServer())
                        System.out.println(line);
                    else
                        this.server.sendAllMessage(line);
                }
            }
        } catch (IOException ex) {
            System.out.println("failed to make a connection");            
        }
    }
    
    
    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }
        
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isInServer() {
        return inServer;
    }

    public void setInServer(boolean inServer) {
        this.inServer = inServer;
    }
    
}
