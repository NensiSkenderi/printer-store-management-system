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
import model.Perdoruesit;
import utils.Utils;

public class PerdoruesitController extends VBox {

	@FXML
	private JFXTextField txtSearch;

	@FXML
	private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;

	@FXML
	private TableView<Perdoruesit> tblPerdoruesit;

	@FXML
	private TableColumn<Perdoruesit, String> tblColPerdoruesi, tblColEmri, tblColMbiemri, tblColTeDrejtat, tblColTelefon, tblColEmail, tblColUserid;

	@FXML 
	private TableColumn<Perdoruesit, Perdoruesit> tblColDelete;
	

	private ObservableList<Perdoruesit> tableViewData = FXCollections.observableArrayList();

	public static Perdoruesit PerdoruesitDataHolder = new Perdoruesit();

	public static boolean edit = false;

	public PerdoruesitController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/perdoruesit.fxml"));

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
			loadPerdoruesit();
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
	                	tblPerdoruesit.setItems(tableViewData);
	                	return;
	                }

	                ObservableList<Perdoruesit> tableItems = FXCollections.observableArrayList();
	                ObservableList<TableColumn<Perdoruesit, ?>> cols = tblPerdoruesit.getColumns();
	               
	                for(int i=0; i<tableViewData.size(); i++) {
	                	for(int j=0; j<6; j++) {
	                		TableColumn<Perdoruesit, ?> col = cols.get(j);
	                		String cellValue = col.getCellData(tableViewData.get(i)).toString();
	                		cellValue = cellValue.toLowerCase();
	                			if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
	                					tableItems.add(tableViewData.get(i));
	                					break;
	                				}                        
	                			}
	                		}

	                tblPerdoruesit.setItems(tableItems);
				}
	     });
	}

	private void loadPerdoruesit() throws SQLException {
		tblPerdoruesit.getItems().clear();
		tableViewData.addAll(ControlDAO.getControlDao().getPerdoruesitDao().viewPerdoruesit());	
		tblColEmri.setCellValueFactory(new PropertyValueFactory<>("name"));
		tblColPerdoruesi.setCellValueFactory(new PropertyValueFactory<>("username"));
		tblColMbiemri.setCellValueFactory(new PropertyValueFactory<>("surname"));
		tblColTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
		tblColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tblColTeDrejtat.setCellValueFactory(new PropertyValueFactory<>("access"));
		tblColUserid.setCellValueFactory(new PropertyValueFactory<>("userid"));
		//tblColData.setCellValueFactory(new PropertyValueFactory<>("created_date"));


		tblColDelete.setStyle("-fx-alignment:center;");
		tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tblColDelete.setCellFactory(param -> new TableCell<Perdoruesit, Perdoruesit>() {

			Button delete = new Button("");
			protected void updateItem(Perdoruesit p, boolean empty) {
				super.updateItem(p, empty);

				if (p == null) {
					setGraphic(null);
					return;
				}

				if (p.getUsername().equalsIgnoreCase("admin") && p.getAccess().equals("Admin")) {
					delete.setDisable(true);
				}
				else {
					delete.setDisable(false);
				}
				
				setGraphic(delete);
				Utils.style_delete_button(delete);
				delete.setOnMouseClicked(event -> {
					JFXAlert alert = new JFXAlert((Stage) tblPerdoruesit.getScene().getWindow());
					JFXButton anullo = new JFXButton("Anullo");
					anullo.setStyle("-fx-background-color: #B74636; -fx-text-fill: white;-fx-cursor: hand;");
					JFXButton konfirmo = new JFXButton("Konfirmo");
					konfirmo.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
					Utils.alert_fshirje(alert,"perdoruesin: "+p.getUsername()+"?", konfirmo, anullo, false, "");
					//konfirmo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());
					//anullo.getStylesheets().add(getClass().getResource("/css/jfoenix_css.css").toExternalForm());

					//konfirmo.getStyleClass().addAll("btnConfirm","btn","btnBlurred");
					//anullo.getStyleClass().addAll("btnLogout" , "btn", "btnBlurred");

					konfirmo.setOnAction(e-> {
						delete(p.getUserid());
						alert.close();
					}); 
					anullo.setOnAction( e1 -> {
						alert.close();
					});
					Utils.refresh_focus_table(tblPerdoruesit);
				});
			}
		});


		tblPerdoruesit.setItems(tableViewData);

	}

	@FXML
	private void add() throws IOException, SQLException {
		edit = false;
		new Utils().openEditScene("perdoruesitShto", "username");
		loadPerdoruesit();
	}

	@FXML
	private void edit() throws IOException, SQLException {
		edit = true;
		if(tblPerdoruesit.getSelectionModel().getSelectedItem() != null) {
			getData();
		}

		else
			Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", AlertType.WARNING);
	}

	private void getData() throws IOException, SQLException {
		Perdoruesit Perdoruesit = tblPerdoruesit.getSelectionModel().getSelectedItem();
		PerdoruesitDataHolder.setName(Perdoruesit.getName());
		PerdoruesitDataHolder.setSurname(Perdoruesit.getSurname());
		PerdoruesitDataHolder.setUsername(Perdoruesit.getUsername());
		PerdoruesitDataHolder.setPassword(Perdoruesit.getPassword());
		PerdoruesitDataHolder.setTelefon(Perdoruesit.getTelefon());
		PerdoruesitDataHolder.setEmail(Perdoruesit.getEmail());
		PerdoruesitDataHolder.setAccess(Perdoruesit.getAccess());
		PerdoruesitDataHolder.setUserid(Perdoruesit.getUserid());
		new Utils().openEditScene("perdoruesitShto", "username");
		loadPerdoruesit();
	}

	@FXML
	private void excel() {
		try {

			Stage stage = (Stage)btnExcel.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (*.csv)", "*.csv");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);
			FileWriter fileWriter = new FileWriter(file);

			String text = "";
			String header = "Nr" + "," + "Perdoruesi" + "," + "Emri" + "," + "Mbiemri"  + "," + "Te drejtat" + "," + "Telefon" + "," + "Email" +"\n" ;

			fileWriter.write(header);
			for(int i=0; i<tableViewData.size(); i++){

				text = i+1 + "," + tableViewData.get(i).getUsername()+ "," + tableViewData.get(i).getName()+ "," + tableViewData.get(i).getSurname()+ "," 
				+ tableViewData.get(i).getAccess() + "," + tableViewData.get(i).getTelefon()+ "," + tableViewData.get(i).getEmail() + "\n";
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

			Stage stage = (Stage)btnExcel.getScene().getWindow();

			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf","*.PDF");
			fileChooser.getExtensionFilters().add(extFilter);

			File file = fileChooser.showSaveDialog(stage);

			Document document = new Document(PageSize.A4.rotate(), 5f, 5f, 5f, 5f);
			PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(file.getAbsolutePath()));

			document.open();

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			Anchor anchorTarget = new Anchor("Data "+dateFormat.format(date) + " Ora " + sdf.format(cal.getTime()));

			Paragraph paragraph1 = new Paragraph();
			paragraph1.setAlignment(Paragraph.ALIGN_RIGHT);
			paragraph1.setSpacingBefore(15);
			paragraph1.setSpacingAfter(15);

			paragraph1.add(anchorTarget);
			document.add(paragraph1);

			Paragraph p2 = new Paragraph("Denas Power Management",FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, new CMYKColor(169,169,169,0)));
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setSpacingBefore(20);		
			document.add(p2);

			Paragraph p3 = new Paragraph("Karburanti",FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setSpacingBefore(25);		
			document.add(p3);

			PdfPTable t = new PdfPTable(6);
			t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			t.setSpacingBefore(30);
			t.setWidthPercentage(95);
			t.setSpacingAfter(5);
			Font bold = new Font(FontFamily.HELVETICA, 14, Font.BOLD);

			Phrase phrase1 = new Phrase("Perdoruesi", bold);
			PdfPCell c1 = new PdfPCell(phrase1);
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c1);
			Phrase phrase2 = new Phrase("Emri", bold);
			PdfPCell c2 = new PdfPCell(phrase2);
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c2);
			Phrase phrase3 = new Phrase("Mbiemri", bold);
			PdfPCell c3 = new PdfPCell(phrase3);
			c3.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c3);
			Phrase phrase4 = new Phrase("Te drejtat", bold);
			PdfPCell c4 = new PdfPCell(phrase4);
			c4.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c4);
			Phrase phrase5 = new Phrase("Telefon", bold);
			PdfPCell c5 = new PdfPCell(phrase5);
			c5.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c5);
			Phrase phrase6 = new Phrase("Email", bold);
			PdfPCell c6 = new PdfPCell(phrase6);
			c6.setHorizontalAlignment(Element.ALIGN_CENTER);
			t.addCell(c6);

			for(Perdoruesit table_pdf : tableViewData){
				t.addCell(table_pdf.getUsername());
				t.addCell(table_pdf.getName());
				t.addCell(table_pdf.getSurname());
				t.addCell(table_pdf.getAccess());
				t.addCell(table_pdf.getTelefon());
				t.addCell(table_pdf.getEmail());

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

	private void delete(int Perdoruesitid) {
		try {
			ControlDAO.getControlDao().getPerdoruesitDao().deletePerdoruesit(Perdoruesitid);
			loadPerdoruesit();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	


}
