package utils;

import controller.klientetController;

import javafx.scene.layout.VBox;

public class Controllers {

	private static klientetController templateController;

	public static void getTransfer(VBox box) {
		templateController = new klientetController() ;
		config(box, templateController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
	}
	
}
