import { RutaApi } from '../api/rutaAPI.js';
//Crear la barra de navegacion
import { crearBarraNavegacion } from '../components/navbar.js';

//Solo se ejecuta cuando se ha creado el dom(html)
document.addEventListener('DOMContentLoaded', async () => {

    
    //Coge el navbar y le mete la barra de navegacion
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const enlaceNabvar = document.querySelector(".indexNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")

    //Cogo el div root(index) y le cargo todo
    const root = document.getElementById('root');
    
    //Hace una peticion a la api donde devuelve todas las rutas
    const rutas = await RutaApi.obtenerTodos();
    
    let listaruta = '';
    
    //Crea una lista de rutas y va mostrando todas las rutas
    for (const ruta of rutas) {
        listaruta += `
            <div class="col-12 col-md-6 col-lg-4 mb-4">
                
                <div class="card h-100 shadow-sm border-0" style="background-color: #FDFBF7;">
                    <div class="card-body d-flex flex-column">
                        
                        <h5 class="card-title fw-bold">${ruta.nombre}</h5>
                        
                        <p class="card-text text-muted mb-4">${ruta.descripcion}</p>
                        
                        <a href="rutaDetalle.html?id=${ruta.id}" class="btn btn-primary mt-auto w-100 fw-bold">
                            Ver detalles
                        </a>
                        
                    </div>
                </div>
                
            </div>`;
    }
    
    // Inserto el root con el contenedor Grid de Bootstrap
    root.innerHTML = `
        <div class="container mt-5 mb-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h1 class="fw-bold">Gestión de Rutas</h1>
            </div>
            
            <div class="row">
                ${listaruta}
            </div>
        </div>
    `;

    
});