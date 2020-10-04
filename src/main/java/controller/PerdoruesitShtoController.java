package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Perdoruesit;
import utils.Utils;

public class PerdoruesitShtoController implements Initializable {

	@FXML
	private JFXTextField txtEmri, txtMbiemri, txtUsername, txtTelefon, txtEmail;
	
	@FXML
	private ComboBox<String> cmbTeDrejtat;
	
	@FXML
	private Label lblError;
	
	@FXML
	private JFXButton btnAnullo;
	
	
	private int userid = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbTeDrejtat.getItems().setAll("Admin", "User");
		
		if(PerdoruesitController.edit == true) {
			userid = PerdoruesitController.PerdoruesitDataHolder.getUserid();
			txtUsername.setText(PerdoruesitController.PerdoruesitDataHolder.getUsername());
			txtEmri.setText(PerdoruesitController.PerdoruesitDataHolder.getName());
			txtMbiemri.setText(PerdoruesitController.PerdoruesitDataHolder.getSurname());
			txtTelefon.setText(PerdoruesitController.PerdoruesitDataHolder.getTelefon());
			txtEmail.setText(PerdoruesitController.PerdoruesitDataHolder.getEmail()+ "");
			cmbTeDrejtat.setValue(PerdoruesitController.PerdoruesitDataHolder.getAccess());

		}
		else {
			cmbTeDrejtat.getItems().setAll("Admin", "User");
			userid = 0;
		}
		
	}

	@FXML
	public void ruaj() throws SQLException {
		
		if (Utils.check_empty_text(txtUsername.getText(), cmbTeDrejtat.getValue())) {
			lblError.setText("Plotesoni te gjitha fushat e detyruara!*");
			return;
		}
		Perdoruesit p = new Perdoruesit();
		p.setUsername(txtUsername.getText());
		p.setUserid(Utils.idP);
		p.setPassword(txtUsername.getText());
		p.setName(txtEmri.getText());
		p.setSurname(txtMbiemri.getText());
		p.setEmail(txtEmail.getText());
		p.setTelefon(txtTelefon.getText());
		p.setAccess(cmbTeDrejtat.getValue());
		p.setUserid(userid);
		
		if(userid == 0) {
			ControlDAO.getControlDao().getPerdoruesitDao().addPerdorues(p);
		}
		else {
			ControlDAO.getControlDao().getPerdoruesitDao().updatePerdorues(p);
		}

		
		Utils.close_stage(btnAnullo);
		
	}
	
	@FXML
	public void anullo() {
		Utils.close_stage(btnAnullo);
	}
}
