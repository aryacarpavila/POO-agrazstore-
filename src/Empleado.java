///////////**
///   en proceso
///
///**//////////

public class Empleado extends Usuario {
    private String ID;

    public Empleado(String nombre, String user, String correo, String contraseña, String ID) {
        super(nombre, user, correo, contraseña);
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
  
}
