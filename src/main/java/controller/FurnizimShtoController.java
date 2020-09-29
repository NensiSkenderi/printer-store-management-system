package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Bojra;
import model.Furnizim;
import model.Inventari;
import model.Klient;
import utils.Combo;
import utils.HelperMethods;

public class FurnizimShtoController implements Initializable{

	@FXML private JFXTextField txtSasia,txtCmimi;
	@FXML private JFXButton btnAnullo;
	@FXML private Label lblError;
	@FXML private JFXComboBox<String> cmbEmriBojes;
	
	private int furnizimId = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperMethods.makeTextfieldDecimal(txtCmimi);
		HelperMethods.makeTextfieldDecimal(txtSasia);
		
		try {
			Combo.populate_combo(cmbEmriBojes, ControlDAO.getControlDao().getBojraDao().getEmriBojes());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(FurnizimController.edit == true) {
			furnizimId = FurnizimController.furnizimDataHolder.getId();
			getData(FurnizimController.furnizimDataHolder);
		}
		else
			furnizimId = 0;
	}

	private void getData(Furnizim f) {
		cmbEmriBojes.setValue(f.getBojra_id().getEmri());
		txtSasia.setText(f.getSasia() + "");
		txtCmimi.setText(f.getCmimi() + "");
	}
	
	@FXML
	public void ruaj() throws Exception {
		if(HelperMethods.checkEmptyText(txtSasia.getText(), txtCmimi.getText()) || HelperMethods.checkEmptyCombobox(cmbEmriBojes)) {
			lblError.setText("Ploteso fushat!*");
			return;
		}
		
		Bojra bojra = new Bojra();
		bojra.setEmri(cmbEmriBojes.getValue());
		bojra.setId(ControlDAO.getControlDao().getFurnizimDao().getIdFromBoja(cmbEmriBojes.getValue()));
		
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
			if(ControlDAO.getControlDao().getFurnizimDao().checkBoja(bojra.getId()))
				ControlDAO.getControlDao().getInventariDao().updateGjendje(inventari);
			else
				ControlDAO.getControlDao().getInventariDao().addGjendje(inventari);
		}
		else {
			ControlDAO.getControlDao().getFurnizimDao().updateFurnizim(furnizim);
			ControlDAO.getControlDao().getInventariDao().updateGjendje(inventari);
		}
		
		HelperMethods.closeStage(btnAnullo);
		
	}
	
	@FXML
	private void anullo() {
		HelperMethods.closeStage(btnAnullo);
	}
}
