package neutron;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Settings {
    public Settings() {
        try {
            Stage stage = new Stage();
            stage.setWidth(600);
            stage.setHeight(700);
            stage.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
