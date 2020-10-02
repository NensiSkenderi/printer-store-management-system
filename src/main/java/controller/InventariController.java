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
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Inventari;

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
					for (int j = 0; j < cols.size(); j++) {
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
		tblColKategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Inventari, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<Inventari, String> obj) {
				return new SimpleStringProperty(obj.getValue().getBojra_id().getLloji_bojes_id().getLloji_bojes());
			}
		});
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
		try {

			Stage stage = (Stage) btnPdf.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);
			FileWriter fileWriter = new FileWriter(file);

			String text = "";
			String header = "Nr" + "," + "Emri i Bojes" + "," + "Lloji i Bojes" + "," + "Gjendja" + "\n";

			fileWriter.write(header);
			for (int i = 0; i < tableViewData.size(); i++) {
				text = i + 1 + "," + tableViewData.get(i).getBojra_id().getEmri() + "," + tableViewData.get(i).getBojra_id().getLloji_bojes_id().getLloji_bojes() + ","
						+ tableViewData.get(i).getGjendja() + "\n";
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

			Anchor anchorTarget = new Anchor("Data " + dateFormat.format(date) + " Ora " + sdf.format(cal.getTime()) + "       ");
			
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

			Paragraph p3 = new Paragraph("Inventari",
					FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, new BaseColor(218, 37, 30)));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingBefore(25);
			document.add(p3);

			PdfPTable t = new PdfPTable(3);
			t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			t.setSpacingBefore(30);
			t.setWidthPercentage(95);
			t.setSpacingAfter(5);
			Font bold = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			Phrase phrase1 = new Phrase("Emri i Bojes", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("Lloji i Bojes", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			Phrase phrase3 = new Phrase("Gjendja", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);

			for (Inventari tablePdf : tableViewData) {
				t.addCell(tablePdf.getBojra_id().getEmri());
				t.addCell(tablePdf.getBojra_id().getLloji_bojes_id().getLloji_bojes());
				t.addCell(String.valueOf(tablePdf.getGjendja()));
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


}
