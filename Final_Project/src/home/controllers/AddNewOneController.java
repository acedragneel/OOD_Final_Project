


package home.controllers;

import java.time.LocalDate;

import home.model.BillWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddNewOneController {
	@FXML
	private TextField fieldNumber;
	@FXML
	private TextField fieldItemName;
	@FXML
	private DatePicker datePicker;
	@FXML
	private AnchorPane pane;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnAdd;
	@FXML
	private TextField fieldCategory;
	@FXML
	private void cancelOnClicks() {
		Stage stage = (Stage)btnCancel.getScene().getWindow();
		stage.close();
	}
	@FXML
	private void addOnClicks() {
		BillWriter writer  =new BillWriter();
		String itemName = fieldItemName.getText();
		Double number = Double.valueOf(fieldNumber.getText());
		LocalDate date = datePicker.getValue();
		String category = fieldCategory.getText();
		writer.write( itemName ,number, date, category);
		//write to the value to the database
		Stage stage = (Stage)btnAdd.getScene().getWindow();
		stage.close();
	}

}
