import { QuedadaApi } from '../api/quedadaAPI.js';
import { crearBarraNavegacion } from '../components/navbar.js';
import { crearElementoUsuario } from '../components/usuarioComponente.js';
import { crearElementoComentario } from '../components/comentarioComponente.js';
import { formatearFecha } from '../components/formatearFecha.js';

document.addEventListener('DOMContentLoaded', async () => {
    document.getElementById('navbar').innerHTML = crearBarraNavegacion();

    const enlaceNabvar = document.querySelector(".quedadasNavbar")
    enlaceNabvar.classList.add("colorSeleccionado")
    //Coge los parametros que hay en la url
    const parametros = new URLSearchParams(window.location.search);
    const idQuedada = parametros.get('id');

    const seccionQuedada = document.getElementById('root');

    //Conseguir los datos de la quedada
    const quedada = await QuedadaApi.obtenerPorId(idQuedada);


    //Conseguir la lista de los usuarios
    const usuarios = await QuedadaApi.obtenerUsuarios(idQuedada);


    //Obtiene los comentarios de una quedada
    const comentarios = await QuedadaApi.verComentariosUnaQuedada(idQuedada);


    //Imprime cada comentario
    let htmlComentarios = '<p>No hay ningun comentario</p>';
    if (comentarios.length > 0) {
        htmlComentarios = '';
        for (const comentario of comentarios) {

            htmlComentarios += `<li>${crearElementoComentario(comentario, 'comentarioDetalle.html')}</li>`;
        }
    }

    

    //Imprime cada usuario
    let htmlUsuarios = '<p>No hay ningun usuario</p>';
    if (usuarios.length > 0) {
        // Abrimos el contenedor row para la cuadrícula
        htmlUsuarios = '<div class="row g-3">';
        
        for (const usuario of usuarios) {
            // Creamos el botón de eliminar idéntico al de las quedadas
            const botonEliminar = `
                <button class="btn btn-danger btn-sm btn-eliminar-usuario mt-auto fw-bold w-100" data-id-usuario="${usuario.id}">
                    Eliminar
                </button>
            `;
            
            // Llamamos a la función de usuario pasándole el botón
            htmlUsuarios += `
            <div class="col-12 col-md-6 col-lg-4">
                ${crearElementoUsuario(usuario, botonEliminar)}
            </div>`;
        }
        
        // Cerramos el contenedor row
        htmlUsuarios += '</div>';
    }


    //Cambiar el nombre del titulo
    document.title = `${"Quedada detalle"}(${quedada.id}) `;

    seccionQuedada.innerHTML += `
        <div class="container mt-4 mb-5">
            
            <div class="card shadow-sm border-0" style="background-color: #FDFBF7;">
                <div class="card-header text-white fw-bold fs-5" style="background-color: #198754;">
                    Quedada #${quedada.id} - ${formatearFecha(quedada.fechaEncuentro)}
                </div>
                
                <div class="card-body mt-2">
                    <h5 class="card-title mb-4">Información de la Quedada</h5>
                    <p class="card-text mb-2"><strong>Fecha de encuentro:</strong> ${formatearFecha(quedada.fechaEncuentro)}</p>
                    <p class="card-text mb-2"><strong>Punto de encuentro:</strong> ${quedada.puntoEncuentro}</p>
                    <p class="card-text mb-2"><strong>Máximos asistentes:</strong> ${quedada.maximosAsistentes}</p>
                </div>
            </div>

            <h3 class="mt-5 mb-3 text-dark">Comentarios</h3>
            <ul class="list-group shadow-sm">${htmlComentarios}</ul>
            
            <h3 class="mt-5 mb-3 text-dark">Usuarios Inscritos</h3>
            <ul class="list-group shadow-sm">${htmlUsuarios}</ul>
            
            <div class="d-flex flex-wrap gap-3 mt-5">
                <a href="editarQuedada.html?id=${idQuedada}" class="btn btn-warning fw-bold">
                    Editar Quedada
                </a>
                <a href="aniadirUsuario.html?id=${idQuedada}" class="btn btn-primary fw-bold">
                    Añadir Usuario
                </a>
                <a href="aniadirComentario.html?id=${idQuedada}" class="btn btn-primary fw-bold">
                    Añadir Comentario
                </a>
            </div>
            
        </div>
    `;

        const botonesEliminar = document.querySelectorAll('.btn-eliminar-usuario');
        botonesEliminar.forEach(boton => {
        boton.addEventListener('click', async (e) => {
            // Sacamos el ID del usuario que guardamos en el botón con 'data-id-usuario'
            const idUsuario = boton.getAttribute('data-id-usuario');

            // Preguntamos para asegurar
            const seguro = confirm("¿Estás seguro de que quieres eliminar a este usuario de la quedada?");
            
            if (seguro) {
                try {
                    // Llamamos a la API para borrarlo
                    await QuedadaApi.eliminarUsuarioDeQuedada(idQuedada, idUsuario);
                    
                    // Si todo va bien, recargamos la página para que desaparezca visualmente
                    window.location.reload();
                } catch (error) {
                    console.error("Error al borrar:", error);
                    alert("No se pudo eliminar al usuario de la quedada.");
                }
            }
        });
    });
});
