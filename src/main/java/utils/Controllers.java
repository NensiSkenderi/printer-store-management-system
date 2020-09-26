package utils;

import controller.ShitjetController;
import controller.KlientetController;

import javafx.scene.layout.VBox;

public class Controllers {

	private static KlientetController klientetController;
	private static ShitjetController shitjetController;
	
	public static void getKlientet(VBox box) {
		klientetController = new KlientetController() ;
		config(box, klientetController);
	}
	
	public static void getShitjet(VBox box) {
		shitjetController = new ShitjetController() ;
		config(box, shitjetController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
	}
	
}
