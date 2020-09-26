package utils;

import java.io.IOException;
import java.time.LocalDate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Utils {

	public static String key = "JohnJohnJohnJohn",initVector = "RandomInitVector"; 
	public static int idP = 0;
	public static int i=0, lastAgencyId = 0;
	public static String rights = "", username = "", name = "";

	public static void alert_fshirje(JFXAlert alert,String entity,Button konfirmo,JFXButton anullo, boolean overlay, String imagePath) {
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.setOverlayClose(overlay);
		JFXDialogLayout layout = new JFXDialogLayout();
		
		if(overlay == false) {
			layout.setBody(new Label("Deshironi te fshini "+entity));
			layout.setActions(konfirmo,anullo);
		}
		else {
			ImageView img = new ImageView(new Image(imagePath));
			img.setFitWidth(700);
			img.setFitHeight(700);
			layout.setBody(img);
		}
		alert.setContent(layout);
		alert.show();
	}
	
	public static void alert_pagese(JFXAlert alert, String entity, JFXButton confirm, JFXButton cancel) {
		alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        ImageView img = new ImageView(new Image("/images/pay.png"));
        layout.setHeading(img);
        layout.setBody(new Label(entity));
        layout.setActions(confirm,cancel);
        alert.setContent(layout);
        alert.show();
   }
	
	
	public static void style_delete_button(Button btn_delete) {
		btn_delete.setMaxWidth(30);
		btn_delete.setCursor(Cursor.HAND);
		btn_delete.getStyleClass().add("delete");
	}
	
	public static void style_paguaj_button(Button paguaj) {
		paguaj.setMaxWidth(30);
		paguaj.setCursor(Cursor.HAND);
		paguaj.getStyleClass().add("paguaj");
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
	public void open_edit_scene(String view_name, String icon) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+view_name+".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT); 
		stage.getIcons().add(new Image("/images/"+icon+".png"));
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}
	public static boolean check_empty_text(String... strings) {
		for(String s : strings)
			if(s == null || s.isEmpty())
				return true;

		return false;
	}

	public static void addTextLimiterPassword(final JFXPasswordField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}

			}
		});
	}

	public static void addTextLimiterText(final JFXTextField tf, final int maxLength) {
		tf.textProperty().addListener(new ChangeListener<String>() {
			public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
				if (tf.getText().length() > maxLength) {
					String s = tf.getText().substring(0, maxLength);
					tf.setText(s);
				}

			}
		});
	}

	public static void refresh_focus_table(TableView<?> tbl) {
		tbl.refresh();
		tbl.requestFocus();
	}

	public static void setLocalDateNow(JFXDatePicker... datePicker) {
		for(i=0;i<datePicker.length;i++) {
			datePicker[i].setValue(LocalDate.now());
		}
	}

	public static void close_stage(JFXButton button) {
		Stage stage = (Stage) button.getScene().getWindow();
		stage.close();
	}

	public void openScene(String view_name) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+view_name+".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
	}

	public void openSplash(String view_name) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+view_name+".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

	public void openSceneNoResizable(String view_name, String title, String icon_path) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+view_name+".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT); 
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		stage.setMaximized(false);
		stage.setResizable(false);
		stage.getIcons().add(new Image(icon_path));
		stage.setTitle(title);
		stage.showAndWait();
	}

	public static boolean check_empty_combobox(ComboBox<?>... combos ) {

		for (ComboBox<?> s : combos)
			if (s.getValue() == null || s.getValue() == "") 
				return true;

		return false;
	}


	public static boolean check_checkbox(JFXCheckBox c) {
		if (!c.isSelected()) 
			return true;

		return false;
	}

	public static void alerti(String title, String text, AlertType type) {
		Alert alert = new Alert (type,text);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public static boolean check_datePicker(JFXDatePicker d) {
		if (d.getValue() == null) 
			return true;

		return false;
	}
	
	public static boolean check_datePickers(JFXDatePicker d, JFXDatePicker f) {
		if (d.getValue() == null  || f.getValue() == null ) 
			return true;

		return false;
	}



	public static void make_textfield_integer(TextField txt) {
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}?")) {
					txt.setText(oldValue);
				}
			}
		});
	}
	
	public static void make_jfxtextfield_integer(JFXTextField txt) {
		txt.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,7}?")) {
					txt.setText(oldValue);
				}
			}
		});
	}

	public void open_viewButton(String view_name, Button btn, String icon_path, String title) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+ view_name +".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		Stage old_stage = (Stage)btn.getScene().getWindow();
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setMaximized(true);
		old_stage.close();
		stage.getIcons().add(new Image(icon_path));
		stage.setTitle(title);
		stage.setOnCloseRequest( event -> {
			System.exit(0);
		});
		stage.show();
	}

	public void openViewModal(String view_name, Button btn, String icon_path, String title) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+ view_name +".fxml")); 
		Parent root=(Parent)loader.load();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setMaximized(true);
		stage.setTitle(title);
		stage.getIcons().add(new Image(icon_path));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();
		stage.setTitle(title);
	}
}
