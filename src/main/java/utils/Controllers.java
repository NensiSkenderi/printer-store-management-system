package utils;

import controller.ShitjetController;
import controller.BojraController;
import controller.FurnizimController;
import controller.InventariController;
import controller.KlientetController;
import javafx.scene.CacheHint;
import javafx.scene.layout.VBox;

public class Controllers {

	private static KlientetController klientetController;
	private static ShitjetController shitjetController;
	private static BojraController bojraController;
	private static FurnizimController furnizimController;
	private static InventariController inventariController;
	
	public static void getKlientet(VBox box) {
		klientetController = klientetController == null ? new KlientetController() : klientetController ;
		config(box, klientetController);
	}
	
	public static void getShitjet(VBox box) {
		shitjetController = shitjetController == null ? new ShitjetController() : shitjetController ;
		config(box, shitjetController);
	}
	
	public static void getBojra(VBox box) {
		bojraController = bojraController == null ? new BojraController() : bojraController ;
		config(box, bojraController);
	}
	
	public static void getFurnizim(VBox box) {
		furnizimController = furnizimController == null ? new FurnizimController() : furnizimController ;
		config(box, furnizimController);
	}
	
	public static void getInventari(VBox box) {
		inventariController =  new InventariController();
		config(box, inventariController);
	}
	
	public static void config(VBox box, VBox content) {
		box.getChildren().clear();
		box.getChildren().add(content);
		box.setCache(true);
		box.setCacheShape(true);
		box.setCacheHint(CacheHint.SPEED);
	}

	
}
