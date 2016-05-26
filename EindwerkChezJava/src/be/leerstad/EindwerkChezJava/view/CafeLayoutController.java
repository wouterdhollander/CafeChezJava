package be.leerstad.EindwerkChezJava.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityZeroException;
import be.leerstad.EindwerkChezJava.Exceptions.TableNotAllowedException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.Table;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CafeLayoutController {
	private View view;
	private Cafe cafe;
	private Stage dialogStage;
	private ObservableList<Table> tables;
	@FXML
	TableView<Order> tableOverViewUnPayed = new TableView<>();
	@FXML
	TableView<Order> tableOverviewPayed = new TableView<>();
	@FXML
	TableView<Order> tableBestelling = new TableView<>();
	@FXML
	NumberTextfield txtFieldQuantity;
	@FXML
	javafx.scene.control.ComboBox<Liquid> cmbLiquids;
	@FXML
	private Label lblTotaal;
	@FXML
	private Label lblTotaalOverview;
	@FXML
    private TableColumn<Order, String> collumOverviewUnpayed;
	@FXML
    private TableColumn<Order, String> collumOverviewPayed;
	@FXML
    private TableColumn<Order, String> collumbestelling;
	List<Circle> circles = new ArrayList<>();
	private ObservableList<Order> orders;
	private ObservableList<Order> unPayedOrdersActiveOber;
	private ObservableList<Order> payedOrdersActiveOber;
	@FXML
	AnchorPane tablePane;
    
	public CafeLayoutController() {
		// TODO Auto-generated constructor stub
	}
    @FXML
    private void initialize() {
    	collumOverviewUnpayed.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	collumOverviewPayed.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	collumbestelling.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	//collumOverview.setCellValueFactory(celldata -> celldata.getValue().getTablesActiveOber());
    	tableBestelling.getSelectionModel().selectedItemProperty().addListener(
		(observable, oldValue, newValue) -> changeInputOrder(newValue));
    	//choiceBoxLiquids.setItems(cafe.getLiquids());
    	//cmbLiquids.setItems(view.getLiquidsData());
    }
    
    private void changeInputOrder(Order orderSelected)
    {
    	if (orderSelected == null)
    	{
    		return;
    	}

		try {
			lblTotaal.setText(String.format("%.2f",cafe.getActiveTable().getOrders().calcutateOrders()));
		} catch (ActiveOberNotSetException e) {
			// TODO Auto-generated catch block
		}


    	Liquid liq = orderSelected.getLiquid();
		cmbLiquids.getSelectionModel().select(liq);
		txtFieldQuantity.setText(Integer.toString(orderSelected.getQuantity()));
    }
    public void showOber(Table t)
    {
    	Circle circle = circles.get(t.getId());

    	Tooltip.install(circle, new Tooltip(""+t.getActiveOber()));
    }
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
            new EventHandler<MouseEvent>() {
     
            @Override
            public void handle(MouseEvent t) {
                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
                orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
            }
        };
    
    EventHandler<MouseEvent> tableDrag = new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent t) {
	        double offsetX = t.getSceneX() - orgSceneX;
	        double offsetY = t.getSceneY() - orgSceneY;
	        double newTranslateX = orgTranslateX + offsetX;
	        double newTranslateY = orgTranslateY + offsetY;
	         
	        ((Circle)(t.getSource())).setTranslateX(newTranslateX);
	        ((Circle)(t.getSource())).setTranslateY(newTranslateY);
	    }
	};
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setView(View view) throws ActiveOberNotSetException {
        this.view = view;
        cmbLiquids.setItems(view.getLiquidsData());
        cmbLiquids.getSelectionModel().select(0);
        
    	int circleCount=0;
    	tables = view.getTablesData();
    	for (Node node : tablePane.getChildren()) {
    		if (node instanceof Circle)
    		{
    			Table t = tables.get(circleCount);
    			Circle c = (Circle) node;
    			circles.add(c);
    			
    			c.setOnMouseClicked(e -> setActiveTable(t));
    			c.setOnMouseMoved(e -> showOber(t));
    			c.setOnMouseDragged(tableDrag);
    			c.setOnMousePressed(circleOnMousePressedEventHandler);
    			circleCount++;	
    		}
		}
    	unPayedOrdersActiveOber =  view.getOrdersUnpayedActiveOber();
    	tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
    	
    	payedOrdersActiveOber = view.getOrdersPayedActiveOber();
    	tableOverviewPayed.setItems(payedOrdersActiveOber);
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    
    private void setActiveTable(Table t) {
    	Circle circle = circles.get(t.getId()); 	
    		
		try {	
			if (cafe.getActiveTable() != null)
			{
				Circle circleold=(Circle) circles.get(cafe.getActiveTable().getId());
	    		circleold.setFill(Color.DODGERBLUE);
	    		orders.clear();
	    	}
			cafe.setActiveTable(t);
			
			orders = view.getOrdersData();
			tableBestelling.setItems(orders);
			
			circle.setFill(Color.BEIGE);
//			lblTotaal.setface
//			cafe.calculateOrdersActiveTable().addListener((observable, oldValue, newValue) -> showPrice(newValue));
		} catch (ActiveOberNotSetException | TableNotAllowedException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}

    }
    
	 @FXML
    private void payOrder() {
		 boolean okClicked = showPayOrderDialog();
		 if (okClicked)
		 {
			 try {
				circles.get(cafe.getActiveTable().getId()).setFill(Color.DODGERBLUE);
				orders.clear();
				
				cafe.getActiveOber().payOrders(cafe.getActiveTable());
				tableBestelling.setItems(orders);//?? best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
				
				unPayedOrdersActiveOber.clear();
				unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
				tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
				
				payedOrdersActiveOber.clear();
				payedOrdersActiveOber = view.getOrdersPayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
				tableOverviewPayed.setItems(payedOrdersActiveOber);
			} catch (ActiveOberNotSetException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setContentText(e.getMessage());// .printStackTrace();
			    alert.showAndWait();
	           // return false;
			}
		 }
		
	}
	 
	 public boolean showPayOrderDialog() 
	 {
	 	try{
	            // Load the fxml file and create a new stage for the popup dialog.
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(View.class.getResource("/be/leerstad/EindwerkChezJava/view/PayOrderDialog.fxml"));
	            AnchorPane page = (AnchorPane) loader.load();

	            // Create the dialog Stage.
	            Stage dialogStage = new Stage();
	            dialogStage.setTitle("Pay Orders");
	            dialogStage.initModality(Modality.WINDOW_MODAL);
	            dialogStage.initOwner(view.getPrimaryStage());//??
	            Scene scene = new Scene(page);
	            dialogStage.setScene(scene);

	            // Set the person into the controller.
	            PayOrderController controller = loader.getController();
	            controller.setModel(cafe);
	            controller.setDialogStage(dialogStage);
	            // Show the dialog and wait until the user closes it
	            dialogStage.showAndWait();
	            return controller.isOkClicked();
		} catch ( TableNotAllowedException | ActiveOberNotSetException | IOException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
	 	return false;
	 }

    @FXML
    private void removeOrder() {
    	try {
    		Order order = tableBestelling.getSelectionModel().getSelectedItem();
    		cafe.getActiveOber().removeOrder(order , cafe.getActiveTable());
			orders.remove(order);
			
			unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
			payedOrdersActiveOber = view.getOrdersPayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
			
    	} catch (ActiveOberNotSetException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
    }

	@FXML
	private void editOrder() {
		Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();
		try {
			Order order = new Order(m,txtFieldQuantity.getInt(), cafe.getActiveOber());
			cafe.getActiveOber().removeOrder(order,cafe.getActiveTable());
			//bestellingsTable.setItems(orders);
			orders.clear();
			orders = view.getOrdersData();
			tableBestelling.setItems(orders);

			unPayedOrdersActiveOber.clear();
			unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
			tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
				
//				unPayedOrdersActiveOber.clear();
//				unPayedOrdersActiveOber = getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
//				tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
		} catch (QuantityToLowException | QuantityZeroException | ActiveOberNotSetException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		
	}
    @FXML
    private void addOrder() {
    	Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();
    	Order order;
		try {
			//order = new Order(m,txtFieldQuantity.getInt(), cafe.getActiveOber());
			//slechte manier maar het werkt,
			//doordat ik de equals methode van order overschreven heb (order met verschillende hoeveelheid is toch gelijk)
			//geeft dit problemen bij de observablearralist (de .add methode werkt enkel bij niet gelijke objecten (dus niet als de hoeveelheid verschillend is!)
			//hierdoor eerst controleren of de order al aanwezig is, zoniet 
			//zoniet gewoonde observablearralist (orders) van nul af aan opvullen.
			//op de backend werkt dit perfect!
			
			
			//unPayedOrdersActiveOber.add(order);
			
			//tableOverview
//			if (!view.getOrdersData().contains(order))//gebruik van getOrdersData want dit throwt een exceptie => is nodig als geen tafel geselecteerd is
//			{
//				
//				orders.add(order);
//			}
//			else
//			{
//				orders.clear();
//				orders = view.getOrdersData();
//				tableBestelling.setItems(orders);
//			}
			
			cafe.getActiveOber().makeOrder(m,txtFieldQuantity.getInt(), cafe.getActiveTable());
			orders.clear();
			orders = view.getOrdersData();
			tableBestelling.setItems(orders);

			unPayedOrdersActiveOber.clear();
			unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
			tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
			txtFieldQuantity.clear();
		} catch (QuantityToLowException | ActiveOberNotSetException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
    }

}
