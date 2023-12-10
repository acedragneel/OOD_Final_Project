
package home.controllers;

import java.util.ArrayList;
import home.model.BillWriter;
import home.model.Category;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class CategoryController {
	@FXML
	private TableView<Category> cateTableView;
	
	@FXML
	private TableColumn<Category, String> cateItemColumn;
	
	@FXML
	private TableColumn<Category, Category> cateEditcolumn;
	
	public void initialize() {
		// TODO Auto-generated method stub
		
		/* Initialize the table */
		cateTableView.setEditable(true);
		cateItemColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("category"));
		cateItemColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		cateItemColumn.setStyle("-fx-alignment: CENTER;");
		
		/* Add event listener to cell： CHANGE */
		cateItemColumn.setOnEditCommit(new EventHandler<CellEditEvent<Category, String>>() {
			@Override
			public void handle(CellEditEvent<Category, String> event) {
				// TODO Auto-generated method stub
				if (!event.getNewValue().equals(event.getOldValue())) {
					Category oldCate = ((Category) event.getTableView().getItems().get(event.getTablePosition().getRow()));
					Category newCate = (Category) oldCate.clone();
					newCate.setCategory(event.getNewValue());
					Category.updateCategory(oldCate.getCategory(), newCate.getCategory());
					BillWriter.updateCategory(oldCate.getCategory(), newCate.getCategory());
				}
			}
		});
		
		
		/* Add event listener to button in cell: DELETE */
		cateEditcolumn.setStyle("-fx-alignment: CENTER;");
		cateEditcolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		cateEditcolumn.setCellFactory(param -> new TableCell<Category, Category>() {
			private final Button deleteButton = new Button("Delete");

			@Override
			protected void updateItem(Category category, boolean empty) {
				super.updateItem(category, empty);

				if (category == null) {
					setGraphic(null);
					return;
				}
				if (!category.getCategory().equals("NULL")) {
					setGraphic(deleteButton);
					deleteButton.setOnAction(event -> {
						getTableView().getItems().remove(category);
						Category.removeCategory(category.getCategory());
						BillWriter.updateCategory(category.getCategory(), "NULL");
						initialize();
					});
				}
			}
		});
		
		updateTable();
		
	}
	
	private void updateTable() {
		/* Render again */
		cateTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		ArrayList<Category> cateList = Category.getCategoryByObject();
		cateTableView.getItems().clear();
		for (Category cate: cateList) {
			cateTableView.getItems().add(cate);
		}
	}
	
}
