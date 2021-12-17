var app = {

    init: function () {
        // PRINCIPAL
        document.getElementById('botoinici').addEventListener('click', app.Entrar);

        document.getElementById('user').addEventListener('submit', app.validarFormulario);
    },

    validarFormulario(evObject) {

        evObject.preventDefault();
        Entrar();

    },

    Entrar: function () {

        document.location.href = "aplicacio.html";

    },

};

document.addEventListener("deviceready", app.init());