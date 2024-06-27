// funciona sobre todos los elementos con la clase "restricted-input"
const restrictedInputs = document.querySelectorAll('.restricted-input');
restrictedInputs.forEach(function (input) {
    input.addEventListener('input', function (event) {
        let value = this.value;
        value = value.replace(/[^\d-]/g, '');
        this.value = value;
    });
});
(() => {
    'use strict'

    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()

                // Find the first invalid element
                const firstInvalidElement = form.querySelector(':invalid')
                if (firstInvalidElement) {
                    // Scroll to the first invalid element
                    firstInvalidElement.scrollIntoView({ behavior: 'smooth', block: 'center' })
                    // Optionally focus on the first invalid element
                    firstInvalidElement.focus()
                }
            }

            form.classList.add('was-validated')
        }, false)
    })
})()
document.getElementById('formulario').addEventListener('submit', function (event) {
    var showError = false;
    var errorMessage = '';

    var nieInput = document.getElementById('nie');
    var nieValue = nieInput.value;
    if (nieValue.length < 7) {
        errorMessage = 'El NIE debe tener 7 caracteres.';
        showError = true;
    }

    var duiInput = document.getElementById('duiEncargado');
    var duiValue = duiInput.value;
    if (duiValue.length < 10) {
        errorMessage = 'El DUI del responsable debe tener 10 caracteres.';
        showError = true;
    }

    var telefonoInput = document.getElementById('telefono');
    var telefonoValue = telefonoInput.value;
    if (telefonoValue.length < 9) {
        errorMessage = 'El Teléfono del alumno debe tener 9 caracteres.';
        showError = true;
    }

    var telefonoEncargadoInput = document.getElementById('telefonoEncargado');
    var telefonoEncargadoValue = telefonoEncargadoInput.value;
    if (telefonoEncargadoValue.length < 9) {
        errorMessage = 'El Teléfono del Encargado debe tener 9 caracteres.';
        showError = true;
    }

    if (showError) {
        showErrorModal(errorMessage);
        event.preventDefault();
    }
});

function showErrorModal(message) {
    document.getElementById('errorMessage').innerText = message;
    var errorModal = new bootstrap.Modal(document.getElementById('errorModal'));
    errorModal.show();
}



