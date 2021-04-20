package neutron;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import neutron.parser.Parser;

import java.io.File;

public class Main extends Application {
    public static Stage stage;
    public static Scene scene;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Neutron IDE");
        stage.setMaximized(true);
        Parent root = FXMLLoader.load(getClass().getResource("/editor.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
// --module-path C:\Users\sando\Downloads\javafx-sdk-15.0.1\lib --add-modules=javafx.controls,javafx.fxml

    public static void main(String[] args) {
        try {
//        scene.getStylesheets().add("/css/login.css");
            //Thread t1 = new Thread(new FPS());
            //t1.start();
            //launch(args);
            Parser parser = new Parser(new File("foo.json"));
            parser.write(new Object[] { "bg","color", "botkos","buzi" });
            parser.read();
            System.exit(0);
        }catch (Throwable th) {
            th.printStackTrace();
            System.exit(-1);
        }
    }
}
