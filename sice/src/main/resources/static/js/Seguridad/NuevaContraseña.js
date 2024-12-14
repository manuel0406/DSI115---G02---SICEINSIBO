const strengthBar = document.getElementById('strength-bar'); // Barra de progreso
const strengthMessage = document.getElementById('strength-message'); // Texto de barra de progreso

function checkPasswordStrength(password) {
    let strength = 0;

    // CRITERIO DE TAMAÑO: AL MENOS 8 CARACTERES
    const lengthValid = password.length >= 8;
    if (lengthValid) strength++;
    // Texto de criterio de tamaño
    const lengthCriterioElement = document.getElementById('length-criteria'); // Criterio de tamaño
    lengthCriterioElement.classList.toggle('text-success', lengthValid); // Exito de tamaño
    lengthCriterioElement.classList.toggle('text-danger', !lengthValid); // Fracaso de tamaño
    // Icono de criterio de tamaño
    const lengthCriteriaIcon = lengthCriterioElement.querySelector('i');
    if (lengthCriteriaIcon) {
        if (lengthValid) {
            lengthCriteriaIcon.classList.add('bi-check-circle-fill');  // Agrega la clase si es válido
            lengthCriteriaIcon.classList.remove('bi-x-circle-fill');  // Elimina la clase si es válido
            lengthCriteriaIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        } else {
            lengthCriteriaIcon.classList.remove('bi-check-circle-fill');  // Elimina la clase si no es válido
            lengthCriteriaIcon.classList.add('bi-x-circle-fill');  // Agrega la clase si no es válido
            lengthCriteriaIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        }
    }

    // CRITERIO DE CARACTER EN MINUSCULA: AL MENOS 1 MINUSCULA
    const lowercaseValid = /[a-z]/.test(password);
    if (lowercaseValid) strength++;
    // Texto de criterio de minuscula
    const lowercaseCriterioElement = document.getElementById('lowercase-criteria'); // Criterio de minuscula
    lowercaseCriterioElement.classList.toggle('text-success', lowercaseValid); // Exito de minuscula minima
    lowercaseCriterioElement.classList.toggle('text-danger', !lowercaseValid); // Fracaso de minuscula minima
    // Icono de criterio de minuscula
    const lowercaseCriterioIcon = lowercaseCriterioElement.querySelector('i');
    if (lowercaseCriterioIcon) {
        if (lowercaseValid) {
            lowercaseCriterioIcon.classList.add('bi-check-circle-fill');  // Agrega la clase si es válido
            lowercaseCriterioIcon.classList.remove('bi-x-circle-fill');  // Elimina la clase si es válido
            lowercaseCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        } else {
            lowercaseCriterioIcon.classList.remove('bi-check-circle-fill');  // Elimina la clase si no es válido
            lowercaseCriterioIcon.classList.add('bi-x-circle-fill');  // Agrega la clase si no es válido
            lowercaseCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        }
    }

    // CRITERIO DE CARACTER EN MAYUSCULA: AL MENOS 1 MAYUSCULA
    const uppercaseValid = /[A-Z]/.test(password);
    if (uppercaseValid) strength++;
    // Texto de criterio de mayuscula
    const uppercaseCriterioElement = document.getElementById('uppercase-criteria') //Criterio de mayuscula
    uppercaseCriterioElement.classList.toggle('text-success', uppercaseValid); // Exito de mayusucula minima
    uppercaseCriterioElement.classList.toggle('text-danger', !uppercaseValid); // Fracaso de mayusucula minima
    // Icono de criterio de mayuscula
    const uppercaseCriterioIcon = uppercaseCriterioElement.querySelector('i');
    if (uppercaseCriterioIcon) {
        if (uppercaseValid) {
            uppercaseCriterioIcon.classList.add('bi-check-circle-fill');  // Agrega la clase si es válido
            uppercaseCriterioIcon.classList.remove('bi-x-circle-fill');  // Elimina la clase si es válido
            uppercaseCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        } else {
            uppercaseCriterioIcon.classList.remove('bi-check-circle-fill');  // Elimina la clase si no es válido
            uppercaseCriterioIcon.classList.add('bi-x-circle-fill');  // Agrega la clase si no es válido
            uppercaseCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        }
    }

    // CRITERIO DE CARACTER NUMERICO: AL MENOS UNO
    const numberValid = /[0-9]/.test(password);
    if (numberValid) strength++;
    // Texto de criterio numerico
    const numberCriterioElement = document.getElementById('number-criteria') // Criterio numerico
    numberCriterioElement.classList.toggle('text-success', numberValid); // Exito de número minimo
    numberCriterioElement.classList.toggle('text-danger', !numberValid); // Fracaso de número minimo
    // Icono de criterio de número
    const numberCriterioIcon = numberCriterioElement.querySelector('i');
    if (numberCriterioIcon) {
        if (numberValid) {
            numberCriterioIcon.classList.add('bi-check-circle-fill');  // Agrega la clase si es válido
            numberCriterioIcon.classList.remove('bi-x-circle-fill');  // Elimina la clase si es válido
            numberCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        } else {
            numberCriterioIcon.classList.remove('bi-check-circle-fill');  // Elimina la clase si no es válido
            numberCriterioIcon.classList.add('bi-x-circle-fill');  // Agrega la clase si no es válido
            numberCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        }
    }

    // CRITERIO DE CARACTER ESPECIAL: AL MENOS UNO
    const specialValid = /[^a-zA-Z0-9]/.test(password);
    if (specialValid) strength++;
    // Texto de criterio de caracter especial
    const specialCriterioElement = document.getElementById('special-criteria'); // Criterio de caracter especial
    specialCriterioElement.classList.toggle('text-success', specialValid); // Exito de caracter especial
    specialCriterioElement.classList.toggle('text-danger', !specialValid); // Fracaso de caracter especial
    //Icono de criterio de caracter especial
    const specialCriterioIcon = specialCriterioElement.querySelector('i');
    if (specialCriterioIcon) {
        if (specialValid) {
            specialCriterioIcon.classList.add('bi-check-circle-fill');  // Agrega la clase si es válido
            specialCriterioIcon.classList.remove('bi-x-circle-fill');  // Elimina la clase si es válido
            specialCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        } else {
            specialCriterioIcon.classList.remove('bi-check-circle-fill');  // Elimina la clase si no es válido
            specialCriterioIcon.classList.add('bi-x-circle-fill');  // Agrega la clase si no es válido
            specialCriterioIcon.classList.remove('bi-info-circle-fill');  // Elimina la clase si es válido
        }
    }

    const strengthPercentage = (strength / 5) * 100;
    strengthBar.style.width = strengthPercentage + '%';
    strengthBar.setAttribute('aria-valuenow', strengthPercentage);

    if (strengthPercentage < 50) {
        strengthBar.classList.add('bg-danger');
        strengthMessage.classList.add('bg-danger');
        strengthBar.classList.remove('bg-warning', 'bg-success', 'bg-info');
        strengthMessage.classList.add('bg-warning', 'bg-success', 'bg-info');
        strengthMessage.textContent = 'Débil';
    } else if (strengthPercentage < 75) {
        strengthBar.classList.add('bg-warning');
        strengthMessage.classList.add('bg-warning');
        strengthBar.classList.remove('bg-danger', 'bg-success', 'bg-info');
        strengthMessage.classList.remove('bg-danger', 'bg-success', 'bg-info');
        strengthMessage.textContent = 'Moderada';
    } else if (strengthPercentage < 100) {
        strengthBar.classList.add('bg-info');
        strengthMessage.classList.add('bg-info');
        strengthBar.classList.remove('bg-danger', 'bg-warning', 'bg-success');
        strengthMessage.classList.remove('bg-danger', 'bg-warning', 'bg-success');
        strengthMessage.textContent = 'Buena';
    } else {
        strengthBar.classList.add('bg-success');
        strengthMessage.classList.add('bg-success');
        strengthBar.classList.remove('bg-danger', 'bg-warning', 'bg-info');
        strengthMessage.classList.remove('bg-danger', 'bg-warning', 'bg-info');
        strengthMessage.textContent = 'Fuerte';
    }
}

function togglePasswordVisibility(fieldId) {
    const field = document.getElementById(fieldId);
    const icon = document.getElementById(fieldId + 'Icon');
    if (field.type === 'password') {
        field.type = 'text';
        icon.classList.replace('bi-eye', 'bi-eye-slash');
    } else {
        field.type = 'password';
        icon.classList.replace('bi-eye-slash', 'bi-eye');
    }
}

document.getElementById('newPassword').addEventListener('input', function() {
    checkPasswordStrength(this.value);
});