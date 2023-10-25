package models;

/**
 *
 * @author Eymar
 */
public class model_categoria {

    private String clave;
    private Long fechaCreado;
    private String nombre;
    private boolean activo;

    public model_categoria(String clave, Long fechaCreado, String nombre, boolean activo) {
        this.clave = clave;
        this.fechaCreado = fechaCreado;
        this.nombre = nombre;
        this.activo = activo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Long fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
