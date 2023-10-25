package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.model_categoria;
import models.model_codigo_de_barras;
import models.model_precio;
import models.model_producto;

/**
 *
 * @author Eymar
 */
public class controller_producto {

    private Connection connection;

    private controller_precio controller_precio;
    private controller_codigo_de_barras controller_codigo_de_barras;

    public controller_producto(Connection connection) {
        this.connection = connection;
        controller_precio = new controller_precio(connection);
        controller_codigo_de_barras = new controller_codigo_de_barras(connection);
    }

    public void agregarProducto(model_producto producto) {
        String query = "INSERT INTO producto (clave, categoria_clave, nombre, activo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, producto.getClave());
            statement.setString(2, producto.getCategoria().getClave());
            statement.setString(3, producto.getNombre());
            statement.setBoolean(4, producto.getActivo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        controller_precio.agregarPrecios(producto);
        controller_codigo_de_barras.agregarCodigosBarras(producto);
    }

    public void actualizarProducto(model_producto producto) {
        String query = "UPDATE producto SET nombre = ?, categoria_clave = ?, activo = ? WHERE clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getCategoria().getClave());
            statement.setBoolean(3, producto.getActivo());
            statement.setString(4, producto.getClave());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        controller_precio.actualizarPrecios(producto);
        controller_codigo_de_barras.actualizarCodigosBarras(producto);
    }

    public void eliminarProducto(String clave) {
        String query = "DELETE FROM producto WHERE clave = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clave);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        controller_precio.eliminarPrecios(clave);
        controller_codigo_de_barras.eliminarCodigosBarras(clave);
    }

    public List<model_producto> listarProductos() {
        List<model_producto> productos = new ArrayList<>();
        String query = "SELECT p.clave, p.nombre, p.activo, c.clave as categoria_clave, c.fecha_creado as categoria_fecha, c.nombre as categoria_nombre "
                + "FROM producto p "
                + "INNER JOIN categoria c ON p.categoria_clave = c.clave";
        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String clave = resultSet.getString("clave");
                String nombre = resultSet.getString("nombre");
                boolean activo = resultSet.getBoolean("activo");
                String categoriaClave = resultSet.getString("categoria_clave");
                Long categoriaFechaCreado = resultSet.getLong("categoria_fecha");
                String categoriaNombre = resultSet.getString("categoria_nombre");
                model_categoria categoria = new model_categoria(categoriaClave, categoriaFechaCreado, categoriaNombre, true);

                List<model_precio> precios = controller_precio.obtenerPreciosDelProducto(clave);
                List<model_codigo_de_barras> codigosBarras = controller_codigo_de_barras.obtenerCodigosBarrasDelProducto(clave);

                model_producto producto = new model_producto(clave, categoria, nombre, precios, codigosBarras, activo);
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

}
