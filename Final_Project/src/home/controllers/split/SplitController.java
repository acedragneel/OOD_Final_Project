package home.controllers.split;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.charset.IllegalCharsetNameException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

import home.model.Bill;
import home.model.BillWriter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import home.controllers.split.People;

public class SplitController implements Initializable {

	@FXML
	private TableView<People> peopleTable;

	@FXML
	private TableView<SimpleBill> billTable;

	@FXML
	private TextField peopleNameInput;

	@FXML
	private Button btnAddPeople;

	@FXML
	private TextField itemInput;

	@FXML
	private TextField amountInput;

	@FXML
	private ListView<String> resultListView;

	private ArrayList<People> peopleList = new ArrayList<>();
	private ArrayList<SimpleBill> billList = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// Add People
		// Table View
		peopleTable();

		billTable();

	}

	@FXML
	private void calculateSplitBill(ActionEvent mouseEvent) throws IOException {
		HashMap<SimpleBill, ArrayList<People>> map = new HashMap<>();

		for (People person : peopleList) {
			ArrayList<String> sharedIndexList = person.getSharedItemIndex();
			person.setAmount((double) 0);

			for (String str : sharedIndexList) {
				// If the shared is "all" add it to all bill
				if (str.equals("all")) {
					for (SimpleBill bill : billList) {
						if (map.containsKey(bill)) {
							map.get(bill).add(person);
						} else {
							ArrayList<People> temp = new ArrayList<>();
							temp.add(person);
							map.put(bill, temp);
						}
					}
					continue;
				}

				// If the shared index is not "all"
				Integer index = Integer.valueOf(str);
				if (index > billList.size()) {
					continue;
				}
				if (map.containsKey(billList.get(index - 1))) {
					map.get(billList.get(index - 1)).add(person);
				} else {
					ArrayList<People> temp = new ArrayList<>();
					temp.add(person);
					map.put(billList.get(index - 1), temp);
				}
			}
		}

		for (SimpleBill bill : map.keySet()) {
			String result = bill.toString() + " ";
			ArrayList<People> value = map.get(bill);
			for (People person : value) {
				result += person.toString();
			}
		}

		for (SimpleBill bill : map.keySet()) {
			String result = bill.toString() + " ";
			double sharedPeopleNum = map.get(bill).size();
			double sharedMoney = bill.getAmount() / sharedPeopleNum;
			ArrayList<People> value = map.get(bill);
			for (People person : value) {
				person.setAmount(person.getAmount() + sharedMoney);
			}
		}
		resultListView.getItems().remove(1, resultListView.getItems().size());
		for (People person : peopleList) {
			resultListView.getItems().add(person.toString());
		}

	}

	private void billTable() {
		// TODO Auto-generated method stub
		TableColumn<SimpleBill, Void> indexColumn = new TableColumn<>("Index");
		indexColumn.setCellFactory(col -> {
			TableCell<SimpleBill, Void> cell = new TableCell<>();
			cell.textProperty().bind(Bindings.createStringBinding(() -> {
				if (cell.isEmpty()) {
					return null;
				} else {
					return Integer.toString(cell.getIndex() + 1);
				}
			}, cell.emptyProperty(), cell.indexProperty()));
			return cell;
		});

		TableColumn<SimpleBill, String> itemColumn = new TableColumn<>("Item");
		itemColumn.setCellValueFactory(new PropertyValueFactory<SimpleBill, String>("item"));

		TableColumn<SimpleBill, Double> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new PropertyValueFactory<SimpleBill, Double>("amount"));

		TableColumn<SimpleBill, SimpleBill> deleteColumn = new TableColumn<SimpleBill, SimpleBill>("Delete");
		deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		deleteColumn.setCellFactory(param -> new TableCell<SimpleBill, SimpleBill>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(SimpleBill bill, boolean empty) {
				super.updateItem(bill, empty);

				if (bill == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					getTableView().getItems().remove(bill);
					billList.remove(bill);
				});
			}
		});
		billTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		billTable.getColumns().add(indexColumn);
		billTable.getColumns().add(itemColumn);
		billTable.getColumns().add(amountColumn);
		billTable.getColumns().add(deleteColumn);
	}

	@FXML
	private void addBill(ActionEvent mouseEvent) throws IOException {
		String item = itemInput.getText();
		try {
			Double amount = Double.valueOf(amountInput.getText());
		} catch (Exception e) {
			return;
		}
		Double amount = Double.valueOf(amountInput.getText());

		billList.add(new SimpleBill(item, amount));
		itemInput.setText(null);
		amountInput.setText(null);
		updateBillTable();

	}

	private void updateBillTable() {
		// TODO Auto-generated method stub
		billTable.getItems().clear();
		double sum = 0;
		for (SimpleBill bill : billList) {
			billTable.getItems().add(bill);
			sum += bill.getAmount();
		}
		if (!resultListView.getItems().isEmpty()) {
			resultListView.getItems().remove(0);
		}
		resultListView.getItems().add(0, "Total bill " + sum);

	}

	private void peopleTable() {
		peopleTable.setEditable(true);
		TableColumn<People, String> peopleNameColumn = new TableColumn<>("Name");
		peopleNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		peopleNameColumn.setCellValueFactory(new PropertyValueFactory<People, String>("name"));

		TableColumn<People, String> shareColumn = new TableColumn<>("Share");
		shareColumn.setCellValueFactory(new PropertyValueFactory<People, String>("shareString"));
		shareColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		shareColumn.setOnEditCommit(new EventHandler<CellEditEvent<People, String>>() {
			@Override
			public void handle(CellEditEvent<People, String> event) {
				// TODO Auto-generated method stub
				String shareString = event.getNewValue();
				shareString = shareString.replace(" ", "");
				String[] str = shareString.split(",");
				ArrayList<String> sharedList = new ArrayList<>(Arrays.asList(str));
				People person = ((People) event.getTableView().getItems().get(event.getTablePosition().getRow()));
				person.setSharedItemIndex(sharedList);
			}
		});

		TableColumn<People, People> editTableColumn = new TableColumn<People, People>("Edit");
		editTableColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		editTableColumn.setCellFactory(param -> new TableCell<People, People>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(People people, boolean empty) {
				super.updateItem(people, empty);

				if (people == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					getTableView().getItems().remove(people);
					peopleList.remove(people);
				});
			}
		});

		peopleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		peopleTable.getColumns().add(peopleNameColumn);
		peopleTable.getColumns().add(shareColumn);
		peopleTable.getColumns().add(editTableColumn);

	}

	private void updatePeopleTable() {
		peopleTable.getItems().clear();
		for (People person : peopleList) {
			peopleTable.getItems().add(person);
		}
	}

	@FXML
	private void addPerson(ActionEvent mouseEvent) throws IOException {
		String input = peopleNameInput.getText();
		if (input.equals("")) {
			return;
		}
		for (People person : peopleList) {
			if (person.getName().equals(input))
				return;
		}
		peopleList.add(new People(peopleNameInput.getText()));
		peopleNameInput.setText(null);
		updatePeopleTable();
	}
}
