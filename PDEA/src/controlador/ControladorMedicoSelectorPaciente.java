package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modelo.Medico;
import modelo.Paciente;


public class ControladorMedicoSelectorPaciente implements Initializable{


    @FXML
    private JFXComboBox<String> inputBuscarPaciente;
    
    @FXML
    private Label campoMedico;

    @FXML
    private JFXButton btnBuscarPaciente;

    @FXML
    private JFXButton btnMenuGeneral;
    
    private ArrayList<String> nombresPacientes = lectorJson.getNombresCompletosPacientesDe(medicoActual);
    
    private ObservableList<String> listaPacientesComboBox = FXCollections.observableArrayList(nombresPacientes);
    
    private static Medico medicoActual = ControladorMedicopp.getMedicoActual();

    //Metodos

    @Override
    public void initialize(URL location, ResourceBundle reosurces) {
    	campoMedico.setText("Hola " +ControladorMedicopp.getMedicoActual().getNombre()+",");
    	inputBuscarPaciente.setItems(listaPacientesComboBox);
    }
    
    public void pressEnter (KeyEvent event) throws IOException {
    	if(event.getCode().equals(KeyCode.ENTER)) {
			try {
				this.pressBtnBuscar();
			} catch (ControladorExcepciones e) {
				ControladorAvisos.setMensajeError("El usuario seleccionado es incorrecto");
				e.abrirVentanaAvisos();
			}
		}
    }

    @FXML
    void comprobarInput(KeyEvent event) throws Exception{
    	//comparar el nombre introducido con los pacientes asignados al medico, para sugerir posibles coincidencias de forma dinamica
    	
    	ArrayList<String> nombresPacientes = lectorJson.getNombresCompletosPacientesDe(medicoActual);
        ArrayList<String> sugerencias = new ArrayList<String>();
        
        boolean sugerenciasEncontradas;
        if(inputBuscarPaciente.getValue()!=null) {
	    	for(int i=0; i< medicoActual.getPacientes().size(); i++) {
	    		//bucle que va comparando el input con el nombre de cada paciente
	    		int longitud=0;
    		
	    		for(int a=0; a<inputBuscarPaciente.getValue().length(); a++) {
	    			//bucle que va comparando los char del input con los char del nombre de paciente
		            if(inputBuscarPaciente.getValue().toLowerCase().charAt(a)==nombresPacientes.get(i).toLowerCase().charAt(a)) {
		            	longitud++;
			    	}
		            else {
		            	break;
		            }
	            }
	            if(longitud==inputBuscarPaciente.getValue().length() ) {
	            	//add nombre en posicion i a un nuevo arraylist que se pasa al comboBox con la observableList listaSugerencias
		        	sugerencias.add(nombresPacientes.get(i));
	            	sugerenciasEncontradas=true;
		        }
	    	}
	
	    	if(sugerenciasEncontradas=true){
	    		inputBuscarPaciente.getItems().clear();
		    	ObservableList<String> listaSugerencias = FXCollections.observableArrayList(sugerencias);
		    	inputBuscarPaciente.setItems(listaSugerencias);
	    	}
        }
    	else {
    		//por defecto se muestra la lista entera de pacientes
    		ObservableList<String> listaPacientesComboBox = FXCollections.observableArrayList(nombresPacientes);
    		inputBuscarPaciente.setItems(listaPacientesComboBox);
    	}
    	inputBuscarPaciente.autosize();
    	inputBuscarPaciente.show();
    }

