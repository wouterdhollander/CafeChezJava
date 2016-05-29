package be.leerstad.EindwerkChezJava.view;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Table;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class RootLayoutController{
	private View view;

	private Stage dialogStage;
	@FXML
    private Stage primaryStage;     
    @FXML
    private BorderPane rootLayout;
    //private AnchorPane cafeOverview;
   // private TitledPane menuBar;
    private ObservableList<Liquid> liquidsData;
    private Set<Table> setTables =  new HashSet<>();
    private Cafe cafe;
    @FXML
    private javafx.scene.control.Label lblActiveOber;
    @FXML
    private AnchorPane anchorCenter;
    @FXML
    private AnchorPane anchorBottom;

	public RootLayoutController() {
		
	}

    @FXML
    public void login() 
    {
    	logout();
    	boolean okClicked = showLoginDialog();
    	if( okClicked)
    	{
    		
    		lblActiveOber.setText(cafe.getActiveOber().toString());
    		//showCafeOverview();
    	}
    }
    
    @FXML
    private void totalOverview() 
    {
    	if (cafe.getActiveOber().equals(new Ober()))
    	{
    		login();
    	}
    	if (!cafe.getActiveOber().equals(new Ober()))
    	{
    		showOverviewObers();
    	}

    }
    
    @FXML
    private void cafeOverview() 
    {
    	if (cafe.getActiveOber().equals(new Ober()))
    	{
    		login();
    	}
    	if (!cafe.getActiveOber().equals(new Ober()))
    	{
    		view.showCafeOverview();
    	}

    }
    @FXML
    private void logout() 
    {
    	cafe.logOut();
    	lblActiveOber.setText("no active Ober");
    	rootLayout.setCenter(null);
    }
    
    public void showOverviewObers() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/TotalOverviewLayout.fxml"));

            TabPane newPane = (TabPane) loader.load();
            
            rootLayout.setCenter(newPane);//.getChildren().add(newPane);

            TotalOverviewController controller = loader.getController();
            controller.setModel(cafe);
            controller.setView(view);
        } catch (IOException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
        }
    }


    /**
     * Opens a dialog login the person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showLoginDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/LoginDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Login ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            LoginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setModel(cafe);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setView(View view) {
    	
        this.view = view;
        cafeOverview();
    }
    public void setModel(Cafe model){
        this.cafe = model;
    }
}
