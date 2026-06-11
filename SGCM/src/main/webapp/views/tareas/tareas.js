let listaTareas = [];
let opcionesCampanias = [];
let opcionesUsuarios = [];
let modal = null;
let tareaSeleccionadaId = null;
let comentariosGlobal = [];

function logout() {
    localStorage.removeItem("token");
    window.location.href = "../login/login.html";
}

function toggleSidebar() {
    document.querySelector(".sidebar").classList.toggle("collapsed");
}

const ESTADOS = [
    { key: "PENDIENTE", id: 1, label: "Pendiente" },
    { key: "EN PROCESO", id: 2, label: "En proceso" },
    { key: "COMPLETADA", id: 3, label: "Completada" },
    { key: "CANCELADA", id: 4, label: "Cancelada" }
];

function inicializarTareas() {
    const modalElement = document.getElementById("modalTarea");
    if (!modalElement) {
        console.warn("No se encontró el modal de tarea al inicializar tareas.");
        return;
    }
    modal = new bootstrap.Modal(modalElement);
    cargarOpciones().finally(() => cargarTareas());
}

function cargarTareas() {

    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/tareas/listar", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(async res => {
            const data = await res.json();
            console.log("RESPONSE COMPLETO:", data);

            if (!res.ok) {
                throw new Error(data.errors?.join(" ") || "Error API");
            }

            return data;
        })
        .then(data => {

            console.log("VALUE:", data.value);

            listaTareas = (data.value || []).map(normalizarTarea);

            console.log("NORMALIZADAS:", listaTareas);

            renderizarTablero(listaTareas);
        })
        .catch(err => {
            console.error("❌ ERROR:", err);
        });
}


function normalizarTarea(tarea) {
    const campania = opcionesCampanias.find(c => String(c.idCampania) === String(tarea.idCampania) || String(c.nombre).trim() === String(tarea.campania).trim());
    const responsable = opcionesUsuarios.find(u => String(u.idUsuario) === String(tarea.idUsuarioResponsable) || String(u.username).trim() === String(tarea.responsable).trim());

    const idCampania = tarea.idCampania || (campania ? campania.idCampania : null);
    const nombreCampania = tarea.campania || (campania ? campania.nombre : "Sin campaña");

    return {
        ...tarea,
        idCampania,
        idEstadoTarea: tarea.idEstadoTarea || getEstadoId(tarea.estado),
        idPrioridadTarea: tarea.idPrioridadTarea || getPrioridadId(tarea.prioridad),
        idUsuarioResponsable: tarea.idUsuarioResponsable || (responsable ? responsable.idUsuario : null),
        estado: tarea.estado || getEstadoLabel(tarea.idEstadoTarea || getEstadoId(tarea.estado)),
        prioridad: tarea.prioridad || getPrioridadLabel(tarea.idPrioridadTarea || getPrioridadId(tarea.prioridad)),
        campania: nombreCampania,

        responsable: tarea.responsable ||
            (responsable ? `${responsable.nombres} ${responsable.apellidos}` : "Sin responsable")
    };
}

function filtrarPorCampania(lista, filtroCampaniaId) {
    if (!filtroCampaniaId) {
        return lista;
    }

    const campaniaSeleccionada = opcionesCampanias.find(c => String(c.idCampania) === String(filtroCampaniaId));
    const nombreCampaniaSeleccionada = campaniaSeleccionada ? String(campaniaSeleccionada.nombre).trim() : null;

    return lista.filter(t => {
        if (String(t.idCampania || "") === String(filtroCampaniaId)) {
            return true;
        }
        return nombreCampaniaSeleccionada && String(t.campania).trim() === nombreCampaniaSeleccionada;
    });
}

