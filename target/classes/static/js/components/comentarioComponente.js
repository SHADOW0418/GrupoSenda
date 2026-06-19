import { formatearFecha } from '../components/formatearFecha.js';
export function crearElementoComentario(comentario) {
    return `
        <p class="card text-decoration-none text-dark mb-3">
            <div class="card-body">
                <h5 class="card-title">Usuario: ${comentario.usuario.nombre} ${comentario.usuario.apellido}</h5>
                <p class="card-text text-muted">Comentario: ${comentario.texto}</p>
                <p class="card-text text-muted">Puntuacion: ${comentario.puntuacion}</p>
                <p class="card-text text-muted">Fecha de publicación: ${formatearFecha(comentario.fechaPublicacion)}</p>
                
            </div>
        </p>`;
}
