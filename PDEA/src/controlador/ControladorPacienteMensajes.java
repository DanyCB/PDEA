package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Accordion;
import javafx.scene.text.Font;
import modelo.Mensaje;
import modelo.Paciente;

public class ControladorPacienteMensajes implements Initializable{

	
	@FXML
	private JFXButton btnEnviar; 
	
	@FXML
	private JFXButton btnVolver;
	
	@FXML
    private Label campoPaciente;
	
	@FXML
	private Tab tabRecibidos;
	
	@FXML
	private TitledPane structMensajeRecib;
	
	@FXML
	private Label textoMensajeRecib;
	
	@FXML
	private Tab tabEnviados;
	
	@FXML
	private TitledPane structMensajeEnv;
	
	@FXML
	private Label textoMensajeEnv;
	
	@FXML
	private TextArea campoEscritura;
	
	@FXML
	private TextField campoAsunto;
	
	@FXML
    private Accordion AccordionMensajesRec;
	
	@FXML
    private Accordion AccordionMensajesEnv;
	
    @FXML
    private AnchorPane anchorPaneRecibidos;

    @FXML
    private AnchorPane anchorPaneEnviados;

		
	
	private static Paciente pacienteActual = new Paciente();
	
	
	@Override
	public void initialize(URL location, ResourceBundle reosurces) {
		Paciente p = ControladorPacientepp.getPacienteActual();
		campoPaciente.setText("Hola " +p.getNombre()+",");
		
		setTitlePanesRecibidos();
		setTitlePanesEnviados();
		
	}
	
	@FXML
	void pressBtnEnviar(ActionEvent event) {
		if(campoEscritura.getLength()>0){
			
			try {
				Paciente p = ControladorPacientepp.getPacienteActual();
				String medPac = p.getMedico();
				Mensaje msg = new Mensaje(p.getDni(), medPac, campoEscritura.getText(), campoAsunto.getText());
				System.out.println("El mensaje ha sido creado");
				ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
				mensajes= lectorJson.lectorJsonMensajes();
				mensajes.add(msg);
				escritorJson.escribirEnJsonMensajes(mensajes);
			
				ControladorAvisos.setMensajeError("Mensaje Enviado.");
				abrirVentanaAvisos();
				
				AccordionMensajesEnv.getPanes().clear();
				
				setTitlePanesEnviados();
				
				campoAsunto.clear();
				campoEscritura.clear();
				
			}
			catch(Exception a) {
				ControladorAvisos.setMensajeError("Error enviando el mensaje.");
				abrirVentanaAvisos();
			}
		}
		else{
			ControladorAvisos.setMensajeError("No ha introducido texto alguno en el mensaje que intenta enviar.");
			abrirVentanaAvisos();
		}
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
		ControladorPacienteMensajes.pacienteActual = pacienteActual;
	}
	
	//METODOS
	public Integer numeroMensajesRecibidos() {
		Paciente p = ControladorPacientepp.getPacienteActual();
		return lectorJson.getMensajesEnviadosA(p.getDni()).size();
	}
	
	public Integer numeroMensajesEnviados() {
		Paciente p = ControladorPacientepp.getPacienteActual();
		return lectorJson.getMensajesEnviadosPor(p.getDni()).size();
	}

	public class sortByDate implements Comparator<Mensaje> {
		 
	    @Override
	    public int compare(Mensaje m2, Mensaje m1) {
	        return m1.getFecha().compareTo(m2.getFecha());
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
    
    public void setTitlePanesEnviados() {
		Paciente p = ControladorPacientepp.getPacienteActual();

		ArrayList<TitledPane> tpse = new ArrayList<TitledPane>();

		if (numeroMensajesEnviados() > 0) {
			
			for (int i = 0; i < numeroMensajesEnviados(); i++) {
				ArrayList<Mensaje> mensajesEnv  = lectorJson.getMensajesEnviadosPor(p.getDni());
				List<Mensaje> listMensajesEnv = new ArrayList<Mensaje>();
				listMensajesEnv.addAll(mensajesEnv);
				Collections.sort(listMensajesEnv, new sortByDate());
				Mensaje mensajeAct = listMensajesEnv.get(i);
				
				Label contenido = new Label(mensajeAct.getMensaje());
				ScrollPane panelContenido = new ScrollPane(contenido);
				contenido.boundsInParentProperty();
				
				//Label titled pane con asunto fecha y hora
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Asunto: ");
				stringBuilder.append(mensajeAct.getAsunto());
				stringBuilder.append("\r");
				stringBuilder.append(mensajeAct.getFechaString());
				
				TitledPane tp = new TitledPane(stringBuilder.toString(), panelContenido) ;
				tpse.add(i, tp);
				
			}
			
			AccordionMensajesEnv.setLayoutY(60);
			AccordionMensajesEnv.setLayoutX(5);
			AccordionMensajesEnv.getPanes().addAll(tpse);
			AnchorPane.setTopAnchor(AccordionMensajesEnv, Double.valueOf(30));
		}
		else {
			Label emptyEnv = new Label("No hay mensajes enviados.");
			emptyEnv.setFont(new Font("Arial", 18));
			emptyEnv.setLayoutY(60);
			emptyEnv.setLayoutX(5);
			anchorPaneEnviados.getChildren().add(emptyEnv);
			
			AnchorPane.setTopAnchor(emptyEnv, Double.valueOf(40));
		}
    }
    
    public void setTitlePanesRecibidos() {
		Paciente p = ControladorPacientepp.getPacienteActual();

		ArrayList<TitledPane> tpsr = new ArrayList<TitledPane>();

    	if (numeroMensajesRecibidos() > 0) {
    		
			for (int i = 0; i < numeroMensajesRecibidos(); i++) {
				ArrayList<Mensaje> mensajesRec  = lectorJson.getMensajesEnviadosA(p.getDni());
				List<Mensaje> listMensajesRec = new ArrayList<Mensaje>();
				listMensajesRec.addAll(mensajesRec);
				Collections.sort(listMensajesRec, new sortByDate());
				Mensaje mensajeAct = listMensajesRec.get(i);
				
				Label contenido = new Label(mensajeAct.getMensaje());
				ScrollPane panelContenido = new ScrollPane(contenido);
				contenido.boundsInParentProperty();
				
				//Label titled pane con asunto fecha y hora
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Asunto: ");
				stringBuilder.append(mensajeAct.getAsunto());
				stringBuilder.append("\r");
				stringBuilder.append(mensajeAct.getFechaString());
				
				TitledPane tp = new TitledPane(stringBuilder.toString(), panelContenido) ;	
				tpsr.add(i, tp);
				
			}
			
			AccordionMensajesRec.setLayoutY(60);
			AccordionMensajesRec.setLayoutX(5);
			AccordionMensajesRec.getPanes().addAll(tpsr);
			AnchorPane.setTopAnchor(AccordionMensajesRec, Double.valueOf(30));
		}
		else {
			Label emptyRec = new Label("No hay mensajes en la bandeja de entrada.");
			emptyRec.setFont(new Font("Arial", 18));
			emptyRec.setLayoutY(60);
			emptyRec.setLayoutX(5);
			anchorPaneRecibidos.getChildren().add(emptyRec);
			
			AnchorPane.setTopAnchor(emptyRec, Double.valueOf(40));
		}
    }
}