function filtrarTareas() {
    const texto = document.getElementById("buscar").value.toLowerCase();
    const estado = document.getElementById("filtroEstado").value;
    const filtroCampaniaId = localStorage.getItem("filtroCampaniaId");
    const campaniaSeleccionada = opcionesCampanias.find(c => String(c.idCampania) === String(filtroCampaniaId));
    const nombreCampaniaSeleccionada = campaniaSeleccionada ? String(campaniaSeleccionada.nombre).trim() : null;

    const filtrado = listaTareas.filter(t => {
        const coincideTexto = (t.nombre || "").toLowerCase().includes(texto) || (t.descripcion || "").toLowerCase().includes(texto);
        const coincideEstado = estado === "TODOS" || getEstadoKey(t.estado) === estado;
        const coincideCampania = !filtroCampaniaId || String(t.idCampania || "") === String(filtroCampaniaId) || (nombreCampaniaSeleccionada && String(t.campania || "").trim() === nombreCampaniaSeleccionada);
        return coincideTexto && coincideEstado && coincideCampania;
    });

    renderizarTablero(filtrado);
}

function abrirModal() {
    mostrarModalTarea("create", null);
}

function cerrarModal() {
    if (modal) {
        modal.hide();
    }
}

function mostrarModalTarea(modo, id) {
    const form = document.getElementById("formTarea");
    form.reset();
    document.getElementById("idTarea").value = "";
    document.getElementById("listaComentarios").innerHTML = "";
    document.getElementById("nuevoComentario").value = "";
    tareaSeleccionadaId = null;

    if (modo === "edit" || modo === "view") {
        const tarea = listaTareas.find(x => x.idTarea === id);
        if (!tarea) return;

        tareaSeleccionadaId = id;
        document.getElementById("idTarea").value = id;
        document.getElementById("nombre").value = tarea.nombre || "";
        document.getElementById("descripcion").value = tarea.descripcion || "";
        document.getElementById("idCampania").value = tarea.idCampania || "";
        document.getElementById("idEstadoTarea").value = tarea.idEstadoTarea || 1;
        document.getElementById("idPrioridadTarea").value = tarea.idPrioridadTarea || 1;
        document.getElementById("idUsuarioResponsable").value = tarea.idUsuarioResponsable || "";
        document.getElementById("fechaInicio").value = tarea.fechaInicio || "";
        document.getElementById("fechaLimite").value = tarea.fechaLimite || "";
        document.getElementById("porcentajeAvance").value = tarea.porcentajeAvance || 0;
        cargarComentarios(id);
    } else {
        const campaniaPreseleccionada = localStorage.getItem("filtroCampaniaId") || "";
        if (campaniaPreseleccionada) {
            document.getElementById("idCampania").value = campaniaPreseleccionada;
        }
    }

    if (modo === "create") {
        document.getElementById("modalTareaTitle").textContent = "Nueva Tarea";
    } else if (modo === "edit") {
        document.getElementById("modalTareaTitle").textContent = "Editar Tarea";
    } else {
        document.getElementById("modalTareaTitle").textContent = "Ver Tarea";
    }

    const isReadOnly = modo === "view";
    const fields = document.querySelectorAll('#formTarea input, #formTarea textarea, #formTarea select');
    fields.forEach(field => {
        field.disabled = isReadOnly;
        field.classList.toggle("bg-light", isReadOnly);
    });

    document.getElementById("btnGuardarTarea").classList.toggle("d-none", isReadOnly);
    document.getElementById("btnCancelarTarea").textContent = isReadOnly ? "Cerrar" : "Cancelar";
    modal.show();
}

function ver(id) {
    mostrarModalTarea("view", id);
}

function editar(id) {
    mostrarModalTarea("edit", id);
}

