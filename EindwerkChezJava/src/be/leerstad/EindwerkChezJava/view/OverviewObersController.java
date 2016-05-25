package be.leerstad.EindwerkChezJava.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;


import org.jfree.chart.*;
import org.jfree.chart.demo.PieChartDemo1;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

import be.leerstad.EindwerkChezJava.View;
import be.leerstad.EindwerkChezJava.Exceptions.ActiveOberNotSetException;
import be.leerstad.EindwerkChezJava.model.Cafe;
import be.leerstad.EindwerkChezJava.model.Ober;
import be.leerstad.EindwerkChezJava.model.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

public class OverviewObersController {
	private View view;
	private Cafe cafe;
	private Stage dialogStage;
	private ObservableList<Order> ordersOverview;
	@FXML
	private DatePicker datepicker;
	@FXML
	private TableView<Order> tableDailyIncome;
	@FXML
    private TableColumn<Order, String> collumDailyIncome;
	@FXML
    private AnchorPane anchorTopDrie;
	@FXML
    private Tab tabTopDrie;
	
	public OverviewObersController() {
		// TODO Auto-generated constructor stub
	}
    @FXML
    private void initialize() {
    	collumDailyIncome.setCellValueFactory(celldata -> celldata.getValue().getPrintout());

    }
    
	@FXML
	public void sendMail()
	{
		
	}
	@FXML
	public void getDailyIncome()
	{
		LocalDate ld = datepicker.getValue();
		//datepicker.getd
		//ordersOverview = view.getOrdersOverview();
		ordersOverview = FXCollections.observableArrayList(view.getDailyIncome(ld));
        tableDailyIncome.setItems(ordersOverview);
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
	
    public void setView(View view) throws ActiveOberNotSetException 
    {
        this.view = view;
        createPieChart();
    }
    
    public void setModel(Cafe model){
        this.cafe = model;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
