
package controlador;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import modelo.Cuidador;
import modelo.Medico;
import modelo.Mensaje;
import modelo.Paciente;



public class escritorJson {



	public static void escribirEnJsonPacientes(ArrayList<Paciente> pacientes) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter ("pacientes.json")){
			gson.toJson(pacientes, writer);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void escribirEnJsonMedicos(ArrayList<Medico> medicos) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter ("medicos.json")){
			gson.toJson(medicos, writer);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void escribirEnJsonCuidadores(ArrayList<Cuidador> cuidadores) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter ("cuidadores.json")){
			gson.toJson(cuidadores, writer);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void escribirEnJsonMensajes(ArrayList<Mensaje> mensajes) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try (FileWriter writer = new FileWriter ("mensajes.json")){
			gson.toJson(mensajes, writer);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	
}
