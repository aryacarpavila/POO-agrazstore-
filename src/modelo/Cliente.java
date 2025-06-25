package modelo;

public class Cliente extends Usuario {
    
    private String FechaNacimiento;

    public Cliente(String tipo, String nombre, String user, String correo, String contraseña, String FechaNacimiento) {
        super("CLIENTE", nombre, user, correo, contraseña);
        this.FechaNacimiento = FechaNacimiento;
    }

    // Getters y Setters
    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

}

