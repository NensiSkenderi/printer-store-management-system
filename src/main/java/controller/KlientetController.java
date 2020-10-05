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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
					for (int j = 1; j < cols.size() - 1; j++) {
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
		tableViewData.addAll(ControlDAO.getControlDao().getKlientDao().getKlient());
		tblColKlienti.setCellValueFactory(new PropertyValueFactory<>("klienti"));
		tblColNipt.setCellValueFactory(new PropertyValueFactory<>("nipt"));
		tblColKontakt.setCellValueFactory(new PropertyValueFactory<>("kontakt"));
		tblColKlientId.setCellValueFactory(new PropertyValueFactory<>("id"));

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

				setGraphic(delete);
				Utils.style_delete_button(delete);
				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblKlient.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #DA251E; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #0093DC; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert, "klientin?", konfirmo, anullo, false, "");

					konfirmo.setOnAction(e -> {
						delete(k.getId());
						alert.close();
					});
					anullo.setOnAction(e1 -> {
						alert.close();
					});
					Utils.refresh_focus_table(tblKlient);
				});
			}
		});

		if(Utils.rights.contentEquals("User"))
			btnEdit.setVisible(false);
		
		tblKlient.setItems(tableViewData);

	}

	private void getData() throws IOException, SQLException {
		Klient klient = tblKlient.getSelectionModel().getSelectedItem();
		klientDataHolder.setId(klient.getId());
		klientDataHolder.setNipt(klient.getNipt());
		klientDataHolder.setKontakt(klient.getKontakt());
		klientDataHolder.setKlienti(klient.getKlienti());

		new Utils().openEditScene("klientetShto", "klienti");
		loadKlient();
	}

	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().openEditScene("klientetShto", "klienti");
		loadKlient();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if (tblKlient.getSelectionModel().getSelectedItem() != null)
			getData();
		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
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
			String header = "Nr" + "," + "Klienti" + "," + "NIPT" + "," + "Kontakt" + "\n";

			fileWriter.write(header);
			for (int i = 0; i < tableViewData.size(); i++) {
				text = i + 1 + "," + tableViewData.get(i).getKlienti() + "," + tableViewData.get(i).getNipt() + ","
						+ tableViewData.get(i).getKontakt() + "\n";
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

			Paragraph p3 = new Paragraph("Klientet",
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

			Phrase phrase1 = new Phrase("Klienti", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("NIPT", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			Phrase phrase3 = new Phrase("Kontakt", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);

			for (Klient tablePdf : tableViewData) {
				t.addCell(tablePdf.getKlienti());
				t.addCell(tablePdf.getNipt());
				t.addCell(tablePdf.getKontakt());
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

	private void delete(int klientId) {
		try {
			ControlDAO.getControlDao().getKlientDao().deleteKlient(klientId);
			loadKlient();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
