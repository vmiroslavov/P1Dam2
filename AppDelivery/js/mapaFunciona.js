window.addEventListener("load" , obrirmapa , false)

function obrirmapa(){
    app.init();
}

var app = {

    init: function () {
        navigator.geolocation.getCurrentPosition(app.onSuccess);
    },

    onSuccess: function (position) {

        var latitud = position.coords.latitude;
        var longitud = position.coords.longitude;
        var altitud = position.coords.altitude;

        var latitud2 = 41.7814002;
        var longitud2 = 1.2845388;
        var latitud3 = 41.670825;
        var longitud3 = 1.270178;
        
        var mapa = L.map('mapid').setView([latitud, longitud, altitud], 13);
        

        L.marker([latitud, longitud], 13).addTo(mapa).bindPopup("<p>Posició Actual</p>").openPopup();
        L.marker([latitud2, longitud2], 13).addTo(mapa).bindPopup("<p>Empresa</p><img src='img/logo petit.jpg' width='150px'/>").openPopup();
        L.marker([latitud3, longitud3], 13).addTo(mapa).bindPopup("<p>Entrega</p>").openPopup();

        L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
            maxZoom: 18,
            attribution: '<a href="https://www.openstreetmap.org/copyright"></a>',
            id: 'mapbox/streets-v11',
            tileSize: 512,
            zoomOffset: -1
        }).addTo(mapa);

        var popup = L.popup();

    },

};

document.addEventListener("deviceready", app.init());