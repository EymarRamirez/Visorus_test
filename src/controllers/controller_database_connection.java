package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Eymar
 */
public class controller_database_connection {

    private Connection connection;

    public controller_database_connection() {
        String url = "jdbc:postgresql://localhost:5432/visorus_test";
        String user = "postgres";
        String password = "RogStrixRamirez266";

        try {
            connection = DriverManager.getConnection(url, user, password);
            //System.out.println("Conexión a la base de datos realzada exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("Error al realizar la conexión a la base de datos");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

}
