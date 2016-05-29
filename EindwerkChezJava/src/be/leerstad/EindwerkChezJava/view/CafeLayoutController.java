package be.leerstad.EindwerkChezJava.view;

import java.util.HashMap;
import java.util.Map;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.QuantityToLowException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Liquid;
import be.leerstad.EindwerkChezJava.model.ObersTableStatus;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.Position;
import be.leerstad.EindwerkChezJava.model.Table;
import be.leerstad.EindwerkChezJava.textfieldCustom.NumberTextfield;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class CafeLayoutController {
	private View view;
	private Cafe cafe;
	private Stage dialogStage;
	private Bounds boundsorig;// = anchorPaneTables.getBoundsInLocal();
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
	Map<Table,Shape> tablesMap = new HashMap<Table,Shape>();
	private ObservableList<Order> orders;
	private ObservableList<Order> unPayedOrdersActiveOber;
	private ObservableList<Order> ordersInWallet;
	private ObservableList<Order> ordersInCashDesk;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
	@FXML
	AnchorPane anchorPaneTables;
	public CafeLayoutController() {
		// TODO Auto-generated constructor stub
	}
	
	
    @FXML
    private void initialize() {
    	collumOverviewUnpayed.setCellValueFactory(celldata -> celldata.getValue().printout());
    	collumOverviewInWallet.setCellValueFactory(celldata -> celldata.getValue().printout());
    	collumActiveTableOrders.setCellValueFactory(celldata -> celldata.getValue().printout());
    	
    	collumOverviewInCashDesk.setCellValueFactory(celldata -> celldata.getValue().printout());
    	tableActiveTableOrders.getSelectionModel().selectedItemProperty().addListener(
		(observable, oldValue, newValue) -> changeInputOrder(newValue));
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
		//txtFieldQuantity.setText(Integer.toString(orderSelected.getQuantity()));
		txtFieldQuantity.setInt(orderSelected.getQuantity());
    }
    public void showOber(Table t)
    {
    	Shape circle = tablesMap.get(t);
    	Tooltip.install(circle, new Tooltip("Tafel; "+ t.getId() + "\n"+t.getActiveOber()));
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            Shape circle = (Shape)(t.getSource());
            Table table = tables.get(Integer.parseInt(circle.getId()));
    		if (cafe.getActiveOber().tableStatus(table).equals(ObersTableStatus.NOTALLOWED))
    		{
    			view.showMessageDialogTableNotAllowed();
    			return;
    		}
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = circle.getTranslateX();
            orgTranslateY = circle.getTranslateY();
        }
    };
    
    EventHandler<MouseEvent> tableDrag = new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent t) {
	        double offsetX = t.getSceneX() - orgSceneX;
	        double offsetY = t.getSceneY() - orgSceneY;
	        double newTranslateX = orgTranslateX + offsetX;
	        double newTranslateY = orgTranslateY + offsetY;

	        Shape c = (Shape)(t.getSource());
	        double sumX= c.getLayoutX() + newTranslateX;
	        double sumY= c.getLayoutY() + newTranslateY;
			if (boundsorig.contains(sumX,sumY))
			{
				c.setTranslateX(newTranslateX);
			    c.setTranslateY(newTranslateY);
			    Table table = tables.get(Integer.parseInt(c.getId()));
			    
			    table.getPosition().setX(sumX);
			    table.getPosition().setY(sumY);
			}
	    }
	};
	@FXML
	public void defaultPosition()
	{
		for (Map.Entry<Table, Shape> entry : tablesMap.entrySet())
		{
			Position defaulpos = entry.getKey().getPositionDefault();
			Shape c = entry.getValue();
		    Table table = tables.get(Integer.parseInt(c.getId()));
		    table.setPosition(defaulpos);
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
    		if (node instanceof Shape)
    		{
    			Table t = tables.get(circleCount);
    			Shape c = (Shape) node;
    			c.setId("" + circleCount);
    			//beetje of topic gegaan. je kan te tafels ook verplaatsen
    			Position posDefault = new Position(c.getLayoutX(),c.getLayoutY());
				t.setPositionDefault(posDefault);	
    			tablesMap.put(t, c);
    			//als er tafelobject geserialiseerd is. zoniet worden de bollen (tafels) automatisch op de default plaats gezet 
    			if (t.getPosition() != null )//.getX() > 0  && t.getPosition().getY() >0)
    			{
	    			c.setLayoutX(t.getPosition().getX());
	    			c.setLayoutY(t.getPosition().getY());
    			}
    			else
    			{
    				Position pos = new Position(c.getLayoutX(),c.getLayoutY());
    				t.setPosition(pos);
    			}
    			c.setOnMouseClicked(e -> setActiveTable(t));
    			c.setOnMouseMoved(e -> showOber(t));
    			c.setOnMouseDragged(tableDrag);
    			c.setOnMousePressed(circleOnMousePressedEventHandler);
    			circleCount++;	
    			fillColorCircle(t);
    		}
		}
    	refreshActiveTableOrders();
    	refreshPayedOrdersInWallet();
    	refreshUnpayedOrdersActiveOber();
    	ordersInCashDesk = view.incomeActiveOber();
    	tableOverViewInCashDesk.setItems(ordersInCashDesk);
    	tableOverViewInCashDesk.setItems(ordersInCashDesk);
    	lblTotalInCashDesk.setText(view.calculateIncomeActiveOber());
		Shape circle = tablesMap.get(cafe.getActiveTable());
		if (circle != null)
		{
			circle.setFill(Color.BEIGE);
		}
    }
    
    private void fillColorCircle(Table table)
    {
    	Shape circle = tablesMap.get(table);
    	if (circle != null)
    	{
    		ObersTableStatus status = cafe.getActiveOber().tableStatus(table);
			switch (status)
			{
				case ACTIVE:
					circle.setFill(Color.GREEN);
					break;
				case NOTALLOWED:
					circle.setFill(Color.RED);
					break;
				default:	
					circle.setFill(Color.DODGERBLUE);	
			}
    	}	
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    
    private void setActiveTable(Table t) {
    	//eerst de vorige active tafel terug in de juiste kleur zetten
		if (!cafe.getActiveTable().equals(new Table(-5)))//veranderen van kleur. bij startup is er geen tafel (=dummytafel) geselecteerd
		{
			fillColorCircle(cafe.getActiveTable());
    		orders.clear();
    	}
		cafe.setActiveTable(t);
		if (cafe.getActiveTable().equals(new Table(-5)))
		{
			view.showMessageDialogTableNotAllowed();
		}
		else
		{
			Shape circle = tablesMap.get(cafe.getActiveTable());
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
			Table previousTable = cafe.getActiveTable();
			cafe.getActiveOber().payOrders(cafe.getActiveTable());
			fillColorCircle(previousTable);
			fillColorCircle(cafe.getActiveTable());
			orders.clear();
			tableActiveTableOrders.setItems(orders);
			
			refreshUnpayedOrdersActiveOber();
			refreshPayedOrdersInWallet();
		 }
	}
    @FXML
    private void removeOrder() {
		Order selectedOrder = tableActiveTableOrders.getSelectionModel().getSelectedItem();
		if (selectedOrder != null) {
			if (cafe.getActiveOber().removeOrder(selectedOrder , cafe.getActiveTable()))
			{
				refreshActiveTableOrders();
				refreshUnpayedOrdersActiveOber();
			}
			else
			{
				view.showMessageDialogTableNotAllowed();
			}
		}else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Geen Selectie");
            alert.setHeaderText("Geen order geselecteerd");
            alert.setContentText("Selecteer een order uit de tabel.");
            alert.showAndWait();
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
            alert.setTitle("Geen Selectie");
            alert.setHeaderText("Geen order geselecteerd");
            alert.setContentText("Selecteer een order uit de tabel.");
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
