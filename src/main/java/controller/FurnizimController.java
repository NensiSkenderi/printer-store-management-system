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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
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
import model.Furnizim;
import model.Inventari;
import utils.HelperMethods;
import utils.Utils;

public class FurnizimController extends VBox {

	@FXML
	private JFXTextField txtSearch;
	@FXML
	private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;
	@FXML
	private TableView<Furnizim> tblFurnizim;
	@FXML
	private TableColumn<Furnizim, String> tblColFurnizimId, tblColBoja, tblColSasia, tblColCmimi, tblColVlera,
	tblColData;
	@FXML
	private TableColumn<Furnizim, Furnizim> tblColDelete;

	private ObservableList<Furnizim> tableViewData = FXCollections.observableArrayList();

	public static Furnizim furnizimDataHolder = new Furnizim();

	public static boolean edit = false;

	public FurnizimController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/furnizim.fxml"));

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
			loadFurnizim();
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
					tblFurnizim.setItems(tableViewData);
					return;
				}

				ObservableList<Furnizim> tableItems = FXCollections.observableArrayList();
				ObservableList<TableColumn<Furnizim, ?>> cols = tblFurnizim.getColumns();

				for (int i = 0; i < tableViewData.size(); i++) {
					for (int j = 1; j < cols.size(); j++) { // shife kte ktu kur tmbushesh klientin
						TableColumn<Furnizim, ?> col = cols.get(j);
						String cellValue = col.getCellData(tableViewData.get(i)).toString();
						cellValue = cellValue.toLowerCase();
						if (cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
							tableItems.add(tableViewData.get(i));
							break;
						}
					}
				}

				tblFurnizim.setItems(tableItems);
			}
		});
	}

	private void loadFurnizim() throws SQLException {
		tblFurnizim.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getFurnizimDao().getFurnizim());

		tblColFurnizimId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tblColBoja.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Furnizim, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Furnizim, String> obj) {
						return new SimpleStringProperty(obj.getValue().getBojra_id().getEmri());
					}
				});
		tblColData.setCellValueFactory(new PropertyValueFactory<>("created_date"));

		tblColSasia.setCellValueFactory(new PropertyValueFactory<>("sasia"));
		tblColCmimi.setCellValueFactory(new PropertyValueFactory<>("cmimi"));
		tblColVlera.setCellValueFactory(new PropertyValueFactory<>("vlera"));

		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<Furnizim, Furnizim>() {

			Button delete = new Button("");

			protected void updateItem(Furnizim f, boolean empty) {
				super.updateItem(f, empty);

				if (f == null) {
					setGraphic(null);
					return;
				}

				setGraphic(delete);
				Utils.style_delete_button(delete);

				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblFurnizim.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #DA251E; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #0093DC; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert, "furnizimin?", konfirmo, anullo, false, "");
					konfirmo.setOnAction(e -> {
						delete(f.getId(), f.getBojra_id(), f.getSasia());
						alert.close();
					});
					anullo.setOnAction(e1 -> {
						alert.close();
					});
					HelperMethods.refresh_focus_table(tblFurnizim);
				});
			}
		});

		tblFurnizim.setItems(tableViewData);

	}

	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().openEditScene("furnizimShto", "supply");
		loadFurnizim();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if (tblFurnizim.getSelectionModel().getSelectedItem() != null)
			getData();
		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
	}

	private void getData() throws IOException, SQLException {
		Furnizim furnizim = tblFurnizim.getSelectionModel().getSelectedItem();
		furnizimDataHolder.setId(furnizim.getId());
		furnizimDataHolder.setBojra_id(furnizim.getBojra_id());
		furnizimDataHolder.setCmimi(furnizim.getCmimi());
		furnizimDataHolder.setSasia(furnizim.getSasia());
		furnizimDataHolder.setVlera(furnizim.getVlera());

		new Utils().openEditScene("furnizimShto", "supply");
		loadFurnizim();
	}

	private void delete(int furnizimId, Bojra bojra, double sasia) {
		try {
			ControlDAO.getControlDao().getFurnizimDao().deleteFurnizim(furnizimId);
			loadFurnizim();

			Platform.runLater(() -> {
				double gjendjaVjeter;
				try {
					gjendjaVjeter = ControlDAO.getControlDao().getInventariDao().getGjendja(bojra);

					Inventari inventari = new Inventari();
					inventari.setBojra_id(bojra);
					inventari.setGjendja(gjendjaVjeter - sasia);

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
	private void excel() {
		try {

			Stage stage = (Stage) btnPdf.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);
			FileWriter fileWriter = new FileWriter(file);

			String text = "";
			String header = "Nr" + "," + "Emri i Bojes" + "," + "Sasia" + "," + "Cmimi" + "," + "Vlera" + "," + "Data"
					+ "\n";

			fileWriter.write(header);
			for (int i = 0; i < tableViewData.size(); i++) {
				text = i + 1 + "," + tableViewData.get(i).getBojra_id().getEmri() + ","
						+ tableViewData.get(i).getSasia() + "," + tableViewData.get(i).getCmimi() + ","
						+ tableViewData.get(i).getVlera() + "," + tableViewData.get(i).getCreated_date() + "\n";
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

			Stage stage = (Stage) btnPdf.getScene().getWindow();

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

			Anchor anchorTarget = new Anchor(
					"Data " + dateFormat.format(date) + " Ora " + sdf.format(cal.getTime()) + "       ");

			Paragraph paragraph1 = new Paragraph();
			paragraph1.setAlignment(Paragraph.ALIGN_RIGHT);
			paragraph1.setSpacingBefore(15);
			paragraph1.setSpacingAfter(15);

			paragraph1.add(anchorTarget);
			document.add(paragraph1);

			Paragraph p2 = new Paragraph("Refill Plus",
					FontFactory.getFont(FontFactory.TIMES, 22, Font.BOLD, new BaseColor(0, 147, 220)));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingBefore(20);
			document.add(p2);

			Paragraph p3 = new Paragraph("Furnizim",
					FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, new BaseColor(218, 37, 30)));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingBefore(25);
			document.add(p3);

			PdfPTable t = new PdfPTable(5);
			t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			t.setSpacingBefore(30);
			t.setWidthPercentage(95);
			t.setSpacingAfter(5);
			Font bold = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			Phrase phrase1 = new Phrase("Emri i Bojes", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("Sasia", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			Phrase phrase3 = new Phrase("Cmimi", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);
			Phrase phrase4 = new Phrase("Vlera", bold);
			PdfPCell c4 = new PdfPCell(phrase4);
			c4.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c4);
			Phrase phrase5 = new Phrase("Data", bold);
			PdfPCell c5 = new PdfPCell(phrase5);
			c5.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c5);

			for (Furnizim tablePdf : tableViewData) {
				t.addCell(tablePdf.getBojra_id().getEmri());
				t.addCell(String.valueOf(tablePdf.getSasia()));
				t.addCell(String.valueOf(tablePdf.getCmimi()));
				t.addCell(String.valueOf(tablePdf.getVlera()));
				t.addCell(tablePdf.getCreated_date().toString());
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
			ex.printStackTrace();
		}
	}

}
