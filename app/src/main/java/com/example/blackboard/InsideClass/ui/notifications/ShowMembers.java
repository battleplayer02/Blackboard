package com.example.blackboard.InsideClass.ui.notifications;
import java.io.Serializable;
public class ShowMembers implements Serializable{

    String email;
    String teacher;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
