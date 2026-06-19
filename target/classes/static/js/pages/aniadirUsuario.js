// Importamos las clases que necesitamos:

import { QuedadaApi } from '../api/quedadaAPI.js';
import { UsuarioApi } from '../api/usuarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';


// Cuando la página termine de cargar, ejecutamos toda nuestra lógica
document.addEventListener('DOMContentLoaded', async () => {
    // Añadimos la barra de navegación al elemento con id "navbar"
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    // root es donde pondremos todo el contenido de la página
    const root = document.getElementById('root');

    // Obtenemos el ID de la quedada de la URL (ejemplo: asignarPista.html?id=123)
    const parametros = new URLSearchParams(window.location.search);
    const idQuedada = parametros.get('id');

    // Obtenemos los datos de la quedada del servidor usando su ID
    const quedadaActual = await QuedadaApi.obtenerPorId(idQuedada);

    // Creamos la estructura HTML de la página:
    root.innerHTML = `
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        
        <a href="quedadaDetalle.html?id=${idQuedada}" class="btn btn-link mb-3 text-decoration-none text-success fw-bold">
            ← Volver a la quedada
        </a>

        <div class="mb-4 text-center">
            <h1 class="h3 fw-bold">Añadir usuario a la quedada</h1>
            <p class="text-muted">Quedada: <strong>${quedadaActual.id} - ${quedadaActual.ruta.nombre}</strong> (${quedadaActual.fechaEncuentro})</p>
        </div>
        
        <div class="row g-3">
            <div class="col-12">
                <div class="form-floating">
                    <select class="form-select" id="usuario-select" aria-label="Seleccionar usuario">
                        <option value="" selected disabled>Selecciona un usuario...</option>
                    </select>
                    <label for="usuario-select">Seleccionar Usuario</label>
                </div>
            </div>

        
        <div class="d-grid gap-2 d-md-flex mt-4">
            <button id="btn-guardar-usuario" class="btn btn-success btn-lg fw-bold flex-grow-1" disabled>
                Añadir a la quedada
            </button>
            <a href="quedadaDetalle.html?id=${idQuedada}" class="btn btn-outline-secondary btn-lg fw-bold px-4">
                Cancelar
            </a>
        </div>
    </div>
`;

    // Guardamos referencias a los elementos que necesitamos manipular
    const selectorUsuario = document.getElementById('usuario-select'); // Selector de usuario
    const botonGuardar = document.getElementById('btn-guardar-usuario');       // Botón de guardar

    // Rellenamos el selector de usuarios:
    // 1. Obtenemos todos los usuarios del servidor
    // 2. Por cada usuario creamos una opción en el selector
    // 3. La opción muestra "nombre e id" y guarda el ID
    const usuarios = await UsuarioApi.obtenerTodos();
    for (const usuario of usuarios) {
        const opcion = document.createElement('option');
        opcion.value = usuario.id;  // El valor será el ID del usuario
        opcion.textContent = `${usuario.nombre} (${usuario.id})`; // El texto visible
        selectorUsuario.appendChild(opcion);
    }



    // Cuando el usuario selecciona una pista:
    // - Habilitamos el botón de guardar solo si hay una pista seleccionada
    selectorUsuario.addEventListener('change', () => {
        botonGuardar.disabled = !selectorUsuario.value;
    });

    // Cuando el usuario hace click en guardar:
    // 1. Creamos una copia del usuario actual
    // 2. Le asignamos la quedada seleccionada
    // 3. Enviamos la actualización al servidor de la quedada actualizada con el nuevo usuario
    // 4. Volvemos a la página de detalles de la quedada
    botonGuardar.addEventListener('click', async () => {
        const usuarioActual = { ...quedadaActual };
        quedadaActual.quedadas = { id: selectorUsuario.value }; // Asignamos la nueva quedada
        const idUsuario = selectorUsuario.value;
        await QuedadaApi.agregarUsuario(idQuedada, idUsuario);
        window.location.href = `quedadaDetalle.html?id=${idQuedada}`;
    });


});
