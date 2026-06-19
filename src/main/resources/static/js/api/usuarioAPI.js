import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const UsuarioApi = {
    obtenerTodos: () => fetchApi('GET', '/v1/usuarios'),
    
    obtenerPorId: (id) => fetchApi('GET', `/v1/usuarios/${id}`),
    
    crear: (usuario) => fetchApi('POST', '/v1/usuarios', usuario),
    
    actualizar: (id, usuario) => fetchApi('PUT', `/v1/usuarios/${id}`, usuario),
    
    //Añado esto para que elimine directamente al usuario y responda con un paquete vacio
    eliminar: async (id) => {
        await fetch(`${API_CONFIG.baseURL}/v1/usuarios/${id}`, {
            method: 'DELETE'
        });
    },

    obtenerComentariosUsuario: (id) => fetchApi('GET', `/v1/usuarios/${id}/comentarios`),

    obtenerQuedadasUsuario: (id) => fetchApi('GET', `/v1/usuarios/${id}/quedadas`)
};