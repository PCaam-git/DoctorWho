package util;

import org.jdbi.v3.core.Jdbi;

public class JdbiConnector {
    private static final Jdbi jdbi = Jdbi.create(
        "jdbc:sqlite:doctorwho.db"   // o "jdbc:mysql://localhost:3306/doctorwho?user=tuUsuario&password=tuPass"
    );

    public static Jdbi get() {
        return jdbi;
    }
}
