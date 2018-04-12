$(document).ready(function() {
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(success);
    }
    show_alert();
});

function success(position) {
    $.getJSON(
        "https://fcc-weather-api.glitch.me/api/current?lat=" +
        position.coords.latitude +
        "&lon=" +
        position.coords.longitude,

        function(json) {
            $("#location").html(json.name + " , " + json.sys.country);
            $("#weatherDescription").html(json.weather[0].description);

        }
    );
    

}
function show_alert() {
    var oArgs = {
        app_key: "qX2dNH9TZGLvBT8B",
        q: "music",
        where: "syracuse",
        page_size: 10,
        sort_order: "popularity",
    }
    EVDB.API.call("/events/search", oArgs, function(oData) {
        // alert(oData.events.event.length);
        for(var x = 0; x < oData.events.event.length; x++){

            if(oData.events.event[x].image) {
                $("#title").append("<li><p>" + oData.events.event[x].title + "</p>" +
                    "<p>" + oData.events.event[x].venue_address + "</p>" +
                    "<img src='" + oData.events.event[x].image.medium.url + "'></li>");
            }else{
                $("#title").append("<li><p>" + oData.events.event[x].title + "</p>" +
                    "<p>" + oData.events.event[x].venue_address + "</p>" +
                    "</li>");
            }

        }

        // $.getJSON(
        //     oData.toJSON(),
        //     function(json) {
        //         alert("here");
        //     }
        // );

        // Note: this relies on the custom toString() methods below
    });

}