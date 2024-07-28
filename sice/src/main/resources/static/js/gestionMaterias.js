// BOTON CANCELAR DEL MODAL
document.getElementById('cancelarNuevaMateria').addEventListener('click', function () {
    var form = document.getElementById('formNuevaMateria');
    form.reset();
    form.classList.remove('was-validated');  // Eliminar la clase de validación

    // Limpieza de errores de unicidad
    var divNombreMateria = document.getElementById('errorNomMateria');
    var divCodigoMateria = document.getElementById('errorCodMateria');
    divCodigoMateria.innerHTML="";
    divNombreMateria.innerHTML="";
});

// VALIDACION DE CAMPOS VACIOS
/*(function () {
    'use strict'
    var forms = document.querySelectorAll('.needs-validation')
    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }
            form.classList.add('was-validated')
        }, false)
    })
})()*/

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
console.log(materias);
document.addEventListener('DOMContentLoaded', function() {
    // Obtener campos de entrada
    var inputCodN = document.getElementById('codMateria');
    var inputNomN = document.getElementById('nomMateria');

    // Obtener mensajes de texto
    var errorMessageContainerC = document.getElementById('errorCodMateria');
    var errorMessageContainerN = document.getElementById('errorNomMateria');
    var errorIconCod = document.getElementById('errorIconCodMateria');
    var errorIconNom = document.getElementById('errorIconNomMateria');
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
                inputCodN.className = 'form-control border-danger';
                errorIconCod.classList.add('show');
            } else {
                errorMessageContainerC.textContent = "";
                inputCodN.classList.remove("border-danger");
                inputCodN.classList.add("is-valid");
                errorIconCod.classList.remove('show');
            }
        } else {
            errorMessageContainerC.textContent = "Por favor, ingrese el codigo de la materia.";
            // Eliminar todas las clases y luego añadir las necesarias
            inputCodN.className = 'form-control border-danger';
            errorIconCod.classList.add('show');
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
                inputNomN.className = 'form-control border-danger';
                errorIconNom.classList.add('show');
            } else {
                errorMessageContainerN.textContent = "";
                inputNomN.classList.remove("border-danger");
                inputNomN.classList.add("is-valid");
                errorIconNom.classList.remove('show');
            }
        } else {
            errorMessageContainerN.textContent = "Por favor, ingrese el nombre de la materia.";
            // Eliminar todas las clases y luego añadir las necesarias
            inputNomN.className = 'form-control border-danger';
            errorIconNom.classList.add('show');
        }
    }

    function updateSaveButtonState() {
        var hasErrors = errorMessageContainerC.textContent !== "" || errorMessageContainerN.textContent !== "";
        saveButton.disabled = hasErrors;
    }

    inputCodN.addEventListener('input', function() {
        validateCodMateria();
        updateSaveButtonState(); // Actualizar el estado del botón de guardar
    });

    inputNomN.addEventListener('input', function() {
        validateNomMateria();
        updateSaveButtonState(); // Actualizar el estado del botón de guardar
    });

});
