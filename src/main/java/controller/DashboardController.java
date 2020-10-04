package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utils.Controllers;
import utils.Utils;

public class DashboardController implements Initializable {
	
	
	@FXML private VBox mainVbox, btnHolders;
	
	@FXML private JFXButton btnPerdoruesit, btnShitjet, btnInventari,
	btnFurnizim, btnBojra, btnKlientet, btnNdryshoPasswordin, btnDil;
	
	@FXML private Label lblWelcome;

	@FXML
	public void klientController() {
		Controllers.getKlientet(mainVbox);
	}
	
	public void shitjetController() {
		Controllers.getShitjet(mainVbox);
	}
	
	public void bojraController() {
		Controllers.getBojra(mainVbox);
	}
	
	public void furnizimController() {
		Controllers.getFurnizim(mainVbox);
	}
	
	public void inventariController() {
		Controllers.getInventari(mainVbox);
	}
	
	public void perdoruesitController() {
		Controllers.getPerdoruesit(mainVbox);
	}
	
	@FXML
	public void ndryshoPasswordin() {
		Utils.build_change_password(mainVbox,btnShitjet);
	}
	
	@FXML
	public void dil() {
		Platform.exit();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblWelcome.setText("Pershendetje " + Utils.name + "! " + DateTimeFormatter.ofPattern("dd.MM.YYYY").format(LocalDate.now()));
		if(Utils.rights.contentEquals("User")) {
			btnHolders.getChildren().remove(btnFurnizim);
			btnHolders.getChildren().remove(btnInventari);
			btnHolders.getChildren().remove(btnPerdoruesit);
		}
	}
}
