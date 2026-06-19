import { ComentarioAPI } from '../api/comentarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
//Dibuja la tarjetas de las quedadas
import { crearElementoComentario } from '../components/comentarioComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const enlaceNabvar = document.querySelector(".comentariosNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")
    
    const seccionComentarios = document.getElementById('root');

    //Conseguir los datos de la ruta
    const comentarios = await ComentarioAPI.obtenerTodos();
    
    //Crea las tarjetas de los comentario
    let htmlComentarios = '<p>No hay comentarios asignadas actualmente</p>';
    if (comentarios.length > 0) {
        htmlComentarios = '';
        for (const comentario of comentarios) {
            htmlComentarios += crearElementoComentario(comentario);
        }
    }
    
    seccionComentarios.innerHTML += `
        <div class="container mt-4">
            
            <h2 class="mt-5">Comentarios</h2>
            <ul class="list-group mt-3">${htmlComentarios}</ul>
        </div>`;
});