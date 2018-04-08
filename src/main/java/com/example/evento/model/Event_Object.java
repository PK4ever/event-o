package com.example.evento.model;

import com.evdb.javaapi.data.Image;

import java.util.List;

public class Event_Object {
    private String title;
    private String venueAddress;
    private String startTime;
    private List<Image> images;
    private String defaultImage;

    public Event_Object(String title, String venueAddress, String startTime, List<Image> images) {
        this.title = title;
        this.venueAddress = venueAddress;
        this.startTime = startTime;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }
}
