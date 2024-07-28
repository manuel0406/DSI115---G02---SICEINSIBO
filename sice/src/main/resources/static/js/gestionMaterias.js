// LLENADO DE MODAL DE ACTUALIZAR/EDITAS
$(document).ready(function() {
    $('.editar-btn').on('click', function() {
        var idMateria = $(this).data('id');
        var codMateria = $(this).data('cod');
        var nomMateria = $(this).data('nom');
        var tipoMateria = $(this).data('tipo');

        $('#idMateria').val(idMateria);
        $('#editCodMateria').val(codMateria);
        $('#editNomMateria').val(nomMateria);
        $('#editTipoMateria').val(tipoMateria);
    });
});

// MOSTRAR MODAL DE ELIMINAR / MENSAJE DE CONFIRMACION
document.addEventListener("DOMContentLoaded", function() {
    var deleteButton = document.querySelectorAll('.delete-btn');
    var modalDelete = new bootstrap.Modal(document.getElementById('eliminarMateriaModal'));
    var confirmDeleteButton = document.getElementById('confirmarEliminarMateria');
    var modalBody = document.querySelector('.modal-body-delete');
    var currentHref = '';
  
    deleteButton.forEach(function(button) {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // Evitar el comportamiento por defecto del botón
        
            // Obtener el atributo href del botón actual (si es necesario)
            currentHref = button.getAttribute('href');
        
            // Obtener la fila (tr) padre del botón actual
            var row = button.closest('tr');
            
            // Obtener el contenido de la columna 2 (índice 1 en base 0)
            var materia = row.cells[1].textContent.trim();
            
            // Modificar el texto del modal con el correo obtenido
            modalBody.textContent = "¿Deseas eliminar la materia de " + materia + "?";
        
            // Mostrar el modal de confirmación
            modalDelete.show();
        });
    });
  
    confirmDeleteButton.addEventListener('click', function() {
        window.location.href = currentHref;
    });
});


// Obtener Materias en BD
var materias = JSON.parse(materiasJson);
document.addEventListener('DOMContentLoaded', function() {
    // Obtener campos de entrada
    var inputCodN = document.getElementById('codMateria');
    var inputNomN = document.getElementById('nomMateria');
    var inputTipoN = document.getElementById('tipoMateria');

    // Obtener mensajes de texto
    var errorMessageContainerC = document.getElementById('errorCodMateria');
    var errorMessageContainerN = document.getElementById('errorNomMateria');
    var errorMessageContainerT = document.getElementById('errorTipoMateria');

    var saveButton = document.getElementById('saveButton'); // Botón de guardar

    function validateCodMateria() {
        var query = inputCodN.value.trim().toLowerCase();

        if (query !== "") {
            var results = materias.filter(function(materia) {
                return materia.codMateria.toLowerCase() === query;
            });

            if (results.length !== 0) {
                errorMessageContainerC.textContent = "El código de la materia ya existe.";
                // Eliminar todas las clases y luego añadir las necesarias
                inputCodN.className = 'form-control';
                inputCodN.classList.add("is-invalid");
            } else {
                errorMessageContainerC.textContent = "";
                inputCodN.className = 'form-control';
                inputCodN.classList.add("is-valid");
            }
        } else {
            errorMessageContainerC.textContent = "Por favor, ingrese el codigo de la materia.";
            // Eliminar todas las clases y luego añadir las necesarias
            inputCodN.className = 'form-control';
            inputCodN.classList.add("is-invalid");
        }
    }

    function validateNomMateria() {
        var query = inputNomN.value.trim().toLowerCase();

        if (query !== "") {
            var results = materias.filter(function(materia) {
                return materia.nomMateria.toLowerCase() === query;
            });

            if (results.length !== 0) {
                errorMessageContainerN.textContent = "El nombre de la materia ya existe.";
                // Eliminar todas las clases y luego añadir las necesarias
                inputNomN.className = 'form-control';
                inputNomN.classList.add("is-invalid");
            } else {
                errorMessageContainerN.textContent = "";
                inputNomN.className = 'form-control';
                inputNomN.classList.add("is-valid");
            }
        } else {
            errorMessageContainerN.textContent = "Por favor, ingrese el nombre de la materia.";
            // Eliminar todas las clases y luego añadir las necesarias
            inputNomN.className = 'form-control';
            inputNomN.classList.add("is-invalid");
        }
    }

    function validateTipoMateria() {
        var selectedValue = inputTipoN.value;
    
        if (selectedValue === "") {
            // Mostrar mensaje de error si no se selecciona ninguna opción
            errorMessageContainerT.textContent = "Por favor, seleccione un tipo de materia.";
            inputTipoN.className = 'form-select';
            inputTipoN.classList.add("is-invalid")
        } else {
            // Limpiar mensaje de error si se selecciona una opción válida
            errorMessageContainerT.textContent = "";
            inputTipoN.className = 'form-select';
            inputTipoN.classList.add("is-valid");
        }
    }

    function updateSaveButtonState() {
        var isTipoValid = inputTipoN.value !== "";
        var isCodValid = inputCodN.value.trim().toLowerCase() !== "";
        var isNomValid = inputNomN.value.trim().toLowerCase() !== "";
        var hasNoErrors = errorMessageContainerC.textContent === "" && errorMessageContainerN.textContent === "" && errorMessageContainerT.textContent === "";
        var shouldEnableSaveButton = isTipoValid && isCodValid && isNomValid && hasNoErrors;
        saveButton.disabled = !shouldEnableSaveButton;
    }

    inputCodN.addEventListener('input', function() {
        validateCodMateria();
        updateSaveButtonState(); // Actualizar el estado del botón de guardar
    });

    inputNomN.addEventListener('input', function() {
        validateNomMateria();
        updateSaveButtonState(); // Actualizar el estado del botón de guardar
    });

    inputTipoN.addEventListener('change', function() {
        validateTipoMateria();
        updateSaveButtonState(); // Actualizar el estado del botón de guardar
    });


    var iconoCerrar = document.getElementById('cancelarNuevaMateriaIco');
    var btnCerrarModal = document.getElementById('cancelarNuevaMateria');

    function cerrarModal() {
        var form = document.getElementById('formNuevaMateria');
        form.reset();
        form.classList.remove('was-validated');  // Eliminar la clase de validación
    
        // Limpieza de campos
        inputCodN.className = 'form-control';
        inputNomN.className = 'form-control';
        inputTipoN.className = 'form-select';
    
        // Obtener mensajes de texto
        errorMessageContainerC.textContent = "";
        errorMessageContainerN.textContent = "";
        errorMessageContainerT.textContent = "";
    }

    iconoCerrar.addEventListener('click', cerrarModal);
    btnCerrarModal.addEventListener('click', cerrarModal);
});
