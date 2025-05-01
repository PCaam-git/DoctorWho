package com.patricia.database;

import org.jdbi.v3.core.Jdbi;

public class Database {
    private Jdbi jdbi;;

    // Para conectar a la base de datos
    public void connect() {

        String url = "jdbc:mariadb://localhost:3306/aa_doctorwho";
        String usuario = "root";
        String contraseña = "5Jtlt6!!mar";

        jdbi = Jdbi.create(url, usuario, contraseña);
    }

    public Jdbi getJdbi() {
        return jdbi;
    }
}
