$(document).ready(function() {
    function updateSecciones() {
        var grado = $('#selectGrado').val();
        var tecnico = $('#selectTecnico').val();
        var selectSeccion = $('#selectSeccion');

        if (grado && tecnico) {
            $.ajax({
                url: '/getSecciones',
                type: 'GET',
                data: { grado: grado, tecnico: tecnico },
                success: function(data) {
                    selectSeccion.empty();
                    selectSeccion.append('<option value="">Seleccionar sección...</option>');
                    $.each(data, function(index, value) {
                        selectSeccion.append('<option value="' + value + '">' + value + '</option>');
                    });
                }
            });
        } else {
            selectSeccion.empty();
            selectSeccion.append('<option value="">Seleccionar sección...</option>');
        }
    }

    $('#selectGrado').change(updateSecciones);
    $('#selectTecnico').change(updateSecciones);
});

(function () {
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
})()

document.getElementById('cancelarNuevaMateria').addEventListener('click', function () {
    var form = document.getElementById('formNuevaMateria');
    form.reset();
    form.classList.remove('was-validated');  // Eliminar la clase de validación
});

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

$(document).ready(function() {
    // Evento de clic en el botón de eliminar
    $('.delete-btn').on('click', function() {
        var idMateria = $(this).closest('tr').find('.editar-btn').data('id'); // Obtener el ID de la materia desde el botón de editar del mismo row
        $('#eliminarMateriaModal').modal('show'); // Mostrar el modal de confirmación
    });
});


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
  