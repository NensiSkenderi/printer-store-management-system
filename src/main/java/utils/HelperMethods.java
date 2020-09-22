package utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

public class HelperMethods {

	public static void closeStage(JFXButton button) {
		Stage stage = (Stage) button.getScene().getWindow();
		stage.close();
	}

	public static void deleteAlert(JFXAlert alert,String entity,Button konfirmo) {
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setOverlayClose(false);
		JFXDialogLayout layout = new JFXDialogLayout();
		ImageView img = new ImageView(new Image("/images/warning.png"));
		Label warning = new Label("Warning! ");
		warning.setStyle("-fx-text-fill: #4186CE;-fx-cursor: hand;");
		HBox.setMargin(warning, new Insets(5, 0, 0, 0));
		HBox hbox = new HBox();
		hbox.getChildren().addAll(img, warning);
		layout.setHeading(hbox);
		layout.setBody(new Label(entity));
		layout.setActions(konfirmo);
		alert.setContent(layout);
		alert.show();
	}

	private static int i = 0;
	
	public static void convertDatepicker(JFXDatePicker... datePicker) {

		for(i=0;i<datePicker.length;i++) {
			datePicker[i].setConverter(new StringConverter<LocalDate>() {
				String pattern = "dd/MM/yyyy";
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
				{
					datePicker[i].setPromptText(pattern.toLowerCase());
				}

				@Override public String toString(LocalDate date) {
					if (date != null) {
						return dateFormatter.format(date);
					} else {
						return "";
					}
				}

				@Override public LocalDate fromString(String string) {
					if (string != null && !string.isEmpty()) {
						return LocalDate.parse(string, dateFormatter);
					} else {
						return null;
					}
				}
			});
		}
	}
	public static void style_delete_button(JFXButton btn_delete) {
		btn_delete.setMaxWidth(20);
		btn_delete.setCursor(Cursor.HAND);
		btn_delete.getStyleClass().add("delete");
	}

	public void openOpenEditScene(VBox vbox,String view_name, String icon) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+view_name+".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT); 
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

	public static void alerti(String title, String text, AlertType type) {
		Alert alert = new Alert (type,text);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public static boolean checkEmptyCombobox(JFXComboBox<?>... combos ) {
		for (JFXComboBox<?> s : combos)
			if (s.getValue() == null || s.getValue() == "") 
				return true;

		return false;
	}

	public static boolean checkEmptyText(String... strings) {
		for(String s : strings)
			if(s == null || s.isEmpty())
				return true;

		return false;
	}

	public static void make_textfield_decimal(JFXTextField txt) {
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,10}([,.]\\d{0,3})?")) {
					txt.setText(oldValue);
				}
			}
		});
	}

	public static void refresh_focus_table(TableView<?> tbl) {
		tbl.refresh();
		tbl.requestFocus();
	}

	public static void errorCheck(String message, JFXButton btn, TextField txt) {
		JFXAlert alert = new JFXAlert((Stage) btn.getScene().getWindow());
		JFXButton ok = new JFXButton("Ok");
		ok.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
		HelperMethods.deleteAlert(alert,message, ok);
		ok.setOnAction( e1 -> {
			txt.setStyle(null);
			txt.requestFocus();
			alert.close();
		});
	}
	
	public static void allowOnlyLetters(TextField textField) {
		textField.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.matches("\\sa-zA-Z*")) {
	            textField.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
	        }
	    });
	}
	
	public static void includeFieldsForErros(TextField txt, String message, JFXButton btn) {
		txt.setStyle("-fx-border-width : 3px; -fx-border-color: red");
		errorCheck(message, btn, txt);		
	}
}
