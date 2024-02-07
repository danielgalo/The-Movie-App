package utils;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;

/**
 * Clase de utilidades para los controladores
 */
public class ControllerUtils {

	/**
	 * Constructor privado
	 */
	private ControllerUtils() {

	}

	/**
	 * Deshabilita uno o varios text fields
	 * 
	 * @param fields
	 */
	public static void disableTextFields(TextField... fields) {

		for (TextField textField : fields) {
			textField.setDisable(true);
		}

	}

	/**
	 * Agrega efecto de sombra a Panes
	 * 
	 * @param shadow efecto de sombra a agregar
	 * @param panes  Panes aplicar el efecto
	 */
	public static void setShadowPanes(DropShadow shadow, Pane... panes) {
		for (Pane pane : panes) {
			pane.setEffect(shadow);
		}
	}

	/**
	 * Agrega efecto de sombra a TextFields
	 * 
	 * @param shadow efecto de sombra a agregar
	 * @param fields textFields a aplicar el efecto
	 */
	public static void setShadowTxtFields(DropShadow shadow, TextField... fields) {
		for (TextField textField : fields) {
			textField.setEffect(shadow);
		}
	}

	/**
	 * Agrega efecto de sombra a Radio Buttons
	 * 
	 * @param shadow       efecto de sombra a agregar
	 * @param radioButtons buttons a aplicar el efecto
	 */
	public static void setShadowRdbtn(DropShadow shadow, RadioButton... radioButtons) {
		for (RadioButton button : radioButtons) {
			button.setEffect(shadow);
		}
	}

	/**
	 * Agrega efecto de sombra a Buttons
	 * 
	 * @param shadow  efecto de sombra a agregar
	 * @param buttons buttons a aplicar el efecto
	 */
	public static void setShadowButtons(DropShadow shadow, Button... buttons) {
		for (Button button : buttons) {
			button.setEffect(shadow);
		}
	}

	/**
	 * Agrega efecto de sombra a Labels
	 * 
	 * @param shadow efecto de sombra a agregar
	 * @param labels labels a aplicar el efecto
	 */
	public static void setShadowLabels(DropShadow shadow, Label... labels) {
		for (Label label : labels) {
			label.setEffect(shadow);
		}
	}
}
