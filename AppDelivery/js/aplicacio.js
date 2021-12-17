window.addEventListener("load", inici, false);


function inici() {

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            lexturaXML(this);
        }
    };
    xhttp.open("GET", "xml/2021-11-30.xml", true);
    xhttp.send();

}

function lexturaXML(xml) {

    var xmlDoc = xml.responseXML;

    for (var i = 0; i < xmlDoc.getElementsByTagName("comanda").length; i++) {

        var IDPaquet = xmlDoc.getElementsByTagName("IDPaquet")[i].childNodes[0].nodeValue;

        var DNIClient = xmlDoc.getElementsByTagName("DNIClient")[i].childNodes[0].nodeValue;

        var DNIRep = xmlDoc.getElementsByTagName("DNIRep")[i].childNodes[0].nodeValue;

        var direccio = xmlDoc.getElementsByTagName("direccio")[i].childNodes[0].nodeValue;

        // var latitud = xmlDoc.getElementsByTagName("latitud")[i].childNodes[0].nodeValue;

        // var longitud = xmlDoc.getElementsByTagName("longitud")[i].childNodes[0].nodeValue;

        var pes = xmlDoc.getElementsByTagName("pes")[i].childNodes[0].nodeValue;

        var codip = xmlDoc.getElementsByTagName("codip")[i].childNodes[0].nodeValue;

        var estat = xmlDoc.getElementsByTagName("estat")[i].childNodes[0].nodeValue;

        var dataEnt = xmlDoc.getElementsByTagName("dataEnt")[i].childNodes[0].nodeValue;

        if (!!xmlDoc.getElementsByTagName("estat")[i]) {
            document.getElementsByTagName("section")[0].innerHTML +=
                "<ul>" +
                "<li style='font-family: verdana;'><strong><u>" + IDPaquet + "</u></strong></li>" +
                "<li style='font-family: verdana;'> <strong>Pes:</strong> " + pes + "</li>" +
                "<li style='font-family: verdana;'> <strong>Adreça:</strong> " + direccio + "</li>" +
                "<li style='font-family: verdana;'> <strong>DNI Client:</strong> " + DNIClient + "</li>" +
                "<li style='font-family: verdana;'> <strong>DNI Repartidor:</strong> " + DNIRep + "</li>" +
                "<li style='font-family: verdana;'> <strong>Estat Paquet:</strong> " + estat + "</li>" +
                // "<li style='font-family: verdana;'> <strong>Latitud:</strong> " + latitud + "</li>" +
                // "<li style='font-family: verdana;'> <strong>Longitud:</strong> " + longitud + "</li>" +
                "<li style='font-family: verdana;'> <strong>Codi Postal:</strong> " + codip + "</li>" +
                "<li style='font-family: verdana;'> <strong>Data entrega:</strong> " + dataEnt + "</li>" +
                "</ul> <br>";
        }


    }
}
