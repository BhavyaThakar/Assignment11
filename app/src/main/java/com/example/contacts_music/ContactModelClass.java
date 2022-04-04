package com.example.contacts_music;

public class ContactModelClass {
    String name, number;

    public ContactModelClass(String name, String number) {
        this.name = name;
        this.number = number;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}