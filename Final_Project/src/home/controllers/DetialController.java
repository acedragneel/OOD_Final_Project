package home.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import home.model.Bill;
import home.model.BillReader;
import home.model.BillTools;
import home.model.BillWriter;
// Creating DetialController
public class DetialController {
	@FXML
	TableView<Bill> detialTable;

	@FXML
	AnchorPane tableContainer;

	@FXML
	DatePicker datePicker;

	public void initialize() {

		detialTable.setEditable(true);
		TableColumn<Bill, String> dateColumn = new TableColumn<>("Time");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("timeString"));
		dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		dateColumn.setOnEditCommit(new EventHandler<CellEditEvent<Bill, String>>() {
			@Override
			public void handle(CellEditEvent<Bill, String> event) {
				// TODO Auto-generated method stub
				Bill oldBill = ((Bill) event.getTableView().getItems().get(event.getTablePosition().getRow()));
				Bill newBill = (Bill) oldBill.clone();
				String str = event.getNewValue();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
				newBill.setTime(dateTime);
				BillWriter.replaceOldWithNew(oldBill, newBill);
			}
		});

		TableColumn<Bill, String> itemColumn = new TableColumn<>("Item");
		itemColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("item"));
		itemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		itemColumn.setOnEditCommit(new EventHandler<CellEditEvent<Bill, String>>() {
			@Override
			public void handle(CellEditEvent<Bill, String> event) {
				// TODO Auto-generated method stub
				Bill oldBill = ((Bill) event.getTableView().getItems().get(event.getTablePosition().getRow()));
				Bill newBill = (Bill) oldBill.clone();
				newBill.setItem(event.getNewValue());
				BillWriter.replaceOldWithNew(oldBill, newBill);

			}
		});

		TableColumn<Bill, String> moneyColumn = new TableColumn<>("Amount");
		moneyColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("amountString"));
		moneyColumn.setComparator((s1, s2) -> {
			s1 = s1.replace(",", "");
			s2 = s2.replace(",", "");

			return (int) (Double.valueOf(s1) - Double.valueOf(s2));
		});
		moneyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		moneyColumn.setOnEditCommit(new EventHandler<CellEditEvent<Bill, String>>() {
			@Override
			public void handle(CellEditEvent<Bill, String> event) {
				if (!event.getNewValue().equals(event.getOldValue())) {
					Bill oldBill = ((Bill) event.getTableView().getItems().get(event.getTablePosition().getRow()));
					Bill newBill = (Bill) oldBill.clone();
					newBill.setAmount(Double.valueOf(event.getNewValue().replace(",", "")));
					BillWriter.replaceOldWithNew(oldBill, newBill);
				}
			}
		});

		TableColumn<Bill, String> categoryColumn = new TableColumn<>("Category");
		categoryColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("category"));
		categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		categoryColumn.setOnEditCommit(new EventHandler<CellEditEvent<Bill, String>>() {
			@Override
			public void handle(CellEditEvent<Bill, String> event) {
				// TODO Auto-generated method stub
				if (!event.getNewValue().equals(event.getOldValue())) {
					Bill oldBill = ((Bill) event.getTableView().getItems().get(event.getTablePosition().getRow()));
					Bill newBill = (Bill) oldBill.clone();
					newBill.setCategory(event.getNewValue());
					BillWriter.replaceOldWithNew(oldBill, newBill);
				}
			}
		});

		TableColumn<Bill, String> paymentMethodColumn = new TableColumn<>("Payment Method");
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("CASH");
		list.add("BANK");
		paymentMethodColumn.setCellValueFactory(new PropertyValueFactory<Bill, String>("paymentMethod"));
		paymentMethodColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(list));
		paymentMethodColumn.setOnEditCommit(new EventHandler<CellEditEvent<Bill, String>>() {
			@Override
			public void handle(CellEditEvent<Bill, String> event) {
				// TODO Auto-generated method stub
				if (!event.getNewValue().equals(event.getOldValue())) {
					Bill oldBill = ((Bill) event.getTableView().getItems().get(event.getTablePosition().getRow()));
					Bill newBill = (Bill) oldBill.clone();
					newBill.setPaymentMethod(event.getNewValue());
					BillWriter.replaceOldWithNew(oldBill, newBill);
				}
			}
		});

		TableColumn<Bill, Bill> editTableColumn = new TableColumn<Bill, Bill>("Edit");
		editTableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		editTableColumn.setCellFactory(param -> new TableCell<Bill, Bill>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(Bill bill, boolean empty) {
				super.updateItem(bill, empty);

				if (bill == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					getTableView().getItems().remove(bill);
					BillWriter.remove(bill);
				});
			}
		});

		detialTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		detialTable.getColumns().add(dateColumn);
		detialTable.getColumns().add(itemColumn);
		detialTable.getColumns().add(moneyColumn);
		detialTable.getColumns().add(categoryColumn);
		detialTable.getColumns().add(paymentMethodColumn);
		detialTable.getColumns().add(editTableColumn);
		
		datePicker.setValue(NOW_LOCAL_DATE());
		
		updateTable();
	}

	public void updateTable() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		// convert String to LocalDate
		Bill[] bills = BillReader.getBillsByDay(datePicker.getValue());
		detialTable.getItems().clear();
		for (Bill bill : bills) {
			detialTable.getItems().add(bill);
		}

	}

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

}

