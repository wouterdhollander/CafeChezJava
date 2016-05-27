package be.leerstad.EindwerkChezJava.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.*;
import be.leerstad.EindwerkChezJava.model.*;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.collections.ObservableList;
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
	TableView<Order> tableOverviewInWallet = new TableView<>();
	@FXML
	TableView<Order> tableOverViewInCashDesk = new TableView<>();
	@FXML
	TableView<Order> tableBestelling = new TableView<>();
	@FXML
	NumberTextfield txtFieldQuantity;
	@FXML
	javafx.scene.control.ComboBox<Liquid> cmbLiquids;
	@FXML
	private Label lblTotal;
	@FXML
	private Label lblTotalUnpayed;
	@FXML
	private Label lblTotalInWallet;
	@FXML
    private TableColumn<Order, String> collumOverviewUnpayed;
	@FXML
    private TableColumn<Order, String> collumOverviewInWallet;
	@FXML
    private TableColumn<Order, String> collumOverviewInCashDesk;
	@FXML
    private TableColumn<Order, String> collumbestelling;
	List<Circle> circles = new ArrayList<>();
	private ObservableList<Order> orders;
	private ObservableList<Order> unPayedOrdersActiveOber;
	private ObservableList<Order> ordersInWallet;
	private ObservableList<Order> ordersInCashDesk;
	@FXML
	AnchorPane tablePane;
	public CafeLayoutController() {
		// TODO Auto-generated constructor stub
	}
    @FXML
    private void initialize() {
    	collumOverviewUnpayed.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	collumOverviewInWallet.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	collumbestelling.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
    	
    	collumOverviewInCashDesk.setCellValueFactory(celldata -> celldata.getValue().getPrintout());
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
		lblTotal.setText(String.format("%.2f",cafe.getActiveTable().getOrders().calcutateOrders()));

		
    	Liquid liq = orderSelected.getLiquid();
		cmbLiquids.getSelectionModel().select(liq);
		txtFieldQuantity.setText(Integer.toString(orderSelected.getQuantity()));
    }
    public void showOber(Table t)
    {
    	Circle circle = circles.get(t.getId());
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
    
    public void setView(View view) {
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
    	
    	ordersInWallet = view.getOrdersPayedActiveOber();
    	tableOverviewInWallet.setItems(ordersInWallet);
    	
    	ordersInCashDesk = view.incomeActiveOber();
    	tableOverViewInCashDesk.setItems(ordersInCashDesk);
    	
    	tableOverViewInCashDesk.setItems(ordersInCashDesk);
    	lblTotalUnpayed.setText(view.calculateUnpayedActiveOber());
    	lblTotalInWallet.setText(view.calculatePayedActiveOber());
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    
    private void setActiveTable(Table t) {
    	Circle circle = circles.get(t.getId()); 	
	
		if (!cafe.getActiveTable().equals(new Table(-5)))//veranderen van kleur. bij startup is er geen tafel (=dummytafel) geselecteerd
		{
			Circle circleold=(Circle) circles.get(cafe.getActiveTable().getId());
    		circleold.setFill(Color.DODGERBLUE);
    		orders.clear();
    	}
		cafe.setActiveTable(t);
		if (cafe.getActiveTable().equals(new Table(-5)))
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("You are not allowed to order!");// .printStackTrace();
		    alert.showAndWait();
		}
		else
		{
			orders = view.getOrdersData();
			tableBestelling.setItems(orders);
			circle.setFill(Color.BEIGE);
	    	lblTotalUnpayed.setText(view.calculateUnpayedActiveOber());
	    	lblTotalInWallet.setText(view.calculatePayedActiveOber());
			lblTotal.setText(view.calculateUnpayedActiveTable());
		}

    }
    
	 @FXML
    private void payOrder() {
	 boolean okClicked = view.showMessageDialog("Pay Orders", cafe.getActiveOber().payOrders(cafe.getActiveTable()));
	 if (okClicked && !cafe.getActiveTable().equals(new Table(-5)))
	 { 
		circles.get(cafe.getActiveTable().getId()).setFill(Color.DODGERBLUE);
		orders.clear();
		
		cafe.getActiveOber().payOrders(cafe.getActiveTable());
		tableBestelling.setItems(orders);//?? best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		
		unPayedOrdersActiveOber.clear();
		unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
		
		ordersInWallet.clear();
		ordersInWallet = view.getOrdersPayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		tableOverviewInWallet.setItems(ordersInWallet);
	 }
		
	}

    @FXML
    private void removeOrder() {
		Order order = tableBestelling.getSelectionModel().getSelectedItem();
		cafe.getActiveOber().removeOrder(order , cafe.getActiveTable());
		orders.remove(order);
		
		unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		ordersInWallet = view.getOrdersPayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
		
    	lblTotalUnpayed.setText(view.calculateUnpayedActiveOber());
    	lblTotalInWallet.setText(view.calculatePayedActiveOber());
		lblTotal.setText(view.calculateUnpayedActiveTable());
    }

	@FXML
	private void editOrder() {
		Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();
		try {
			Order order = new Order(m,txtFieldQuantity.getInt(), cafe.getActiveOber());
			cafe.getActiveOber().removeOrder(order,cafe.getActiveTable());
			orders.clear();
			orders = view.getOrdersData();
			tableBestelling.setItems(orders);

			unPayedOrdersActiveOber.clear();
			unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
			tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
	    	lblTotalUnpayed.setText(view.calculateUnpayedActiveOber());
	    	lblTotalInWallet.setText(view.calculatePayedActiveOber());
			lblTotal.setText(view.calculateUnpayedActiveTable());
		} catch (QuantityToLowException | QuantityZeroException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
		
	}
    @FXML
    private void addOrder() {
    	Liquid m = cmbLiquids.getSelectionModel().getSelectedItem();
		try {	
			cafe.getActiveOber().makeOrder(m,txtFieldQuantity.getInt(), cafe.getActiveTable());
			orders.clear();
			orders = view.getOrdersData();
			tableBestelling.setItems(orders);

			unPayedOrdersActiveOber.clear();
			unPayedOrdersActiveOber = view.getOrdersUnpayedActiveOber();//best opnieuw te berekenen want als er maar een deel van een bestelling verwijderd wordt, is dit niet compleet weergegeven in de overview table
			tableOverViewUnPayed.setItems(unPayedOrdersActiveOber);
			txtFieldQuantity.clear();
	    	lblTotalUnpayed.setText(view.calculateUnpayedActiveOber());
	    	lblTotalInWallet.setText(view.calculatePayedActiveOber());
			lblTotal.setText(view.calculateUnpayedActiveTable());
		} catch (QuantityToLowException e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText(e.getMessage());// .printStackTrace();
		    alert.showAndWait();
		}
    }

}
