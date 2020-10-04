package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.Utils;

public class LoginController implements Initializable {

	@FXML private JFXTextField txtUsername;
	@FXML private JFXPasswordField txtPassword;
	@FXML private Label lblError;
	@FXML private Button btnLogin;
	

	public void initialize(URL location, ResourceBundle resources) {
		try {
			ControlDAO.getControlDao().getLoginDao().flush();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	private void checkLogin() throws IOException, SQLException {
		String user = txtUsername.getText();
		String passwd = Utils.encrypt(Utils.key, Utils.initVector, txtPassword.getText());

		if(user.isEmpty() || txtPassword.getText().isEmpty() ) {
			lblError.setText("Ju lutem plotesoni fushat!");	
			return;
		}

	if(ControlDAO.getControlDao().getLoginDao().check_user_and_pass(user,passwd)) {
			
			Stage stage = (Stage) btnLogin.getScene().getWindow();
			stage.close();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
			Parent root = loader.load();
	    	Scene scene = new Scene(root);
	    	Stage primaryStage = new Stage();
	    	primaryStage.setMaximized(true);
	    	primaryStage.setResizable(true);
	    	primaryStage.setScene(scene);
	    	primaryStage.centerOnScreen();
	    	primaryStage.setTitle("Refill Plus");
	    	primaryStage.getIcons().add(new Image("/images/logo_icon.png"));
	    	primaryStage.show();
	    	primaryStage.setOnCloseRequest( e-> System.exit(0));   
	    	
		}
		else {
			lblError.setText("Username ose Passwordi gabim!");	
			return;
		}
		
	}

	
}
