package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.model_categoria;

/**
 *
 * @author Eymar
 */
public class controller_categoria {

    private Connection conn;

    public controller_categoria(Connection conn) {
        this.conn = conn;
    }

    public boolean agregarCategoria(model_categoria categoria) {
        try {
            String sql = "INSERT INTO categoria (clave, fecha_creado, nombre, activo) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, categoria.getClave());
            statement.setLong(2, categoria.getFechaCreado());
            statement.setString(3, categoria.getNombre());
            statement.setBoolean(4, categoria.getActivo());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editarCategoria(model_categoria categoria) {
        try {
            String sql = "UPDATE categoria SET fecha_creado = ?, nombre = ?, activo = ? WHERE clave = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, categoria.getFechaCreado());
            statement.setString(2, categoria.getNombre());
            statement.setBoolean(3, categoria.getActivo());
            statement.setString(4, categoria.getClave());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCategoria(String clave) {
        try {
            String sql = "DELETE FROM categoria WHERE clave = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, clave);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<model_categoria> listarCategorias() {
        List<model_categoria> categorias = new ArrayList<>();
        try {
            String sql = "SELECT clave, nombre, fecha_creado, activo FROM categoria";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                model_categoria categoria = new model_categoria(
                        resultSet.getString("clave"), resultSet.getLong("fecha_creado"), resultSet.getString("nombre"), resultSet.getBoolean("activo")
                );
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }

}
