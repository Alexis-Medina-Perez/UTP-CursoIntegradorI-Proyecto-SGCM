var listaCampanias = [];
var modoVista = "list";
var modal = null;
var modalEliminar = null;
var idCampaniaEliminar = null;
var modoModal = "create";

function logout() {
    localStorage.removeItem("token");
    window.location.href = "../login/login.html";
}

function toggleSidebar() {
    document.querySelector(".sidebar").classList.toggle("collapsed");
}

function getModalCampania() {
    var element = document.getElementById('modalCampania');
    if (!element) return null;
    if (!modal || modal._element !== element) {
        modal = new bootstrap.Modal(element);
    }
    return modal;
}

function getModalEliminar() {
    var element = document.getElementById('modalConfirmEliminar');
    if (!element) return null;
    if (!modalEliminar || modalEliminar._element !== element) {
        modalEliminar = new bootstrap.Modal(element);
    }
    return modalEliminar;
}

function cargarCampanias() {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/campanias/listar", {
        headers: {
            "Authorization": "Bearer " + token
        }
    })
        .then(res => res.json())
        .then(data => {
            listaCampanias = data.value || [];
            if (modoVista === "grid") {
                pintarCards(listaCampanias);
            } else {
                pintarTabla(listaCampanias);
            }
        });
}

function pintarTabla(lista) {
    let html = "";

    lista.forEach(c => {
        let progreso = Math.floor(Math.random() * 100);

        html += `
        <tr>
            <td>
                <strong>${c.nombre}</strong><br>
                <small class="text-muted">${c.descripcion || ''}</small>
            </td>
            <td>${c.tipo || ''}</td>
            <td>
                <span class="badge rounded-pill ${getEstadoClass(c.estado)}">${c.estado || ''}</span>
            </td>
            <td>
                <small>${c.fechaInicioText || ''}<br>${c.fechaFinText || ''}</small>
            </td>
            <td>$${c.presupuesto || 0}</td>
            <td>
                <div class="progress" role="progressbar">
                    <div class="progress-bar" style="width:${progreso}%"></div>
                </div>
                <small class="text-muted">${progreso}%</small>
            </td>
            <td class="actions">
                <i class="fa fa-eye me-2" onclick="ver(${c.idCampania})"></i>
                <i class="fa fa-edit me-2" onclick="editar(${c.idCampania})"></i>
                <i class="fa fa-trash me-2" onclick="confirmarEliminar(${c.idCampania})"></i>
            </td>
        </tr>
        `;
    });

    document.getElementById("tablaCampanias").innerHTML = html;
    document.getElementById("tablaContainer").classList.remove("d-none");
    document.getElementById("contenedorVista").classList.add("d-none");
}

function getEstadoClass(estado) {
    if (estado === "ACTIVA") return "bg-success-subtle text-success";
    if (estado === "PLANIFICADA") return "bg-primary-subtle text-primary";
    if (estado === "FINALIZADA") return "bg-secondary-subtle text-secondary";
    if (estado === "EN PROCESO") return "bg-warning-subtle text-warning";
    return "bg-light text-dark";
}

function getEstadoId(estado) {
    if (estado === 1 || estado === "1" || estado === "ACTIVA") return 1;
    if (estado === 2 || estado === "2" || estado === "EN PROCESO") return 2;
    if (estado === 3 || estado === "3" || estado === "FINALIZADA") return 3;
    return 1;
}

function getTipoId(tipo) {
    if (tipo === 1 || tipo === "1" || /marketing/i.test(tipo)) return 1;
    if (tipo === 2 || tipo === "2" || /publicidad/i.test(tipo)) return 2;
    if (tipo === 3 || tipo === "3" || /email/i.test(tipo)) return 3;
    return 1;
}

function filtrarCampanias() {
    let texto = document.getElementById("buscar").value.toLowerCase();
    let filtrado = listaCampanias.filter(c => (c.nombre || "").toLowerCase().includes(texto));

    if (modoVista === "grid") {
        pintarCards(filtrado);
    } else {
        pintarTabla(filtrado);
    }
}

