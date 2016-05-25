package be.leerstad.EindwerkChezJava;

import javafx.application.Application;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;

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



import be.leerstad.EindwerkChezJava.view.RootLayoutController;
import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;
import be.leerstad.EindwerkChezJava.Exceptions.DAOException;
import be.leerstad.EindwerkChezJava.Exceptions.TableNotAllowedException;
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
    
	public View() {
		try {
		cafe = new Cafe();
		} catch (DAOException e) {
			 Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(primaryStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("ERROR");
	            alert.setContentText(e.getMessage());
	            primaryStage.close();
			//e.printStackTrace();
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
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	        e.printStackTrace();
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
	public LinkedHashMap<Ober, Double> topDrieObers()
	{
		//oberAllowed();
		//LinkedHashMap<Ober, Double> topDrieOber = new LinkedHashMap<>();;
		try {
			return cafe.topDrieObers();
		} catch (ActiveOberNotSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	public ObservableList<Liquid> getLiquidsData() {
		return  FXCollections.observableArrayList(cafe.getLiquids());
	}
	
	public OrderSet getDailyIncome(LocalDate date) {
		try {
			return cafe.getDailyIncome(date);
		} catch (DAOException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		return null;
	}
	public ObservableList<Order> getOrdersData() throws TableNotAllowedException {
		return  FXCollections.observableArrayList(cafe.getOrdersActiveTable());//
	}
	public ObservableList<Order> getOrdersUnpayedActiveOber() throws ActiveOberNotSetException {

		return FXCollections.observableArrayList(cafe.getUnpayedOrderActiveOber());
	}
	
	public ObservableList<Order> getOrdersPayedActiveOber() throws ActiveOberNotSetException {

		return FXCollections.observableArrayList(cafe.getPayedOrderActiveOber());
	}

	
	public ObservableList<Table> getTablesData() {
		ObservableList<Table> tables = null;
		try {
			List<Table> list = new ArrayList<>();
			list.addAll(cafe.getTables());
			tables = FXCollections.observableList(list);
		} catch (ActiveOberNotSetException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		return tables;
	}



}
