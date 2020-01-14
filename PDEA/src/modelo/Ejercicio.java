package modelo;

public class Ejercicio{
	private Integer id;
	private String nombre;
	private Integer duracion;
	private String gif;
	
	public Ejercicio (Integer id, String nombre, Integer duracion, String gif) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.gif = gif;
	}
	
	public Ejercicio() {
		this.id = null;
		this.nombre = null;
		this.duracion = null;
		this.gif = null;
	}
	
	
	
	//GETTERS
	public Integer getDuracion() {
		return duracion;
	}
	public String getGif() {
		return gif;
	}
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	
	//SETTERS
	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}
	public void setGif(String gif) {
		this.gif = gif;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}