function filtrarPorEstado(estado) {
    if (estado === "TODAS") {
        if (modoVista === "grid") {
            pintarCards(listaCampanias);
        } else {
            pintarTabla(listaCampanias);
        }
        return;
    }

    let filtrado = listaCampanias.filter(c => c.estado === estado);

    if (modoVista === "grid") {
        pintarCards(filtrado);
    } else {
        pintarTabla(filtrado);
    }
}

function abrirModal() {
    mostrarModalCampania("create", null);
}

function cerrarModal() {
    var m = getModalCampania();
    if (m) {
        m.hide();
    }
}

function mostrarModalCampania(modo, id) {
    const form = document.getElementById("formCampania");
    form.reset();
    document.getElementById("idCampania").value = "";
    modoModal = modo;

    if (modo === "edit" || modo === "view") {
        let c = listaCampanias.find(x => x.idCampania === id);
        if (!c) return;

        document.getElementById("idCampania").value = id;
        document.getElementById("nombre").value = c.nombre || "";
        document.getElementById("descripcion").value = c.descripcion || "";
        document.getElementById("objetivo").value = c.objetivo || "";
        document.getElementById("fechaInicio").value = c.fechaInicioText || "";
        document.getElementById("fechaFin").value = c.fechaFinText || "";
        document.getElementById("presupuesto").value = c.presupuesto || "";
        document.getElementById("estado").value = getEstadoId(c.idEstadoCampania || c.estado || 1);
        document.getElementById("tipo").value = getTipoId(c.idTipoCampania || c.tipo || 1);
    }

    if (modo === "create") {
        document.getElementById("modalCampaniaTitle").textContent = "Nueva Campaña";
    } else if (modo === "edit") {
        document.getElementById("modalCampaniaTitle").textContent = "Editar Campaña";
    } else {
        document.getElementById("modalCampaniaTitle").textContent = "Ver Campaña";
    }

    const isReadOnly = modo === "view";
    const fields = document.querySelectorAll('#formCampania input, #formCampania textarea, #formCampania select');
    fields.forEach(field => {
        field.disabled = isReadOnly;
        field.classList.toggle('bg-light', isReadOnly);
    });

    document.getElementById("btnGuardarModal").classList.toggle("d-none", isReadOnly);
    document.getElementById("btnCancelarModal").textContent = isReadOnly ? "Cerrar" : "Cancelar";

    var m = getModalCampania();
    if (m) {
        m.show();
    }
}

function guardarCampania() {
    guardarOActualizar();
}

function verTareasDeCampania(id, nombre) {
    localStorage.setItem("filtroCampaniaId", id);
    localStorage.setItem("filtroCampaniaNombre", nombre || "");
    localStorage.setItem("tareasDesdeCampania", "1");
    cargarVista('tareas/tareas.html');
}

function ver(id) {
    mostrarModalCampania("view", id);
}

function editar(id) {
    mostrarModalCampania("edit", id);
}

function guardarOActualizar() {
    const token = localStorage.getItem("token");
    const id = document.getElementById("idCampania").value;
    const nombre = document.getElementById("nombre").value.trim();
    const descripcion = document.getElementById("descripcion").value.trim();
    const objetivo = document.getElementById("objetivo").value.trim();
    const fechaInicio = document.getElementById("fechaInicio").value;
    const fechaFin = document.getElementById("fechaFin").value;

    if (!nombre) {
        alert("El nombre de la campaña es obligatorio.");
        return;
    }

    const baseData = {
        nombre,
        descripcion,
        objetivo,
        fechaInicio: fechaInicio || "",
        fechaFin: fechaFin || "",
        presupuesto: Number(document.getElementById("presupuesto").value || 0),
        idEstadoCampania: parseInt(document.getElementById("estado").value, 10),
        idTipoCampania: parseInt(document.getElementById("tipo").value, 10)
    };

    const data = id
        ? { ...baseData, idCampania: parseInt(id, 10) }
        : baseData;

    const url = id
        ? "http://localhost:8080/SGCM/api/campanias/actualizar"
        : "http://localhost:8080/SGCM/api/campanias/crear";

    const method = id ? "PUT" : "POST";

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(data)
    })
        .then(async res => {
            const text = await res.text();
            if (!res.ok) {
                let mensaje = text || "No se pudo guardar la campaña";
                try {
                    const parsed = JSON.parse(text);
                    mensaje = parsed.errors?.join(" ") || parsed.message || mensaje;
                } catch (e) {
                    // Ignorar parseo si el cuerpo no es JSON
                }
                throw new Error(mensaje);
            }
            modal.hide();
            cargarCampanias();
        })
        .catch(error => {
            console.error(error);
            alert(`No se pudo guardar la campaña. ${error.message}`);
        });
}

