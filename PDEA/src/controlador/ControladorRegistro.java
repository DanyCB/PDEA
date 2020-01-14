package controlador;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Cuidador;
import modelo.Medico;
import modelo.Paciente;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControladorRegistro implements Initializable {

    @FXML
    private TextField textoDNI;

    @FXML
    private TextField textoNombre;

    @FXML
    private PasswordField textoContrasena;

    @FXML
    private PasswordField textoContrasena2;

    @FXML
    private ComboBox<String> comboRol;

    @FXML
    private TextField textoApellidos;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private TextField textoTelefono;

    @FXML
    private TextField textoFechaNac;

    @FXML
    private JFXButton btnAceptar;



    private ObservableList<String> dbTypeList = FXCollections.observableArrayList("Paciente","Cuidador","Medico");


	@Override
	public void initialize(URL location, ResourceBundle reosurces) {
		comboRol.setItems(dbTypeList);
	}

    @FXML
    void pulsarBtnAceptar_reg(ActionEvent event) {
    try {
	    	textoDNI.getText();
	    	textoNombre.getText();
	    	textoApellidos.getText();
	
	    	
	    	//Comprobacion de que coinciden las contrase�as
	    	String pswrd =textoContrasena.getText();
	    	String pswrdSecond =textoContrasena2.getText();
	    	String passwordEncriptada = getMd5(pswrd);

	
	    	//Comprobacion de el resto de campos
	
	
	    	int roltype=0;
	
			if (comboRol.getValue().equals ("Paciente")) {
				roltype=1;
			}
			if (comboRol.getValue().equals ("Cuidador")) {
				roltype=2;
			}
			if (comboRol.getValue().equals ("Medico")) {
				roltype=3;
			}
	
			if (roltype==0) {
				abrirVentanaAvisos();
			}
	
	
	
			else {
				System.out.println("Usuario seleccionado: " +comboRol.getValue());
				if ( pswrd.length()<4 | !pswrd.equals(pswrdSecond) ){
		    		try {
		    			ControladorAvisos.setMensajeError("Ambas contrasenas deben coincidir y tener minimos de 4 caracteres.");
		    			abrirVentanaAvisos();
	
		    		}
		    		catch(Exception a) {
		    			System.out.println("Error");
		    			 a.printStackTrace();
		    		}
		    	}
	
	
				else {
					
					System.out.println(passwordEncriptada);
					if( comprobarDigitosDNI()==false| Character.isLetter(textoDNI.getText().charAt(8)) == false) {
						ControladorAvisos.setMensajeError("Por favor revise los datos introducidos.");
			    			abrirVentanaAvisos();
					}
					else {
						try {
							switch (roltype) {
	
								case 1:
									try {
										String sDate1=textoFechaNac.getText();
										Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
	
										Paciente newPaciente = new Paciente();
										newPaciente.setDni(textoDNI.getText().toUpperCase());
										newPaciente.setNombre(textoNombre.getText());
										newPaciente.setApellidos(textoApellidos.getText());
	
										newPaciente.setFecha_nacimiento(date1);
										newPaciente.setTelefono(Integer.parseInt(textoTelefono.getText()));
										newPaciente.setContrasena(passwordEncriptada);
										System.out.println("Registrando usuario Paciente");
	
										ArrayList<Paciente> pacientes = new ArrayList<Paciente>();
										pacientes = lectorJson.lectorJsonPacientes();
										pacientes.add(newPaciente);
										escritorJson.escribirEnJsonPacientes(pacientes);
	
	
										ControladorAvisos.setMensajeError("Usuario Registrado.");
										abrirVentanaAvisos();
										Stage  CerrarRegistro= (Stage) btnAceptar.getScene().getWindow();
										CerrarRegistro.close();
									}
									catch(Exception a) {
										ControladorAvisos.setMensajeError("Error registrando paciente, por favor revise los datos introducidos.");
										abrirVentanaAvisos();
									}
									break;
	
								case 2:
									try {
	
										Cuidador newCuidador = new Cuidador();
										newCuidador.setDni(textoDNI.getText());
										newCuidador.setNombre(textoNombre.getText());
										newCuidador.setApellidos(textoApellidos.getText());
	
	
										newCuidador.setTelefono(Integer.parseInt(textoTelefono.getText()));
										newCuidador.setContrasena(passwordEncriptada);
	
	
										ArrayList<Cuidador> cuidadores = new ArrayList<Cuidador>();
										cuidadores = lectorJson.lectorJsonCuidadores();
										cuidadores.add(newCuidador);
										escritorJson.escribirEnJsonCuidadores(cuidadores);
	
										ControladorAvisos.setMensajeError("Usuario Registrado.");
										abrirVentanaAvisos();
										Stage  CerrarRegistro= (Stage) btnAceptar.getScene().getWindow();
										CerrarRegistro.close();
									}
									catch(Exception a) {
										ControladorAvisos.setMensajeError("error registrando cuidador, por favor revise los datos introducidos.");
										abrirVentanaAvisos();
									}
									break;
	
								case 3:
									try {
	
	
										Medico newMedico = new Medico();
										newMedico.setDni(textoDNI.getText());
										newMedico.setNombre(textoNombre.getText());
										newMedico.setApellidos(textoApellidos.getText());
	
	
										newMedico.setTelefono(Integer.parseInt(textoTelefono.getText()));
										newMedico.setContrasena(passwordEncriptada);
	
	
										ArrayList<Medico> medicos = new ArrayList<Medico>();
										medicos = lectorJson.lectorJsonMedicos();
										medicos.add(newMedico);
										escritorJson.escribirEnJsonMedicos(medicos);
	
										ControladorAvisos.setMensajeError("Usuario Registrado.");
										abrirVentanaAvisos();
										Stage  CerrarRegistro= (Stage) btnAceptar.getScene().getWindow();
										CerrarRegistro.close();
									}
									catch(Exception a) {
										ControladorAvisos.setMensajeError("error registrando medico, por favor revise los datos introducidos.");
										abrirVentanaAvisos();
									}
									break;
	
								default:
									ControladorAvisos.setMensajeError("Elija un Rol.");
									abrirVentanaAvisos();
							}
						}
	
						catch(Exception a) {
							ControladorAvisos.setMensajeError("Elija un Rol ");
							abrirVentanaAvisos();
						}
					}
				}
			}
    	}
    	catch(Exception a) {
    		ControladorAvisos.setMensajeError("Revise los campos a completar.");
    		abrirVentanaAvisos();
    	}
    }

    @FXML
    void pulsarBtnCancelar_reg(ActionEvent event) {
    	System.out.println("Cerrando ventana de Login.");
		Stage CerrarVentanaRegistro = (Stage) btnAceptar.getScene().getWindow();
		CerrarVentanaRegistro.close();
    }

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }



            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
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
    public boolean comprobarDigitosDNI() {
		String inputUser = textoDNI.getText().toUpperCase();
		for(int i=0; i<inputUser.length()-1; i++) {
			if(Character.isDigit((inputUser.charAt(i)))==false) {
				ControladorAvisos.setMensajeError("El usuario debe estar compuesto por 8 digitos y una letra.");
					return false;
			}
		}
		if(textoDNI.getText().length()!=9 | textoNombre.getText().length()<1 | textoApellidos.getText().length()<1 | textoTelefono.getText().length()!=9) {
			ControladorAvisos.setMensajeError("Por favor revise los datos introducidos.");
			return false;
		}
		return true;
	}
      //-----------------------------------------

    //Getters y Setters
	public TextField getTextoDNI() {
		return textoDNI;
	}

	public void setTextoDNI(TextField textoDNI) {
		this.textoDNI = textoDNI;
	}

	public TextField getTextoNombre() {
		return textoNombre;
	}

	public void setTextoNombre(TextField textoNombre) {
		this.textoNombre = textoNombre;
	}

	public PasswordField getTextoContrasena() {
		return textoContrasena;
	}

	public void setTextoContrasena(PasswordField textoContrasena) {
		this.textoContrasena = textoContrasena;
	}

	public PasswordField getTextoContrasena2() {
		return textoContrasena2;
	}

	public void setTextoContrasena2(PasswordField textoContrasena2) {
		this.textoContrasena2 = textoContrasena2;
	}

	public ComboBox<?> getComboRol() {
		return comboRol;
	}

	public void setComboRol(ComboBox<String> comboRol) {
		this.comboRol = comboRol;
	}

	public TextField getTextoApellidos() {
		return textoApellidos;
	}

	public void setTextoApellidos(TextField textoApellidos) {
		this.textoApellidos = textoApellidos;
	}


}
