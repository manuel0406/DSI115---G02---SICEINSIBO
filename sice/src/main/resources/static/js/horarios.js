document.addEventListener('DOMContentLoaded', (event) => {
    // Se encarga de mostrar valores de referencia adecuados para el usuario y enviar 
    // los datos correspondientes al guardar el formulario
    const checkboxes = document.querySelectorAll('.checkbox-clase');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                const hora = this.getAttribute('data-hora');
                const value = this.value;

                document.getElementById('horaSeleccionada').value = hora;
                document.getElementById('horaSeleccionada').dataset.value = value;

                // Por si acaso necesito el codigoBachilleratop
                //const seccionSeleccionada = document.getElementById('seccionSeleccionada');

                // const codigoBachillerato = document.getElementById('codigoBachilleratop').value;
                // seccionSeleccionada.dataset.value = codigoBachillerato;
            }
        });
    });

    const form = document.getElementById('formNuevaHora');
    form.addEventListener('submit', function (event) {
        const horaInput = document.getElementById('horaSeleccionada');
        //const seccionInput = document.getElementById('seccionSeleccionada');

        horaInput.value = horaInput.dataset.value;
        //seccionInput.value = seccionInput.dataset.value;

        // Añadir los parámetros carrera, grado y seccion al formulario antes de enviarlo para
        // actualizar la vista para la misma sección
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