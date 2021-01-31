package neutron.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import org.controlsfx.control.textfield.CustomTextField;

public class SettingsController {
    @FXML
    CustomTextField cTextField = new CustomTextField();
    @FXML
    CustomTextField pyTextField = new CustomTextField();
    @FXML
    ColorPicker bgPicker = new ColorPicker();
    @FXML
    Spinner<Integer> fontSpinner = new Spinner<Integer>();


}
