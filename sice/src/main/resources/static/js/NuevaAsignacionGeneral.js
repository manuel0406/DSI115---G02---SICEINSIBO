// Obtener información de respuesta.
var materias = JSON.parse(materiasJSON);
var docentes = JSON.parse(docentesJSON);
var asignaciones = JSON.parse(asignacionesJSON);
var primeros = JSON.parse(primerosJSON);
var segundos = JSON.parse(segundosJSON);
var terceros = JSON.parse(tercerosJSON);

// Función para manejar el cambio de selección
document.getElementById('idMateria').addEventListener('change', function() {
    
    var materiaSeleccionada = this.value;
    var materia = materias.filter(materia => materia.idMateria == materiaSeleccionada)[0];


    var contenedoresP = document.getElementById('cbxPrimeros');
    var contenedoresS = document.getElementById('cbxSegundos');
    var contenedoresT = document.getElementById('cbxTerceros');

    if(materiaSeleccionada == ''){
        console.log("HOLA");
        contenedoresP.innerHTML = '';
        contenedoresS.innerHTML = '';
        contenedoresT.innerHTML = '';

    }
    else{
        // Filtrar las asignaciones a grados que ya se le ha asignado
        var asignacionesPrimeros = asignaciones.filter(asignacion => 
            asignacion.materia.idMateria == materiaSeleccionada && asignacion.bachillerato.grado == 1
        );  
        var asignacionesSegundos = asignaciones.filter(asignacion => 
            asignacion.materia.idMateria == materiaSeleccionada && asignacion.bachillerato.grado == 2
        ); 
        var asignacionesTerceros = asignaciones.filter(asignacion => 
            asignacion.materia.idMateria == materiaSeleccionada && asignacion.bachillerato.grado == 3
        );  

        // Quitar bachilleratos ya asignados
        var primerosFiltrados = quitarBachilleratos(primeros, asignacionesPrimeros, 'codigoBachillerato');
        var segundosFiltrados = quitarBachilleratos(segundos, asignacionesSegundos, 'codigoBachillerato');
        var tercerosFiltrados = quitarBachilleratos(terceros, asignacionesTerceros, 'codigoBachillerato');
        
        // Mostrar los resultados filtrados en las secciones correspondientes
        mostrarResultados(contenedoresP, primerosFiltrados, 'primeros-anos');
        mostrarResultados(contenedoresS, segundosFiltrados, 'segundos-anos');
        // Asegúrate de que el objeto materia tiene la propiedad tipoMateria
        if (materia && materia.tipoMateria !== "Básica") {
            mostrarResultados(contenedoresT, tercerosFiltrados, 'terceros-anos');
        } else {
            contenedoresT.innerHTML = '';
        }

    }

});

// Función para quitar los bachilleratos de la lista principal que ya están en la lista a eliminar
function quitarBachilleratos(listaPrincipal, listaAEliminar, propiedad) {
    return listaPrincipal.filter(itemPrincipal => 
        !listaAEliminar.some(itemAEliminar => itemPrincipal[propiedad] === itemAEliminar.bachillerato[propiedad])
    );
}

// Función para mostrar los resultados filtrados en las secciones correspondientes
function mostrarResultados(seccion, listaFiltrada, tipoanio) {
    seccion.innerHTML = ''; // Limpiar contenido previo

    listaFiltrada.forEach(item => {
        var div = document.createElement('div');
        div.className = 'col-md-6 mb-3 form-check form-switch';

        var input = document.createElement('input');
        input.className = 'form-check-input';
        input.classList.add(tipoanio);
        input.classList.add("checkbox-group");
        input.type = 'checkbox';
        input.name = 'codigoBachillerato';
        input.id = item.codigoBachillerato;
        input.value = item.codigoBachillerato;

        var label = document.createElement('label');
        label.className = 'form-check-label label-checkbox-group';
        label.innerHTML = `<span>${item.nombreCarrera}</span ><br /><span id="seccionSpan">Sección: ${item.seccion}</span>`;

        div.appendChild(input);
        div.appendChild(label);
        seccion.appendChild(div);
    });
}
