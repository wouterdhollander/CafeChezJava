package be.leerstad.EindwerkChezJava;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import be.leerstad.EindwerkChezJava.Exceptions.InternalException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;
import be.leerstad.EindwerkChezJava.model.Table;
import be.leerstad.EindwerkChezJava.view.CafeLayoutController;
import be.leerstad.EindwerkChezJava.view.EditOrdersController;
import be.leerstad.EindwerkChezJava.view.MessageDialogOkCancelController;
import be.leerstad.EindwerkChezJava.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class View extends Application {
	
	@FXML
    private Stage primaryStage;     
    @FXML
    private BorderPane rootLayout;
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
            	OrderSet unpayedOrders = cafe.getUnpayedOrders();
            	if (unpayedOrders.size() == 0)
            	{
            		cafe.close();
            	}
            	else
            	{
            		//een beetje gepruts
            		StringBuilder stringbuilder = new StringBuilder();
            		stringbuilder.append("Niet alle obers hebben hun tafels afgerekend! \n");
            		Set<Ober> obers = new HashSet<>();
            		for (Order order : unpayedOrders) {
            			obers.add(order.getOber());
					}
            		for (Ober ober : obers)
            		{
            			stringbuilder.append(ober.toString() + "\n");
            		}
            		
            		stringbuilder.append("Deze bestellingen worden bewaard. \n Wilt U doorgaan?");
            		if (showMessageDialogOkCancel("Afsluiten", stringbuilder.toString()))
            		{
            			cafe.close();
            		}
            		else
            		{
            			we.consume();
            		}
            	}
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(primaryStage);
	            alert.setTitle("oopsie");
	            alert.setHeaderText("ERROR");
	            alert.setContentText(e.getMessage());
	            alert.showAndWait();
			}
        }
    });
		 initRootLayout(); 
	}
    
    public void showCafeOverview() {
        
		try {
			cafe.setActiveTable(new Table(-5));
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/CafeLayout.fxml"));

			AnchorPane newPane = (AnchorPane) loader.load();
	        rootLayout.setCenter(newPane);//.getChildren().add(newPane);
	
	        CafeLayoutController controller = loader.getController();
	        controller.setModel(cafe);
	        controller.setView(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// e.printStackTrace();
			e.printStackTrace();
		    alert.showAndWait();
		}

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
    /**
     * Opens a dialog to edit details for the specified order. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param order the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showEditOrdersDialog(Order order) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/EditOrdersDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Order ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditOrdersController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setView(this);
            controller.setOrder(order);
            controller.setModel(cafe);
           
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Returns the main stage.
     *
     * @return the primarystage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
	 public boolean showMessageDialogOkCancel(String Title, String text) 
	 {
	 	try{
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/MessageDialogOkCancle.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage.
           Stage dialogStage = new Stage();
           dialogStage.setTitle(Title);
           
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(this.getPrimaryStage());//??
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Set the person into the controller.
           MessageDialogOkCancelController controller = loader.getController();
           controller.setModel(cafe);
           controller.setDialogStage(dialogStage);
           controller.setText(text);
           // Show the dialog and wait until the user closes it
           dialogStage.showAndWait();
           Boolean bool = controller.isOkClicked();
           return bool;
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
	 	return false;
	 }
	 
	 public boolean showMessageDialogTableNotAllowed()
	 {
		 return this.showMessageDialogOk("Cafe Chez Java", "U mag deze Tafel niet besteller! \n Selecteer een tafel. \n Of vind uw tafel! \n hover over te tafels om de actieve obers te zien.");
	 }
	 
	 public boolean showMessageDialogOk(String Title, String text) 
	 {
	 	try{
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/MessageDialogOk.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage.
           Stage dialogStage = new Stage();
           dialogStage.setTitle(Title);
           
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(this.getPrimaryStage());//??
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Set the person into the controller.
           MessageDialogOkCancelController controller = loader.getController();
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
	public String calculateIncomeActiveOber()
	{
		String income = "";
		try {
			 income = String.format("%.2f",cafe.getIncome(cafe.getActiveOber()).calcutateOrders());
		} catch (InternalException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		return income;
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
			alert.setContentText("Actieve Ober niet geset!");// .printStackTrace();
		    alert.showAndWait();
		}
		else
		{
			tables = FXCollections.observableList(list);
		}

		return tables;
	}



}
