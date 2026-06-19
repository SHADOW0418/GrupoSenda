export function crearElementoUsuario(usuario, botonesExtra = '') {
    return `
        <div class="card h-100 shadow-sm border-0" style="background-color: #FDFBF7;">
            <div class="card-body d-flex flex-column">
                
                <a href="usuarioDetalle.html?id=${usuario.id}" class="text-decoration-none text-dark flex-grow-1 mb-3">
                    <h5 class="card-title fw-bold">Usuario: ${usuario.nombre} ${usuario.apellido}</h5>
                    <p class="card-text text-muted mb-1">Nivel de experiencia: ${usuario.nivelExperiencia}</p>
                    <p class="card-text text-muted mb-0">Email del usuario: ${usuario.email}</p>
                </a>
                
                ${botonesExtra}
                
            </div>
        </div>
    `;
}