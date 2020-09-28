package controller;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Bojra;
import model.Inventari;
import model.Klient;

public class InventariController extends VBox {

	@FXML
	private JFXTextField txtSearch;

	@FXML
	private JFXButton btnEdit, btnPdf;

	@FXML
	private TableView<Inventari> tblnventari;

	@FXML
	private TableColumn<Inventari, String> tblColKategoria, tblColBoja, tblColGjendja;

	private ObservableList<Inventari> tableViewData = FXCollections.observableArrayList();

	public InventariController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/inventari.fxml"));

		fxml.setRoot(this);
		fxml.setController(this);
		try {
			fxml.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() throws Exception {
		try {
			loadInventari();
			searchTableview();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void searchTableview() {
		txtSearch.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {

				if (txtSearch.textProperty().get().isEmpty()) {
					tblnventari.setItems(tableViewData);
					return;
				}

				ObservableList<Inventari> tableItems = FXCollections.observableArrayList();
				ObservableList<TableColumn<Inventari, ?>> cols = tblnventari.getColumns();

				for (int i = 0; i < tableViewData.size(); i++) {
					for (int j = 0; j < 6; j++) {
						TableColumn<Inventari, ?> col = cols.get(j);
						String cellValue = col.getCellData(tableViewData.get(i)).toString();
						cellValue = cellValue.toLowerCase();
						if (cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
							tableItems.add(tableViewData.get(i));
							break;
						}
					}
				}

				tblnventari.setItems(tableItems);
			}
		});
	}

	private void loadInventari() throws SQLException {
		tblnventari.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getInventariDao().getInventari());
//		tblColKategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inventari, String>, ObservableValue<String>>() {
//			@Override
//			public ObservableValue<String> call(TableColumn.CellDataFeatures<Inventari, String> obj) {
//				return new SimpleStringProperty(obj.getValue().getBojra_id().getLloji_bojes_id().getLloji_bojes());
//			}
//		});
		tblColBoja.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inventari, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Inventari, String> obj) {
				return new SimpleStringProperty(obj.getValue().getBojra_id().getEmri());
			}
		});
		tblColGjendja.setCellValueFactory(new PropertyValueFactory<>("gjendja"));
		
		tblnventari.setItems(tableViewData);

	}


	@FXML
	private void excel() {

	}

	@FXML
	private void pdf() {

	}

}