    @FXML
    void pressBtnBuscarPaciente(ActionEvent event) throws IOException {
    	try {
    		this.pressBtnBuscar();
    	}catch(ControladorExcepciones e) {
    		ControladorAvisos.setMensajeError("El usuario seleccionado no es correcto.");
    		e.abrirVentanaAvisos();
    	}
    	
    }
    private void pressBtnBuscar() throws IOException {
    	String pacienteBuscado = inputBuscarPaciente.getValue();

    	Paciente p = coincidencia(pacienteBuscado);
    	if (coincidencia(pacienteBuscado)!= null) {
    		try {
	    		System.out.println("coincidencia encontrada.");
	    		System.out.println("Cargando ventana principal de Medico...");
	    		ControladorMedicoSubmenuPaciente.setMedicoActual(medicoActual);
	    		ControladorMedicoSubmenuPaciente.setPacienteActual(p);
				Parent medicoSubMenuPaciente = FXMLLoader.load(getClass().getResource("/vista/medico_submenu_paciente.fxml"));
				Stage subMenuPaciente = new Stage();
				subMenuPaciente.setTitle("Menu " +p.getNombre() + " " + p.getApellidos());
				subMenuPaciente.setScene(new Scene(medicoSubMenuPaciente));
				subMenuPaciente.show();
				subMenuPaciente.setMinHeight(600);
				subMenuPaciente.setMinWidth(800);
				
				
				System.out.println("Cerrando ventana Selector...");
				Stage CerrarSelectorPaciente = (Stage) btnMenuGeneral.getScene().getWindow();
				CerrarSelectorPaciente.close();
    		}catch(ControladorExcepciones case1) {
    			ControladorAvisos.setMensajeError("No se pudo abrir la ventana de Submenu.");
				case1.abrirVentanaAvisos();
    		}
		}
    	else {
	   		// imprimir mensaje de aviso en caso de no encontrar coincidencia alguna
	    	ControladorAvisos.setMensajeError("No se ha encontrado el paciente introducido.");
	       	abrirVentanaAvisos();
		}
    }

    private Paciente coincidencia (String pacienteBuscado) {
    	for(int i=0; i< medicoActual.getPacientes().size(); i++) {
            ArrayList<String> nombresPacientes = lectorJson.getNombresCompletosPacientesDe(medicoActual);
	    	if(nombresPacientes.get(i).equalsIgnoreCase(pacienteBuscado)) {
	    		Paciente p = lectorJson.getPaciente(medicoActual.getPacientes().get(i));
	    		return p;
	    	}
    	}
    	return null;
    }
    
    @FXML
    void pressBtnMenuGeneral(ActionEvent event) throws IOException{
    	//abre la ventana general de medico
    	try {
			System.out.println("Cargando ventana principal de Medico...");
			Parent MedicoVentana = FXMLLoader.load(getClass().getResource("/vista/medicopp.fxml"));
			Stage Medicopp = new Stage();
			Medicopp.setTitle("Menu Principal Medico");
			Medicopp.setScene(new Scene(MedicoVentana));
			Medicopp.show();
			Medicopp.setMinHeight(600);
			Medicopp.setMinWidth(800);

			Stage CerrarSelectorPaciente = (Stage) btnMenuGeneral.getScene().getWindow();
			CerrarSelectorPaciente.close();
		}

		catch(ControladorExcepciones case3){
			ControladorAvisos.setMensajeError("No se pudo abrir la ventana de Medico.");
			case3.abrirVentanaAvisos();
		}
    }


    public void abrirVentanaAvisos() {
		try {
			Parent avisos = FXMLLoader.load(getClass().getResource("../vista/avisos.fxml"));
			Stage VentanaAvisos = new Stage();
			VentanaAvisos.setTitle("Aviso");
			VentanaAvisos.setScene(new Scene(avisos));
			VentanaAvisos.show();
			VentanaAvisos.setMinHeight(200);
			VentanaAvisos.setMinWidth(500);
			VentanaAvisos.setMaxHeight(200);
			VentanaAvisos.setMaxWidth(600);

		}
		catch(Exception a) {
			System.out.println("Error");
		}
	}

    //Getters y Setters
    public static Medico getMedicoActual() {
		return medicoActual;
	}

	public static void setMedicoActual(Medico MedicoActual) {
		medicoActual = MedicoActual;
	}

}
