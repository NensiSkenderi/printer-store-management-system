package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import utils.Controllers;

public class dashboardController implements Initializable {
	
	
	@FXML
	private VBox mainVbox;

	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	public void templateController() {
		Controllers.getTransfer(mainVbox);
	}
	
	@FXML
	public void dil() {
		Platform.exit();
	}
}
