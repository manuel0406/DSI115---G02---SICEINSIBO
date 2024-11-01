var tooltipTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-t="tooltip"]')
);
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});

$(document).ready(function () {
    $('.matricular-btn').on('click', function () {
        var idAlumno = $(this).data('id');
        var nombreAlumno = $(this).data('non');
        var apellidoAlumno = $(this).data('ape');
        var nie = $(this).data('nie');
        var carrera = $(this).data('ca');
        var seccion = $(this).data('sec');
        var grado = $(this).data('gra');
        var codigo = $(this).data('cod');
        var estado = $(this).data('est');

        $('#idAlumno').val(idAlumno);
        $('#nombreAlumno').val(nombreAlumno);
        $('#apellidoAlumno').val(apellidoAlumno);
        $('#nie').val(nie);
        $('#carrera').val(carrera);
        $('#seccion').val(seccion);
        $('#grado').val(grado);
        $('#codigo').val(codigo);
        $('#estado').val(estado);

        // Crear el mensaje dinámico
        var mensaje = `El alumno ${nombreAlumno} ${apellidoAlumno} cursó el técnico vocacional en ${carrera}, sección: ${seccion}, grado: ${grado}.`;
        if (estado) {
            mensaje += " Puede cursar el siguiente año de la misma carrera.";
        } else {
            mensaje += " Tendrá que cursar el mismo grado nuevamente.";
        }

        // Mostrar el mensaje en el modal
        $('#alumnoMensaje').text(mensaje);

    });
});