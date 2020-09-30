package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Arketuar;
import model.Bojra;
import model.Inventari;
import model.Klient;
import model.Shitje;
import utils.Combo;
import utils.HelperMethods;

public class ShitjetShtoController implements Initializable{

	@FXML private JFXTextField txtLlojiFatures, txtSasia, txtCmimi;
	@FXML private JFXComboBox<String> cmbEmriBojes, cmbKlienti, cmbArketimi;
	@FXML private JFXButton btnAnullo;
	@FXML private Label lblError;
	@FXML private JFXCheckBox checkLikujduar;

	private int shitjeId = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			Combo.populate_combo(cmbArketimi, ControlDAO.getControlDao().getArketimDao().getArketimet());
			Combo.populate_combo(cmbEmriBojes, ControlDAO.getControlDao().getBojraDao().getEmriBojes());
			Combo.populate_combo(cmbKlienti, ControlDAO.getControlDao().getKlientDao().getKlientet());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(ShitjetController.edit == true) {
			shitjeId = ShitjetController.shitjeDataHolder.getId();
			getData(ShitjetController.shitjeDataHolder);
		}
		else
			shitjeId = 0;
	}

	private void getData(Shitje sh) {
		txtLlojiFatures.setText(sh.getLloji_fatures());
		txtSasia.setText(sh.getSasia() + "");
		txtCmimi.setText(sh.getCmimi() + "");
		
		cmbArketimi.setValue(sh.getArketim_id().getMenyra());
		cmbEmriBojes.setValue(sh.getBojra_id().getEmri());
		cmbKlienti.setValue(sh.getKlient_id().getKlienti());
		
	}

	@FXML
	public void ruaj() throws Exception {
		if(HelperMethods.checkEmptyText(txtLlojiFatures.getText(), txtCmimi.getText() , txtSasia.getText()) 
				|| HelperMethods.checkEmptyCombobox(cmbEmriBojes, cmbKlienti)) {
			lblError.setText("Ploteso fushat!*");
			return;
		}
		
		Arketuar arketuar = new Arketuar();
		arketuar.setMenyra(cmbArketimi.getValue());
		arketuar.setId(ControlDAO.getControlDao().getArketimDao().getIdFromArketim(cmbArketimi.getValue()));
		
		Bojra bojra = new Bojra();
		bojra.setEmri(cmbEmriBojes.getValue());
		bojra.setId(ControlDAO.getControlDao().getFurnizimDao().getIdFromBoja(cmbEmriBojes.getValue()));
		
		Klient klient = new Klient();
		klient.setKlienti(cmbKlienti.getValue());
		klient.setId(ControlDAO.getControlDao().getKlientDao().getIdFromKlienti(cmbKlienti.getValue()));
		
		Shitje shitje = new Shitje();
		shitje.setId(shitjeId);
		shitje.setCmimi(Double.parseDouble(txtCmimi.getText()));
		shitje.setSasia(Double.parseDouble(txtSasia.getText()));
		if(checkLikujduar.isSelected())
			shitje.setDate_likujduar(Date.valueOf(LocalDate.now()));
		shitje.setLloji_fatures(txtLlojiFatures.getText());
		shitje.setVlera(shitje.getCmimi() * shitje.getSasia());
		shitje.setArketim_id(arketuar);
		shitje.setBojra_id(bojra);
		shitje.setKlient_id(klient);
		
		if(shitjeId == 0)
			ControlDAO.getControlDao().getShitjeDao().addShitje(shitje);
		else
			ControlDAO.getControlDao().getShitjeDao().updateShitje(shitje);
		
		Inventari inventari = new Inventari();
		inventari.setBojra_id(bojra);
		
		double gjendjaVjeter = ControlDAO.getControlDao().getInventariDao().getGjendja(bojra);
		inventari.setGjendja(gjendjaVjeter - shitje.getSasia());
		
		ControlDAO.getControlDao().getInventariDao().updateGjendje(inventari);
		HelperMethods.closeStage(btnAnullo);

	}

	@FXML
	private void anullo() {
		HelperMethods.closeStage(btnAnullo);
	}
}
