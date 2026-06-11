
let chart = null;

/* ✅ TOKEN */
function obtenerToken() {
    return localStorage.getItem("token");
}

/* ✅ MOSTRAR CARD */
function mostrarGrafico(titulo) {
    document.getElementById("tituloGrafico").innerText = titulo;
    document.getElementById("cardGrafico").classList.remove("d-none");
}

/* ================= USUARIO ================= */
function verTareasUsuario() {

    fetch("http://localhost:8080/SGCM/api/reportes/tareas-por-usuario", {
        headers: {
            "Authorization": "Bearer " + obtenerToken()
        }
    })
        .then(res => res.json())
        .then(data => {

            const lista = data.value || [];

            let labels = [];
            let values = [];

            lista.forEach(item => {
                labels.push(item.usuario);
                values.push(item.total);
            });

            renderGrafico("bar", labels, values, "Tareas por Usuario");
        });
}

/* ================= CAMPAÑA ================= */
function verTareasCampania() {

    fetch("http://localhost:8080/SGCM/api/reportes/tareas-por-campania", {
        headers: {
            "Authorization": "Bearer " + obtenerToken()
        }
    })
        .then(res => res.json())
        .then(data => {

            const lista = data.value || [];

            let labels = [];
            let values = [];

            lista.forEach(item => {
                labels.push(item.campania);
                values.push(item.totalTareas);
            });

            renderGrafico("bar", labels, values, "Tareas por Campaña");
        });
}

/* ================= ESTADO ================= */
function verTareasEstado() {

    fetch("http://localhost:8080/SGCM/api/reportes/tareas-por-estado", {
        headers: {
            "Authorization": "Bearer " + obtenerToken()
        }
    })
        .then(res => res.json())
        .then(data => {

            const lista = data.value || [];

            let labels = [];
            let values = [];

            lista.forEach(item => {
                labels.push(item.estado);
                values.push(item.total);
            });

            renderGrafico("pie", labels, values, "Tareas por Estado");
        });
}

/* ================= RENDER ================= */

function renderGrafico(tipo, labels, values, titulo) {

    document.getElementById("cardGrafico").classList.remove("d-none");
    document.getElementById("tituloGrafico").innerText = titulo;

    if (chart) chart.destroy();

    chart = new Chart(document.getElementById("graficoReporte"), {
        type: tipo,
        data: {
            labels,
            datasets: [{
                label: titulo,
                data: values,
                backgroundColor: ['#3b82f6','#22c55e','#f59e0b','#ef4444']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

/* ================= DESCARGAS ================= */

function descargarExcelUsuario() {
    descargarConToken(
        "http://localhost:8080/SGCM/api/reportes/tareas-por-usuario/excel",
        "tareas_usuario.xlsx"
    );
}

function descargarEmpresasCampania() {
    descargarConToken(
        "http://localhost:8080/SGCM/api/reportes/empresas-por-campania/excel",
        "campanias.xlsx"
    );
}

function descargarEmpresasDetalle() {
    descargarConToken(
        "http://localhost:8080/SGCM/api/reportes/empresas-detalle/excel",
        "detalle_empresas.xlsx"
    );
}

function descargarConToken(url, nombreArchivo) {

    fetch(url, {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
        .then(res => res.blob())
        .then(blob => {

            const link = document.createElement("a");
            link.href = URL.createObjectURL(blob);
            link.download = nombreArchivo;
            link.click();

        })
        .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded", () => {

    renderGrafico(
        "bar",
        ["Sin datos"],
        [0],
        "Selecciona un reporte"
    );

});

function mostrarGrafico(titulo) {
    document.getElementById("cardGrafico").classList.remove("d-none");
}