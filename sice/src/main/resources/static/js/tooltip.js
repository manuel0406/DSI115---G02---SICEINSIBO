var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl, {
        delay: { show: 600, hide: 100 }  // 600ms de retardo al mostrar, 100ms al ocultar
    });
});

// Funciona para el boton usado en Horario
var button = document.getElementById('botonNav');
// var button = document.querySelector('.btn-dark.bi-layout-sidebar');

// Inicializa el tooltip usando el atributo title, solo al pasar el cursor (hover)
new bootstrap.Tooltip(button, {
    placement: 'top',
    title: function() {
        return button.getAttribute('title');
    },
    delay: { show: 600, hide: 100 },
    trigger: 'hover' 
});


