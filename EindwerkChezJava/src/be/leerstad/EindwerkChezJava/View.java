package be.leerstad.EindwerkChezJava;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import be.leerstad.EindwerkChezJava.view.MessageDialogController;
import be.leerstad.EindwerkChezJava.view.RootLayoutController;
import be.leerstad.EindwerkChezJava.Exceptions.*;

import be.leerstad.EindwerkChezJava.model.*;

public class View extends Application {
	
	@FXML
    private Stage primaryStage;     
    @FXML
    private BorderPane rootLayout;
    //private AnchorPane cafeOverview;
   // private TitledPane menuBar;
    private ObservableList<Liquid> liquidsData;

	private ObservableSet<Table> TablesData;
    private Cafe cafe;
    @FXML
    private javafx.scene.control.Label activeOberLabel;
    @FXML
    private AnchorPane anchorCenter;
    @FXML
    private AnchorPane anchorBottom;
    @FXML
    private MenuItem menulogin;
    @FXML
    private MenuItem menuLogout;
    @FXML
    private Label lblActiveOber;
    
    @FXML
    private Label lblTest;
    
	public View() throws Exception {
		try {
		cafe = new Cafe();
		} catch (InternalException e) {
			//InternalException intException = new InternalException();
			 Alert alert = new Alert(AlertType.ERROR);
	            //alert.initOwner(primaryStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("ERROR");
	            alert.setContentText(e.getMessage());
	            alert.showAndWait();
	            throw e;
	            //primaryStage.close();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ChezJava");
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        public void handle(WindowEvent we) {
            try {
				cafe.close();
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				 Alert alert = new Alert(AlertType.ERROR);
		            alert.initOwner(primaryStage);
		            alert.setTitle("Invalid Fields");
		            alert.setHeaderText("ERROR");
		            alert.setContentText(e.getMessage());
		            alert.showAndWait();
		            //primaryStage.close();
			}
        }
    });
		 initRootLayout(); 
	}
	
	public void  initRootLayout()
	{
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/RootLayout.fxml")); 
			rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            RootLayoutController controller = loader.getController();
            controller.setDialogStage(primaryStage);
            controller.setModel(cafe);
            controller.setView(this);
		}
		catch (IOException e) {
			 Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("ERROR");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            //primaryStage.close();
		}
	}

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	 public boolean showMessageDialog(String Title, String text) 
	 {
	 	try{
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/MessageDialog.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage.
           Stage dialogStage = new Stage();
           dialogStage.setTitle(Title);
           
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(this.getPrimaryStage());//??
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Set the person into the controller.
           MessageDialogController controller = loader.getController();
           controller.setModel(cafe);
           controller.setDialogStage(dialogStage);
           controller.setText(text);
           // Show the dialog and wait until the user closes it
           dialogStage.showAndWait();
           return controller.isOkClicked();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
	 	return false;
	 }
	public LinkedHashMap<Ober, Double> topDrieObers()
	{
		LinkedHashMap<Ober, Double> topDrieOber = new LinkedHashMap<>();;
		try {
			topDrieOber = cafe.topDrieObers();
		} catch (InternalException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		
		return topDrieOber;
	}
 
	public ObservableList<Liquid> getLiquidsData() {
		return  FXCollections.observableArrayList(cafe.getLiquids());
	}
	
	public OrderSet income(LocalDate date) {
		try {
			return cafe.getIncome(date);
		} catch (InternalException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		return null;
	}
	
	public ObservableList<Order> incomeActiveOber() {
		OrderSet orders = new OrderSet();
		try {
			orders =  cafe.getIncome(cafe.getActiveOber());
		} catch (InternalException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		return FXCollections.observableArrayList(orders);
	}
	public String calculateUnpayedActiveTable()
	{
		return String.format("%.2f",cafe.getActiveTable().getOrders().calcutateOrders());
	}
	public String calculateUnpayedActiveOber()
	{
		return String.format("%.2f",cafe.calculateUnpayedOrders(cafe.getActiveOber()));
	}
	public String calculatePayedActiveOber()
	{
		return String.format("%.2f", cafe.getActiveOber().getPayedOrders().calcutateOrders());
	}
	public ObservableList<Order> getOrdersData() {
		return  FXCollections.observableArrayList(cafe.getActiveTable().getOrders());//
	}
	public ObservableList<Order> getOrdersUnpayedActiveOber() {
		OrderSet  orders = cafe.getUnpayedOrders(cafe.getActiveOber());
		return  FXCollections.observableArrayList(orders);//
	}
	
	public ObservableList<Order> getOrdersPayedActiveOber() {
		return FXCollections.observableArrayList(cafe.getActiveOber().getPayedOrders());
	}

	public ObservableList<Table> getTablesData() {
		ObservableList<Table> tables = null;
		List<Table> list = cafe.getTables();
		if (list.contains(new Table(-5)))
		{			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("Active Ober not set");// .printStackTrace();
		    alert.showAndWait();
		}
		else
		{
			tables = FXCollections.observableList(list);
		}

		return tables;
	}



}
