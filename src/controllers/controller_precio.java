package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.model_precio;
import models.model_producto;

/**
 *
 * @author Eymar
 */
public class controller_precio {

    private Connection connection;

    public controller_precio(Connection connection) {
        this.connection = connection;
    }

    public void agregarPrecios(model_producto producto) {
        String query = "INSERT INTO precio (producto_clave, precio) VALUES (?, ?)";
        for (model_precio precio : producto.getPrecios()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, producto.getClave());
                statement.setFloat(2, precio.getPrecio());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void actualizarPrecios(model_producto producto) {
        String deleteQuery = "DELETE FROM precio WHERE producto_clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setString(1, producto.getClave());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        agregarPrecios(producto);
    }

    public void eliminarPrecios(String claveProducto) {
        String query = "DELETE FROM precio WHERE producto_clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, claveProducto);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<model_precio> obtenerPreciosDelProducto(String claveProducto) {
        List<model_precio> precios = new ArrayList<>();
        String query = "SELECT precio FROM precio WHERE producto_clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, claveProducto);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    float precio = resultSet.getFloat("precio");
                    model_precio precioObj = new model_precio(precio);
                    precios.add(precioObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precios;
    }

}
