package be.leerstad.EindwerkChezJava.view;


import java.time.LocalDate;
import java.util.Map;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.InternalException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import be.leerstad.EindwerkChezJava.model.OrderSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Wouter
 * @version 0.1 everything is visible on github https://github.com/wouterdhollander/CafeChezJava
 * @since 30/05/2016
 * @see <a href="https://github.com/wouterdhollander/CafeChezJava">GithubAccount</a>
 */
public class TotalOverviewController {
	private View view;
	private Cafe cafe;
	private Stage dialogStage;
	private ObservableList<Order> ordersOverview;
    
	private ObservableList<Order> obsOrdersUnpayed;
	private ObservableList<Order> obsOrdersPayed;
	@FXML
	private DatePicker datepicker;
	@FXML
	private CheckBox chckboxCreateOpen;
	@FXML
	private TableView<Order> tableDailyIncome;
	@FXML
    private TableColumn<Order, String> collumDailyIncome;
    @FXML
    private TableColumn<Order, String> collumUnpayed;
    @FXML
    private TableColumn<Order, String> collumUnpayedOber;
    @FXML
    private TableColumn<Order, String> collumUnpayedLiquid;
    @FXML
    private TableColumn<Order, String> collumInWalletOber;
    @FXML
    private TableColumn<Order, String> collumInWallet;
    @FXML
    private TableColumn<Order, String> collumInWalletLiquid;
	@FXML
    private AnchorPane anchorTopDrie;
	@FXML
    private Tab tabTopDrie;
	@FXML
    private Button btnVerstuurPDF;
	@FXML
    private Button btnCreatePDF;
	@FXML
    private Button btnCreatePieChart;
	@FXML
	TableView<Order> tableUnpayed = new TableView<>();
	@FXML
	TableView<Order> tableInWallet = new TableView<>();
	@FXML
	Label lblTotalUnpayed;
	@FXML
	Label lblTotalInWallet;


	public TotalOverviewController() {
		// TODO Auto-generated constructor stub
	}
    @FXML
    private void initialize() {
    	collumDailyIncome.setCellValueFactory(celldata -> celldata.getValue().printout());

    	collumUnpayed.setCellValueFactory(celldata -> new SimpleStringProperty(String.format("%.2f",celldata.getValue().getPrice())));
    	collumUnpayedOber.setCellValueFactory(celldata -> new SimpleStringProperty( celldata.getValue().getOber().toString()));
    	collumUnpayedLiquid.setCellValueFactory(celldata -> new SimpleStringProperty( celldata.getValue().getLiquid().toString()));
    	
    	collumInWallet.setCellValueFactory(celldata -> new SimpleStringProperty(String.format("%.2f",celldata.getValue().getPrice())));
    	collumInWalletOber.setCellValueFactory(celldata -> new SimpleStringProperty(celldata.getValue().getOber().toString()));
    	collumInWalletLiquid.setCellValueFactory(celldata -> new SimpleStringProperty( celldata.getValue().getLiquid().toString()));
    }
    
	@FXML
	public void sendMail()
	{
		//ObservableList<Order> ordersDay = tableDailyIncome.getItems();
		if (cafe.SendMail(Filename))
		{
			 view.showMessageDialogOkCancel("Verzending mail","De mail is goed verzonden");
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setContentText("De mail is niet goed verzonden");// .printStackTrace();
		    alert.showAndWait();
		}

	}
	
	private String Filename = "";
	
	@FXML
	public void createPDF()
	{
		if (tableDailyIncome.getItems().size() != 0)
		{
			ObservableList<Order> ordersDay = tableDailyIncome.getItems();
			 try {
				 Filename = cafe.createPDF(ordersDay, chckboxCreateOpen.isSelected());
				 btnVerstuurPDF.setVisible(true);
			} catch (InternalException e) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("WARNING");
				alert.setContentText(e.getMessage());// .printStackTrace();
			    alert.showAndWait();
			}
		}
	}
	@FXML
	public void getDailyIncome()
	{
		LocalDate ld = datepicker.getValue();
		//datepicker.getd
		//ordersOverview = view.getOrdersOverview();
		OrderSet orders = view.income(ld);
		ordersOverview = FXCollections.observableArrayList(orders);
        tableDailyIncome.setItems(ordersOverview);
        btnVerstuurPDF.setVisible(false);
	}
	
	@FXML
	private void bereken()
	{
	    OrderSet  ordersUnpayed = cafe.getUnpayedOrders();
	    obsOrdersUnpayed =  FXCollections.observableArrayList(ordersUnpayed);  
	    tableUnpayed.setItems(obsOrdersUnpayed);

	    OrderSet  ordersPayed = cafe.getPayedOrders();
	    obsOrdersPayed =  FXCollections.observableArrayList(ordersPayed);  
	    tableInWallet.setItems(obsOrdersPayed);
	   		
		lblTotalUnpayed.setText(String.format("%.2f",cafe.calculateUnpayedOrders()));
		lblTotalInWallet.setText(String.format("%.2f",cafe.calculatePayedOrders()));
	}
	@FXML
	private void createPieChart()
	{
        Map<Ober, Double> topDrie = view.topDrieObers();
        PieChartChezJava demo = new PieChartChezJava("Comparison", "Best Obers of Chez Java",topDrie);
        demo.pack();
       
//        System.out.println(anchorTopDrie.getChildren().size());
//        anchorTopDrie.getChildren().add(demo.getch);
//        System.out.println(anchorTopDrie.getChildren().size());
////        tabTopDrie.setContent(demo);
////         //demo = tabTopDrie.getContent()
////        System.out.println(tabTopDrie.getContent().getScaleX());
////        System.out.println(tabTopDrie.getContent().getClass());
////        System.out.println(tabTopDrie.getContent().);
//       // demo.setMaxSize(500, 400);
//        
//        //demo.autosize();
        demo.setVisible(true);
	}

    public void setView(View view)
    {
        this.view = view;
        btnVerstuurPDF.setVisible(false);
        
        bereken();
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
