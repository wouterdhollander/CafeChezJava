package be.leerstad.EindwerkChezJava.view;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOloginNotAllowed;
import be.leerstad.EindwerkChezJava.Exceptions.InternalException;
import be.leerstad.EindwerkChezJava.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private PasswordField passwordField;
    
    private Stage dialogStage;
    private boolean okClicked = false;
    private Cafe model;
    
    @FXML
    private void initialize() {
    }
    
    public void setModel(Cafe model){
        this.model = model;
    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	try {
				if (!model.login(lastNameField.getText(), firstNameField.getText(), passwordField.getText()))
				{
					okClicked = false;
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING");
					alert.setContentText("Login Not allowed! \n wrong username and/or password" );// .printStackTrace();
				    alert.showAndWait();
				}
				else{
					okClicked = true;
					dialogStage.close();
				}
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setContentText(e.getMessage());// .printStackTrace();
				e.printStackTrace();
			    alert.showAndWait();
			}
        }
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n"; 
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }

}
