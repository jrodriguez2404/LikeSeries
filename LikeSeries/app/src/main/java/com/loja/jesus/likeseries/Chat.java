package com.loja.jesus.likeseries;

import java.util.ArrayList;

public class Chat {
    private ArrayList<ChatGeneral> chatgeneral;

    public Chat() {
    }

    public Chat(ArrayList<ChatGeneral> chatgeneral) {
        this.chatgeneral = chatgeneral;
    }

    public ArrayList<ChatGeneral> getChatgeneral() {
        return chatgeneral;
    }

    public void setChatgeneral(ArrayList<ChatGeneral> chatgeneral) {
        this.chatgeneral = chatgeneral;
    }
}
