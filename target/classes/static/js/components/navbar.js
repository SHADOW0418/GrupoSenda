export function crearBarraNavegacion() {
    return `
        <nav class="navbar navbar-expand-lg navbar-dark px-4 shadow-sm" style="background-color: #198754;">
            <div class="container-fluid">
                <a class="navbar-brand text-light fw-bold" href="index.html">Grupo Senda</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item px-2">
                            <a class="nav-link text-white-50 indexNavbar"  href="index.html">Rutas</a>
                        </li>
                        <li class="nav-item px-2">
                            <a class="nav-link text-white-50 quedadasNavbar" href="quedadas.html">Quedadas</a>
                        </li>
                        <li class="nav-item px-2">
                            <a class="nav-link text-white-50 usuariosNavbar"  href="usuariosLista.html">Usuarios</a>
                        </li>
                        <li class="nav-item px-2">
                            <a class="nav-link text-white-50 comentariosNavbar"  href="comentariosLista.html">Comentarios</a>
                        </li>
                    </ul>
                    
                </div>
            </div>
        </nav>
    `;
}