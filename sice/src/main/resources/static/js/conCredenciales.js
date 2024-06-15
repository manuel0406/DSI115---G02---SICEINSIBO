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

function irHacia(boton) {
  // Obtener el ID del botón y mostrarlo en la consola
  var idDelBoton = boton.textContent;
  // Redirigir a la página con el parámetro en la URL igual al ID del botón
  window.location.href = '/gestionarCredenciales?pagina=' + idDelBoton;
}

document.getElementById('buscarForm').addEventListener('submit', function(event) {
  event.preventDefault(); // Evitar el envío del formulario por defecto

  // Obtener el valor del campo de entrada y quitar espacios en blanco al inicio y al final
  var correo = document.getElementById('buscar').value.trim();

  // Validar si el campo está vacío
  if (correo === '') {
    alert('El campo de búsqueda está vacío. Por favor, ingresa un correo.');
    return; // Detener la ejecución si el campo está vacío
  }

  window.location.href = '/buscarUsuarioCredencial?correoUsuario=' + correo;

});


function closeAlert() {
  var alertDiv = document.getElementById('errorAlert');
  alertDiv.style.display = 'none';
}