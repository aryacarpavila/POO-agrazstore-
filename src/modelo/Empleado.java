package modelo;


public class Empleado extends Usuario {
    private String ID;
    private String FechaNacimiento;


    public Empleado(String tipo, String nombre, String user, String correo, String contraseña, String ID, String FechaNacimiento) {
        super("EMPLEADO", nombre, user, correo, contraseña);
        this.ID = ID;
        this.FechaNacimiento = FechaNacimiento;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }


    public String getID() {
        return ID;
    }
  
}
