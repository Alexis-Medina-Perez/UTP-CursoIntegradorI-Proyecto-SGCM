function login() {

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8080/SGCM/api/usuarios/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username,
            password
        })
    })
    .then(res => res.json())
    .then(data => {
        
        if (data.success && data.value && data.value.token) {


            localStorage.setItem("token", data.value.token);

            window.location.href = "../dashboard/dashboard.html";

        } else {

            alert(data.value?.respuesta || "Error en login");
        }

    })
    .catch(error => {
        console.error("ERROR:", error);
        alert("Error en el login");
    });
}
