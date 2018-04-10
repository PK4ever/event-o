package com.example.evento.controller;

import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.Image;
import com.evdb.javaapi.data.User;
import com.example.evento.model.Event_Object;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.evdb.javaapi.*;

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

    @RequestMapping("/controller")
    @ResponseBody
    public String helloWorld() {
        return " This is a new controller ";
    }

//    @RequestMapping("/")
//    @ResponseBody
//    String home() {
//
//        return " the begining of event-o ";
//    }

    @GetMapping("/")
    String events(Model model) throws EVDBRuntimeException, EVDBAPIException {
        List<Event_Object> eventList = new ArrayList<>();
        Document document;
        Event_Object eventObject;
        try {

            int count = 1;

            for (Event event: eventfulController.getEvents()){
                document = Jsoup.connect(event.getURL()).get();
                Elements doc = document.getElementsByClass("image-viewer");
                Elements images = doc.tagName("img");
                eventObject = new Event_Object(event.getTitle(),event.getVenueAddress(), event.getStartTime().toString(), event.getImages());
                eventList.add(eventObject);
//                System.out.println(event.getPrice());
                for (Element e: images){
//                System.out.println(e.getElementsByTag("img"));
                    for (Element img: e.getElementsByTag("img")){
                        if (img.attr("alt").equals(event.getTitle().replaceAll("'","&#39;")+" Photo #1")) {
                            eventObject.setDefaultImage(img.attr("src"));
                            count++;
                        }
                    }
                }

            }
//            Elements elements = document.getElementsByTag("img");

        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("eventList", eventList);
        return "events";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
