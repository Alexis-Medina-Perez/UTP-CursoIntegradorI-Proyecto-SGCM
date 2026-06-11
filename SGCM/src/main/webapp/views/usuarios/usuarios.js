
let usuarios = [];
let roles = [];
let modal;

function logout() {
    localStorage.removeItem("token");
    window.location.href = "../login/login.html";
}

function toggleSidebar() {
    document.querySelector(".sidebar").classList.toggle("collapsed");
}

document.addEventListener("DOMContentLoaded", () => {

    modal = new bootstrap.Modal(document.getElementById("modalUsuario"));
    modalEliminar = new bootstrap.Modal(document.getElementById("modalEliminar"));

    document.getElementById("modalUsuario")
        .addEventListener("hidden.bs.modal", limpiarFormulario);

    cargarUsuarios();
    cargarRoles();
});

function token() {
    return localStorage.getItem("token");
}

/* ================= USUARIOS ================= */
function cargarUsuarios() {

    return fetch("http://localhost:8080/SGCM/api/usuarios/listar", {
        headers: { Authorization: "Bearer " + token() }
    })
        .then(async res => {
            const text = await res.text();

            try {
                return JSON.parse(text);
            } catch {
                throw new Error(text);
            }
        })
        .then(data => {
            usuarios = data.value || [];
            renderUsuarios(usuarios);
        });
}

function renderUsuarios(lista) {

    const tbody = document.getElementById("tablaUsuarios");

    tbody.innerHTML = lista.map(u => `
        <tr>
            <td>${u.nombres} ${u.apellidos}</td>
            <td>${u.username}</td>
            <td>${u.correo}</td>
            <td>
                <span class="badge ${u.activo ? 'bg-success' : 'bg-danger'}">
                    ${u.activo ? 'Activo' : 'Inactivo'}
                </span>
            </td>

            <td id="roles-${u.idUsuario}">Cargando...</td>

            <td>
                <button class="btn btn-sm btn-primary" onclick="editar(${u.idUsuario})">
                    <i class="fa fa-edit"></i>
                </button>
                <button class="btn btn-sm btn-danger" onclick="eliminar(${u.idUsuario})">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        </tr>
    `).join("");

    lista.forEach(u => cargarRolesUsuarioEnTabla(u.idUsuario));
}

/* ================= FILTRO ================= */
function filtrarUsuarios() {
    const texto = document.getElementById("buscarUsuario").value.toLowerCase();

    const filtrado = usuarios.filter(u =>
        u.username.toLowerCase().includes(texto) ||
        u.correo.toLowerCase().includes(texto)
    );

    renderUsuarios(filtrado);
}

/* ================= CRUD ================= */

function abrirModalUsuario() {
    document.getElementById("idUsuario").value = "";
    modal.show();
}

function editar(id) {

    const u = usuarios.find(x => x.idUsuario === id);

    document.getElementById("idUsuario").value = id;

    nombres.value = u.nombres;
    apellidos.value = u.apellidos;
    correo.value = u.correo;

    username.value = u.username;
    password.value = "";

    verRoles(id);

    modal.show();
}

function guardarUsuario() {

    const id = document.getElementById("idUsuario").value;

    let data;

    if (!id) {

        if (!username.value || !password.value) {
            alert("Username y password son obligatorios");
            return;
        }
    }

    if (id) {
        data = {
            idUsuario: parseInt(id),
            nombres: nombres.value,
            apellidos: apellidos.value,
            correo: correo.value
        };

    } else {
        data = {
            nombres: nombres.value,
            apellidos: apellidos.value,
            correo: correo.value,
            username: username.value,
            password: password.value
        };
    }

    const url = id
        ? "http://localhost:8080/SGCM/api/usuarios/actualizar"
        : "http://localhost:8080/SGCM/api/usuarios/registrar";

    fetch(url, {
        method: id ? "PUT" : "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token()
        },
        body: JSON.stringify(data)
    })
    .then(async res => {

        const resp = await res.json();

        if (!resp.success) {
            alert(resp.errors.join(", "));
            return;
        }

        await cargarUsuarios();

        if (!id) {
            const nuevo = usuarios[usuarios.length - 1];
            asignarRolNuevo(nuevo.idUsuario);
        }

        limpiarFormulario();
        modal.hide();
    })
    .catch(err => {
        console.error("Error:", err);
        alert("Error al guardar usuario");
    });
}

