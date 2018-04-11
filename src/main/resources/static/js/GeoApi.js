$(document).ready(function() {
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(success);
    }
});

function success(position) {
    alert(position)
}
