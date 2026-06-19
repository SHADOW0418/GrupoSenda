import { QuedadaApi } from '../api/quedadaAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
//Dibuja la tarjetas de las quedadas
import { crearElementoQuedada } from '../components/quedadaComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const enlaceNabvar = document.querySelector(".quedadasNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")
    
    
    const root = document.getElementById('root');

    //Conseguir los datos de las quedadas
    const quedadas = await QuedadaApi.obtenerTodos();

    //Crea las tarjetas de las quedadas
    let htmlQuedadas = '<p>No hay ninguna quedada</p>';
    if (quedadas.length > 0) {
        
        htmlQuedadas = '';
        for (const quedada of quedadas) {
            htmlQuedadas += crearElementoQuedada(quedada);
        }
    }
    
    root.innerHTML += `
        <div class="container mt-4">
            
            <h2 class="mt-5">Quedadas</h2>
            <ul class="list-group mt-3">${htmlQuedadas}</ul>
        </div>`;
});