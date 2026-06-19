import { RutaApi } from "../api/rutaAPI.js";
import { crearBarraNavegacion } from "../components/navbar.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();

    //Coge los parametros que hay en la url
    const parametros = new URLSearchParams(window.location.search);
    const idRuta = parametros.get("id");

    const seccionRuta = document.getElementById("root");

    //Conseguir los datos de la ruta
    const ruta = await RutaApi.obtenerPorId(idRuta);

    

    seccionRuta.innerHTML += `
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        
        <h2 class="mb-4 text-dark fw-bold"> Editar Ruta #${ruta.id}</h2>
        
        <form id="formulario-actualizar-ruta" class="needs-validation" novalidate>
            
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="inputNombre" placeholder="Nombre" value="${ruta.nombre}" required>
                <label for="inputNombre">Nombre de la ruta</label>
            </div>

            <div class="form-floating mb-3">
                <textarea class="form-control" id="inputDescripcion" placeholder="Descripción" style="height: 120px" required>${ruta.descripcion}</textarea>
                <label for="inputDescripcion">Descripción</label>
            </div>

            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <input type="number" class="form-control" id="inputDesnivel" placeholder="Desnivel" value="${ruta.desnivel}" required>
                        <label for="inputDesnivel">Desnivel (metros)</label>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <input type="number" class="form-control" id="inputAltitud" placeholder="Altitud" value="${ruta.altitud}" required>
                        <label for="inputAltitud">Altitud (metros)</label>
                    </div>
                </div>
            </div>

            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control" id="inputDificultad" placeholder="Dificultad" value="${ruta.dificultad}" required>
                        <label for="inputDificultad">Dificultad</label>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-floating mb-3">
                        <input type="number" step="0.01" class="form-control" id="inputDistancia" placeholder="Distancia" value="${ruta.distanciaKm}" required>
                        <label for="inputDistancia">Distancia (Km)</label>
                    </div>
                </div>
            </div>

            <div class="d-flex gap-3 mt-4">
                <button type="submit" class="btn btn-success fw-bold px-4">Guardar Cambios</button>
                <a href="rutaDetalle.html?id=${ruta.id}" class="btn btn-outline-secondary fw-bold px-4">Cancelar</a>
            </div>
            
        </form>
    </div>
`;

    const formularioActualizarRuta = document.getElementById('formulario-actualizar-ruta');
    
     formularioActualizarRuta.addEventListener('submit', async (e) => {
        
        e.preventDefault();

        const datosRuta = {
                //Elementos que saco del formulario
                nombre: document.getElementById("inputNombre").value,
                descripcion: document.getElementById("inputDescripcion").value,
                desnivel: parseFloat(document.getElementById("inputDesnivel").value),
                altitud: parseFloat(document.getElementById("inputAltitud").value),
                dificultad: document.getElementById("inputDificultad").value,
                distanciaKm: parseFloat(document.getElementById("inputDistancia").value),
                
            
        };

        await RutaApi.actualizar(idRuta, datosRuta);
        window.location.href = `rutaDetalle.html?id=${idRuta}`;
    });
});
