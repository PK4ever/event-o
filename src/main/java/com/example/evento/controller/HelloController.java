package com.example.evento.controller;

import com.evdb.javaapi.data.Event;
import com.evdb.javaapi.data.Image;
import com.evdb.javaapi.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.evdb.javaapi.*;

@Controller
public class HelloController {
    EventfulController eventfulController = new EventfulController();

    HelloController(){
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
//        ArrayList<Event> eventList = new ArrayList<>();
//        for (com.evdb.javaapi.data.Event event: eventfulController.getEvents()){
//            eventList.add(new Event(event.getTitle(), event.getSeid(), event.getVenueAddress()));
//        }
//        model.addAttribute("eventList", eventList);
        
        model.addAttribute("eventList", eventfulController.getEvents());
        return "events";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
