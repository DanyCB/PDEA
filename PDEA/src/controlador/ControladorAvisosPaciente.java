package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import modelo.Ejercicio;
import modelo.Paciente;


public class ControladorAvisosPaciente implements Initializable{



	@FXML
	private ImageView LogoEWBAvisos;
	@FXML
	private Label campoEjercicios;
	@FXML
	private JFXButton btnVolver;
	@FXML
	private Label campoPaciente;
	@FXML
	private ArrayList<Ejercicio> ejercicios;

	private static Paciente pacienteActual = new Paciente();

	@Override
	public void initialize(URL location, ResourceBundle reosurces) {

		Paciente p = ControladorPacientepp.getPacienteActual();
		campoPaciente.setText("Hola " +p.getNombre()+",");
		this.ejercicios = lectorJson.getEjercicios(p);
		campoEjercicios.setText("Le faltan "+ this.ejercicios.size()+" ejercicios.");

	}

	@FXML
	void pressBtnVolver(ActionEvent event) throws IOException {
		try {
			System.out.println("Cargando ventana principal de Paciente...");
			Parent PacienteVentana = FXMLLoader.load(getClass().getResource("/vista/menupaciente.fxml"));
			Stage Pacientepp = new Stage();
			Pacientepp.setTitle("Menu Principal Paciente");
			Pacientepp.setScene(new Scene(PacienteVentana));
			Pacientepp.show();
			Pacientepp.setMinHeight(550);
			Pacientepp.setMinWidth(500);

			System.out.println("Cerrando ventana de Login.");
			Stage CerrarVentanaLogin = (Stage) btnVolver.getScene().getWindow();
			CerrarVentanaLogin.close();
		}	

		catch(ControladorExcepciones case1){
			ControladorAvisos.setMensajeError("No se pudo abrir la ventana de Paciente.");
			case1.abrirVentanaAvisos();
		}
	}
	//GETTER
	public static Paciente getPacienteActual() {
		return pacienteActual;
	}

	//SETTER
	public static void setPacienteActual(Paciente pacienteActual) {
		ControladorAvisosPaciente.pacienteActual = pacienteActual;
	}
}





