package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import utils.HelperMethods;

public class templateController extends VBox {

	@FXML private TextField txtSuma, txtInLitere, txtNume, txtPrenume, txt1, txt2, txtEmail;
	@FXML private TextField txtNr, txtBl, txtSc, txtEt, txtApt;
	@FXML private ComboBox<String> cmb1, cmb2, cmb3, cmb4, cmb5, cmb6, cmb7, cmb8;
	@FXML private JFXCheckBox checkBoxSms;
	@FXML private JFXButton btnSave;
	
	public templateController() {
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/transfer.fxml"));

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
		//remove these and give characters not allowed alert
		HelperMethods.allowOnlyLetters(txtNume);
		HelperMethods.allowOnlyLetters(txtPrenume);
		HelperMethods.allowOnlyLetters(txtSc);
	}
	
	@FXML
	private void save() {
		if (HelperMethods.checkEmptyText(txtNume.getText())) {
			HelperMethods.includeFieldsForErros(txtNume, "Nu ati introdus numele beneficiarului.", btnSave);
			return;
		}
		if (HelperMethods.checkEmptyText(txtPrenume.getText())) {
			HelperMethods.includeFieldsForErros(txtPrenume, "Nu ati introdus prenumele beneficiarului.", btnSave);
			return;
		}
		if (HelperMethods.checkEmptyText(txt1.getText())) {
			HelperMethods.includeFieldsForErros(txt1, "Nu ati completat corect adresa beneficiar (prea putine caractere).", btnSave);
			return;
		}
		System.out.println("success");
	}
	
	@FXML
	private void cancel() {
		HelperMethods.closeStage(btnSave);
	}

}
