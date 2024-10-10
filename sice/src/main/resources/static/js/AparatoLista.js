// Validación de formularios
(function () {
  "use strict";

  var forms = document.querySelectorAll(".needs-validation");

  Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener(
      "submit",
      function (event) {
        if (!form.checkValidity()) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add("was-validated");
      },
      false
    );
  });

  // Función para limpiar el formulario
  function limpiarFormulario(formId) {
    var form = document.getElementById(formId);
    form.reset(); // Resetea el formulario
    form.classList.remove("was-validated"); // Remueve las clases de validación
  }

  // Mapa de botones y formularios
  const formularios = {
    btnCancelarDocente: "formDocente",
    cancelarDocente: "formDocente",
    btnCancelarAdministrativo: "formAdministrativo",
    cancelarAdministrativo: "formAdministrativo",
  };

  // Agregar event listeners para los botones de cancelar
  for (const [buttonId, formId] of Object.entries(formularios)) {
    document.getElementById(buttonId).addEventListener("click", function () {
      limpiarFormulario(formId);
    });
  }
})();

$(document).ready(function() {
  $('.editar-btn-docente').on('click', function() {
    var docenteNombre = $(this).data('docenteActual');
    var docenteId = $(this).data('docenteDui');
    var numeroAparatoDocente = $(this).data('numeroAparatoDocente');
    var idAparatoDocente = $(this).data('idAparatoDocente');


     console.log("idDocente: " + docenteId);
     console.log("docenteNombre: " + docenteNombre);
     console.log("numeroAparatoDocente: " + numeroAparatoDocente);
     console.log("idAparatoDocente: " + idAparatoDocente);


     $('#numAparatoDocente').val(numeroAparatoDocente);
     $('#docenteAsignado').val(docenteId);
     $('#idAparatoDocente').val(idAparatoDocente);
  });
});
$(document).ready(function() {
  $('.editar-btn-personal').on('click', function() {
    var personalNombre = $(this).data('personalActual');
    var personalId = $(this).data('personalDui');
    var numeroAparatoPersonal = $(this).data('numeroAparatoPersonal');
    var idAparatoPersonal = $(this).data('idAparatoPersonal');


     console.log("personalId: " + personalId);
     console.log("personalNombre: " + personalNombre);
     console.log("numeroAparatoPersonal: " + numeroAparatoPersonal);
     console.log("idAparatoPersonal: " + idAparatoPersonal);


     $('#numAparatoPersonal').val(numeroAparatoPersonal);
     $('#personalAsignado').val(personalId);
     $('#idAparatoPersonal').val(idAparatoPersonal);
  });
});