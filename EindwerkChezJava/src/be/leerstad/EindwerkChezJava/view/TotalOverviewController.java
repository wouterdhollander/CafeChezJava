package be.leerstad.EindwerkChezJava.view;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Map;


import com.itextpdf.text.DocumentException;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.*;
import be.leerstad.EindwerkChezJava.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

public class TotalOverviewController {
	private View view;
	private Cafe cafe;
	private Stage dialogStage;
	private ObservableList<Order> ordersOverview;
	@FXML
	private DatePicker datepicker;
	@FXML
	private CheckBox chckboxCreateOpen;
	@FXML
	private TableView<Order> tableDailyIncome;
	@FXML
    private TableColumn<Order, String> collumDailyIncome;
	@FXML
    private AnchorPane anchorTopDrie;
	@FXML
    private Tab tabTopDrie;
	@FXML
    private Button verstuurPDF;
	@FXML
    private Button CreatePDF;
	public TotalOverviewController() {
		// TODO Auto-generated constructor stub
	}
    @FXML
    private void initialize() {
    	collumDailyIncome.setCellValueFactory(celldata -> celldata.getValue().getPrintout());

    }
    
	@FXML
	public void sendMail()
	{
		//ObservableList<Order> ordersDay = tableDailyIncome.getItems();
		if (cafe.SendMail(Filename))
		{
			 view.showMessageDialog("Verzending mail","De mail is goed verzonden");
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
				 verstuurPDF.setVisible(true);
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
		ordersOverview = FXCollections.observableArrayList(view.income(ld));
        tableDailyIncome.setItems(ordersOverview);
        verstuurPDF.setVisible(false);
	}
	
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
        createPieChart();
        verstuurPDF.setVisible(false);
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
