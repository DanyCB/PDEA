package controlador;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ControladorAvisos implements Initializable {
    
	private static String MensajeError;
	 
	@FXML
    private AnchorPane AnchorPaneAvisos;

    @FXML
    private ImageView LogoEWBAvisos;

    @FXML
    private Label LabelTituloAvisos;
    
    @FXML
    private Label LabelMensajeError;
    
    @FXML
    private JFXButton ButtonVolver;
   
  
	@Override
    public void initialize(URL location, ResourceBundle reosurces) {
    	
    	System.out.println("Ventana de avisos:");
    	LabelMensajeError.setText(MensajeError);
    
    }
    
	//Metodos
    public void pressBtnVolver(ActionEvent event) {
		System.out.println("Cerrando ventana de avisos.");
		Stage VentanaAvisos = (Stage) ButtonVolver.getScene().getWindow();
		VentanaAvisos.close();
	}
    
  
    
    //Getters y Setters
    public static String getMensajeError() {
  		return MensajeError;
  	}

	public static void setMensajeError(String mensajeError) {
  		MensajeError = mensajeError;
  	}
  	
}
