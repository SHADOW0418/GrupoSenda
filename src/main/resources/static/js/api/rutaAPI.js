import { API_CONFIG } from '../config/apiConfig.js';
import { fetchApi } from '../utils/apiUtils.js';

export const RutaApi = {
    obtenerTodos: () => fetchApi('GET', '/v1/rutas'),
    
    obtenerPorId: (id) => fetchApi('GET', `/v1/rutas/${id}`),

    obtenerQuedadas: (id) => fetchApi('GET', `/v1/rutas/${id}/quedadas`),
    
    crear: (ruta) => fetchApi('POST', '/v1/rutas', ruta),
    
    actualizar: (id, ruta) => fetchApi('PUT', `/v1/rutas/${id}`, ruta),
    
    eliminar: (id) => fetchApi('DELETE', `/v1/rutas/${id}`)
};