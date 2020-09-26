package controller;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Klient;
import utils.Utils;

public class KlientetController extends VBox {

	@FXML
	private JFXTextField txtSearch;

	@FXML
	private JFXButton btnAdd, btnEdit, btnPdf;

	@FXML
	private TableView<Klient> tblKlient;

	@FXML
	private TableColumn<Klient, String> tblColKlienti, tblColNipt, tblColKontakt, tblColKlientId;

	@FXML
	private TableColumn<Klient, Klient> tblColDelete;

	private ObservableList<Klient> tableViewData = FXCollections.observableArrayList();

	public static Klient klientDataHolder = new Klient();

	public static boolean edit = false;

	public KlientetController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/klientet.fxml"));

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
			loadKlient();
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
					tblKlient.setItems(tableViewData);
					return;
				}

				ObservableList<Klient> tableItems = FXCollections.observableArrayList();
				ObservableList<TableColumn<Klient, ?>> cols = tblKlient.getColumns();

				for (int i = 0; i < tableViewData.size(); i++) {
					for (int j = 0; j < 6; j++) {
						TableColumn<Klient, ?> col = cols.get(j);
						String cellValue = col.getCellData(tableViewData.get(i)).toString();
						cellValue = cellValue.toLowerCase();
						if (cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
							tableItems.add(tableViewData.get(i));
							break;
						}
					}
				}

				tblKlient.setItems(tableItems);
			}
		});
	}

	private void loadKlient() throws SQLException {
		tblKlient.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getKlientDao().viewKlient());
		tblColKlienti.setCellValueFactory(new PropertyValueFactory<>("klienti"));
		tblColNipt.setCellValueFactory(new PropertyValueFactory<>("nipt"));
		tblColKontakt.setCellValueFactory(new PropertyValueFactory<>("kontakt"));
		tblColKlientId.setCellValueFactory(new PropertyValueFactory<>("id"));
		// tblColData.setCellValueFactory(new PropertyValueFactory<>("created_date"));

		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<Klient, Klient>() {

			Button delete = new Button("");

			protected void updateItem(Klient k, boolean empty) {
				super.updateItem(k, empty);

				if (k == null) {
					setGraphic(null);
					return;
				}

//				if (p.getUsername().equalsIgnoreCase("admin") && p.getAccess().equals("Admin")) {
//					delete.setDisable(true);
//				} else {
//					delete.setDisable(false);
//				}

				setGraphic(delete);
				Utils.style_delete_button(delete);
				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblKlient.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #B74636; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert, "klientin?", konfirmo, anullo, false, "");
					// konfirmo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());
					// anullo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());

					// konfirmo.getStyleClass().addAll("btnConfirm","btn","btnBlurred");
					// anullo.getStyleClass().addAll("btnLogout" , "btn", "btnBlurred");

					konfirmo.setOnAction(e -> {
		//				delete(k.getId());
						alert.close();
					});
					anullo.setOnAction(e1 -> {
						alert.close();
					});
					Utils.refresh_focus_table(tblKlient);
				});
			}
		});

		tblKlient.setItems(tableViewData);

	}
	
	@FXML
	private void add() {

	}

	@FXML
	private void edit() {

	}

	@FXML
	private void excel() {

	}

	@FXML
	private void pdf() {

	}

}
