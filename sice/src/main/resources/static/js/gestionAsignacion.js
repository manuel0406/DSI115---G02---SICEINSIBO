$(document).ready(function() {
    $('#actualizarMateriaModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget); // Botón que disparó el modal
        var idAsignacion = button.data('id');
        var nomMateria = button.data('materia'); // Asegúrate de que este es el atributo correcto
        var duiDocente = button.data('profesor'); // Asegúrate de que este es el atributo correcto
        var codBachillerato = button.data('cod-bachillerato'); // Asegúrate de que este es el atributo correcto

        var modal = $(this);
        modal.find('#idAsignacion').val(idAsignacion);
        modal.find('#editarMateria').val(nomMateria); // Asegúrate de que este es el ID correcto
        modal.find('#editarBachillerato').val(codBachillerato); // Asegúrate de que este es el ID correcto
        modal.find('#profesor').val(duiDocente); // Asegúrate de que este es el ID correcto
    });
});


(function () {
    'use strict'

    // Obtén todos los formularios con la clase needs-validation
    var forms = document.querySelectorAll('.needs-validation')

    Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            var isValid = form.checkValidity()

            // Validación programática para campos readonly
            var readonlyFields = form.querySelectorAll('[readonly]')
            readonlyFields.forEach(function(field) {
                // Aquí puedes agregar lógica para validar el campo readonly
                if (!field.value) {
                    isValid = false
                    field.classList.add('is-invalid')
                    // Añadir un mensaje de error si se requiere
                    var feedback = field.nextElementSibling
                    if (feedback && feedback.classList.contains('invalid-feedback')) {
                        feedback.textContent = 'Este campo es obligatorio.'
                    }
                } else {
                    field.classList.remove('is-invalid')
                }
            })

            if (!isValid) {
                event.preventDefault()
                event.stopPropagation()
            }

            form.classList.add('was-validated')
        }, false)
    })
})()
