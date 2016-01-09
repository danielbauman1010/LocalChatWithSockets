/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localchatwithsockets;


import java.time.LocalDateTime;

/**
 *
 * @author daniel
 */
public class Message {
    private LocalDateTime time;
    private String content;
    private String username;

    public Message(LocalDateTime time, String content, String username) {
        this.time = time;
        this.content = content;
        this.username = username;
    }

    @Override
    public String toString() {
        return this.username + ":\n" + this.content +"\nat\t" + formatTime(time);
    }
    
    public String formatTime(LocalDateTime time) {
        return time.getHour() + ":" + time.getMinute() + "\t" + time.getDayOfMonth() + "/" +
                time.getMonthValue() + "/" + time.getYear();
    }
    
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    
}
