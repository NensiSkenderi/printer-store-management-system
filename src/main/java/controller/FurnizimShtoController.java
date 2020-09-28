package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Bojra;
import model.Furnizim;
import model.Inventari;
import model.Klient;
import utils.HelperMethods;

public class FurnizimShtoController implements Initializable{

	@FXML private JFXTextField txtEmriBojes,txtSasia,txtCmimi;
	@FXML private JFXButton btnAnullo;
	@FXML private Label lblError;
	
	private int furnizimId = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperMethods.makeTextfieldDecimal(txtCmimi);
		HelperMethods.makeTextfieldDecimal(txtSasia);
		
		if(FurnizimController.edit == true) {
			furnizimId = FurnizimController.furnizimDataHolder.getId();
			getData(FurnizimController.furnizimDataHolder);
		}
		else
			furnizimId = 0;
	}

	private void getData(Furnizim f) {
		txtEmriBojes.setText(f.getBojra_id().getEmri());
		txtSasia.setText(f.getSasia() + "");
		txtCmimi.setText(f.getCmimi() + "");
	}
	
	@FXML
	public void ruaj() throws Exception {
		if(HelperMethods.checkEmptyText(txtEmriBojes.getText(), txtSasia.getText(), txtCmimi.getText())) {
			lblError.setText("Ploteso fushat!*");
			return;
		}
		
		Bojra bojra = new Bojra();
		bojra.setEmri(txtEmriBojes.getText());
		bojra.setId(ControlDAO.getControlDao().getFurnizimDao().getIdFromBoja(txtEmriBojes.getText()));
		
		Furnizim furnizim = new Furnizim();
		furnizim.setId(furnizimId);
		furnizim.setBojra_id(bojra);
		furnizim.setCmimi(Double.parseDouble(txtCmimi.getText()));
		furnizim.setSasia(Double.parseDouble(txtSasia.getText()));
		
		Inventari inventari = new Inventari();
		inventari.setBojra_id(bojra);
		inventari.setGjendja(furnizim.getSasia());
		
		if(furnizimId == 0) {
			ControlDAO.getControlDao().getFurnizimDao().addFurnizim(furnizim);
			ControlDAO.getControlDao().getInventariDao().addGjendje(inventari);
		}
		else {
			ControlDAO.getControlDao().getFurnizimDao().updateFurnizim(furnizim);
		}
		
		HelperMethods.closeStage(btnAnullo);
		
	}
	
	@FXML
	private void anullo() {
		HelperMethods.closeStage(btnAnullo);
	}
}
