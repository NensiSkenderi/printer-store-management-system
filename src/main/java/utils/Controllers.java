package utils;

import controller.ShitjetController;
import controller.klientetController;

import javafx.scene.layout.VBox;

public class Controllers {

	private static klientetController klientetController;

	public static void getKlientet(VBox box) {
		klientetController = new klientetController() ;
		config(box, klientetController);
	private static klientetController templateController;
	private static ShitjetController shitjetController;
	
	public static void getTransfer(VBox box) {
		templateController = new klientetController() ;
		config(box, templateController);
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
