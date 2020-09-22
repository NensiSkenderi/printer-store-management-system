package utils;

import controller.templateController;

import javafx.scene.layout.VBox;

public class Controllers {

	private static templateController templateController;

	public static void getTransfer(VBox box) {
		templateController = new templateController() ;
		config(box, templateController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
	}
	
}
