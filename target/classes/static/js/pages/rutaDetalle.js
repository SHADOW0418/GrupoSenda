import { RutaApi } from '../api/rutaAPI.js';
import { QuedadaApi } from '../api/quedadaAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoQuedada } from '../components/quedadaComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const enlaceNabvar = document.querySelector(".indexNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")
    //Coge los parametros que hay en la url
    const parametros = new URLSearchParams(window.location.search);
    const idRuta = parametros.get('id');

    const root = document.getElementById('root');

    //Conseguir los datos de la ruta
    const ruta = await RutaApi.obtenerPorId(idRuta);

    //Obtiene las quedadas de una ruta
    const quedadas = await RutaApi.obtenerQuedadas(idRuta);

    //Cambiar el nombre del titulo
    document.title = `${ruta.nombre} (${ruta.id})`;

    //Imprime cada quedada
    let htmlQuedadas = '<p>No hay ninguna quedada</p>';
    if (quedadas.length > 0) {
        // Abrimos el contenedor row para que las tarjetas se pongan en cuadrícula
        htmlQuedadas = '<div class="row g-3">';
        
        for (const quedada of quedadas) {
            const botonEliminar = `
                <button class="btn btn-danger btn-sm btn-eliminar-quedada mt-auto fw-bold w-100" data-id-quedada="${quedada.id}">
                    Eliminar
                </button>
            `;
            
            // Llamamos a tu función pasándole el botón para que lo meta en la tarjeta
            htmlQuedadas += `
            <div class="col-12 col-md-6 col-lg-4">
                ${crearElementoQuedada(quedada, botonEliminar)}
            </div>`;
        }
        
        // Cerramos el contenedor row
        htmlQuedadas += '</div>';
    }

    root.innerHTML += `
        <div class="container mt-4 mb-5">
            
            <div class="card shadow-sm border-0" style="background-color: #FDFBF7;">
                <div class="card-header text-white fw-bold fs-5" style="background-color: #198754;">
                    ${ruta.nombre}
                </div>
                
                <div class="card-body mt-2">
                    <h5 class="card-title mb-4">Información de la Ruta</h5>
                    <p class="card-text mb-2"><strong>Descripción:</strong> ${ruta.descripcion}</p>
                    <p class="card-text mb-2"><strong>Desnivel:</strong> ${ruta.desnivel} m</p>
                    <p class="card-text mb-2"><strong>Dificultad:</strong> ${ruta.dificultad}</p>
                    <p class="card-text mb-2"><strong>Altitud:</strong> ${ruta.altitud} m</p> <p class="card-text mb-2"><strong>Distancia:</strong> ${ruta.distanciaKm} km</p>
                </div>
            </div>

            <h3 class="mt-5 mb-3 text-dark">Quedadas Programadas</h3>
            <ul class="list-group shadow-sm">${htmlQuedadas}</ul>
            
            <div class="d-flex flex-wrap gap-3 mt-5">
                <a href="editarRuta.html?id=${idRuta}" class="btn btn-warning fw-bold">
                    Editar Ruta
                </a>
                <a href="aniadirQuedada.html?id=${idRuta}" class="btn btn-primary fw-bold">
                    Añadir Quedada
                </a>
            </div>
            
        </div>
    `;

    const botonesEliminar = document.querySelectorAll('.btn-eliminar-quedada');
    botonesEliminar.forEach(boton => {
        boton.addEventListener('click', async (e) => {
            // Sacamos el ID del usuario que guardamos en el botón con 'data-id-usuario'
            const idQuedada = boton.getAttribute('data-id-quedada');
            console.log(idQuedada);
            

            // Preguntamos para asegurar
            const seguro = confirm("¿Estás seguro de que quieres eliminar esta quedada?");

            if (seguro) {
                try {
                    // Llamamos a la API para borrarlo
                    await QuedadaApi.eliminar(idQuedada);

                    // Si todo va bien, recargamos la página para que desaparezca visualmente
                    window.location.reload();
                } catch (error) {
                    console.error("Error al borrar:", error);
                    alert("No se pudo eliminar la quedada");
                }
            }
        });
    });
});