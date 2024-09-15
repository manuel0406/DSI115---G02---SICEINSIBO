document.addEventListener("DOMContentLoaded", function () {
    var agregarButton = document.querySelectorAll(".alumnno-btn");
    var confirmAgregarModal = new bootstrap.Modal(
        document.getElementById("AlumnoModal")
    );

    agregarButton.forEach(function (button) {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            confirmAgregarModal.show();
        });
    });

});

document.addEventListener("DOMContentLoaded", function () {
    var agregarButton = document.querySelectorAll(".notas-btn");
    var confirmAgregarModal = new bootstrap.Modal(
        document.getElementById("notasModal")
    );

    agregarButton.forEach(function (button) {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            confirmAgregarModal.show();
        });
    });

});