function guardarOActualizar() {
    const token = localStorage.getItem("token");
    const id = document.getElementById("idTarea").value;
    const nombre = document.getElementById("nombre").value.trim();

    if (!nombre) {
        alert("El nombre de la tarea es obligatorio.");
        return;
    }

    let data;

    if (id) {
        // ACTUALIZAR
        data = {
            idTarea: parseInt(id, 10),
            idCampania: parseInt(document.getElementById("idCampania").value, 10),
            idEstadoTarea: parseInt(document.getElementById("idEstadoTarea").value, 10),
            idPrioridadTarea: parseInt(document.getElementById("idPrioridadTarea").value, 10),

            idUsuarioResponsable: parseInt(document.getElementById("idUsuarioResponsable").value),
            nombre,
            descripcion: document.getElementById("descripcion").value.trim(),
            fechaInicio: document.getElementById("fechaInicio").value,
            fechaLimite: document.getElementById("fechaLimite").value
        };
    } else {
        // CREAR (SIN idTarea ni porcentajeAvance)
        data = {
            idCampania: parseInt(document.getElementById("idCampania").value, 10),
            idEstadoTarea: parseInt(document.getElementById("idEstadoTarea").value, 10),
            idPrioridadTarea: parseInt(document.getElementById("idPrioridadTarea").value, 10),
            idUsuarioResponsable: parseInt(document.getElementById("idUsuarioResponsable").value),
            nombre,
            descripcion: document.getElementById("descripcion").value.trim(),
            fechaInicio: document.getElementById("fechaInicio").value,
            fechaLimite: document.getElementById("fechaLimite").value
        };
    }

    const url = id ? "http://localhost:8080/SGCM/api/tareas/actualizar" : "http://localhost:8080/SGCM/api/tareas/crear";
    const method = id ? "PUT" : "POST";

    fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(data)
    })
        .then(async res => {
            const text = await res.text();
            if (!res.ok) {
                throw new Error(text || "No se pudo guardar la tarea");
            }
            cerrarModal();
            cargarTareas();
        })
        .catch(error => {
            console.error(error);
            alert(`No se pudo guardar la tarea. ${error.message}`);
        });
}

function cargarOpciones() {
    const token = localStorage.getItem("token");

    return Promise.all([
        fetch("http://localhost:8080/SGCM/api/campanias/listar", {
            headers: { "Authorization": "Bearer " + token }
        }),
        fetch("http://localhost:8080/SGCM/api/usuarios/listar", {
            headers: { "Authorization": "Bearer " + token }
        })
    ])
        .then(async ([campaniaRes, usuariosRes]) => {
            const campaniasData = await campaniaRes.json();
            const usuariosData = await usuariosRes.json();
            opcionesCampanias = campaniasData.value || [];
            opcionesUsuarios = usuariosData.value || [];
            poblarSelects();
        })
        .catch(error => console.error(error));
}

function poblarSelects() {
    const selectCampania = document.getElementById("idCampania");
    const selectResponsable = document.getElementById("idUsuarioResponsable");

    if (selectCampania) {
        selectCampania.innerHTML = '<option value="">Selecciona una campaña</option>' + opcionesCampanias.map(c => `<option value="${c.idCampania}">${c.nombre || "Sin nombre"}</option>`).join("");
    }

    if (selectResponsable) {
        selectResponsable.innerHTML =
            '<option value="">Selecciona un responsable</option>' +
            opcionesUsuarios.map(u => `
            <option value="${u.idUsuario}">
                ${u.nombres} ${u.apellidos} (${u.username})
            </option>
    `).join("");
    }
}

