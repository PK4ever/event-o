package com.example.evento.model;

import com.evdb.javaapi.data.Venue;

public class Event {
    private String title;
    private String id;
    private String address;

    public Event(String title, String id, String address) {
        this.title = title;
        this.id = id;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
