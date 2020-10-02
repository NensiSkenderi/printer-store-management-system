package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Bojra;
import model.Inventari;
import model.Shitje;
import utils.HelperMethods;
import utils.Utils;

public class ShitjetController extends VBox {

	@FXML
	private JFXTextField txtSearch;
	@FXML
	private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;
	@FXML
	private TableView<Shitje> tblShitjet;
	@FXML
	private TableColumn<Shitje, String> tblColShitjeId, tblColLlojiFatures, tblColData, tblColBoja, tblColKlienti,
	tblColSasia, tblColCmimi, tblColVlera, tblColArketuar, tblColLikujduar;
	@FXML
	private TableColumn<Shitje, Shitje> tblColDelete;
	@FXML 
	private JFXDatePicker datePickerFrom, datePickerTo;

	private ObservableList<Shitje> tableViewData = FXCollections.observableArrayList();

	public static Shitje shitjeDataHolder = new Shitje();

	public static boolean edit = false;

	public ShitjetController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/shitjet.fxml"));

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
			HelperMethods.convertDatepicker(datePickerFrom, datePickerTo);
			datePickerTo.setValue(LocalDate.now());
			datePickerFrom.setValue(datePickerTo.getValue().minusDays(15));
			loadShitjet();
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
					tblShitjet.setItems(tableViewData);
					return;
				}

				ObservableList<Shitje> tableItems = FXCollections.observableArrayList();
				ObservableList<TableColumn<Shitje, ?>> cols = tblShitjet.getColumns();

				for (int i = 0; i < tableViewData.size(); i++) {
					for (int j = 1; j < cols.size(); j++) { // shife kte ktu kur tmbushesh klientin
						TableColumn<Shitje, ?> col = cols.get(j);
						String cellValue = col.getCellData(tableViewData.get(i)).toString();
						cellValue = cellValue.toLowerCase();
						if (cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
							tableItems.add(tableViewData.get(i));
							break;
						}
					}
				}

				tblShitjet.setItems(tableItems);
			}
		});
	}

	private void loadShitjet() throws SQLException {
		tblShitjet.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getShitjeDao().getShitje(datePickerFrom.getValue(), datePickerTo.getValue()));	

		tblColShitjeId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tblColLlojiFatures.setCellValueFactory(new PropertyValueFactory<>("lloji_fatures"));
		tblColData.setCellValueFactory(new PropertyValueFactory<>("created_date"));
		tblColLikujduar.setCellValueFactory(new PropertyValueFactory<>("date_likujduar"));
		tblColBoja.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shitje, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Shitje, String> obj) {
						return new SimpleStringProperty(obj.getValue().getBojra_id().getEmri());
					}
				});

		tblColArketuar.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shitje, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Shitje, String> obj) {
						return new SimpleStringProperty(obj.getValue().getArketim_id().getMenyra());
					}
				});

		tblColKlienti.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Shitje, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Shitje, String> obj) {
						return new SimpleStringProperty(obj.getValue().getKlient_id().getKlienti());
					}
				});

		tblColSasia.setCellValueFactory(new PropertyValueFactory<>("sasia"));
		tblColCmimi.setCellValueFactory(new PropertyValueFactory<>("cmimi"));
		tblColVlera.setCellValueFactory(new PropertyValueFactory<>("vlera"));

		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<Shitje, Shitje>() {

			Button delete = new Button("");

			protected void updateItem(Shitje sh, boolean empty) {
				super.updateItem(sh, empty);

				if (sh == null) {
					setGraphic(null);
					return;
				}

				setGraphic(delete);
				Utils.style_delete_button(delete);

				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblShitjet.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #DA251E; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #0093DC; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert, "shitjen?", konfirmo, anullo, false, "");
					konfirmo.setOnAction(e -> {
						delete(sh.getId(), sh.getBojra_id(), sh.getSasia());
						alert.close();
					});
					anullo.setOnAction(e1 -> {
						alert.close();
					});
					HelperMethods.refresh_focus_table(tblShitjet);
				});
			}
		});

		tblShitjet.setItems(tableViewData);

	}

	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().openEditScene("shitjeShto", "sales");
		loadShitjet();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if (tblShitjet.getSelectionModel().getSelectedItem() != null)
			getData();
		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
	}

	private void getData() throws IOException, SQLException {
		Shitje shitje = tblShitjet.getSelectionModel().getSelectedItem();
		shitjeDataHolder.setId(shitje.getId());
		shitjeDataHolder.setArketim_id(shitje.getArketim_id());
		shitjeDataHolder.setBojra_id(shitje.getBojra_id());
		shitjeDataHolder.setCmimi(shitje.getCmimi());
		shitjeDataHolder.setDate_likujduar(shitje.getDate_likujduar());
		shitjeDataHolder.setKlient_id(shitje.getKlient_id());
		shitjeDataHolder.setLloji_fatures(shitje.getLloji_fatures());
		shitjeDataHolder.setSasia(shitje.getSasia());
		shitjeDataHolder.setVlera(shitje.getVlera());

		new Utils().openEditScene("shitjeShto", "sales");
		loadShitjet();
	}

	@FXML
	private void excel() {
		try {

			Stage stage = (Stage) btnExcel.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);
			FileWriter fileWriter = new FileWriter(file);

			String text = "";
			String header = "Nr" + "," + "Lloji i Fatures" + "," + "Data" + "," + "Boja" + "," + "Klienti" + ","
					+ "Sasia" + "," + "Cmimi" + "," + "Vlera" + "," + "Pagesa" + "," + "Likujduar" + "\n";

			fileWriter.write(header);
			for (int i = 0; i < tableViewData.size(); i++) {
				text = i + 1 + "," + tableViewData.get(i).getLloji_fatures() + ","
						+ tableViewData.get(i).getCreated_date() + "," + tableViewData.get(i).getBojra_id().getEmri()
						+ "," + tableViewData.get(i).getKlient_id().getKlienti() + "," + tableViewData.get(i).getSasia()
						+ "," + tableViewData.get(i).getCmimi() + "," + tableViewData.get(i).getVlera() + ","
						+ tableViewData.get(i).getArketim_id().getMenyra() + ","
						+ tableViewData.get(i).getDate_likujduar() + "\n";
				fileWriter.write(text);
			}

			fileWriter.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@FXML
	private void pdf() {
		try {

			Stage stage = (Stage) btnExcel.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf",
					"*.PDF");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);

			Document document = new Document(PageSize.A4.rotate(), 5f, 5f, 5f, 5f);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));

			document.open();

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			Anchor anchorTarget = new Anchor("Data " + dateFormat.format(date) + " Ora " + sdf.format(cal.getTime()));

			Paragraph paragraph1 = new Paragraph();
			paragraph1.setAlignment(Paragraph.ALIGN_RIGHT);
			paragraph1.setSpacingBefore(15);
			paragraph1.setSpacingAfter(15);

			paragraph1.add(anchorTarget);
			document.add(paragraph1);

			Paragraph p2 = new Paragraph("Refill Plus",
					FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, new CMYKColor(169, 169, 169, 0)));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingBefore(20);
			document.add(p2);
			Paragraph p3 = new Paragraph("Shitjet",
					FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingBefore(25);
			document.add(p3);

			PdfPTable t = new PdfPTable(9);
			t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			t.setSpacingBefore(30);
			t.setWidthPercentage(95);
			t.setSpacingAfter(5);
			Font bold = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			Phrase phrase1 = new Phrase("Lloji i Fatures", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("Data", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			Phrase phrase3 = new Phrase("Boja", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);
			Phrase phrase4 = new Phrase("Klienti", bold);
			PdfPCell c4 = new PdfPCell(phrase4);
			c4.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c4);
			Phrase phrase5 = new Phrase("Sasia", bold);
			PdfPCell c5 = new PdfPCell(phrase5);
			c5.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c5);
			Phrase phrase6 = new Phrase("Cmimi", bold);
			PdfPCell c6 = new PdfPCell(phrase6);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c6);
			Phrase phrase7 = new Phrase("Vlera", bold);
			PdfPCell c7 = new PdfPCell(phrase7);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c7);
			Phrase phrase8 = new Phrase("Pagesa", bold);
			PdfPCell c8 = new PdfPCell(phrase8);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c8);
			Phrase phrase9 = new Phrase("Likujduar", bold);
			PdfPCell c9 = new PdfPCell(phrase9);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c9);

			for (Shitje tablePdf : tableViewData) {
				t.addCell(tablePdf.getLloji_fatures());
				t.addCell(tablePdf.getCreated_date().toString());
				t.addCell(tablePdf.getBojra_id().getEmri());
				t.addCell(tablePdf.getKlient_id().getKlienti());
				t.addCell(String.valueOf(tablePdf.getSasia()));
				t.addCell(String.valueOf(tablePdf.getCmimi()));
				t.addCell(String.valueOf(tablePdf.getVlera()));
				t.addCell(tablePdf.getArketim_id().getMenyra());
				t.addCell(tablePdf.getDate_likujduar().toString());

			}
			document.add(t);

			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(95);
			table.setSpacingBefore(50);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			document.add(table);
			document.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void delete(int shitjeId, Bojra bojra, double sasia) {
		try {
			ControlDAO.getControlDao().getShitjeDao().deleteShitje(shitjeId);
			loadShitjet();
			Platform.runLater(() ->{
				double gjendjaVjeter;
				try {
					gjendjaVjeter = ControlDAO.getControlDao().getInventariDao().getGjendja(bojra);

					Inventari inventari = new Inventari();
					inventari.setBojra_id(bojra);
					inventari.setGjendja(gjendjaVjeter + sasia);

					ControlDAO.getControlDao().getInventariDao().updateGjendje(inventari);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void filterDate() throws SQLException {
		loadShitjet();
		searchTableview();
	}

}
