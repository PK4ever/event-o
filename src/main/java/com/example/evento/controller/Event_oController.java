package com.example.evento.controller;


import com.evdb.javaapi.EVDBAPIException;
import com.evdb.javaapi.EVDBRuntimeException;
import com.evdb.javaapi.data.Event;
import com.example.evento.model.Event_Object;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

@Controller
public class Event_oController {
    EventfulController eventfulController = new EventfulController();

    Event_oController(){
        eventfulController.connectToEventFul();
    }

    @GetMapping("/")
    public String home(@RequestParam(name="location", required=false, defaultValue="") String location, @RequestParam(name="keyword", required=false, defaultValue="") String keyWord, Model model){
        List<Event_Object> eventList = getEventList(location, keyWord);


        model.addAttribute("location", location);
        model.addAttribute("keyword", keyWord);
        model.addAttribute("eventList", eventList);
        return "publicHomePage";
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
