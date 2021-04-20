package neutron.controller;

import com.sun.source.tree.Tree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import neutron.io.Reader;
import neutron.io.Writer;
import org.jetbrains.annotations.NotNull;

import javax.naming.PartialResultException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final String OS = System.getProperty("os.name").toLowerCase();
    public final boolean IS_WINDOWS = OS.contains("win");
    public final boolean IS_MAC = OS.contains("mac");
    public final boolean IS_UNIX = OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0;

    @FXML MenuBar menuBar = new MenuBar();
    @FXML TextArea input = new TextArea();
    @FXML Label outLabel = new Label();
    @FXML TextArea outField = new TextArea();
    @FXML TreeView<String> filesTreeView = new TreeView<>();

    @FXML public void handleClose() { System.exit(0); }
    @FXML public void handleRun() { cmd(input.getText()); }
    @FXML public void handleLogin() {
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
    @FXML public void handleClear() { outField.clear(); }

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
    @Override public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");
        filesTreeView.setEditable(false);
        System.out.println("init");
        TreeItem<String> root = new TreeItem<>(new File(".").getName());
        System.out.println("init");
        root.setExpanded(true);
        System.out.println("init");
        direct(root);
        System.out.println("init");
        filesTreeView.setRoot(root);
        System.out.println("init");
    }
    public ArrayList<File> direct(TreeItem<String> root) {
        ArrayList<File> files = new ArrayList<>();
        for(File i : Objects.requireNonNull(new File(".").listFiles())) {
            files.add(i);
            TreeItem<String> current = new TreeItem<String>(i.getName());
            root.getChildren().add(current);
            iff:if(i.isDirectory()) {
                ImageView view = new ImageView(new Image("/images/empty_directory.png"));
                view.setFitWidth(20);
                view.setFitHeight(20);
                current.setGraphic(view); // TODO Icon made by https://www.flaticon.com/authors/dinosoftlabs from @flaticon
                if(i.listFiles() == null) break iff;
//                for(File x : Objects.requireNonNull(i.listFiles())) {
//                    current.getChildren().add(new TreeItem<>(x.getName()));
//
//                }
                foo(current, i.listFiles());
            }else if(i.isFile()){
                Image img = new Image("/images/file.png");
                i.getName().split("^[A-Za-z0-9]");
                // TODO Different images for file extensions
                ImageView fileIcon = new ImageView(img);
                fileIcon.setFitHeight(20);
                fileIcon.setFitWidth(20);
                current.setGraphic(fileIcon);
            }
        }
        return files;
    }
    void foo(TreeItem<String> root,File[] arr) {
        for(File i : arr) {
            root.getChildren().add(new TreeItem<>(i.getName()));
        }
    }
    public ArrayList<String> direct(String path) {
        ArrayList<String> files = new ArrayList<>();
        if(new File(path).isDirectory() && new File(path).listFiles() != null) {
            System.err.println("be-for");
            for (File i : new File(path).listFiles()) {
                System.err.println("for");
                files.add(i.getName());
                if (i.isDirectory()) {
                    System.err.println("dir");
                    //current.setGraphic(new ImageView(new Image("/images/empty_directory.png"))); // TODO Icon made by https://www.flaticon.com/authors/dinosoftlabs from @flaticon
                    if(i.listFiles() != null) {
                        System.err.println("while");
                    }
                } else if (i.isFile()) {
                    System.err.println("file");
                    i.getName().split("^[A-Za-z0-9]");
                }
            }
        }
        return files;
    }
    public ArrayList<File> direct(File directory) throws NotDirectoryException {
        ArrayList<File> files = new ArrayList<>();
        if(!directory.isDirectory()) {
            throw new NotDirectoryException("Parameter directory is not a directory");
        }
            System.err.println("be-for");
            for (File i : directory.listFiles()) {
                System.err.println("for");
                files.add(i);
                if (i.isDirectory()) {
                    System.err.println("dir");
                    //current.setGraphic(new ImageView(new Image("/images/empty_directory.png"))); // TODO Icon made by https://www.flaticon.com/authors/dinosoftlabs from @flaticon
                    if(i.listFiles() != null) {
                        System.err.println("while");
                    }
                } else if (i.isFile()) {
                    System.err.println("file");
                    i.getName().split("^[A-Za-z0-9]");
                }
            }
        return files;
    }
}