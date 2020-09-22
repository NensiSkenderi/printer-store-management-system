package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

import com.jfoenix.controls.JFXComboBox;

public class Combo {

    private Combo() {
    }

    public static void populate_combo(JFXComboBox combo, List lista) {
        data(combo, FXCollections.observableArrayList(lista));
    }

    private static void data(JFXComboBox combo, ObservableList dados) {
        if (dados.isEmpty() || dados == null) {
        	pastro(combo);
        } else {
            combo.setItems(dados);
        }
    }

    private static void pastro(JFXComboBox<Object> combo) {
        combo.getItems().clear();
        combo.setPromptText("-- Te dhenat nuk u gjeten --");
    }
}