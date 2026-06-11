var chartTareas = null;
var chartCampanias = null;

function logout() {
    localStorage.removeItem("token");
    window.location.href = "../login/login.html";
}

function toggleSidebar() {
    document.querySelector(".sidebar").classList.toggle("collapsed");
}

function cargarDashboard() {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/reportes/tareas-por-estado", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => {

        const lista = data.value;

        crearGraficoTareas(lista);

        let completadas = 0;
        let pendientes = 0;
        let enProceso = 0;

        lista.forEach(item => {
            if (item.estado === "COMPLETADA") completadas = item.total;
            if (item.estado === "PENDIENTE") pendientes = item.total;
            if (item.estado === "EN PROCESO") enProceso = item.total;
        });

        document.getElementById("tareasCompletadas").innerText = completadas;
        document.getElementById("tareasPendientes").innerText = pendientes;
        document.getElementById("tareasProceso").innerText = enProceso;

    });

    cargarGraficoCampanias();
}

function crearGraficoTareas(dataList) {

    if (chartTareas) {
        chartTareas.destroy();
    }

    let labels = [];
    let values = [];
    let total = 0;

    dataList.forEach(item => {
        labels.push(item.estado);
        values.push(item.total);
        total += item.total;
    });

    chartTareas = new Chart(document.getElementById("graficoTareas"), {
        type: 'pie',
        data: {
            labels: labels,
            datasets: [{
                data: values,
                backgroundColor: [
                    '#22c55e', // verde
                    '#3b82f6', // azul
                    '#f59e0b'  // naranja
                ]
            }]
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function (context) {
                            let value = context.raw;
                            let percentage = ((value / total) * 100).toFixed(1);
                            return context.label + ": " + percentage + "%";
                        }
                    }
                }
            }
        }
    });
}

function cargarGraficoCampanias() {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/reportes/tareas-por-campania", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => {

        const lista = data.value;

        console.log("📊 campañas:", lista);

        if (!lista || lista.length === 0) {
            console.warn("⚠️ Sin datos");
            return;
        }

        if (chartCampanias) {
            chartCampanias.destroy();
        }

        let labels = [];
        let values = [];

        lista.forEach(item => {
            labels.push(item.campania);
            values.push(item.totalTareas);
        });

        chartCampanias = new Chart(document.getElementById("graficoCampanias"), {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Tareas por Campaña',
                        data: values,
                        backgroundColor: '#3b82f6'
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top'
                    }
                }
            }
        });

    });
}

window.onload = function () {
    cargarDashboard();
};
