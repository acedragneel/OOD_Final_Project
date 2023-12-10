package home.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import home.model.BillTools;
import home.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class StatController implements Initializable {
	@FXML
	private BarChart<String, Double> statBarChart;
	
	@FXML
	private CategoryAxis barXAxis, lineXAxis;
	
	@FXML
	private PieChart statPieChart;
	
	@FXML
	private LineChart<String, Double> statLineChart, statLineChart1;
	
	@FXML
	private DatePicker statStartDate, statEndDate;
	
	@FXML
	private Button btnConfirm;
	
	private boolean chartVisibility;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		// Set default value of DatePicker
		statStartDate.setValue(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1));
		statEndDate.setValue(LocalDate.now().plusDays(1));
		
		// Initialize the BarChart
		HashMap<String, Double> billMap = BillTools.getCateBillsSumByDays(LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1), LocalDate.now().plusDays(1));
		
		// Set Charts' visibility
		this.chartVisibility = true;
		this.statBarChart.setVisible(chartVisibility);
		this.statPieChart.setVisible(chartVisibility);
		this.statLineChart.setVisible(!chartVisibility);
		this.statLineChart1.setVisible(!chartVisibility);
		barXAxis.setAnimated(false);
		lineXAxis.setAnimated(false);
		// Draw charts		
		this.drawBarChart(billMap);
		this.drawPieChart(billMap);
		this.drawLineChart(LocalDate.now(), LocalDate.now().plusDays(1));
	}
	
	public void updateCharts() {
		/* Once the user change the date and click button, charts update Simultaneously */
		LocalDate startDate = statStartDate.getValue();
		LocalDate endDate = statEndDate.getValue();
		if (startDate.isBefore(endDate)) {
			statBarChart.getData().clear();
			statPieChart.getData().clear();
			statLineChart.getData().clear();
			statLineChart1.getData().clear();
			HashMap<String, Double> billMap = BillTools.getCateBillsSumByDays(startDate, endDate);
			this.drawBarChart(billMap);
			this.drawPieChart(billMap);
			this.drawLineChart(startDate, endDate);
			this.btnConfirm.setStyle("");
		}
		else {
			this.btnConfirm.setStyle("-fx-background-color:#ff0000");
		}
	}
	
	public void switchCharts() {
		/* Switch bar & pie to line, or line to bar & pie */
		this.chartVisibility = !this.chartVisibility;
		this.statBarChart.setVisible(chartVisibility);
		this.statPieChart.setVisible(chartVisibility);
		this.statLineChart.setVisible(!chartVisibility);
		this.statLineChart1.setVisible(!chartVisibility);
		this.updateCharts();
	}
	
	private void drawBarChart(HashMap<String, Double> billMap) {
		/* Method to draw bar chart */
		Iterator<String> iterator = billMap.keySet().iterator();
		Series<String, Double> cateChart = new Series<String, Double>();
		cateChart.setName("Categories");
	    while (iterator.hasNext()) {
	        String key = iterator.next();
	        double value = billMap.get(key);
			cateChart.getData().add(new Data<>(key, value));
	    }
	    statBarChart.getData().add(cateChart);
	}
	
	private void drawPieChart(HashMap<String, Double> billMap) {
		/* Method to draw pie chart */
		ObservableList<PieChart.Data> chartItemList = FXCollections.observableArrayList();
		Iterator<String> iterator = billMap.keySet().iterator();
		while (iterator.hasNext()) {
	        String key = iterator.next();
	        double value = billMap.get(key);
			chartItemList.add(new PieChart.Data(key, value));
	    }
		statPieChart.setData(chartItemList);
	}
	
	private void drawLineChart(LocalDate startDate, LocalDate endDate) {
		/* Method to draw line chart */
		LocalDate currDate = startDate;
		ArrayList<String> cateList = Category.getCategories();
		HashMap<String, Double> currMap, sumMap = new HashMap<>();
		HashMap<String, Series<String, Double>> seriesMap = new HashMap<>();
		HashMap<String, Series<String, Double>> seriesMap1 = new HashMap<>();
		
		for (String cate: cateList) {
			Series<String, Double> series = new Series<String, Double>();
			Series<String, Double> series1 = new Series<String, Double>();
			series.setName(cate);
			seriesMap.put(cate, series);
			series1.setName(cate);
			seriesMap1.put(cate, series1);
			sumMap.put(cate, 0.0);
		}
		
		/* Each day is a  different series, each day is a spot */
		while (currDate.isBefore(endDate)) {
			currMap = BillTools.getCateBillsSumByDays(currDate, currDate.plusDays(1));
			Iterator<String> iterator = sumMap.keySet().iterator();
			while (iterator.hasNext()) {
		        String key = iterator.next();
		        double value, value1;
		        if (currMap.containsKey(key)) {
		        	value1 = currMap.get(key) + sumMap.get(key);
		        	value = currMap.get(key);
		        }
		        else {
		        	value = sumMap.get(key);
		        	value1 = sumMap.get(key);
		        }
		        sumMap.put(key, value1);
				Series<String, Double> newSeries = seriesMap.get(key);
				Series<String, Double> newSeries1 = seriesMap1.get(key);
				newSeries.getData().add(new Data<>(currDate.toString(), value));
				newSeries1.getData().add(new Data<>(currDate.toString(), value1));
				seriesMap.put(key, newSeries);
				seriesMap1.put(key, newSeries1);
		    }
			currDate = currDate.plusDays(1);
		}
		Iterator<String> iterator = seriesMap.keySet().iterator();
		while (iterator.hasNext()) {
	        String key = iterator.next();
	        Series<String, Double> value = seriesMap.get(key);
	        Series<String, Double> value1 = seriesMap1.get(key);
			this.statLineChart.getData().add(value);
			this.statLineChart1.getData().add(value1);
	    }
	}
}
