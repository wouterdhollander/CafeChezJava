package be.leerstad.EindwerkChezJava.view;

import be.leerstad.EindwerkChezJava.model.Cafe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class MessageDialogOkCancelController {
@FXML
private Label lblText;
private Stage dialogStage;
private Cafe cafe;
private boolean okClicked = false;
	public MessageDialogOkCancelController() {
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

		
    }
    public void setText(String text)
    {
    	lblText.setText(text);
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
    	okClicked = false;
        dialogStage.close();
    }
}
