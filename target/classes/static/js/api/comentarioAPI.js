import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const ComentarioAPI = {
    obtenerTodos: () => fetchApi('GET', '/v1/comentarios'),
    
    obtenerPorId: (id) => fetchApi('GET', `/v1/comentarios/${id}`),

    obtenerComentarios: (id) => fetchApi('GET', `/v1/quedadas/${id}/comentarios`),
    
    crear: (comentario) => fetchApi('POST', '/v1/comentarios', comentario),
    
    actualizar: (id, comentario) => fetchApi('PUT', `/v1/comentarios/${id}`, comentario),
    
    eliminar: (id) => fetchApi('DELETE', `/v1/comentarios/${id}`)
};