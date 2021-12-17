//window.addEventListener("load", obrirmapa, false)

//function obrirmapa() {
//  init();
//}

//mapa = null;

// var app = {


function init() {
    navigator.geolocation.getCurrentPosition(onSuccess);
}

function onSuccess(position) {

    var latitud = position.coords.latitude;
    var longitud = position.coords.longitude;
    var altitud = position.coords.altitude;

    mapa = L.map('mapid').setView([latitud, longitud, altitud], 13);

    inici();

    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 18,
        attribution: '<a href="https://www.openstreetmap.org/copyright"></a>',
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1
    }).addTo(mapa);

    var popup = L.popup();

}

function inici() {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            lecturaXML(this);
        }
    };
    xhttp.open("GET", "\\192.168.1.35\\xml", true);
    xhttp.send();

}

function lecturaXML(xml) {

    var xmlDoc = xml.responseXML;

    var redIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    var greenIcon = new L.Icon({
        iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
        shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
        iconSize: [25, 41],
        iconAnchor: [12, 41],
        popupAnchor: [1, -34],
        shadowSize: [41, 41]
    });

    for (var i = 0; i < xmlDoc.getElementsByTagName("comanda").length; i++) {
        latitudA = xmlDoc.getElementsByTagName("latitud")[i].childNodes[0].nodeValue;
        longitudA = xmlDoc.getElementsByTagName("longitud")[i].childNodes[0].nodeValue;
        IDPaquet = xmlDoc.getElementsByTagName("IDPaquet")[i].childNodes[0].nodeValue;
        direccio = xmlDoc.getElementsByTagName("direccio")[i].childNodes[0].nodeValue;
        var estat = xmlDoc.getElementsByTagName("estat")[i].childNodes[0].nodeValue;
        
        if (xmlDoc.getElementsByTagName("estat")[i].childNodes[0].nodeValue == 'No entregat') {
            L.marker([latitudA, longitudA], { icon: redIcon }, 13).addTo(mapa).bindPopup(
                '<img src="" alt="foto" id="foto">' +
                '<div>' +
                '<p id="infop"><strong>Direcció:</strong> ' + IDPaquet + '</p>' +
                '<p id="infop"><strong>Nº paquet:</strong> ' + direccio + '</p>' +
                '</div>' +
                '<div id="opcions">' +
                '<input type="button" value="Captura" id="boto1" onclick="takephoto()">' +
                '<input type="button" value="Entregat" id="boto2" onclick="modixml()">' +
                '</div>').openPopup();
        }
        else {
            L.marker([latitudA, longitudA], { icon: greenIcon }, 13).addTo(mapa).bindPopup(
                '<img src="" alt="foto" id="foto">' +
                '<div>' +
                '<p id="infop"><strong>Direcció:</strong> ' + IDPaquet + '</p>' +
                '<p id="infop"><strong>Nº paquet:</strong> ' + direccio + '</p>' +
                '</div>' +
                '<div id="opcions">' +
                '<input type="button" value="Captura" id="boto1" onclick="takephoto()">' +
                '<input type="button" value="Entregat" id="boto2" onclick="modixml()">' +
                '</div>').openPopup();
        }


    }
    //document.getElementById("boto2").addEventListener("click", modixml())

    // function modixml(){
        
    //     modixml();
    // }

}

function modixml() {
    console.log(estat);
    debugger;
    estat.nodeValue = "Entregat";
    console.log(estat);
}


function takephoto() {
    navigator.camera.getPicture(cameraSuccess, cameraError, {
        quality: 100,
        destinationType: Camera.DestinationType.DATA_URL,
        correctOrientation: true
    })
}

function cameraSuccess(imageURI) {
    document.getElementById('foto').src = imageURI;
    document.getElementById('foto').style.display = "block";
}

function cameraError(message) {
    alert('Failed because: ' + message);
}

// };

document.addEventListener("deviceready", init());