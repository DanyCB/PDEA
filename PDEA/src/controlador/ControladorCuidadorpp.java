package controlador;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.scene.control.Label;
import modelo.Cuidador;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class ControladorCuidadorpp implements Initializable {

	 	@FXML
	    private ComboBox<?> comboPacientes;

	    @FXML
	    private Button btnAvisos;

	    @FXML
	    private Button btnEjercicios;

	    @FXML
	    private Button btnDatos;
	    
	    @FXML
	    private Label campoCuidador;
	    
	    private static  Cuidador cuidadorActual = new Cuidador();
	    
	    @Override
	    public void initialize(URL location, ResourceBundle reosurces) {
	    	//add controlador para tomar solo el nombre(comprobar si cada caracter es un espacio y cuando lo sea cortar el string ahi).
	    	campoCuidador.setText("Hola " +ControladorCuidadorpp.getCuidadorActual().getNombre()+",");
		}
	    
	    //Getters y Setters
		public static Cuidador getCuidadorActual() {
			return cuidadorActual;
		}

		public static void setCuidadorActual(Cuidador CuidadorActual) {
			cuidadorActual = CuidadorActual;
		}
}
