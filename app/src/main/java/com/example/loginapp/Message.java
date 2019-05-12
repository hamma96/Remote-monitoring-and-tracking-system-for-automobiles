package com.example.loginapp;

public class Message {
    // cette classe est utile pour linsertion dans la base car on peut inserer que des instances des classes
    //dans cette classe je peux ajouter un attribut qui reference le temps de la reception de la notification
    String msg;

    public Message(){}
    public Message(String m){
        this.msg=m;
    }

    public String getMsg() {
        return msg;
    }
}
