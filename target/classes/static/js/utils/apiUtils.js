import { API_CONFIG } from '../config/apiConfig.js';

export async function fetchApi(method = 'GET', path, body = null) {
    let url = `${API_CONFIG.baseURL}${path}`;
    let response = {};

    if (method === 'POST' || method === 'PUT') {
        response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });

    } else {
        response = await fetch(url, { method: method });
    }

    return await response.json();
}