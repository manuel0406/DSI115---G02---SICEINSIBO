// funciona sobre todos los elementos con la clase "restricted-input"
const restrictedInputs = document.querySelectorAll('.restricted-input');
restrictedInputs.forEach(function (input) {
    input.addEventListener('input', function (event) {
        let value = this.value;
        value = value.replace(/[^\d-]/g, '');
        this.value = value;
    });
});

document.getElementById('formulario').addEventListener('submit', function (event) {
    var nieInput = document.getElementById('nie');
    var nieValue = nieInput.value;
    if (nieValue.length < 7) {
        showErrorModal('El NIE debe tener al menos 7 caracteres.');
        event.preventDefault();
    }

    var duiInput = document.getElementById('dui');
    var duiValue = duiInput.value;
    if (duiValue.length < 10) {
        showErrorModal('El DUI debe tener al menos 10 caracteres.');
        event.preventDefault();
    }

    var telefonoInput = document.getElementById('telefono');
    var telefonoValue = telefonoInput.value;
    if (telefonoValue.length < 9) {
        showErrorModal('El Teléfono debe tener al menos 9 caracteres.');
        event.preventDefault();
    }

    var telefonoEncargadoInput = document.getElementById('telefonoEncargado');
    var telefonoEncargadoValue = telefonoEncargadoInput.value;
    if (telefonoEncargadoValue.length < 9) {
        showErrorModal('El Teléfono del Encargado debe tener al menos 9 caracteres.');
        event.preventDefault();
    }
});

function showErrorModal(message) {
    document.getElementById('errorMessage').innerText = message;
    var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    errorModal.show();
}

