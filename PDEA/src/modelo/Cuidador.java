package modelo;

import java.util.ArrayList;

public final class Cuidador extends Usuario{
	ArrayList<String> pacientes;
	public Cuidador(String dni, String nombre, String apellidos, Integer telefono, String contrasena, ArrayList<String> pacientes) {
		super(dni, nombre, apellidos, telefono, contrasena);
		this.pacientes = pacientes;
	}
	
	public Cuidador() {
		super();
		this.pacientes = new ArrayList<String>();
	}
	
	//GETTERS
	public ArrayList<String> getPacientes(){
		return pacientes;
	}
	
	//SETTERS
	public void setPacientes(ArrayList<String> pacientes) {
		this.pacientes = pacientes;
	}
}
