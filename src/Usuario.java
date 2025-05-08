
public class Usuario {
    private final String nombreUsuario;
    private final String contraseña;
    private final boolean esAdmin;

    public Usuario(String nombreUsuario, String contraseña, boolean esAdmin) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.esAdmin = esAdmin;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public boolean validarCredenciales(String usuarioIngresado, String contraseñaIngresada) {
        return this.nombreUsuario.equals(usuarioIngresado) && this.contraseña.equals(contraseñaIngresada);
    }

    public boolean esAdministrador() {
        return esAdmin;
    }

}

