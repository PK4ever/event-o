package com.example.evento.controller;

import com.evdb.javaapi.APIConfiguration;
import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.Location;
import com.evdb.javaapi.data.request.EventSearchRequest;
import com.evdb.javaapi.operations.EventOperations;

import java.util.ArrayList;

public class EventfulController {
    final private String API_KEY = "qX2dNH9TZGLvBT8B";
    final private String USER_NAME = "events420";
    final private String PASSWORD = "pass55word";

    private EventOperations eventOperations;
    private EventSearchRequest eventSearchRequest;

    public void connectToEventFul(){
        APIConfiguration apiConfiguration = new APIConfiguration();
        apiConfiguration.setApiKey(API_KEY);
        eventOperations = new EventOperations();
        eventSearchRequest = new EventSearchRequest();

        apiConfiguration.setEvdbUser(USER_NAME);
        apiConfiguration.setEvdbPassword(PASSWORD);
    }

    public ArrayList<Event> getEvents() throws EVDBRuntimeException, EVDBAPIException {
        eventSearchRequest.setPageSize(50);
//        eventSearchRequest.setKeywords("all");
        eventSearchRequest.setLocation("Syracuse, NY");
        return (ArrayList<Event>) eventOperations.search(eventSearchRequest).getEvents();
    }
}
