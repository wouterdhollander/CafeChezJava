package be.leerstad.EindwerkChezJava.view;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
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
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the order to be edited in the dialog.
     * 
     * @param order the order to be edited
     */
    public void setOrder(Order order) {
        this.Order = order;
        
        ObservableList<Liquid> liquids = view.getLiquidsData();
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

