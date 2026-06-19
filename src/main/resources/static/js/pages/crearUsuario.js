import { UsuarioApi } from "../api/usuarioAPI.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    root.innerHTML = `
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        
        <a href="usuariosLista.html" class="btn btn-link mb-3 text-decoration-none text-success fw-bold">
            ← Volver a la lista
        </a>

        <div class="mb-4 text-center">
            <h1 class="h3 fw-bold">Registro de Nuevo Usuario</h1>
            <p class="text-muted">Completa los datos para dar de alta un nuevo usuario</p>
        </div>
        
        <form id="formulario-crear-usuario" class="needs-validation" novalidate>
            
            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="nombre" placeholder="Nombre" required>
                        <label for="nombre">Nombre</label>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="apellido" placeholder="Apellido" required>
                        <label for="apellido">Apellido</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="email" class="form-control" id="email" placeholder="ejemplo@correo.com" required>
                        <label for="email">Correo Electrónico</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="nivelExperiencia" min="0" max="10" step="0.1" placeholder="2.5" required>
                        <label for="nivelExperiencia">Nivel de Experiencia (0-10)</label>
                    </div>
                </div>
            </div>

            <div class="d-grid mt-4 mb-5">
                <button type="submit" class="btn btn-success btn-lg fw-bold">Crear Usuario</button>
            </div>
            
        </form>
    </div>
`;

    //Parametros de la cabecera
    const parametros = new URLSearchParams(window.location.search);

    const formularioCrearUsuario = document.getElementById('formulario-crear-usuario');

    //Elemento del dom con formulario-reserva(El formulario en si)
    formularioCrearUsuario.addEventListener("submit", async (e) => {
        e.preventDefault();

        const datosUsuario = {
                //Elementos que saco del formulario
                nombre: document.getElementById("nombre").value,
                apellido: document.getElementById("apellido").value,
                email: document.getElementById("email").value,
                nivelExperiencia: parseFloat(document.getElementById("nivelExperiencia").value)
            
        };

        await UsuarioApi.crear(datosUsuario);
        window.location.href = `usuariosLista.html?`;
    });
});