function eliminar(id) {
    usuarioEliminar = id;
    modalEliminar.show();
}

/* ================= ROLES ================= */

function cargarRolesUsuarioEnTabla(idUsuario) {

    const container = document.getElementById(`roles-${idUsuario}`);
    if (!container) return;

    fetch(`http://localhost:8080/SGCM/api/usuarios/${idUsuario}/roles`, {
        headers: { Authorization: "Bearer " + token() }
    })
    .then(res => res.json())
    .then(data => {

        const html = (data.value || []).map(r => `
            <span class="badge bg-primary me-1">${r.nombre}</span>
        `).join("");

        container.innerHTML = html;
    });
}

function cargarRoles() {

    fetch("http://localhost:8080/SGCM/api/roles/listar", {
        headers: { Authorization: "Bearer " + token() }
    })

        .then(async res => {
            const text = await res.text();

            try {
                return JSON.parse(text);
            } catch {
                throw new Error(text);
            }
        })
        .then(data => {
            roles = data.value || [];
            document.getElementById("selectRol").innerHTML =
                roles.map(r => `<option value="${r.idRol}">${r.nombre}</option>`).join("");
        });
}

function verRoles(idUsuario) {

    fetch(`http://localhost:8080/SGCM/api/usuarios/${idUsuario}/roles`, {
        headers: { Authorization: "Bearer " + token() }
    })
        .then(async res => {
            const text = await res.text();

            try {
                return JSON.parse(text);
            } catch {
                throw new Error(text);
            }
        })
        .then(data => {

            document.getElementById("listaRoles").innerHTML =
                (data.value || []).map(r => `
                <div class="d-flex justify-content-between border p-2 mb-1">
                    ${r.nombre}
                    <button class="btn btn-sm btn-danger" onclick="quitarRol(${idUsuario}, ${r.idRol})">X</button>
                </div>
            `).join("");
        });
}

function agregarRol() {

    const idUsuario = document.getElementById("idUsuario").value;
    const idRol = document.getElementById("selectRol").value;

    fetch("http://localhost:8080/SGCM/api/usuarios/roles/asignar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token()
        },
        body: JSON.stringify({ idUsuario, idRol })
    })
        .then(() => verRoles(idUsuario));
}

function quitarRol(idUsuario, idRol) {

    fetch("http://localhost:8080/SGCM/api/usuarios/roles/quitar", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token()
        },
        body: JSON.stringify({ idUsuario, idRol })
    })
        .then(() => verRoles(idUsuario));
}

let usuarioEliminar = null;
let modalEliminar;

function confirmarEliminar() {

    fetch(`http://localhost:8080/SGCM/api/usuarios/eliminar/${usuarioEliminar}`, {
        method: "DELETE",
        headers: { Authorization: "Bearer " + token() }
    })
        .then(() => {
            modalEliminar.hide();
            usuarioEliminar = null;
            cargarUsuarios();
        });
}

function limpiarFormulario() {

    document.getElementById("idUsuario").value = "";

    nombres.value = "";
    apellidos.value = "";
    correo.value = "";
    username.value = "";
    password.value = "";

    document.getElementById("listaRoles").innerHTML = "";
}

function asignarRolNuevo(idUsuario) {

    const idRol = document.getElementById("selectRol").value;

    fetch("http://localhost:8080/SGCM/api/usuarios/roles/asignar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token()
        },
        body: JSON.stringify({ idUsuario, idRol })
    });
}