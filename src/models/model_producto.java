package models;

import java.util.List;

/**
 *
 * @author Eymar
 */
public class model_producto {

    private String clave;
    private model_categoria categoria;
    private String nombre;
    private List<model_precio> precios;
    private List<model_codigo_de_barras> codigosBarras;
    private boolean activo = true;

    public model_producto(String clave, model_categoria categoria, String nombre, List<model_precio> precios, List<model_codigo_de_barras> codigosBarras, boolean activo) {
        this.clave = clave;
        this.categoria = categoria;
        this.nombre = nombre;
        this.precios = precios;
        this.codigosBarras = codigosBarras;
        this.activo = activo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public model_categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(model_categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<model_precio> getPrecios() {
        return precios;
    }

    public void setPrecios(List<model_precio> precios) {
        this.precios = precios;
    }

    public List<model_codigo_de_barras> getCodigosBarras() {
        return codigosBarras;
    }

    public void setCodigosBarras(List<model_codigo_de_barras> codigosBarras) {
        this.codigosBarras = codigosBarras;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
