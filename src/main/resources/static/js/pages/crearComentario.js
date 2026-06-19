// Importamos las clases que necesitamos:
// - AvionAPI: para gestionar los aviones (obtener y actualizar)
// - AeropuertoAPI: para obtener aeropuertos y sus pistas
// - crearBarraNavegacion: componente visual de navegación compartido
import { QuedadaApi } from '../api/quedadaAPI.js';
import { UsuarioApi } from '../api/usuarioAPI.js';
import { ComentarioAPI } from '../api/comentarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';


// Cuando la página termine de cargar, ejecutamos toda nuestra lógica
document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const parametros = new URLSearchParams(window.location.search);
    const idQuedada = parametros.get('id');

    // Obtenemos los datos del avión del servidor usando su ID
    const quedadaActual = await QuedadaApi.obtenerPorId(idQuedada);
    root.innerHTML = `
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        
        <a href="quedadaDetalle.html?id=${idQuedada}" class="btn btn-link mb-3 text-decoration-none text-success fw-bold">
            ← Volver a la quedada
        </a>

        <div class="mb-4 text-center">
            <h1 class="h3 fw-bold">Añadir comentario</h1>
            <p class="text-muted">Quedada: <strong>${quedadaActual.id} - ${quedadaActual.ruta.nombre}</strong> (${quedadaActual.fechaEncuentro})</p>
        </div>
        
        <form id="formulario-crear-comentario" class="needs-validation" novalidate>
            
            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <select class="form-select" id="usuario-select" aria-label="Usuario que comenta" required>
                            <option value="" selected disabled>Selecciona un usuario...</option>
                        </select>
                        <label for="usuario-select">Usuario que comenta</label>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="puntuacion-input" min="0" max="5" step="0.5" placeholder="4.5" required>
                        <label for="puntuacion-input">Puntuación (0 al 5)</label>
                    </div>
                </div>

                <div class="col-12">
                    <div class="form-floating">
                        <textarea class="form-control" id="texto-input" placeholder="Escribe aquí tu comentario..." style="height: 120px" required></textarea>
                        <label for="texto-input">Comentario</label>
                    </div>
                </div>
            </div>

            <div class="d-flex gap-3 mt-4">
                <button type="submit" class="btn btn-success fw-bold px-4">Crear Comentario</button>
                <a href="quedadaDetalle.html?id=${idQuedada}" class="btn btn-outline-secondary fw-bold px-4">Cancelar</a>
            </div>
            
        </form>
    </div>
`;

    //Parametros de la cabecera
    const selectorUsuario = document.getElementById('usuario-select');
    const formularioCrearComentario = document.getElementById('formulario-crear-comentario');
    const usuarios = await QuedadaApi.obtenerUsuarios(idQuedada);

    for (const usuario of usuarios) {
        const opcion = document.createElement('option');
        opcion.value = usuario.id;  // El valor será el ID del aeropuerto
        opcion.textContent = `${usuario.nombre} (${usuario.id})`; // El texto visible
        selectorUsuario.appendChild(opcion);
    }


    //Elemento del dom con formulario-reserva(El formulario en si)
    formularioCrearComentario.addEventListener("submit", async (e) => {
        e.preventDefault();
        const fechaActual = new Date().toISOString().slice(0, 19);
        const datosComentario = {
                //Elementos que saco del formulario
                texto: document.getElementById("texto-input").value,
                puntuacion: parseFloat(document.getElementById("puntuacion-input").value),
                fechaPublicacion: fechaActual,
                usuario: { id: parseInt(selectorUsuario.value)}, 
                quedada: { id: parseInt(idQuedada)}
            
        };

        await ComentarioAPI.crear(datosComentario);
        window.location.href = `quedadaDetalle.html?id=${idQuedada}`;
    });
});
