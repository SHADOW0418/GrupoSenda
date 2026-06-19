import { UsuarioApi } from '../api/usuarioAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoUsuario } from '../components/usuarioComponente.js';
import { crearElementoComentario } from '../components/comentarioComponente.js';
import { crearElementoQuedada } from '../components/quedadaComponente.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();
    const enlaceNabvar = document.querySelector(".usuariosNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")
    //Coge los parametros que hay en la url
    const parametros = new URLSearchParams(window.location.search);
    const idUsuario = parametros.get('id');
    
    const root = document.getElementById('root');

    //Conseguir los datos del usuario
    const usuario = await UsuarioApi.obtenerPorId(idUsuario);
    //Conseguir los comentarios de un usuario
    const comentarios = await UsuarioApi.obtenerComentariosUsuario(idUsuario);
    //Conseguir las quedadas de un usuario
    const quedadas = await UsuarioApi.obtenerQuedadasUsuario(idUsuario);


    //Crea las tarjetas de las comentarios
        let htmlComentarios = '<p>No hay comentarios asignados actualmente</p>';
        if (comentarios.length > 0) {
            
            htmlComentarios = '';
            for (const comentario of comentarios) {
                htmlComentarios += crearElementoComentario(comentario);
            }
        }

    //Crea las tarjetas de las quedadas
        let htmlQuedadas = '<p>No esta inscrito en ninguna quedada</p>';
        if (quedadas.length > 0) {
            
            htmlQuedadas = '';
            for (const quedada of quedadas) {
                htmlQuedadas += crearElementoQuedada(quedada);
            }
        }

    
    
    //Cambiar el nombre del titulo
    document.title = `${usuario.nombre} (${usuario.id})`;

    let htmlUsuario = crearElementoUsuario(usuario)
    root.innerHTML += `
        <div class="container mt-4 mb-5">
            
            <div class="mb-5 p-3 border-bottom">
                <h1 class="text-success fw-bold">Usuario: ${usuario.nombre} ${usuario.apellido}</h1>
                <p class="fs-5 text-muted"><strong>Nivel:</strong> ${usuario.nivelExperiencia} | <strong>Email:</strong> ${usuario.email}</p>
            </div>
            
            <h3 class="mt-5 mb-3 text-dark">Quedadas de ${usuario.nombre}</h3>
            <div>${htmlQuedadas}</div>
            
            <h3 class="mt-5 mb-3 text-dark">Comentarios de ${usuario.nombre}</h3>
            <ul class="list-group shadow-sm">${htmlComentarios}</ul>
            
            <div class="text-center mt-5 pt-4 border-top">
                <button id="boton-cancelar" class="btn btn-outline-danger btn-lg px-5 fw-bold">
                    Eliminar usuario
                </button>
            </div>
        </div>
    `;

        document.getElementById('boton-cancelar').addEventListener('click', async () => {
        if (confirm('¿Está seguro de que desea eliminar este usuario?')) {
            await UsuarioApi.eliminar(idUsuario);
            console.log("Usuario eliminado");
            
            window.location.href = 'usuariosLista.html';
        }
    });
});