function renderizarTablero(lista) {
    const board = document.getElementById("kanbanBoard");
    if (!board) return;

    if (!lista.length) {
        board.innerHTML = '<div class="alert alert-light text-center">No hay tareas registradas para esta vista.</div>';
        return;
    }

    const columnas = ESTADOS.map(estado => ({
        ...estado,
        tareas: lista.filter(t => getEstadoKey(t.estado) === estado.key)
    }));

    board.innerHTML = `
        <div class="row g-3">
            ${columnas.map(col => `
                <div class="col-12 col-xl-3">
                    <div class="kanban-column">
                        <div class="kanban-column-header">
                            <div>
                                <h6 class="fw-bold mb-1">${col.label}</h6>
                                <small class="text-muted">${col.tareas.length} tarea${col.tareas.length === 1 ? "" : "s"}</small>
                            </div>
                            <span class="badge rounded-pill bg-light text-dark">${col.tareas.length}</span>
                        </div>
                        <div class="kanban-column-body">
                            ${col.tareas.length ? col.tareas.map(t => `
                                <div class="task-card">
                                    <div class="d-flex justify-content-between align-items-start gap-2 mb-2">
                                        <div>
                                            <h6 class="fw-bold mb-1">${t.nombre || "Sin nombre"}</h6>
                                            <small class="text-muted">${t.campania || "Sin campaña"}</small>
                                        </div>
                                        <span class="badge rounded-pill ${getEstadoClass(t.estado)}">${t.estado || col.label}</span>
                                    </div>
                                    <p class="small text-muted mb-3">${t.descripcion ? t.descripcion.substring(0, 110) + (t.descripcion.length > 110 ? "..." : "") : "Sin descripción"}</p>
                                    <div class="d-flex justify-content-between align-items-center mb-3 text-muted small">
                                        <span><i class="fa fa-user me-1"></i>${t.responsable || "Sin responsable"}</span>
                                        <span><i class="fa fa-calendar me-1"></i>${t.fechaLimite || "Sin fecha"}</span>
                                    </div>
                                    <div class="progress mb-3" role="progressbar">
                                        <div class="progress-bar" style="width:${t.porcentajeAvance ?? 0}%"></div>
                                    </div>
                                    <div class="d-flex flex-wrap gap-2">
                                        <button type="button" class="btn btn-sm btn-outline-primary" onclick="editar(${t.idTarea})"><i class="fa fa-edit"></i></button>
                                        <button type="button" class="btn btn-sm btn-outline-secondary" onclick="ver(${t.idTarea})"><i class="fa fa-eye"></i></button>
                                        <button type="button" class="btn btn-sm btn-outline-danger" onclick="cancelarTarea(${t.idTarea})"><i class="fa fa-times"></i></button>
                                    </div>
                                    <div class="d-flex justify-content-between gap-2 mt-3">
                                        ${getEstadoAnterior(col.key) ? `<button type="button" class="btn btn-sm btn-light" onclick="cambiarEstado(${t.idTarea}, ${getEstadoId(getEstadoAnterior(col.key))})"><i class="fa fa-arrow-left me-1"></i> ${getEstadoAnterior(col.key)}</button>` : ""}
                                        ${getEstadoSiguiente(col.key) ? `<button type="button" class="btn btn-sm btn-light" onclick="cambiarEstado(${t.idTarea}, ${getEstadoId(getEstadoSiguiente(col.key))})">${getEstadoSiguiente(col.key)} <i class="fa fa-arrow-right ms-1"></i></button>` : ""}
                                    </div>
                                </div>
                            `).join("") : '<div class="empty-column">Sin tareas en esta etapa.</div>'}
                        </div>
                    </div>
                </div>
            `).join("")}
        </div>
    `;
}

function getEstadoClass(estado) {
    const key = getEstadoKey(estado);
    if (key === "COMPLETADA") return "bg-success-subtle text-success";
    if (key === "EN PROCESO") return "bg-warning-subtle text-warning";
    if (key === "PENDIENTE") return "bg-primary-subtle text-primary";
    if (key === "CANCELADA") return "bg-secondary-subtle text-secondary";
    return "bg-light text-dark";
}

function getEstadoKey(estado) {
    if (!estado) return "PENDIENTE";
    const normalized = String(estado).trim().toUpperCase();
    const map = {
        "1": "PENDIENTE",
        "2": "EN PROCESO",
        "3": "COMPLETADA",
        "4": "CANCELADA",
        "PENDIENTE": "PENDIENTE",
        "EN PROCESO": "EN PROCESO",
        "EN PROCESO ": "EN PROCESO",
        "COMPLETADA": "COMPLETADA",
        "CANCELADA": "CANCELADA"
    };
    return map[normalized] || normalized;
}

