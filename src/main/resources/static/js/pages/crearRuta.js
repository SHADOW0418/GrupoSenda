import { RutaApi } from '../api/rutaAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';

// Cuando la página termine de cargar, ejecutamos toda nuestra lógica
document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();
    const root = document.getElementById("root");

    const parametros = new URLSearchParams(window.location.search);

    root.innerHTML = `
    <a href="index.html" class="btn btn-link mb-3 text-decoration-none text-success fw-bold">
        ← Volver atrás
    </a>
        
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        <div id="info-cabecera" class="mb-4 text-center">
            <h1 class="h3 fw-bold">Registro de Nueva Ruta</h1>
        </div>
        
        <form id="formulario-crear-ruta" class="needs-validation" novalidate>
        
            <h2 class="h5 mb-3 text-muted">Datos técnicos de la ruta</h2>
            
            <div class="row g-3">
                
                <div class="col-12">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="nombre" placeholder="Nombre de la ruta" required>
                        <label for="nombre">Nombre de la ruta</label>
                    </div>
                </div>

                <div class="col-12">
                    <div class="form-floating">
                        <textarea class="form-control" id="descripcion" placeholder="Descripción detallada" style="height: 100px" required></textarea>
                        <label for="descripcion">Descripción detallada</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="desnivel" min="0" placeholder="0" required>
                        <label for="desnivel">Desnivel acumulado (metros)</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-floating">
                        <select class="form-select" id="dificultad" required>
                            <option value="" disabled selected>Selecciona el nivel</option>
                            <option value="Baja">Baja</option>
                            <option value="Media">Media</option>
                            <option value="Alta">Alta</option>
                        </select>
                        <label for="dificultad">Dificultad</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="altitud" min="0" placeholder="0" required>
                        <label for="altitud">Altitud máxima (metros)</label>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="distanciaKm" min="0" step="0.1" placeholder="0.0" required>
                        <label for="distanciaKm">Distancia total (Km)</label>
                    </div>
                </div>

            </div>

            <div class="d-grid mt-4 mb-5">
                <button type="submit" class="btn btn-success btn-lg fw-bold">Guardar Ruta</button>
            </div>
        </form>
    </div>
`;

    const formularioCrearRuta = document.getElementById('formulario-crear-ruta');


    //Elemento del dom con formulario-reserva(El formulario en si)
    formularioCrearRuta.addEventListener("submit", async (e) => {
        e.preventDefault();
        const fechaActual = new Date().toISOString().slice(0, 19);
        const datosRuta = {
                //Elementos que saco del formulario
                nombre: document.getElementById("nombre").value,
                descripcion: document.getElementById("descripcion").value,
                desnivel: parseFloat(document.getElementById("desnivel").value),
                dificultad: document.getElementById("dificultad").value,
                altitud: parseInt(document.getElementById("altitud").value),
                distanciaKm: parseFloat(document.getElementById("distanciaKm").value)
            
        };

        await RutaApi.crear(datosRuta);
        // window.location.href = `index.html`;
    });
});