package main;
	
import java.io.IOException;
import java.sql.SQLException;

import dao.ControlDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	public Parent root;
	
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {       
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
    	root=(Parent)loader.load();
    	
    	Scene scene = new Scene(root);
    	primaryStage.setResizable(true);
    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Refill Plus");
    	primaryStage.getIcons().add(new Image("/images/logo_icon.png"));
    	//ControlDAO.getControlDao().getTemplateDao().getTest();
    	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}