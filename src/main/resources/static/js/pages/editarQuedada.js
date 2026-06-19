import { QuedadaApi } from "../api/quedadaAPI.js";
import { UsuarioApi } from "../api/usuarioAPI.js";
import { crearBarraNavegacion } from "../components/navbar.js";
import { crearElementoUsuario } from "../components/usuarioComponente.js";
import { crearElementoComentario } from "../components/comentarioComponente.js";
import { formatearFecha } from "../components/formatearFecha.js";

document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("navbar").innerHTML = crearBarraNavegacion();

    //Coge los parametros que hay en la url
    const parametros = new URLSearchParams(window.location.search);
    const idQuedada = parametros.get("id");

    const seccionQuedada = document.getElementById("root");

    //Conseguir los datos de la quedada
    const quedada = await QuedadaApi.obtenerPorId(idQuedada);

    //Conseguir la lista de los usuariosQuedada
    const usuariosQuedada = await QuedadaApi.obtenerUsuarios(idQuedada);

    const usuarios = await UsuarioApi.obtenerTodos();

    const ahora = new Date();
    ahora.setMinutes(ahora.getMinutes() - ahora.getTimezoneOffset());
    const fechaMinima = ahora.toISOString().slice(0, 16);

    seccionQuedada.innerHTML += `
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        
        <a href="quedadaDetalle.html?id=${idQuedada}" class="btn btn-link mb-3 text-decoration-none text-success fw-bold">
            ← Volver a la quedada
        </a>

        <div class="mb-4 text-center">
            <h1 class="h3 fw-bold">Editar Quedada #${quedada.id}</h1>
        </div>
        
        <form id="formulario-actualizar-quedada" class="needs-validation" novalidate>
        
            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="datetime-local" class="form-control" id="inputFecha" min="${fechaMinima}" value="${quedada.fechaEncuentro}" required>
                        <label for="inputFecha">Fecha y Hora de encuentro</label>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="inputAsistentes" min="2" value="${quedada.maximosAsistentes}" required>
                        <label for="inputAsistentes">Máximo de asistentes</label>
                    </div>
                </div>

                <div class="col-12">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="inputPunto" value="${quedada.puntoEncuentro}" required>
                        <label for="inputPunto">Punto exacto de encuentro</label>
                    </div>
                </div>
            </div>

            <div class="d-grid gap-2 d-md-flex mt-4 mb-5">
                <button type="submit" class="btn btn-success btn-lg fw-bold flex-grow-1">Actualizar quedada</button>
                <a href="quedadaDetalle.html?id=${idQuedada}" class="btn btn-outline-secondary btn-lg fw-bold px-4">Cancelar</a>
            </div>
            
        </form>
    </div>
`;

    const formularioActualizarQuedada = document.getElementById('formulario-actualizar-quedada');
    
     formularioActualizarQuedada.addEventListener('submit', async (e) => {
        
        e.preventDefault();

        const datosQuedada = {
                //Elementos que saco del formulario
                fechaEncuentro: document.getElementById("inputFecha").value,
                puntoEncuentro: document.getElementById("inputPunto").value,
                maximosAsistentes: parseFloat(document.getElementById("inputAsistentes").value),
                
            
        };

        await QuedadaApi.actualizar(idQuedada, datosQuedada);
        window.location.href = `quedadaDetalle.html?id=${idQuedada}`;
    });
});
