package com.example.evento.controller;


import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.example.evento.model.Event_Object;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Event_oController {
    EventfulController eventfulController = new EventfulController();

    Event_oController(){
        eventfulController.connectToEventFul();
    }

    @GetMapping("/java")
    public String home(@RequestParam(name="location", required=false, defaultValue="") String location, @RequestParam(name="keyword", required=false, defaultValue="") String keyWord, Model model){
        List<Event_Object> eventList = getEventList(location, keyWord);


        model.addAttribute("location", location);
        model.addAttribute("keyword", keyWord);
        model.addAttribute("eventList", eventList);
        return "publicHomePage1";
    }

    @RequestMapping("/tokensignin")
    public String tokenSignIn(HttpServletRequest request, RedirectAttributes attributes) throws GeneralSecurityException, IOException {
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport transport = new NetHttpTransport();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("86079661113-c1o8glmf5hv8fb7pe10n218e5puusvbl.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

// (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(request.getParameter("idtoken"));
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

            return "redirect:/userHome";
        } else {
            System.out.println("Invalid ID token.");
            return "not autorized";
        }
    }

    @RequestMapping("/userHome")
    public String userHome(){
        return "home";
    }

    @GetMapping("/")
    public String  home(){

        return "publicHomePage";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    public List<Event_Object> getEventList(String location, String keyWord){
        List<Event_Object> eventList = new ArrayList<>();
        Document document = null;
        Event_Object eventObject = null;

        for (Event event: eventfulController.getEvents(location, keyWord)){
            eventList = getEventImagesFromUrl(document, eventObject,event, eventList);
        }

        return eventList;
    }

    public List<Event_Object> getEventImagesFromUrl(Document document, Event_Object eventObject, Event event, List<Event_Object> eventList){
        try {
            document = Jsoup.connect(event.getURL()).get();

        Elements doc = document.getElementsByClass("image-viewer");
        Elements images = doc.tagName("img");
        eventObject = new Event_Object(event.getTitle(),event.getVenueAddress(), event.getStartTime().toString(), event.getImages());
        eventList.add(eventObject);
        for (Element e: images){
            for (Element img: e.getElementsByTag("img")){
                if (img.attr("alt").equals(event.getTitle().replaceAll("'","&#39;")+" Photo #1")) {
                    eventObject.setDefaultImage(img.attr("src"));
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO send connection error message via response
        }
        return eventList;
    }



//    @GetMapping("/greeting")
//    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }


//    @RequestMapping("/controller")
//    @ResponseBody
//    public String helloWorld() {
//        return " This is a new controller ";
//    }

//    @RequestMapping("/")
//    @ResponseBody
//    String home() {
//
//        return " the begining of event-o ";
//    }

}
