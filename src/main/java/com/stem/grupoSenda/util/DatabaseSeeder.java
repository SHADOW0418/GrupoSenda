package com.stem.grupoSenda.util;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.github.javafaker.Faker;
import com.stem.grupoSenda.model.Comentario;
import com.stem.grupoSenda.model.Quedada;
import com.stem.grupoSenda.model.Ruta;
import com.stem.grupoSenda.model.Usuario;
import com.stem.grupoSenda.repository.ComentarioRepository;
import com.stem.grupoSenda.repository.QuedadaRepository;
import com.stem.grupoSenda.repository.RutaRepository;
import com.stem.grupoSenda.repository.UsuarioRepository;
@Component
@Transactional
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private QuedadaRepository quedadaRepository;
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Override
    public void run(String... args) {
        if (rutaRepository.count() > 0) {
            System.out.println("La base de datos ya tiene datos. Omitiendo Faker...");
            return;
        }
        System.out.println("Generando datos falsos para Grupo Senda...");
        Faker faker = new Faker(new Locale("es"));
        Random random = new Random();
        String[] dificultades = {"Fácil", "Moderada", "Difícil", "Experto"};
        // 1. GENERAR RUTAS
        List<Ruta> rutas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Ruta ruta = new Ruta(
                "Ruta " + faker.address().cityName(),
                faker.lorem().paragraph(),
                faker.number().numberBetween(100, 1500),
                dificultades[random.nextInt(dificultades.length)],
                faker.number().numberBetween(500, 3000),
                faker.number().randomDouble(2, 3, 25)
            );
            rutas.add(rutaRepository.save(ruta));
        }
        // 2. GENERAR USUARIOS
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            String email;
            do {
                email = faker.internet().emailAddress();
            } while (usuarioRepository.existsByEmail(email));
            Usuario usuario = new Usuario(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.number().randomDouble(1, 1, 10),
                email
            );
            usuarios.add(usuarioRepository.save(usuario));
        }
        // 3. GENERAR QUEDADAS
        List<Quedada> quedadas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Ruta ruta = rutas.get(random.nextInt(rutas.size()));
            LocalDateTime fecha = faker.date().future(30, TimeUnit.DAYS)
                                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Quedada quedada = new Quedada(
                fecha,
                "Parking de " + faker.address().streetName(),
                faker.number().numberBetween(5, 20),
                ruta
            );
            quedadas.add(quedadaRepository.save(quedada));
        }
        // 4. ASIGNAR USUARIOS A QUEDADAS Y CREAR COMENTARIOS
        for (Quedada quedada : quedadas) {
            int numAsistentes = faker.number().numberBetween(1, Math.min(5, usuarios.size()));
            List<Usuario> mezclados = new ArrayList<>(usuarios);
            java.util.Collections.shuffle(mezclados, random);
            for (int j = 0; j < numAsistentes; j++) {
                Usuario usuario = mezclados.get(j);
                quedada.getUsuarios().add(usuario);
                usuario.getQuedadas().add(quedada);
                usuarioRepository.save(usuario);
                if (random.nextBoolean()) {
                    Comentario comentario = new Comentario(
                        faker.lorem().sentence(),
                        faker.number().randomDouble(1, 1, 5),
                        LocalDateTime.now().minusDays(faker.number().numberBetween(1, 10)),
                        usuario,
                        quedada
                    );
                    comentarioRepository.save(comentario);
                }
            }
            quedadaRepository.save(quedada);
        }
        System.out.println("¡Datos falsos generados con éxito!");
    }
}