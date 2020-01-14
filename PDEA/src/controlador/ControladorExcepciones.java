package controlador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


		public class ControladorExcepciones extends IOException {
			
			private static final long serialVersionUID = 2918227521048319923L;
			public ControladorExcepciones(){};
			

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
				catch(IOException a) {
					System.out.println("Error");
					 a.printStackTrace();
				}
			}
		}
		//-----------------------------------------

