package home.controllers;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

import home.model.BillWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


	public class UploadBillController {
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
		private Button btnupload;
		@FXML
		private Button btnAdd;
		@FXML
		private TextField fieldCategory;
		@FXML
		private static final String DESTINATION_FOLDER = "/Users/ajitpatil/Desktop/ThirdSemester/OODLab/PROJECT"; // specify the destination folder here
		@FXML
		private void cancelOnClicks() {
			Stage stage = (Stage)btnCancel.getScene().getWindow();
			stage.close();
		}
		@FXML
private void uploadbillOnClicks() {
		    FileChooser fc = new FileChooser();
		    fc.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.gif", "*.png"));
		    List<File> selectedFiles = fc.showOpenMultipleDialog(null);
		    if (selectedFiles != null) {
		        for (File sourceFile : selectedFiles) {
		            String fileName = sourceFile.getName();
		            File destinationFile = new File(DESTINATION_FOLDER + "/" + fileName);
		            try {
		                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		                System.out.println("Image copied to: " + destinationFile.getAbsolutePath());

		                // Get data from text field
		                String data = fieldItemName.getText();

		                // Write data to text file
		                File textFile = new File(DESTINATION_FOLDER + "/" + fileName + ".txt");
		                FileWriter fileWriter = new FileWriter(textFile);
		                fileWriter.write(data);
		                fileWriter.close();

		                System.out.println("Data written to text file: " + textFile.getAbsolutePath());
		                
		                // Show success message dialog
		                Alert alert = new Alert(Alert.AlertType.INFORMATION);
		                alert.setTitle("Success");
		                alert.setHeaderText(null);
		                alert.setContentText("Bill uploaded successfully");
		                alert.showAndWait();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		    Stage stage = (Stage)btnupload.getScene().getWindow();
		    stage.close();
		}
	
	
	}
	
	




