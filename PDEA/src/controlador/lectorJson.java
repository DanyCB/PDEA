package controlador;

import java.io.FileReader;
import java.lang.reflect.Type;

import java.util.ArrayList;

import modelo.Cuidador;
import modelo.Medico;
import modelo.Mensaje;
import modelo.Paciente;
import modelo.Ejercicio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class lectorJson {
	
	public static ArrayList<Paciente> lectorJsonPacientes() {
		try {
			//guardas en una clase FileReader el nombre del json que quieres leer
			FileReader fr = new FileReader("pacientes.json");
			Gson gson= new Gson();
			//Guardas el tipo de datos que se va a encontrar dentro del json
			//en este caso un arraylist de Paciente
			Type tipoListaPacientes = new TypeToken<ArrayList<Paciente>>(){}.getType();
			//guardas con el fromJson en la variable que vas a devolver, los argumentos siempre son el FileReader y el tipo de datos que se va a encontrar
			ArrayList<Paciente> pacientes = gson.fromJson(fr, tipoListaPacientes);
			//devuelves el arraylist de pacientes
			return pacientes;
		}catch (Exception e) {
			System.out.println("Fallo en: " +e);
		}
		return null;
	}

	//Lo mismo que la anterior
	public static ArrayList<Medico> lectorJsonMedicos() {
		try {
			FileReader fr = new FileReader("medicos.json");
			Gson gson= new Gson();
			Type tipoListaMedico = new TypeToken<ArrayList<Medico>>(){}.getType();
			ArrayList<Medico> medicos = gson.fromJson(fr, tipoListaMedico);
			return medicos;
		}catch (Exception e) {
			System.out.println("Fallo en: " +e);
		}
		return null;
	}
	
	public static ArrayList<Cuidador> lectorJsonCuidadores() {
		try {
			FileReader fr = new FileReader("cuidadores.json");
			Gson gson= new Gson();
			Type tipoListaCuidador = new TypeToken<ArrayList<Cuidador>>(){}.getType();
			ArrayList<Cuidador> cuidadores = gson.fromJson(fr, tipoListaCuidador);
			return cuidadores;
		}catch (Exception e) {
			System.out.println("Fallo en: " +e);
		}
		return null;
	}
	
	public static ArrayList<Mensaje> lectorJsonMensajes() {
		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			FileReader fr = new FileReader("mensajes.json");
			Gson gson= new Gson();
			Type tipoListoMensajes = new TypeToken<ArrayList<Mensaje>>(){}.getType();
			mensajes = gson.fromJson(fr, tipoListoMensajes);
		}catch (Exception e) {
			System.out.println("Fallo en: " +e);
		}
		return mensajes;
	}
	
	public static ArrayList<Ejercicio> lectorJsonEjercicios() {
		ArrayList<Ejercicio> ejercicios = new ArrayList<Ejercicio>();
		try {
			FileReader fr = new FileReader("ejercicios.json");
			Gson gson= new Gson();
			Type tipoListaEjercicios = new TypeToken<ArrayList<Ejercicio>>(){}.getType();
			ejercicios = gson.fromJson(fr, tipoListaEjercicios);
		}catch (Exception e) {
			System.out.println("Fallo en: " +e);
		}
		return ejercicios;
	}
	
	//METODOS
	
	//Buscas un paciente por su dni empleando la funcion leer pacientes anterior  y lo recorres comparando los dnis
	public static Paciente getPaciente (String dni) {
		Paciente p = new Paciente();
		ArrayList<Paciente> pac = lectorJsonPacientes();
		for (int i = 0; i< pac.size(); i++) {
			p = pac.get(i);
			if (p.getDni().equalsIgnoreCase(dni)) {
				return p;
			}
		}
		return null;
	}
	
	public static Medico getMedico (String dni) {
		Medico p = new Medico();
		ArrayList<Medico> pac = lectorJsonMedicos();
		for (int i = 0; i< pac.size(); i++) {
			p = pac.get(i);
			if (p.getDni().equalsIgnoreCase(dni)) {
				return p;
			}
		}
		return null;
	}
	
	public static Cuidador getCuidador (String dni) {
		Cuidador p = new Cuidador();
		ArrayList<Cuidador> pac = lectorJsonCuidadores();
		for (int i = 0; i< pac.size(); i++) {
			p = pac.get(i);
			if (p.getDni().equalsIgnoreCase(dni)) {
				return p;
			}
		}
		return null;
	}
	
	// El dni que metes es el del emisor
	public static ArrayList<Mensaje> getMensajesEnviadosPor (String dni) {
		ArrayList<Mensaje> mensajesEnviados = new ArrayList<Mensaje>();
		Mensaje m = new Mensaje();
		ArrayList<Mensaje> mensajes = lectorJsonMensajes();
		for (int i = 0; i< mensajes.size(); i++) {
			m = mensajes.get(i);
			if(!m.isBorrado()) {
				if (m.getEmisor().equalsIgnoreCase(dni)) {
					mensajesEnviados.add(m);
				}
			}
		}
		return mensajesEnviados;
	}
	
	// El dni que metes es el del receptor
	public static ArrayList<Mensaje> getMensajesEnviadosA (String dni) {
		ArrayList<Mensaje> mensajesRecibidos = new ArrayList<Mensaje>();
		Mensaje m = new Mensaje();
		ArrayList<Mensaje> mensajes = lectorJsonMensajes();
		for (int i = 0; i< mensajes.size(); i++) {
			m = mensajes.get(i);
			if(!m.isBorrado()) {
				if (m.getReceptor().equalsIgnoreCase(dni)) {
					mensajesRecibidos.add(m);
				}
			}
		}
		return mensajesRecibidos;
	}
	
	public static ArrayList<Mensaje> getMensajesA(String dniPac, String dniMed){
		ArrayList<Mensaje> mensajesA = new ArrayList<Mensaje>();
		Mensaje m = new Mensaje();
		ArrayList<Mensaje> mensajes = lectorJsonMensajes();
		for (int i = 0; i <mensajes.size(); i++) {
			m = mensajes.get(i);
			if(!m.isBorrado()) {
				if(m.getReceptor().equalsIgnoreCase(dniPac)) {
					if(m.getEmisor().equalsIgnoreCase(dniMed)) {
						mensajesA.add(m);
					}
				}
			}
		}
		return mensajesA;
	}
	
	//Devuelve los ejercicios correspondientes al paciente
	public static ArrayList<Ejercicio> getEjercicios (Paciente p) {
		//Array ids de los ejercicios de paciente
		ArrayList<Integer> idsEjercicios = p.getEjercicios();
		//Array donde estan ejercicios
		ArrayList<Ejercicio> pac = lectorJsonEjercicios();
		ArrayList<Ejercicio> ejerciciosDePaciente = new ArrayList<Ejercicio>();
		for (int i = 0; i < idsEjercicios.size(); i++) {
			for(int a=0; a< pac.size();a++) {
				if (idsEjercicios.get(i).equals(pac.get(a).getId())){
					ejerciciosDePaciente.add(pac.get(a));
				}
			}	
		}
	 return ejerciciosDePaciente;	
	}
	
	public static ArrayList<String> getNombresCompletosPacientesDe (Medico m){
		ArrayList<String> nombresCompletos= new ArrayList<String>();
		ArrayList<String> dnis = m.getPacientes();
		for (int i = 0; i < dnis.size() ; i++) {
			Paciente p = getPaciente(dnis.get(i));
			String n = p.getNombre() + " " + p.getApellidos();
			nombresCompletos.add(n);
		}
		return nombresCompletos;
	}
	
	
}
