// Añade al modal los valores necesarios para el envío y referencia del usuario
document.addEventListener('DOMContentLoaded', (event) => {
    const checkboxes = document.querySelectorAll('.checkbox-clase');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                const hora = this.getAttribute('data-hora');
                const dia = this.getAttribute('data-dia');
                const horaBase = this.getAttribute('data-horaBase')
                const value = this.value;

                document.getElementById('horaSeleccionada').value = hora;
                document.getElementById('dia').value = dia;
                document.getElementById('horaBase').value = horaBase;
                document.getElementById('horaSeleccionada').dataset.value = value;
            }
        });
    });

    // Añadir los parámetros carrera, grado, seccion al select para mantener el contexto
    const form = document.getElementById('formNuevaHora');
    form.addEventListener('submit', function (event) {
        const horaInput = document.getElementById('horaSeleccionada');
        horaInput.value = horaInput.dataset.value;

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

    // Desmarcar checkboxes al hacer clic en Cancelar
    const cancelarBtn = document.getElementById('cancelarNuevaHora');
    cancelarBtn.addEventListener('click', function () {
        checkboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
    });

    // Desmarcar checkboxes cuando el modal se cierra
    const modal = document.getElementById('nuevaHoradeClase');
    modal.addEventListener('hidden.bs.modal', function () {
        checkboxes.forEach(checkbox => {
            checkbox.checked = false;
        });
    });
});