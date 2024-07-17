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
