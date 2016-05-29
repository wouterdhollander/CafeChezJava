package be.leerstad.EindwerkChezJava.view;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.*;
import be.leerstad.EindwerkChezJava.model.*;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

        txtFieldQuantity.setInt(order.getQuantity());
        cmbLiquids.setItems(liquids);
        cmbLiquids.getSelectionModel().select(order.getLiquid());
        
    }
    private View view;
    public void setView(View view)
    {
    	this.view = view;
    }
    
	/**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
		Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();

		try {
			Order.setQuantity(txtFieldQuantity.getInt());
			Order.setLiquid(m);
			okClicked = true;
	        dialogStage.close();
		} catch (QuantityToLowException e) { //not possible because the specific customtextbox doesn't allowed values below zero
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("internal Error");
			alert.setContentText("heb je iets raar uitgespoken?");// .printStackTrace();
		    alert.showAndWait();
		} catch (QuantityZeroException e) {
			if (m.equals(Order.getLiquid()))
			{
				cafe.getActiveOber().removeOrder(Order, cafe.getActiveTable());
				okClicked = true;
		        dialogStage.close();
			}
			else
			{
				view.showMessageDialogOkCancel("Edit Order", "De drank " + m + " zit niet in de oorspronkelijke orders \n en kan dus niet op 0 gezet worden.");
			
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

}

