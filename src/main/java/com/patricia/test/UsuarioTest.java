package com.patricia.test;

import com.patricia.dao.UsuarioDao;
import com.patricia.database.Database;
import com.patricia.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioTest {
    public static void main(String[] args) {
        Database database = new Database();
        database.connect();

        UsuarioDao dao = new UsuarioDao(database.getJdbi());

        Usuario nuevo = new Usuario();
        nuevo.setNombre("River Song");
        nuevo.setEmail("river@spoilers.com");
        nuevo.setContraseña("tardis123");
        nuevo.setRol("cliente");
        nuevo.setFechaRegistro(LocalDate.now());
        nuevo.setEstado("activo");
        nuevo.setActivo(true);

        dao.insertarUsuario(nuevo);
        System.out.println("✅ Usuario insertado.");

        List<Usuario> usuarios = dao.listarUsuarios();
        System.out.println("📋 Lista de usuarios:");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }

        System.out.println("\n✏️ Modificando usuario con ID 1...");
        Usuario modificado = usuarios.get(0);
        modificado.setNombre("River Song (actualizada)");
        dao.actualizarUsuario(modificado);

        System.out.println("❌ Desactivando usuario con ID 1...");
        dao.eliminarUsuario(modificado.getId());

        // Listar otra vez para comprobar cambios
        System.out.println("\n🔁 Lista actualizada:");
        List<Usuario> listaFinal = dao.listarUsuarios();
        for (Usuario u : listaFinal) {
            System.out.println(u);
        }

    }

}
