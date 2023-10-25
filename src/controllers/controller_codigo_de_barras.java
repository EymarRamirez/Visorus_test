package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.model_codigo_de_barras;
import models.model_producto;

/**
 *
 * @author Eymar
 */
public class controller_codigo_de_barras {

    private Connection connection;

    public controller_codigo_de_barras(Connection connection) {
        this.connection = connection;
    }

    public void agregarCodigosBarras(model_producto producto) {
        String query = "INSERT INTO codigo_de_barras (producto_clave, codigo) VALUES (?, ?)";
        for (model_codigo_de_barras codigo : producto.getCodigosBarras()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, producto.getClave());
                statement.setString(2, codigo.getCodigo());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void actualizarCodigosBarras(model_producto producto) {
        String deleteQuery = "DELETE FROM codigo_de_barras WHERE producto_clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setString(1, producto.getClave());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        agregarCodigosBarras(producto);
    }

    public void eliminarCodigosBarras(String claveProducto) {
        String query = "DELETE FROM codigo_de_barras WHERE producto_clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, claveProducto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<model_codigo_de_barras> obtenerCodigosBarrasDelProducto(String claveProducto) {
        List<model_codigo_de_barras> codigosBarras = new ArrayList<>();
        String query = "SELECT codigo FROM codigo_de_barras WHERE producto_clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, claveProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String codigo = resultSet.getString("codigo");
                    model_codigo_de_barras codigoDeBarras = new model_codigo_de_barras(codigo);
                    codigosBarras.add(codigoDeBarras);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigosBarras;
    }

}
