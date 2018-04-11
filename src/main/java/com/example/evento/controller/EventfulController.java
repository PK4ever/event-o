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

    public ArrayList<Event> getEvents(String location, String keyWord) {
        eventSearchRequest.setPageSize(8);
        eventSearchRequest.setKeywords(keyWord);
        eventSearchRequest.setLocation(location);
        eventSearchRequest.setLocationRadius(30);
        try {
            return (ArrayList<Event>) eventOperations.search(eventSearchRequest).getEvents();
        } catch (EVDBRuntimeException e) {
            e.printStackTrace();
            //TODO handle the error appropriately
        } catch (EVDBAPIException e) {
            e.printStackTrace();
            //TODO handle the error appropriately
        }
        //TODO refractor this
        return new ArrayList<>();
    }
}