function getEstadoId(estado) {
    const key = getEstadoKey(estado);
    const found = ESTADOS.find(e => e.key === key);
    return found ? found.id : 1;
}

function getEstadoLabel(id) {
    const found = ESTADOS.find(e => e.id === Number(id));
    return found ? found.key : "PENDIENTE";
}

function getEstadoAnterior(estadoKey) {
    const index = ESTADOS.findIndex(e => e.key === estadoKey);
    return index > 0 ? ESTADOS[index - 1].key : null;
}

function getEstadoSiguiente(estadoKey) {
    const index = ESTADOS.findIndex(e => e.key === estadoKey);
    return index >= 0 && index < ESTADOS.length - 1 ? ESTADOS[index + 1].key : null;
}

function getPrioridadId(prioridad) {
    if (!prioridad) return 1;
    const normalized = String(prioridad).trim().toUpperCase();
    if (normalized.includes("ALTA")) return 3;
    if (normalized.includes("MEDIA")) return 2;
    return 1;
}

function getPrioridadLabel(id) {
    if (Number(id) === 3) return "Alta";
    if (Number(id) === 2) return "Media";
    return "Baja";
}

function cambiarEstado(idTarea, idEstado) {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/tareas/estado", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ idTarea, idEstadoTarea: idEstado })
    })
        .then(async res => {
            const text = await res.text();
            if (!res.ok) {
                throw new Error(text || "No se pudo actualizar el estado");
            }
            cargarTareas();
        })
        .catch(error => {
            console.error(error);
            alert("No se pudo actualizar el estado de la tarea.");
        });
}

function cancelarTarea(idTarea) {
    cambiarEstado(idTarea, 4);
}

function cargarComentarios(idTarea) {
    const token = localStorage.getItem("token");

    fetch(`http://localhost:8080/SGCM/api/comentarios/tarea/${idTarea}`, {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(async res => {
            const data = await res.json();
            if (!res.ok) {
                throw new Error(data.errors?.join(" ") || "No se pudieron cargar los comentarios");
            }
            return data;
        })
        .then(data => {
            comentariosGlobal = data.value || [];
            renderizarComentarios();
        })
        .catch(error => {
            console.error(error);
            document.getElementById("listaComentarios").innerHTML = '<div class="text-muted small">No se pudieron cargar los comentarios.</div>';
        });
}

function renderizarComentarios() {
    const container = document.getElementById("listaComentarios");
    if (!container) return;

    if (!comentariosGlobal.length) {
        container.innerHTML = '<div class="text-muted small">Aún no hay comentarios.</div>';
        return;
    }

    container.innerHTML = comentariosGlobal.map(c => `
        <div class="comment-item">
            <div class="fw-semibold small">${c.usuario || "Usuario"}</div>
            <div class="small text-muted">${c.comentario || ""}</div>
        </div>
    `).join("");
}

function guardarComentario() {
    const token = localStorage.getItem("token");
    const comentario = document.getElementById("nuevoComentario").value.trim();

    if (!tareaSeleccionadaId || !comentario) {
        return;
    }

    fetch("http://localhost:8080/SGCM/api/comentarios/crear", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ idTarea: tareaSeleccionadaId, comentario })
    })
        .then(async res => {
            const text = await res.text();
            if (!res.ok) {
                throw new Error(text || "No se pudo guardar el comentario");
            }
            document.getElementById("nuevoComentario").value = "";
            cargarComentarios(tareaSeleccionadaId);
        })
        .catch(error => {
            console.error(error);
            alert("No se pudo guardar el comentario.");
        });
}



document.addEventListener("DOMContentLoaded", () => {

    if (document.getElementById("kanbanBoard")) {
        inicializarTareas();
    }

});
``
