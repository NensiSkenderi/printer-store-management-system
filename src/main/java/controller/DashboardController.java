package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import utils.Controllers;

public class DashboardController implements Initializable {
	
	
	@FXML
	private VBox mainVbox;

	public void initialize(URL location, ResourceBundle resources) {
		
	}

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
	
	@FXML
	public void dil() {
		Platform.exit();
	}
}
