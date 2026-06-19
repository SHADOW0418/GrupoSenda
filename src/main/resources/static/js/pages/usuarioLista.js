import { UsuarioApi } from '../api/usuarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
//Dibuja la tarjetas de las quedadas
import { crearElementoUsuario } from '../components/usuarioComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    
    const enlaceNabvar = document.querySelector(".usuariosNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")

    const seccionUsuarios = document.getElementById('root');


    //Conseguir la lista de los usuarios
    const usuarios = await UsuarioApi.obtenerTodos();
    
    
    //Crea las tarjetas de los usuarios
    let htmlUsuarios = '<p>No hay usuarios asignados actualmente</p>';
    if (usuarios.length > 0) {
        htmlUsuarios = '';
        for (const usuario of usuarios) {
            htmlUsuarios += crearElementoUsuario(usuario);
        }
    }
    
    seccionUsuarios.innerHTML += `
        <div class="container mt-4">
            
            <h2 class="mt-5">Usuarios</h2>
            <ul class="list-group mt-3">${htmlUsuarios}</ul>
            <div class="mt-4">
                    <a href="registrarUsuario.html?" class="btn btn-primary">
                        Registrar usuario
                    </a>
                </div>
        </div>`;
});