package modelo;

public abstract class Usuario {
	private String dni;
	private String nombre;
	private String apellidos;
	private Integer telefono;
	private String contrasena;

	
	public Usuario (String dni, String nombre, String apellidos, Integer telefono, String contrasena) {
		this.dni= dni;
		this.nombre= nombre;
		this.apellidos= apellidos;
		this.telefono= telefono;
		this.contrasena= contrasena;
	}
	
	public Usuario() {
		this.dni= null;
		this.contrasena= null;
		this.nombre = null;
		this.apellidos= null;
		this.telefono= null;
	}
	
	//GETTERS
	public String getContrasena() {
		return contrasena;
	}
	
	public String getDni() {
		return dni;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getTelefono() {
		return telefono;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	//SETTERS
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
}
