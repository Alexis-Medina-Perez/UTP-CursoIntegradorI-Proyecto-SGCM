let logs = [];
let logsFiltrados = [];

let paginaActual = 1;
const porPagina = 5;

/* ================= DATOS FALSOS ================= */
document.addEventListener("DOMContentLoaded", () => {

    logs = generarLogsFake(30);
    aplicarFiltros();
});

/* ================= GENERAR DATA ================= */
function generarLogsFake(cantidad) {

    const usuarios = ["Maria", "Carlos", "Ana", "Luis"];
    const operaciones = ["CREATE", "UPDATE", "DELETE"];
    const tablas = ["usuario", "campaña", "tarea"];

    const lista = [];

    for (let i = 1; i <= cantidad; i++) {

        lista.push({
            fecha: `2026-05-${10 + (i % 10)} 10:${i}:00`,
            usuario: usuarios[i % usuarios.length],
            operacion: operaciones[i % 3],
            tabla: tablas[i % 3],
            id: `ID-${i}`,
            descripcion: "Operación realizada en el sistema",
            ip: `192.168.1.${i}`
        });
    }

    return lista;
}

/* ================= FILTRO ================= */
function filtrarLogs() {
    aplicarFiltros();
}

function aplicarFiltros() {

    const texto = buscador.value.toLowerCase();
    const op = filtroOperacion.value;
    const tabla = filtroTabla.value;

    logsFiltrados = logs.filter(l =>
        l.usuario.toLowerCase().includes(texto) &&
        (op === "" || l.operacion === op) &&
        (tabla === "" || l.tabla === tabla)
    );

    paginaActual = 1;

    renderKPIs();
    renderTabla();
}

/* ================= KPIs ================= */
function renderKPIs() {

    document.getElementById("totalLogs").innerText = logsFiltrados.length;
    document.getElementById("totalCreate").innerText = logsFiltrados.filter(x => x.operacion === "CREATE").length;
    document.getElementById("totalUpdate").innerText = logsFiltrados.filter(x => x.operacion === "UPDATE").length;
    document.getElementById("totalDelete").innerText = logsFiltrados.filter(x => x.operacion === "DELETE").length;
}

/* ================= TABLA ================= */
function renderTabla() {

    const inicio = (paginaActual - 1) * porPagina;
    const data = logsFiltrados.slice(inicio, inicio + porPagina);

    tablaLogs.innerHTML = data.map(l => `
        <tr>
            <td>${l.fecha}</td>
            <td>${l.usuario}</td>
            <td><span class="badge ${colorOperacion(l.operacion)}">${l.operacion}</span></td>
            <td>${l.tabla}</td>
            <td>${l.id}</td>
            <td>${l.descripcion}</td>
            <td>${l.ip}</td>
        </tr>
    `).join("");

    renderPaginacion();
}

/* ================= COLORES ================= */
function colorOperacion(op) {
    if (op === "CREATE") return "bg-success";
    if (op === "UPDATE") return "bg-primary";
    return "bg-danger";
}

/* ================= PAGINACIÓN ================= */
function renderPaginacion() {

    const totalPaginas = Math.ceil(logsFiltrados.length / porPagina);

    paginacion.innerHTML = "";

    for (let i = 1; i <= totalPaginas; i++) {

        paginacion.innerHTML += `
            <li class="page-item ${i === paginaActual ? 'active' : ''}">
                <button class="page-link" onclick="cambiarPagina(${i})">${i}</button>
            </li>
        `;
    }
}

function cambiarPagina(pag) {
    paginaActual = pag;
    renderTabla();
}
