package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Bojra;
import utils.HelperMethods;
import utils.Utils;

public class BojraController extends VBox {

	@FXML private JFXTextField txtSearch;
	@FXML private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;
	@FXML private TableView<Bojra> tblBojra;
	@FXML private TableColumn<Bojra, String> tblColBojraId, tblColKategoria, tblColBoja, tblColSasia;
	@FXML private TableColumn<Bojra, Bojra> tblColDelete;

	private ObservableList<Bojra> tableViewData = FXCollections.observableArrayList();

	public static Bojra bojraDataHolder = new Bojra();

	public static boolean edit = false;

	public BojraController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/bojra.fxml"));

		fxml.setRoot(this);
		fxml.setController(this);
		try {
			fxml.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void initialize() {
		try {
			loadBojrat();
			searchTableview();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void searchTableview() {
		txtSearch.textProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable o) {

				if(txtSearch.textProperty().get().isEmpty()) {
					tblBojra.setItems(tableViewData);
					return;
				}

				ObservableList<Bojra> tableItems = FXCollections.observableArrayList();
				ObservableList<TableColumn<Bojra, ?>> cols = tblBojra.getColumns();

				for(int i=0; i<tableViewData.size(); i++) {
					for(int j=1; j<4; j++) { //shife kte ktu kur tmbushesh klientin
						TableColumn<Bojra, ?> col = cols.get(j);
						String cellValue = col.getCellData(tableViewData.get(i)).toString();
						cellValue = cellValue.toLowerCase();
						if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
							tableItems.add(tableViewData.get(i));
							break;
						}                        
					}
				}

				tblBojra.setItems(tableItems);
			}
		});
	}

	private void loadBojrat() throws SQLException {
		tblBojra.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getBojraDao().getBojra());	

		tblColBojraId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tblColBoja.setCellValueFactory(new PropertyValueFactory<>("emri"));
		//tblColSasia.setCellValueFactory(new PropertyValueFactory<>("created_date"));
		tblColKategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bojra, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Bojra, String> obj) {
				return new SimpleStringProperty(obj.getValue().getLloji_bojes_id().getLloji_bojes());
			}
		});

		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<Bojra, Bojra>() {

			Button delete = new Button("");
			protected void updateItem(Bojra b, boolean empty) {
				super.updateItem(b, empty);

				if (b == null) {
					setGraphic(null);
					return;
				}

				setGraphic(delete);
				Utils.style_delete_button(delete);

				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblBojra.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #DA251E; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #0093DC; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert,"bojen "+b.getEmri()+"?", konfirmo, anullo, false, "");
					konfirmo.setOnAction(e-> {
						delete(b.getId());
						alert.close();
					}); 
					anullo.setOnAction( e1 -> {
						alert.close();
					});
					HelperMethods.refresh_focus_table(tblBojra);
				});
			}
		});


		tblBojra.setItems(tableViewData);

	}

	private void getData() throws IOException, SQLException {
		Bojra bojra = tblBojra.getSelectionModel().getSelectedItem();
		bojraDataHolder.setId(bojra.getId());
		bojraDataHolder.setEmri(bojra.getEmri());
		bojraDataHolder.setLloji_bojes_id(bojra.getLloji_bojes_id());

		new Utils().openEditScene("bojraShto", "ink");
		loadBojrat();
	}

	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().openEditScene("bojraShto", "ink");
		loadBojrat();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if(tblBojra.getSelectionModel().getSelectedItem() != null) 
			getData();
		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
	}

	private void delete(int bojraId) {
		try {
			ControlDAO.getControlDao().getBojraDao().deleteBojra(bojraId);
			loadBojrat();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


}
