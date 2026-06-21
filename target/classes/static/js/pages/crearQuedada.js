// Importamos las clases que necesitamos:
// - AvionAPI: para gestionar los aviones (obtener y actualizar)
// - AeropuertoAPI: para obtener aeropuertos y sus pistas
// - crearBarraNavegacion: componente visual de navegación compartido
import { QuedadaApi } from '../api/quedadaAPI.js';
import { RutaApi } from '../api/rutaAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';


// Cuando la página termine de cargar, ejecutamos toda nuestra lógica
document.addEventListener('DOMContentLoaded', async () => {
    // Añadimos la barra de navegación al elemento con id "navbar"
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    
    // root es donde pondremos todo el contenido de la página
    const root = document.getElementById('root');
    
    // Obtenemos el ID del avión de la URL (ejemplo: asignarPista.html?id=123)
    const parametros = new URLSearchParams(window.location.search);
    const idRuta = parametros.get('id');

    // Obtenemos los datos del avión del servidor usando su ID
    const rutaActual = await RutaApi.obtenerPorId(idRuta);

    //Evita que puedas poner fecha anterior a la actual
    const ahora = new Date();
    ahora.setMinutes(ahora.getMinutes() - ahora.getTimezoneOffset());
    const fechaMinima = ahora.toISOString().slice(0, 16);
    
    // Creamos la estructura HTML de la página:
    // - Un botón para volver
    // - Información del avión (modelo, matrícula y pista actual)
    // - Selector de aeropuerto
    // - Selector de pista (inicialmente deshabilitado)
    // - Botones de guardar y desasignar
    root.innerHTML = `
    <a href="rutaDetalle.html?id=${idRuta}" class="btn btn-link mb-3 text-decoration-none text-success fw-bold">
        ← Volver atrás
    </a>
        
    <div class="container mt-4 mb-5" style="max-width: 800px;">
        <div id="info-cabecera" class="mb-4 text-center">
            <h1 class="h3 fw-bold">Registro de Nueva Quedada</h1>
        </div>
        
        <form id="formulario-crear-quedada" class="needs-validation" novalidate>
        
            <div class="alert alert-light border shadow-sm mb-4">
                <p class="mb-0"><strong>Ruta asignada:</strong> ${rutaActual.id} - ${rutaActual.nombre}</p>
            </div>
            
            <h2 class="h5 mb-3 text-muted">Datos de la quedada</h2>
            
            <div class="row g-3">
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="datetime-local" class="form-control" id="fechaEncuentro" min="${fechaMinima}" required>
                        <label for="fechaEncuentro">Fecha y Hora de encuentro</label>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="form-floating">
                        <input type="number" class="form-control" id="maximosAsistentes" min="2" placeholder="15" required>
                        <label for="maximosAsistentes">Máximo de asistentes</label>
                    </div>
                </div>

                <div class="col-12">
                    <div class="form-floating">
                        <input type="text" class="form-control" id="puntoEncuentro" placeholder="Punto de encuentro" required>
                        <label for="puntoEncuentro">Punto exacto de encuentro</label>
                    </div>
                </div>
            </div>

            <div class="d-grid mt-4 mb-5">
                <button type="submit" class="btn btn-success btn-lg fw-bold">Crear Quedada</button>
            </div>
        </form>
    </div>
`;

    // Guardamos referencias a los elementos que necesitamos manipular
    const formularioCrearQuedada = document.getElementById('formulario-crear-quedada');

    //Elemento del dom con formulario-reserva(El formulario en si)
        formularioCrearQuedada.addEventListener("submit", async (e) => {
            e.preventDefault();
    
            const datosQuedada = {
                    //Elementos que saco del formulario
                    fechaEncuentro: document.getElementById("fechaEncuentro").value,
                    puntoEncuentro: document.getElementById("puntoEncuentro").value,
                    maximosAsistentes: document.getElementById("maximosAsistentes").value,

                
            };
    
            await QuedadaApi.crear(datosQuedada, idRuta);
            window.location.href = `rutaDetalle.html?id=${idRuta}`;
        });
});
