package be.leerstad.EindwerkChezJava.view;

import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOloginNotAllowed;
import be.leerstad.EindwerkChezJava.Exceptions.InternalException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.model.*;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditOrdersController {
    @FXML
    private NumberTextfield txtFieldQuantity;
	@FXML
	javafx.scene.control.ComboBox<Liquid> cmbLiquids;
	
    private Stage dialogStage;
    private boolean okClicked = false;
    private Cafe cafe;
    private Order Order;
    @FXML
    private void initialize() {
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
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
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setOrder(Order order, ObservableList<Liquid> liquids) {
        this.Order = order;

        txtFieldQuantity.setText(Double.toString(order.getQuantity()));
        cmbLiquids.setItems(liquids);
        cmbLiquids.getSelectionModel().select(0);
        
    }
    
	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
		Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();
		Order.setLiquid(m);
		try {
			Order.setQuantity(txtFieldQuantity.getInt());
		} catch (QuantityToLowException | QuantityZeroException e) { 
			//not possible because numbertextbox doesn't provide values below zero
			e.printStackTrace();
		}
        okClicked = true;
        dialogStage.close();
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

}

