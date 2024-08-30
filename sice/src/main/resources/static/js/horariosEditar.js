// Añade los parámetros necesarios al enviar el form, estos datos son necesario en el controller 'guardarHora'
const form = document.getElementById('formNuevaHora');
form.addEventListener('submit', function (event) {

    // Crea un campo oculto para enviar 'carrera' 'grado' y 'seccion'
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

    // Obtiene el 'select' de ID 'asignacionSeleccionada' y el valor 'data-docente' de la opción seleccionada
    const asignacionSelect = document.getElementById('asignacionSeleccionada');
    const selectedOption = asignacionSelect.options[asignacionSelect.selectedIndex];
    const duiDocente = selectedOption.getAttribute('data-docente');

    // Crea un campo oculto para enviar 'duiDocente'
    const duiDocenteInput = document.createElement('input');
    duiDocenteInput.type = 'hidden';
    duiDocenteInput.name = 'duiDocente';
    duiDocenteInput.value = duiDocente;

    form.appendChild(duiDocenteInput);
});

// Maneja el filtro de días para la tabla
document.addEventListener("DOMContentLoaded", function () {
    const filtroDia = document.getElementById('filtroDia');
    const filas = document.querySelectorAll('tbody tr');

    filtroDia.addEventListener('change', function () {
        const diaSeleccionado = this.value;

        filas.forEach(fila => {
            const columnaDia = fila.querySelector('td:nth-child(2)'); // Indice de la columna
            const dia = columnaDia.textContent.trim();

            if (diaSeleccionado === "Todos" || dia === diaSeleccionado) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    });
});

// Toma la informacion relacionada al registro seleccionado de la tabla y la pasa al form
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