package home.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;

import home.model.Bill;
import home.model.BillReader;
import home.model.BillTools;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HomeContentController implements Initializable {
	@FXML
	private BarChart<String, Double> barChart;

	@FXML
	private Label labelDate;

	@FXML
	private Label labelTodaySpend;

	@FXML 
	private Button btnAddNewOne;


	@FXML
	private void handleOnClicks() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/AddNewOne.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Add New One");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void todaySpendRender() {
		Double todaySpend = BillTools.getTodaySpend();
		labelTodaySpend.setText(String.format("%.2f", todaySpend)); 
	}
	private void dateRender() {
		LocalDate today = LocalDate.now();
		labelDate.setText(today.toString());
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		// TODO Auto-generated method stub
//		handleOnClicks();
		todaySpendRender();
		dateRender();
		Series<String, Double> todayBar = new Series<String,Double>();
		
		LocalDate today = LocalDate.now();
		int item = 0;
		LocalDate endDay =today.plusDays(1);
//		double drinkAll = 0;
//		double homeAll = 0;
//		double playAll = 0;
//		double foodAll = 0;
//		double movieAll = 0;
//		double bookAll = 0;
		HashMap<String, Double> oneDayMap = BillTools.getCateBillsSumByDays(today,endDay);
//		Set<String> keys = oneDayMap.keySet();
		Iterator<String> iterator = oneDayMap.keySet().iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			double value = oneDayMap.get(key);
			todayBar.getData().add(new Data<>(key,value));
		}
		
		
		todayBar.setName("Today");
//		seriesplay.setName("Play");
//		seriesfood.setName("Food");
//		seriesdrink.setName("Drink");
//		seriesmoive.setName("Movie");
//		seriesbook.setName("Book");

//		serieshome.getData().add(new XYChart.Data(LocalDate.now().toString(), homeAll));
//		seriesplay.getData().add(new XYChart.Data(LocalDate.now().toString(), playAll));
//		seriesfood.getData().add(new XYChart.Data(LocalDate.now().toString(), foodAll));
//		seriesdrink.getData().add(new XYChart.Data(LocalDate.now().toString(), drinkAll));
//		seriesmoive.getData().add(new XYChart.Data(LocalDate.now().toString(), movieAll));
//		seriesbook.getData().add(new XYChart.Data(LocalDate.now().toString(), bookAll));

//		barChart.getData().addAll(serieshome, seriesplay, seriesfood, seriesdrink, seriesmoive, seriesbook);
		barChart.getData().add(todayBar);
	}
}

