$(document).ready(function() {
  function updateMasterCheckbox(groupClass, masterCheckboxId) {
      var allChecked = $(groupClass).length === $(groupClass + ':checked').length;
      $(masterCheckboxId).prop('checked', allChecked);
  }

  $('.marcar-todos').on('change', function() {
      var groupClass = '.' + $(this).data('group');
      $(groupClass).prop('checked', $(this).is(':checked'));
  });

  $('.form-check-input:not(.marcar-todos)').on('change', function() {
      var groupClass = '.' + $(this).attr('class').split(' ')[1];
      var masterCheckboxId = '#marcarTodos' + groupClass.charAt(1).toUpperCase() + groupClass.slice(2);
      updateMasterCheckbox(groupClass, masterCheckboxId);
  });

  $('.form-check-input.primeros-anos').on('change', function() {
      updateMasterCheckbox('.primeros-anos', '#marcarTodosPrimerosAnos');
  });

  $('.form-check-input.segundos-anos').on('change', function() {
      updateMasterCheckbox('.segundos-anos', '#marcarTodosSegundosAnos');
  });

  $('.form-check-input.terceros-anos').on('change', function() {
      updateMasterCheckbox('.terceros-anos', '#marcarTodosTercerosAnos');
  });
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

$(document).ready(function() {
    function updateMasterCheckbox(groupClass, masterCheckboxId) {
        var allChecked = $(groupClass).length === $(groupClass + ':checked').length;
        $(masterCheckboxId).prop('checked', allChecked);
    }

    function validateCheckboxes() {
        var valid = $('.primeros-anos:checked').length > 0 || 
                    $('.segundos-anos:checked').length > 0 || 
                    $('.terceros-anos:checked').length > 0;
        
        // Aplicar estilos según la validez de los checkboxes
        if (!valid) {
            $('#error-message').show(); // Mostrar el mensaje de error si no hay selección
            $('.checkbox-group').addClass('border-danger').removeClass('border-success');
            $('.label-checkbox-group').addClass('text-danger').removeClass('text-success');

        } else {
            $('#error-message').hide(); // Ocultar el mensaje de error si hay al menos una selección
            $('.checkbox-group').addClass('border-success').removeClass('border-danger');
            $('.label-checkbox-group').addClass('text-success').removeClass('text-danger');

        }
        return valid;
    }

    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    $('.marcar-todos').on('change', function() {
        var groupClass = '.' + $(this).data('group');
        $(groupClass).prop('checked', $(this).is(':checked'));
        validateCheckboxes(); // Validar después de cambiar el estado de los checkboxes
    });

    $('.form-check-input:not(.marcar-todos)').on('change', function() {
        var groupClass = '.' + $(this).attr('class').split(' ')[1];
        var masterCheckboxId = '#marcarTodos' + capitalizeFirstLetter(groupClass);
        updateMasterCheckbox(groupClass, masterCheckboxId);
        validateCheckboxes(); // Validar después de cambiar el estado de los checkboxes
    });

    $('.form-check-input.primeros-anos').on('change', function() {
        updateMasterCheckbox('.primeros-anos', '#marcarTodosPrimerosAnos');
        validateCheckboxes(); // Validar después de cambiar el estado de los checkboxes
    });

    $('.form-check-input.segundos-anos').on('change', function() {
        updateMasterCheckbox('.segundos-anos', '#marcarTodosSegundosAnos');
        validateCheckboxes(); // Validar después de cambiar el estado de los checkboxes
    });

    $('.form-check-input.terceros-anos').on('change', function() {
        updateMasterCheckbox('.terceros-anos', '#marcarTodosTercerosAnos');
        validateCheckboxes(); // Validar después de cambiar el estado de los checkboxes
    });

    // Validación adicional para al menos un checkbox seleccionado
    $('form.needs-validation').on('submit', function(event) {
        if (!validateCheckboxes()) {
            event.preventDefault();
            event.stopPropagation();
            $('#error-message').show(); // Mostrar el mensaje de error si no hay selección
        } else {
            $('#error-message').hide(); // Ocultar el mensaje de error si hay al menos una selección
        }
        $(this).addClass('was-validated');
    });

    // Inicializar el mensaje de error como oculto
    $('#error-message').hide();
    // Inicializar los bordes como no válidos
    $('.checkbox-group').removeClass('border-danger');
    $('.label-checkbox-group').removeClass('text-danger');
});

// Función para volver a la página anterior
function goBack() {
    window.history.back();
}