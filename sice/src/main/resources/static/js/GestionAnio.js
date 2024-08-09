var tooltipTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="tooltip"]')
);
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});

document.addEventListener("DOMContentLoaded", function () {
    var agregarButton = document.querySelectorAll(".Agregar-btn");
    var confirmAgregarModal = new bootstrap.Modal(
        document.getElementById("agregarModal")
    );
    var confirmAgregarButton = document.getElementById("agregarModalButton");
    var currentHref = "";

    agregarButton.forEach(function (button) {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            //currentHref = button.getAttribute('href');
            confirmAgregarModal.show();
        });
    });
    confirmAgregarButton.addEventListener("click", function () {
        var form = document.getElementById("formAnio");
        if (!form.checkValidity()) {
            form.classList.add("was-validated");
        } else {
            form.submit();
        }
    });
});
document.addEventListener("DOMContentLoaded", function () {
    var consultarButton = document.querySelectorAll(".consultar-btn");
    var confirmConsultarModal = new bootstrap.Modal(document.getElementById("consultarModal"));
    var confirmConsultarModalButton = document.getElementById("consultarModalButton");    

    consultarButton.forEach(function (button) {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            //currentHref = button.getAttribute('href');
            confirmConsultarModal.show();
        });
    });

    confirmAgregarButton.addEventListener("click", function () {
        var form = document.getElementById("formAnio");
        if (!form.checkValidity()) {
            form.classList.add("was-validated");
        } else {
            form.submit();
        }
    });
});

(() => {
    "use strict";

    // Selecciona todos los formularios que tienen la clase "needs-validation"
    const forms = document.querySelectorAll(".needs-validation");
    Array.from(forms).forEach((form) => {
        // Añade un listener para el evento "submit" en cada formulario
        form.addEventListener(
            "submit",
            (event) => {
                // Verifica si el formulario es válido
                if (!form.checkValidity()) {
                    // Si no es válido, previene el envío del formulario
                    event.preventDefault();
                    event.stopPropagation();

                    // Encuentra el primer elemento inválido en el formulario
                    const firstInvalidElement = form.querySelector(":invalid");
                    if (firstInvalidElement) {
                        // Desplaza la vista al primer elemento inválido
                        firstInvalidElement.scrollIntoView({
                            behavior: "smooth",
                            block: "center",
                        });
                        // Opcionalmente, enfoca el primer elemento inválido
                        firstInvalidElement.focus();
                    }
                }

                // Añade la clase "was-validated" al formulario
                form.classList.add("was-validated");
            },
            false
        );
    });
})();

document
    .getElementById("cancelarAnio")
    .addEventListener("click", function () {
        var form = document.getElementById("formAnio");
        form.reset();
        form.classList.remove("was-validated"); // Eliminar la clase de validación
    });

    $(document).ready(function() {
        $('.editar-btn').on('click', function() {
            var idAnio = $(this).data('id');
            var estado = $(this).data('est');
            var anio = $(this).data('an');
                 
    
            $('#editIdAnio').val(idAnio);
            $('#editEstado').prop('checked', estado);
            $('#editAnio').val(anio);
            
           
        });
    });


   