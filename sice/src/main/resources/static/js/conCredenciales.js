document.addEventListener("DOMContentLoaded", function() {
    var contrasenas = document.querySelectorAll(".contrasena");

    contrasenas.forEach(function(celda) {
      var textoContrasena = celda.textContent;
      celda.textContent = "*".repeat(textoContrasena.length); // Reemplaza la contraseña con asteriscos
    });
});

function irHacia(boton) {
  // Obtener el ID del botón y mostrarlo en la consola
  var idDelBoton = boton.id;
  // Redirigir a la página con el parámetro en la URL igual al ID del botón
  window.location.href = '/gestionarCredenciales?pagina=' + idDelBoton;
}