package be.leerstad.EindwerkChezJava.view;


import be.leerstad.EindwerkChezJava.Exceptions.*;
import be.leerstad.EindwerkChezJava.model.Cafe;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PayOrderController {
@FXML
private Label lblPayOrders;
private Stage dialogStage;
private Cafe cafe;
private boolean okClicked = false;
	public PayOrderController() {
		// TODO Auto-generated constructor stub
	}
	
    @FXML
    private void initialize() {

	}

    public void setModel(Cafe model){
        this.cafe = model;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage; 

		lblPayOrders.setText(cafe.getActiveOber().payOrders(cafe.getActiveTable()));
    }
    @FXML
    private void handleOk() {
			//cafe.payOrders();
            okClicked = true;
            dialogStage.close();
    }
    
    public boolean isOkClicked() {
        return okClicked;
    }
    
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
