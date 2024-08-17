// Añadir los parámetros carrera, grado y seccion al formulario antes de enviarlo para actualizar la vista usando el mismo contexto
const form = document.getElementById('formNuevaHora');
form.addEventListener('submit', function (event) {

    const carreraInput = document.createElement('input');
    carreraInput.type = 'hidden';
    carreraInput.name = 'carrera';
    carreraInput.value = document.getElementById('carrera').value;

    const gradoInput = document.createElement('input');
    gradoInput.type = 'hidden';
    gradoInput.name = 'grado';
    gradoInput.value = document.getElementById('grado').value;

    const seccionInputHidden = document.createElement('input');
    seccionInputHidden.type = 'hidden';
    seccionInputHidden.name = 'seccion';
    seccionInputHidden.value = document.getElementById('seccion').value;

    form.appendChild(carreraInput);
    form.appendChild(gradoInput);
    form.appendChild(seccionInputHidden);
});

// Añade al modal los valores necesarios para el envío y referencia del usuario
document.addEventListener("DOMContentLoaded", function () {
    const editButtons = document.querySelectorAll(".editar-btn");

    editButtons.forEach(button => {
        button.addEventListener("click", function () {
            const idAsignacionHorario = this.getAttribute("data-id");
            const horarioBase = this.getAttribute("data-horario");
            const intervalo = this.getAttribute("data-intervalo");
            const docente = this.getAttribute("data-docente");
            const hora = this.getAttribute("data-hora");
            const dia = this.getAttribute("data-dia");

            document.getElementById("idAsignacionHorario").value = idAsignacionHorario;
            document.getElementById("horaSeleccionada").value = horarioBase;
            document.getElementById("intervaloHora").value = intervalo;
            document.getElementById("asignacionSeleccionada").value = docente;
            document.getElementById("horaBase").value = hora;
            document.getElementById("dia").value = dia;
        });
    });
});