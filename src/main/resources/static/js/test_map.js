

function initMap() {
    var obj = JSON.parse($("#location").val())

    var myLatLng = {lat: obj.latitude, lng: obj.longtitude};
    var myLatLng3 = {lat: obj.latitude + 0.1, lng: obj.longtitude - 0.1};
    var mypoint2 = {lat: obj.latitude + 0.2, lng: obj.longtitude - 0.2};
    var jsonBom = $('#jsonBom').val();
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        center: myLatLng
    });

    var marker = new google.maps.Marker({
        position: myLatLng,
        map: map,
        title: 'Location1'
    });
    var marker2 = new google.maps.Marker({
        position: mypoint2,
        map: map,
        title: 'Location2'


        });
    var marker3 = new google.maps.Marker({
        position: myLatLng3,
        map: map,
        title: 'Location1'
    });

}