function confirmarEliminar(id) {
    idCampaniaEliminar = id;
    const c = listaCampanias.find(x => x.idCampania === id);
    document.getElementById("nombreCampaniaEliminar").textContent = c ? c.nombre : "esta campaña";
    var m = getModalEliminar();
    if (m) {
        m.show();
    }
}

function cerrarModalEliminar() {
    var m = getModalEliminar();
    if (m) {
        m.hide();
    }
}

function eliminarConfirmado() {
    const token = localStorage.getItem("token");

    fetch("http://localhost:8080/SGCM/api/campanias/estado", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({
            idCampania: idCampaniaEliminar,
            activo: false
        })
    })
        .then(async res => {
            const text = await res.text();
            if (!res.ok) {
                throw new Error(text || "No se pudo eliminar la campaña");
            }
            modalEliminar.hide();
            cargarCampanias();
        })
        .catch(error => {
            console.error(error);
            alert("No se pudo eliminar la campaña.");
        });
}

function cambiarVista(tipo) {
    modoVista = tipo;

    const btnGrid = document.getElementById("btnVistaGrid");
    const btnList = document.getElementById("btnVistaList");

    btnGrid.classList.toggle("btn-primary", tipo === "grid");
    btnGrid.classList.toggle("btn-outline-secondary", tipo !== "grid");
    btnGrid.classList.toggle("active", tipo === "grid");

    btnList.classList.toggle("btn-primary", tipo === "list");
    btnList.classList.toggle("btn-outline-secondary", tipo !== "list");
    btnList.classList.toggle("active", tipo === "list");

    if (tipo === "grid") {
        pintarCards(listaCampanias);
    } else {
        pintarTabla(listaCampanias);
    }
}

function pintarCards(lista) {
    let html = "";

    lista.forEach(c => {
        html += `
        <div class="col-12 col-md-6 col-xl-4">
            <div class="card card-campania h-100 border-0 shadow-sm">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-start mb-3">
                        <div>
                            <h5 class="fw-bold mb-1">${c.nombre || ''}</h5>
                            <p class="text-muted small mb-0">${c.descripcion || ''}</p>
                        </div>
                        <span class="badge rounded-pill ${getEstadoClass(c.estado)}">${c.estado || ''}</span>
                    </div>

                    <div class="mb-3">
                        <small class="text-muted d-block">${c.fechaInicioText || ''} - ${c.fechaFinText || ''}</small>
                        <p class="fw-semibold mb-0 mt-2">$${c.presupuesto || 0}</p>
                    </div>

                    <div class="actions d-flex flex-wrap gap-2">
                        <i class="fa fa-eye" onclick="ver(${c.idCampania})"></i>
                        <i class="fa fa-edit" onclick="editar(${c.idCampania})"></i>
                        <button type="button" class="btn btn-sm btn-outline-danger" onclick="confirmarEliminar(${c.idCampania})">
                            <i class="fa fa-trash"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        `;
    });

    document.getElementById("contenedorVista").innerHTML = html;
    document.getElementById("contenedorVista").classList.remove("d-none");
    document.getElementById("tablaContainer").classList.add("d-none");
}


document.addEventListener("DOMContentLoaded", () => {

    console.log("✅ Cargando campañas...");

    cargarCampanias(); 
    cambiarVista(modoVista);
});
