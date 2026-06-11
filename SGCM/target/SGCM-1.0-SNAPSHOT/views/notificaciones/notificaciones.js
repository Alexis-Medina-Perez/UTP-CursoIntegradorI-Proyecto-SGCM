let listaNotificaciones = [];

function logout() {
    localStorage.removeItem("token");
    window.location.href = "../login/login.html";
}

function toggleSidebar() {
    document.querySelector(".sidebar").classList.toggle("collapsed");
}

function cargarNotificaciones() {
    console.log("✅ cargarNotificaciones ejecutado");

    const token = localStorage.getItem("token");
    const userId = obtenerUserIdDesdeToken();

    fetch(`http://localhost:8080/SGCM/api/notificaciones/usuario/${userId}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
    .then(res => res.json())
    .then(data => {
        console.log("DATA:", data);

        listaNotificaciones = data.value || [];
        pintarNotificaciones(listaNotificaciones);
    })
    .catch(err => {
        console.error(err);
        document.getElementById("listaNotificaciones").innerHTML =
            '<div class="alert alert-danger">No se pudieron cargar</div>';
    });
}


function pintarNotificaciones(lista) {
    const contenedor = document.getElementById("listaNotificaciones");

    if (!lista.length) {
        contenedor.innerHTML = '<div class="alert alert-light text-center">No tienes notificaciones.</div>';
        return;
    }

    contenedor.innerHTML = lista.map(n => {
        const leida = n.leido ? "notificacion-leida" : "notificacion-no-leida";
        const badge = n.leido ? 'bg-secondary-subtle text-secondary' : 'bg-primary-subtle text-primary';
        const textoLeido = n.leido ? 'Leída' : 'No leída';

        return `
            <div class="card notificacion-item ${leida} border-0 shadow-sm">
                <div class="card-body d-flex justify-content-between align-items-start gap-3">
                    <div>
                        <div class="d-flex align-items-center gap-2 mb-2">
                            <h5 class="fw-bold mb-0">${n.titulo || 'Notificación'}</h5>
                            <span class="badge rounded-pill ${badge}">${textoLeido}</span>
                        </div>
                        <p class="text-muted mb-0">${n.mensaje || ''}</p>
                    </div>
                    ${!n.leido ? `<button class="btn btn-sm btn-outline-primary" onclick="marcarLeida(${n.idNotificacion})">Marcar como leída</button>` : ''}
                </div>
            </div>
        `;
    }).join('');
}

function filtrarNotificaciones() {
    const texto = document.getElementById("buscarNotificacion").value.toLowerCase();
    const filtro = document.getElementById("filtroNotificacion").value;

    const filtradas = listaNotificaciones.filter(n => {
        const coincideTexto = (n.titulo || "").toLowerCase().includes(texto) || (n.mensaje || "").toLowerCase().includes(texto);
        const coincideEstado = filtro === "TODAS" || (filtro === "NO_LEIDAS" && !n.leido) || (filtro === "LEIDAS" && n.leido);
        return coincideTexto && coincideEstado;
    });

    pintarNotificaciones(filtradas);
}

function marcarLeida(id) {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/notificaciones/leido", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ idNotificacion: id })
    })
        .then(() => cargarNotificaciones());
}

function obtenerUserIdDesdeToken() {
    const token = localStorage.getItem("token");

    if (!token) return null;

    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.id_usuario; // 👈 TU CAMPO REAL
    } catch (e) {
        console.error("Error leyendo token", e);
        return null;
    }
}

document.addEventListener("DOMContentLoaded", () => {

    console.log("DOM cargado");

    if (document.getElementById("listaNotificaciones")) {
        cargarNotificaciones();
    }

});
