$(document).ready(function() {

    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(success,fail);
    }

    $("#searchSubmit").click(function() {
        getEvents($("#location").val(),$("#keyword").val());
    });
});


function success(position) {
    $.getJSON(
        "https://fcc-weather-api.glitch.me/api/current?lat=" +
        position.coords.latitude +
        "&lon=" +
        position.coords.longitude,
        
        function(json) {
            // alert(position.coords.latitude + "  " + position.coords.longitude);

            var location = json.name+ " , "+ json.sys.country;
            // alert(json.name+ " , "+ json.sys.country);
            $("#location").html(location);
            $("#weatherDescription").html(json.weather[0].description);

        }
    );
    $.getJSON(
        "https://maps.googleapis.com/maps/api/geocode/json?" +
        "latlng=+"+position.coords.latitude+","+position.coords.longitude+"&key=AIzaSyDAdYEKAXvFad5gMEjpY12XLYPACrNuDt0",
        function (data) {
             console.log(data);

            var location = data.results[0].address_components[3].long_name+", "+data.results[0].address_components[5].short_name;
            getEvents(location,"" );
        });
}
function fail() {
    getEvents("Brooklyn, NY", "");
}

function getEvents(location, keyword) {
    $("#location").attr("placeholder", location);

    var oArgs = {
        app_key: "qX2dNH9TZGLvBT8B",
        q: keyword,
        where: location,
        // where: "syracuse",
        page_size: 10,
        sort_order: "popularity",
    }
    EVDB.API.call("/events/search", oArgs, function(oData) {
        // alert(oData.events.event.length);
        $("#event").empty();
        for(var x = 0; x < oData.events.event.length; x++){
            if(oData.events.event[x].image) {
                $("#event").append("<li><p>" + oData.events.event[x].title + "</p>" +
                    "<p>" + oData.events.event[x].venue_address + "</p>" +
                    "<p>" + oData.events.event[x].city_name +" , "+ oData.events.event[x].region_abbr+"</p>" +
                    "<p>" + oData.events.event[x].start_time + "</p>" +
                    "<img src='" + oData.events.event[x].image.medium.url + "'></li>");
            }else{
                $("#event").append("<li><p id='eventTitle'>" + oData.events.event[x].title + "</p>" +
                    "<p id='eventAddress'>" + oData.events.event[x].venue_address + "</p>" +
                    "</li>");
            }
        }
    });

}

var google;
function initialize() {
    var input = document.getElementById('location');
    new google.maps.places.Autocomplete(input);
}

google.maps.event.addDomListener(window, 'load', initialize);

// / LOGIN
function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    //get the id token and send to the backend
    var id_token = googleUser.getAuthResponse().id_token;
    console.log("ID_TOKEN"+ id_token)
    var xhr = new XMLHttpRequest();
    //TODO: CHANGE THIS WHEN DEPLOYING TO HEROKU TO THE SERVER URL
    // xhr.open('POST', 'https://yourbackend.example.com/tokensignin');
    xhr.open('POST', 'http://localhost:8000/tokensignin');
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function() {
        // console.log("HERE");
        console.log('Signed in as: ' + xhr.responseText);
    };
    xhr.send('idtoken=' + id_token);
}
//LOGOUT
function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
}