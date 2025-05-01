package com.patricia.database;

import org.jdbi.v3.core.Jdbi;

public class Database {
    private Jdbi jdbi;
    
    public Database() {
        // No hacer nada en el constructor
    }
    
    public void connect() {
        try {
            // Carga explícita del driver
            Class.forName("org.mariadb.jdbc.Driver");
            
            // Configuración de la conexión
            String url = "jdbc:mariadb://localhost:3306/aa_doctorwho";
            String user = "root"; 
            String password = "5Jtlt6!!mar"; 
            
            // Crear el objeto Jdbi
            this.jdbi = Jdbi.create(url, user, password);
            System.out.println("Conexión establecida correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MariaDB: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public Jdbi getJdbi() {
        return jdbi;
    }
}