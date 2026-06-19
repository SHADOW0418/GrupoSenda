export function formatearFecha(fechaNormal) {
    const fecha = new Date(fechaNormal);

    const fechaFormateada = fecha.toLocaleString('es-ES', {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    }).replace(', ', ' - ');

    return fechaFormateada;
}