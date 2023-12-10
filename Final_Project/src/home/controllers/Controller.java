package home.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private AnchorPane HomeRoot;

	@FXML
	private Button btnStat;

	@FXML
	private Button btnHome;

	@FXML
	private Button btnCate;

	@FXML
	private Button btnDetial;

	@FXML
	private Button btnImport;

	@FXML
	private Button btnSplit;
	
	@FXML
	private Button btnMarket;

	@FXML
	private AnchorPane navBar;
	
	@FXML
	private Button btnAddNewOne;

	@FXML
	private Label pageTitle;
	
	@FXML
	private Button btnUploadBill;

	@FXML
	private BarChart<String, Double> barChart;

	// my bad - the freaking mouse event
	@FXML
	private void handleButtonClicks(ActionEvent mouseEvent) throws IOException {
		if (mouseEvent.getSource() == btnHome) {
			loadHomeRoot(mouseEvent, "/home/fxml/HomeContent.fxml");
			setTitle("Home");
		} else if (mouseEvent.getSource() == btnStat) {
			loadHomeRoot(mouseEvent, "/home/fxml/Stat.fxml");
			setTitle("Graph");
		} else if (mouseEvent.getSource() == btnCate) {
			loadHomeRoot(mouseEvent, "/home/fxml/Category.fxml");
			setTitle("Category");
		} else if (mouseEvent.getSource() == btnDetial) {
			loadHomeRoot(mouseEvent, "/home/fxml/Detial.fxml");
			setTitle("Transactions");
		} else if (mouseEvent.getSource() == btnSplit) {
			loadHomeRoot(mouseEvent, "/home/fxml/Split.fxml");
			setTitle("Split Bill");
		} else if (mouseEvent.getSource() == btnMarket) {
			loadHomeRoot(mouseEvent, "/home/fxml/market.fxml");
			setTitle("Market Place");
		} else if(mouseEvent.getSource() == btnUploadBill) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/UploadBill.fxml"));
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.setTitle("Upload Bills");
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(mouseEvent.getSource() == btnAddNewOne) {
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
	}

	private void setTitle(String str) {
		pageTitle.setText("Savings Sage - " + str);
	}

	private void loadStage(String fxml, String title) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle(title);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void loadHomeRoot(ActionEvent e, String url) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource(url));
		HomeRoot.getChildren().setAll(pane);
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		try {
			Pane pane = FXMLLoader.load(getClass().getResource("/home/fxml/HomeContent.fxml"));
			HomeRoot.getChildren().setAll(pane);
			setTitle("Home");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
