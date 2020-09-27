package utils;

import controller.ShitjetController;
import controller.BojraController;
import controller.FurnizimController;
import controller.KlientetController;

import javafx.scene.layout.VBox;

public class Controllers {

	private static KlientetController klientetController;
	private static ShitjetController shitjetController;
	private static BojraController bojraController;
	private static FurnizimController furnizimController;
	
	public static void getKlientet(VBox box) {
		klientetController = new KlientetController() ;
		config(box, klientetController);
	}
	
	public static void getShitjet(VBox box) {
		shitjetController = new ShitjetController() ;
		config(box, shitjetController);
	}
	
	public static void getBojra(VBox box) {
		bojraController = new BojraController() ;
		config(box, bojraController);
	}
	
	public static void getFurnizim(VBox box) {
		furnizimController = new FurnizimController() ;
		config(box, furnizimController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
	}

	
}
