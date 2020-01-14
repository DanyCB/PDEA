package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import modelo.Medico;
import modelo.Mensaje;
import modelo.Paciente;

public class ControladorMedicoSubmenuPaciente implements Initializable {

    @FXML
    private Label campoMedico;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEjercicios;

    @FXML
    private JFXTabPane JFXTabPaneMensajeria;

    @FXML
    private Tab tabRecibidos;

    @FXML
    private AnchorPane anchorPaneRecibidos;

    @FXML
    private JFXButton btnResponder;

    @FXML
    private Accordion AccordionMensajesRec;

    @FXML
    private Label labelBandejaEntrada;

    @FXML
    private Tab tabEnviados;

    @FXML
    private AnchorPane anchorPaneEnviados;

    @FXML
    private Accordion AccordionMensajesEnv;

    @FXML
    private Label labelBandejaSalida;

    @FXML
    private TextArea campoRedactar;

    @FXML
    private JFXButton btnConfirmarEnvio;

    @FXML
    private JFXComboBox<?> comboBoxElegirDestinatario;

    @FXML
    private Label labelRedactar;
    
    @FXML
    private JFXTextField campoAsunto;

    private static Paciente pacienteActual = new Paciente();
    
    private static Medico medicoActual = new Medico();
    
    @Override
    public void initialize(URL location, ResourceBundle reosurces) {
    	System.out.println(medicoActual.getNombre());
    	System.out.println(pacienteActual.getNombre());
    	campoMedico.setText("Hola " +ControladorMedicopp.getMedicoActual().getNombre()+",");
    	
    	setTitledPanesEnviados();
    	setTitledPanesRecibidos();
    }
    
    @FXML
    void pressBtnConfirmarEnvio(ActionEvent event) {
        	
	    	if(campoRedactar.getText().length()>0) {
	    		try {
	    			String pac = pacienteActual.getDni();
		    		enviarMensaje(pac);
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
    void pressBtnResponder(ActionEvent event) {
    	JFXTabPaneMensajeria.getSelectionModel().select(2);
    }
    
    public static Paciente getPacienteActual() {
		return pacienteActual;
	}
    public static void setPacienteActual(Paciente p) {
		pacienteActual = p;
	}
    
    public static Medico getMedicoActual() {
    	return medicoActual;
    }
    public static void setMedicoActual(Medico m) {
    	medicoActual = m;
    }
    
    private void enviarMensaje(String dniPac) {
    	Mensaje msg = new Mensaje(getMedicoActual().getDni(), dniPac, campoRedactar.getText(), campoAsunto.getText());
		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
		
		mensajes= lectorJson.lectorJsonMensajes();
		mensajes.add(msg);
		escritorJson.escribirEnJsonMensajes(mensajes);

		ControladorAvisos.setMensajeError("Mensaje Enviado.");
		abrirVentanaAvisos();

		AccordionMensajesEnv.getPanes().clear();
		
		setTitledPanesEnviados();
		campoRedactar.clear();
		campoAsunto.clear();
    }
    
    
    public void setTitledPanesRecibidos() {
    	ArrayList<TitledPane> tpsr = new ArrayList<TitledPane>();

		if(numeroMensajesRecibidos()>0) {
    		for(int i=0; i<numeroMensajesRecibidos(); i++) {
    			ArrayList<Mensaje> mensajesRec =lectorJson.getMensajesA(medicoActual.getDni(), pacienteActual.getDni());
    			List<Mensaje> listMensajesRec = new ArrayList<Mensaje>();
				listMensajesRec.addAll(mensajesRec);
				Collections.sort(listMensajesRec, new sortByDate());
    			Mensaje mensajeAct = listMensajesRec.get(i);
    			
    			Paciente pacEmisor = lectorJson.getPaciente(mensajeAct.getEmisor());
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
				tp.setId(pacEmisor.getDni());
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

	public void setTitledPanesEnviados() {
		ArrayList<TitledPane> tpse = new ArrayList<TitledPane>();

    	if (numeroMensajesEnviados() > 0) {
			//identificar primero tipo de usuario

			for (int i = 0; i < numeroMensajesEnviados(); i++) {
				ArrayList<Mensaje> mensajesEnv  = lectorJson.getMensajesA(medicoActual.getDni(), pacienteActual.getDni());
				List<Mensaje> listMensajesEnv = new ArrayList<Mensaje>();
				listMensajesEnv.addAll(mensajesEnv);
				Collections.sort(listMensajesEnv, new sortByDate());
				Mensaje mensajeAct = mensajesEnv.get(i);
				
				Label contenido = new Label(mensajeAct.getMensaje());
				ScrollPane panelContenido = new ScrollPane(contenido);
				contenido.minHeight(60);
				contenido.boundsInParentProperty();
				contenido.wrapTextProperty();
				
				//Label titled pane con asunto fecha y hora
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Asunto: ");
				stringBuilder.append(mensajeAct.getAsunto());
				stringBuilder.append("\r");
				stringBuilder.append(mensajeAct.getFechaString());
				
				TitledPane tp = new TitledPane(stringBuilder.toString(), panelContenido) ;
				tp.setId(pacienteActual.getDni());
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
	
	

	public Integer numeroMensajesRecibidos() {
		Medico m = ControladorMedicopp.getMedicoActual();
		return lectorJson.getMensajesA(m.getDni(), pacienteActual.getDni()).size();
	}

	public Integer numeroMensajesEnviados() {
		Medico m = ControladorMedicopp.getMedicoActual();
		return lectorJson.getMensajesA(pacienteActual.getDni(), m.getDni()).size();
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
}