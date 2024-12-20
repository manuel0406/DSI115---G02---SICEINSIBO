function irHacia(boton) {
  // Obtener el ID del botón y mostrarlo en la consola
  var idDelBoton = boton.textContent;
  // Redirigir a la página con el parámetro en la URL igual al ID del botón
  window.location.href = '/gestionarRechazados?pagina=' + idDelBoton;
}

document.addEventListener("DOMContentLoaded", function() {
  const urlParams = new URLSearchParams(window.location.search);
  let pagina = urlParams.get('pagina');

  if (pagina === null) {
    pagina = "1";
  }
  
  pagina = "pagina" + pagina;

  const button = document.getElementById(pagina);
  if (button) {
    button.classList.remove('btn-outline-dark');
    button.classList.add('btn-primary');
    button.classList.add('btnPaginado');
  }
});

document.getElementById('buscarForm').addEventListener('submit', function(event) {
  event.preventDefault(); // Evitar el envío del formulario por defecto

  // Obtener el valor del campo de entrada y quitar espacios en blanco al inicio y al final
  var correo = document.getElementById('buscar').value.trim();
  var sinCorreo = document.getElementById('errorCorreo');

  // Validar si el campo está vacío
  if (correo === '') {
    sinCorreo.style.display = 'block';
    return; // Detener la ejecución si el campo está vacío
  }
  window.location.href = '/buscarUsuariosRechazados?correoUsuario=' + correo;

});
  
function closeAlert() {
  var alertDiv = document.getElementById('errorAlert');
  alertDiv.style.display = 'none';
}

function closeCorreo() {
  var alertDiv = document.getElementById('errorCorreo');
  alertDiv.style.display = 'none';
}

document.addEventListener("DOMContentLoaded", function() {
  var AcceptButtons = document.querySelectorAll('.btn-accept');
  var confirmAcceptModal = new bootstrap.Modal(document.getElementById('confirmAcceptModal'));
  var confirmAcceptButton = document.getElementById('confirmAcceptButton');
  var modalBody = document.querySelector('.body-modal-accept');
  var currentHref = '';

  AcceptButtons.forEach(function(button) {
      button.addEventListener('click', function(event) {
          event.preventDefault(); // Evitar el comportamiento por defecto del botón
      
          // Obtener el atributo href del botón actual (si es necesario)
          currentHref = button.getAttribute('href');
      
          // Obtener la fila (tr) padre del botón actual
          var row = button.closest('tr');
          
          // Obtener el contenido de la columna 2 (índice 1 en base 0)
          var correo = row.cells[1].textContent.trim();
          
          // Modificar el texto del modal con el correo obtenido
          modalBody.textContent = "¿Deseas aceptar al usuario " + correo + "?";
      
          // Mostrar el modal de confirmación
          confirmAcceptModal.show();
      });
  });

  confirmAcceptButton.addEventListener('click', function() {
    Swal.fire({
      title: "¡Procesado creación de credenciales!",
      html: "Se está procesando la petición de aceptación de usuario",
      allowOutsideClick: false, // Evita que se cierre al hacer clic fuera de la alerta
      didOpen: () => {
        Swal.showLoading(); // Muestra el indicador de carga
      }
    });
    window.location.href = currentHref;
  });
});


// Usuario que no se pueden desbloquear
document.querySelectorAll(".btn-rechazado-inactivo").forEach(function (element) {
  element.addEventListener("click", function () {
    Swal.fire({
      position: "center",
      icon: "error",
      title: "Oops",
      text: "Este usuario no puede ser aceptado, informar a soporte.",
      showConfirmButton: false,
      timer: 5000,
    });
  });
});

