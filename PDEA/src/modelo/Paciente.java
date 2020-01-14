package modelo;

import java.util.ArrayList;
import java.util.Date;

public final class Paciente extends Usuario{
	private Date fecha_nacimiento;
	private ArrayList<Integer> ejercicios;
	ArrayList<String> cuidadores; 
	String medico;
	
	public Paciente(String dni, String nombre, String apellidos, Integer telefono, String contrasena, Date fecha_nacimiento, ArrayList<String> cuidadores, String medico, ArrayList<Integer> ejercicios) {
		super(dni, nombre, apellidos, telefono, contrasena);
		this.fecha_nacimiento= fecha_nacimiento;
		this.cuidadores = cuidadores;
		this.medico = medico;
		this.ejercicios = ejercicios;
		
	}
	
	public Paciente () {
		super();
		this.fecha_nacimiento= null;
		this.medico = null;
		this.cuidadores = new ArrayList<String>();
		this.ejercicios = new ArrayList<Integer>();
	}
	
	//GETTERS
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public ArrayList<String> getCuidadores(){
		return cuidadores;
	}
	public String getMedico() {
		return medico;
	}

	public ArrayList<Integer> getEjercicios() {
		return ejercicios;
	}

	
	//SETTERS
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento= fecha_nacimiento;
	}

	public void setCuidadores (ArrayList<String> cuidadores) {
		this.cuidadores = cuidadores;
	}
	
	public void setMedico (String medico) {
		this.medico = medico;
	}
	
	public void setEjercicios(ArrayList<Integer> ejercicios) {
		this.ejercicios = ejercicios;
	}
	//METODOS


		
}

