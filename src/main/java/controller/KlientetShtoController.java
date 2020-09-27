package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Klient;
import utils.HelperMethods;

public class KlientetShtoController implements Initializable{

	@FXML private JFXTextField txtKlienti,txtNIPT,txtKontakt;
	@FXML private JFXButton btnAnullo;
	@FXML private Label lblError;
	
	private int klientId = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(KlientetController.edit == true) {
			klientId = KlientetController.klientDataHolder.getId();
			getData(KlientetController.klientDataHolder);
		}
		else
			klientId = 0;
	}

	private void getData(Klient k) {
		txtKlienti.setText(k.getKlienti());
		txtNIPT.setText(k.getNipt());
		txtKontakt.setText(k.getKontakt());
	}
	
	@FXML
	public void ruaj() throws Exception {
		if(HelperMethods.checkEmptyText(txtKlienti.getText())) {
			lblError.setText("Ploteso fushat!*");
			return;
		}
		
		Klient klient = new Klient();
		klient.setId(klientId);
		klient.setKlienti(txtKlienti.getText());
		klient.setNipt(txtNIPT.getText());
		klient.setKontakt(txtKontakt.getText());
		
		if(klientId == 0)
			ControlDAO.getControlDao().getKlientDao().addKlient(klient);
		else
			ControlDAO.getControlDao().getKlientDao().updateKlient(klient);
		
		HelperMethods.closeStage(btnAnullo);
		
	}
	
	@FXML
	private void anullo() {
		HelperMethods.closeStage(btnAnullo);
	}
}
