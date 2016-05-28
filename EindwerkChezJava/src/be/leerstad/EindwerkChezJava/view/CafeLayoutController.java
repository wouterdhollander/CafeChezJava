package be.leerstad.EindwerkChezJava.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.*;
import be.leerstad.EindwerkChezJava.model.*;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
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
	TableView<Order> tableOverviewInWallet = new TableView<>();
	@FXML
	TableView<Order> tableOverViewInCashDesk = new TableView<>();
	@FXML
	TableView<Order> tableActiveTableOrders = new TableView<>();
	@FXML
	NumberTextfield txtFieldQuantity;
	@FXML
	javafx.scene.control.ComboBox<Liquid> cmbLiquids;
	@FXML
	private Label lblTotalOrder;
	@FXML
	private Label lblTotalUnpayed;
	@FXML
	private Label lblTotalInWallet;
	
	@FXML
	private Label lblTotalInCashDesk;
	
	@FXML
    private TableColumn<Order, String> collumOverviewUnpayed;
	@FXML
    private TableColumn<Order, String> collumOverviewInWallet;
	@FXML
    private TableColumn<Order, String> collumOverviewInCashDesk;
	@FXML
    private TableColumn<Order, String> collumActiveTableOrders;
	Map<Circle,double[]> circles = new HashMap<Circle,double[]>();
	private ObservableList<Order> orders;
	private ObservableList<Order> unPayedOrdersActiveOber;
	private ObservableList<Order> ordersInWallet;
	private ObservableList<Order> ordersInCashDesk;
	@FXML
	AnchorPane anchorPaneTables;
	public CafeLayoutController() {
		// TODO Auto-generated constructor stub
	}
    @FXML
    private void initialize() {
    	collumOverviewUnpayed.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	collumOverviewInWallet.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	collumActiveTableOrders.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	
    	collumOverviewInCashDesk.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	//collumOverview.setCellValueFactory(celldata -> celldata.getValue().getTablesActiveOber());
    	tableActiveTableOrders.getSelectionModel().selectedItemProperty().addListener(
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
		lblTotalOrder.setText(String.format("%.2f",cafe.getActiveTable().getOrders().calcutateOrders()));

		
    	Liquid liq = orderSelected.getLiquid();
		cmbLiquids.getSelectionModel().select(liq);
		txtFieldQuantity.setText(Integer.toString(orderSelected.getQuantity()));
    }
    public void showOber(Table t)
    {
		String id = ""+ t.getId();
		Circle circle = circles.keySet().stream().filter(circ -> circ.getId().equals(id)).findFirst().get();
    	//Circle circle = circles.get(t.getId());
    	Tooltip.install(circle, new Tooltip("Tafel; "+ t.getId() + "\n"+t.getActiveOber()));
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
    private Bounds boundsorig;// = anchorPaneTables.getBoundsInLocal();
    EventHandler<MouseEvent> tableDrag = new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent t) {

	        double offsetX = t.getSceneX() - orgSceneX;
	        double offsetY = t.getSceneY() - orgSceneY;

	        double newTranslateX = orgTranslateX + offsetX;
	        double newTranslateY = orgTranslateY + offsetY;

	        Circle c = (Circle)(t.getSource());
	        System.out.println("origScene: " + c.getTranslateX());
	        double sumX= c.getLayoutX() + newTranslateX;
	        double sumY= c.getLayoutY() + newTranslateY;
			if (boundsorig.contains(sumX,sumY))
			{
				c.setTranslateX(newTranslateX);
			    c.setTranslateY(newTranslateY);
			    Table table = tables.get(Integer.parseInt(c.getId()));
			    table.setPositionX(sumX);
			    table.setPositionY(sumY);
			}
	    }
	};
	@FXML
	public void defaultPosition()
	{
		for (Map.Entry<Circle, double[]> entry : circles.entrySet())
		{
			//System.out.println(entry.getValue()[0]);
			double xOrigineel = entry.getValue()[0];
			double yOrigineel = entry.getValue()[1];
			Circle c = entry.getKey();
		    Table table = tables.get(Integer.parseInt(c.getId()));
		    table.setPositionX(xOrigineel);
		    table.setPositionY(yOrigineel);
		}
		
		view.showCafeOverview();//is makkelijkste manier om de tafels terug naar hun oorspronkelijke positie te verschuiven

	}
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    
    
    public void setView(View view) {
        this.view = view;
        boundsorig = anchorPaneTables.getBoundsInLocal();
        cmbLiquids.setItems(view.getLiquidsData());
        cmbLiquids.getSelectionModel().select(0);
        
    	int circleCount=0;
    	tables = view.getTablesData();
    	for (Node node : anchorPaneTables.getChildren()) {
    		if (node instanceof Circle)
    		{
    			Table t = tables.get(circleCount);
    			Circle c = (Circle) node;
    			c.setId("" + circleCount);
    			double d[] = new double[2];
    			System.out.println("X origineel : " + c.getLayoutX());
    			d[0] = c.getLayoutX();
    			d[1] = c.getLayoutY();
    			circles.put(c, d);
    			if (t.getPositionX() > 0  && t.getPositionY()>0)//
    			{
	    			c.setLayoutX(t.getPositionX());
	    			c.setLayoutY(t.getPositionY());
    			}
    			c.setOnMouseClicked(e -> setActiveTable(t));
    			c.setOnMouseMoved(e -> showOber(t));
    			c.setOnMouseDragged(tableDrag);
    			c.setOnMousePressed(circleOnMousePressedEventHandler);
    			circleCount++;	
    		}
		}
    	refreshActiveTableOrders();
    	refreshPayedOrdersInWallet();
    	refreshUnpayedOrdersActiveOber();
    	ordersInCashDesk = view.incomeActiveOber();
    	tableOverViewInCashDesk.setItems(ordersInCashDesk);
    	tableOverViewInCashDesk.setItems(ordersInCashDesk);
    	lblTotalInCashDesk.setText(view.calculateIncomeActiveOber());	
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    
    private void setActiveTable(Table t) {
    	
		 
    	//Circle circle = circles.get(t.getId()); 	
	
		if (!cafe.getActiveTable().equals(new Table(-5)))//veranderen van kleur. bij startup is er geen tafel (=dummytafel) geselecteerd
		{
			String id = ""+ cafe.getActiveTable().getId();
			Circle circleold = circles.keySet().stream().filter(circ -> circ.getId().equals(id)).findFirst().get();
	    	
			//Circle circleold=(Circle) circles.get(cafe.getActiveTable().getId());
    		circleold.setFill(Color.DODGERBLUE);
    		orders.clear();
    	}
		cafe.setActiveTable(t);
		if (cafe.getActiveTable().equals(new Table(-5)))
		{
			view.showMessageDialogTableNotAllowed();
		}
		else
		{
			String id = ""+ t.getId();
			 Circle circle = circles.keySet().stream().filter(circ -> circ.getId().equals(id)).findFirst().get();
	    	
			circle.setFill(Color.BEIGE);
			refreshActiveTableOrders();
		}
    }
    
    private void refreshActiveTableOrders()
    {
    	if (orders != null)
    	{
    		orders.clear();
    	}
		
		orders = view.getOrdersData();
		tableActiveTableOrders.setItems(orders);
		lblTotalOrder.setText(view.calculateUnpayedActiveTable());
    }
    
    private void refreshUnpayedOrdersActiveOber()
    {
    	if (unPayedOrdersActiveOber != null)
    	{
    		unPayedOrdersActiveOber.clear();
    	}
		unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
		lblTotalUnpayed.setText(view.calculateUnpayedActiveOber());
    }
    
    private void refreshPayedOrdersInWallet()
    {
    	if (ordersInWallet != null)
    	{
    		ordersInWallet.clear();
    	}
		ordersInWallet = view.getOrdersPayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		tableOverviewInWallet.setItems(ordersInWallet);
		lblTotalInWallet.setText(view.calculatePayedActiveOber());
    }
	 @FXML
    private void payOrder() {
	 boolean okClicked = view.showMessageDialogOk("Pay Orders", cafe.getActiveOber().payOrders(cafe.getActiveTable()));
	 if (okClicked && !cafe.getActiveTable().equals(new Table(-5)))
	 { 
		 String id = ""+ cafe.getActiveTable().getId();
		 Circle c = circles.keySet().stream().filter(circ -> circ.getId().equals(id)).findFirst().get();
		 c.setFill(Color.DODGERBLUE);

		

		cafe.getActiveOber().payOrders(cafe.getActiveTable());
		orders.clear();
		tableActiveTableOrders.setItems(orders);
		
		refreshUnpayedOrdersActiveOber();
		refreshPayedOrdersInWallet();
	 }
		
	}
    @FXML
    private void removeOrder() {
		Order order = tableActiveTableOrders.getSelectionModel().getSelectedItem();
		if (cafe.getActiveOber().removeOrder(order , cafe.getActiveTable()))
		{
			refreshActiveTableOrders();
			refreshUnpayedOrdersActiveOber();
		}
		else
		{
			view.showMessageDialogTableNotAllowed();
		}
    }  
	@FXML
	private void editOrder() {
        Order selectedOrder = tableActiveTableOrders.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            boolean okClicked = view.showEditOrdersDialog(selectedOrder);
            if (okClicked) {
				refreshActiveTableOrders();
				refreshUnpayedOrdersActiveOber();			
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
	}

    @FXML
    private void addOrder() {
    	Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();
		try {	
			if (cafe.getActiveOber().makeOrder(m,txtFieldQuantity.getInt(), cafe.getActiveTable())){
				refreshActiveTableOrders();
				refreshUnpayedOrdersActiveOber();
				txtFieldQuantity.clear();
			}
			else
			{
				view.showMessageDialogTableNotAllowed();
			}
		} catch (QuantityToLowException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
    }

}
