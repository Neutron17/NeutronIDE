package neutron.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import neutron.io.Reader;
import neutron.io.Writer;
import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private String OS = System.getProperty("os.name").toLowerCase();
    boolean IS_WINDOWS = OS.contains("win");
    boolean IS_MAC = OS.contains("mac");
    boolean IS_UNIX = OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0;
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    @FXML
    MenuBar menuBar = new MenuBar();
    @FXML
    TextArea input = new TextArea();
    @FXML
    Label outLabel = new Label();
    @FXML
    TextArea outField = new TextArea();
    @FXML
    TreeView<String> filesTreeView = new TreeView<String>();

    @FXML
    public void handleClose() {
        System.exit(0);
    }
    @FXML
    public void handleRun() {
        cmd(input.getText());
    }
    @FXML
    public void handleLogin() {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/login.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleClear() { outField.clear(); }

    public void cmd(@NotNull String command) {
        Writer.bufferedWriter("",System.getProperty("user.dir")+"\\test.c");
        try {
//            Pattern pattern = Pattern.compile(".[\"]");
//            Matcher matcher = pattern.matcher(input.getText());
//            ProcessBuilder builder = new ProcessBuilder();
            if (IS_WINDOWS) {

                for (int i = 0;i<input.getText().split("\n").length;i++){
                    Writer.withoutOverwrite(input.getText().split("\n")[i]+"\n", System.getProperty("user.dir")+"\\test.c");
                }
                ArrayList<String> list = Reader.read(System.getProperty("user.dir")+"\\test.c");
//                for (int i = 0;i<list.size();i++) {
//                    System.out.println(list.get(i));
//                }
                run("cmd.exe","/c","gcc test.c");
                run("cmd.exe","/c",".\\a.exe");
            }else if(IS_MAC) {
              //  builder.command("/bin/bash","-c","ls");
            }else if(IS_UNIX) {
               // builder.command("/bin/bash","-c",command);
            }else{
                Stage stage = new Stage();
                Label text = new Label("Error: incompatible OS");
                text.setStyle("-fx-text-fill: Red;");
                stage.setScene(new Scene(new Pane(text)));
                stage.show();
            }
//            builder.redirectErrorStream(true);
//            Process p = builder.start();
//            InputStreamReader inputStreamRead = new InputStreamReader(p.getInputStream());
//            BufferedReader buff = new BufferedReader(inputStreamRead);
//            String line = null;
//            while (true) {
//                line = buff.readLine();
//                if (line == null) {
//                    break;
//                }
//                outField.setText(outField.getText() + line + "\n");
//                System.out.println(line);
//            }
//            p.waitFor();
//            buff.close();
//            p.destroy();
        }catch (Exception e) {
            System.err.println("cmd");
            e.printStackTrace();
        }
    }
    public void run(@NotNull String... arg) {
        try {
            ProcessBuilder builder = new ProcessBuilder(arg);
            Process p = builder.start();
            InputStreamReader inputStreamRead = new InputStreamReader(p.getInputStream());
            BufferedReader buff = new BufferedReader(inputStreamRead);
            String line = null;
            while (true) {
                line = buff.readLine();
                if (line == null) {
                    break;
                }
                //outField.setText(outField.getText() + line + "\n");
                System.out.println(line);
            }
            p.waitFor();
            buff.close();
            p.destroyForcibly();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filesTreeView.setEditable(false);
        TreeItem<String> root = new TreeItem<>(new File(".").getName());
        root.setExpanded(true);
        direct(root);
        filesTreeView.setRoot(root);
    }
    void direct(TreeItem<String> root) {
        for(File i : Objects.requireNonNull(new File(".").listFiles())) {
            TreeItem<String> current = new TreeItem<String>(i.getName());
            root.getChildren().add(current);
            if(i.isDirectory()) {
                current.setGraphic(new ImageView(new Image("/images/empty_directory.png"))); // TODO Icon made by https://www.flaticon.com/authors/dinosoftlabs from @flaticon
                while(i.listFiles() != null) {

                }
            }else {
                // TODO Different images for file extensions
                ImageView fileIcon = new ImageView(new Image("/images/file.png"));
                //fileIcon.setFitHeight(20);
                //fileIcon.setFitWidth(20);
                current.setGraphic(fileIcon);
            }
        }
    }
}