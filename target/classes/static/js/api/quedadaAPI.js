import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const QuedadaApi = {
    obtenerTodos: () => fetchApi('GET', '/v1/quedadas'),
    
    obtenerPorId: (id) => fetchApi('GET', `/v1/quedadas/${id}`),

    obtenerUsuarios: (id) => fetchApi('GET', `/v1/quedadas/${id}/usuarios`),
    
    crear: (quedada, idRuta) => fetchApi('POST', `/v1/quedadas/ruta/${idRuta}`, quedada),
    
    actualizar: (id, quedada) => fetchApi('PUT', `/v1/quedadas/${id}`, quedada),
    
    eliminar: async (id) => {
        await fetch(`${API_CONFIG.baseURL}/v1/quedadas/${id}`, {
            method: 'DELETE'
        });
    },

    agregarUsuario: (idQuedada, idUsuario) => fetchApi('POST', `/v1/quedadas/${idQuedada}/usuarios/${idUsuario}`),

    verComentariosUnaQuedada: (id) => fetchApi('GET', `/v1/quedadas/${id}/comentarios`),

    
    eliminarUsuarioDeQuedada: async (idQuedada, idUsuario) => {
        await fetch(`${API_CONFIG.baseURL}/v1/quedadas/${idQuedada}/usuarios/${idUsuario}`, {
            method: 'DELETE'
        });
    }
    
};