import { formatearFecha } from '../components/formatearFecha.js';
export function crearElementoQuedada(quedada, botonesExtra = '') {
    return `
        <div class="card h-100 shadow-sm border-0" style="background-color: #FDFBF7;">
            <div class="card-body d-flex flex-column">
                
                <a href="quedadaDetalle.html?id=${quedada.id}" class="text-decoration-none text-dark flex-grow-1 mb-3">
                    <h5 class="card-title fw-bold">Quedada #${quedada.id}</h5>
                    <p class="card-text text-muted mb-1"><strong>Punto de encuentro:</strong> ${quedada.puntoEncuentro}</p>
                    <p class="card-text text-muted mb-1"><strong>Fecha de encuentro:</strong>  ${formatearFecha(quedada.fechaEncuentro)}</p>
                    <p class="card-text text-muted mb-0"><strong>Máx. asistentes:</strong> ${quedada.maximosAsistentes}</p>
                </a>
                
                ${botonesExtra}
                
            </div>
        </div>
    `;
}