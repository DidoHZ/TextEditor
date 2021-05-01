package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    static final String AppName = "TextEditor";
    static final String NewFileName = "New File";
    static final String NewAppName = NewFileName + " - " + AppName;

    private static Stage MainStage;

    static Stage GetStage(){
        return MainStage;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        MainStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        MainStage.setTitle(NewAppName);
        Scene scene = new Scene(root, 600, 500);
        MainStage.setScene(scene);
        MainStage.getIcons().add(new Image("\\images\\images.png"));
        MainStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
