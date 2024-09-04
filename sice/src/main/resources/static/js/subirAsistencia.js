document.addEventListener("DOMContentLoaded", function () {
    let selectedFile;
    document.getElementById("input-excel").addEventListener("change", (e) => {
      selectedFile = e.target.files[0];
    });
  
    document.getElementById("idCarga").addEventListener("click", () => {
      if (selectedFile) {
        handleFile();
      } else {
        Swal.fire({
          position: "center",
          icon: "error",
          title: "Error de archivo",
          text: "Por favor, seleccione un archivo primero.",
          showConfirmButton: false,
          timer: 5000
        });
      }
    });
  
    function handleFile() {
      const reader = new FileReader();
      reader.onload = function (event) {
        try {
          const data = new Uint8Array(event.target.result);
          const workbook = XLSX.read(data, { type: "array" });
          const output = document.getElementById("output");
          output.innerHTML = "";
          const sheetNameToFind = "Estadísticas de anormalías"; // Hoja buscada
          if (workbook.SheetNames.includes(sheetNameToFind)) {
            const sheet = workbook.Sheets[sheetNameToFind];
            const json = XLSX.utils.sheet_to_json(sheet, { header: 1 });
            let results = [];
            let columnIndexID = -1;
  
            function isValidTime(value) {
              return /^([01]\d|2[0-3]):([0-5]\d)$/.test(value);
            }
  
            function formatDate(dateStr) {
              const [day, month, year] = dateStr.split("/");
              return `${year}-${month}-${day}`;
            }
  
            function formatTimeToTwoDigits(timeStr) {
              // Asegura que el tiempo esté en el formato HH:mm (e.g., "06:50" en lugar de "6:50")
              return timeStr.length === 4 ? `0${timeStr}` : timeStr;
            }
  
            // Encuentra la columna con el título "ID"
            for (let i = 0; i < json.length; i++) {
              let row = json[i];
              columnIndexID = row.indexOf("ID.");
              if (columnIndexID !== -1) {
                // Encontró la columna "ID"
                for (let j = i + 1; j < json.length; j++) {
                  let dataRow = json[j];
                  let id = dataRow[columnIndexID];
                  if (id !== undefined) {
                    let adjacentData = [];
                    for (let k = 0; k <= 6; k++) {
                      adjacentData.push(dataRow[columnIndexID + k]);
                    }
  
                    // Formatear los datos según el formato deseado
                    const formattedDate = formatDate(adjacentData[3]);
  
                    // Verificar si los campos de "Inicio" y "Fin" periodo 1 están presentes
                    if (
                      (adjacentData[4] || adjacentData[4] === "Falta") &&
                      (adjacentData[5] || adjacentData[5] === "Falta")
                    ) {
                      const formattedInicioMatutino = adjacentData[4] && adjacentData[4] !== "Falta" 
                        ? `${formattedDate}T${formatTimeToTwoDigits(adjacentData[4])}:00` 
                        : `${formattedDate}T01:00:00`;
                      const formattedFinMatutino = adjacentData[5] && adjacentData[5] !== "Falta" 
                        ? `${formattedDate}T${formatTimeToTwoDigits(adjacentData[5])}:00` 
                        : `${formattedDate}T01:00:00`;
  
                      // Agregar datos para periodo solo si Inicio o Fin están presentes
                      results.push({
                        ID: id,
                        Depart: adjacentData[2],
                        Turno: "Matutino",
                        Inicio: formattedInicioMatutino,
                        Fin: formattedFinMatutino,
                      });
                    }
  
                    // Verificar si los campos de "Inicio" y "Fin" para periodo 2 están presentes
                    if (
                      (adjacentData[6] || adjacentData[6] === "Falta") &&
                      (adjacentData[7] || adjacentData[7] === "Falta")
                    ) {
                      const formattedInicioVespertino = adjacentData[6] && adjacentData[6] !== "Falta" 
                        ? `${formattedDate}T${formatTimeToTwoDigits(adjacentData[6])}:00` 
                        : `${formattedDate}T01:00:00`;
                      const formattedFinVespertino = adjacentData[7] && adjacentData[7] !== "Falta" 
                        ? `${formattedDate}T${formatTimeToTwoDigits(adjacentData[7])}:00` 
                        : `${formattedDate}T01:00:00`;
  
                      // Agregar datos periodo 2 solo si Inicio o Fin están presentes
                      results.push({
                        ID: id,
                        Depart: adjacentData[2],
                        Turno: "Vespertino",
                        Inicio: formattedInicioVespertino,
                        Fin: formattedFinVespertino,
                      });
                    }
                  }
                }
                break;
              }
            }
  
            if (columnIndexID === -1) {
              console.log("No se encontró la columna 'ID'.");
            }
  
            output.innerHTML = JSON.stringify(results, null, 2);
            enviarDatos(results); // Envia los datos al servidor
          } else {
            output.innerHTML = `<p>La hoja con el nombre "${sheetNameToFind}" no se encontró en el archivo.</p>`;
          }
        } catch (error) {
          console.error("Error al procesar el archivo:", error);
          document.getElementById("output").innerText =
            "Hubo un error al procesar el archivo. Por favor, inténtalo de nuevo.";
        }
      };
      reader.readAsArrayBuffer(selectedFile);
    }
  
    function enviarDatos(datos) {
      const validDatos = datos.filter(dato => dato.Depart && dato.Depart.trim() !== "");
      if (validDatos.length === 0) {
        alert("No hay datos válidos para enviar.");
        return;
      }

      const csrfToken = $('input[name="_csrf"]').val();
      console.log(csrfToken)

      $.ajax({
        url: "/asistencias/procesarAsistencias",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(validDatos),
        headers: {
          "X-XSRF-TOKEN": csrfToken // Agregar el token CSRF al encabezado
        },
        success: function (response) {
          console.log("Respuesta del servidor:", response);
          Swal.fire({
            position: "center",
            icon: "success",
            title: response.mensaje,
            showConfirmButton: false,
            timer: 5000
          });
        },
        error: function (xhr, status, error) {
          console.error("Error al enviar datos:", error);
          alert("Error al enviar datos.");
        }
      });
    }
  });
  