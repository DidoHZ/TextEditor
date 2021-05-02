package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Functions {
    @FXML
    public TextArea Text_fld;
    public Stage MainStage,Find;
    private File file ;
    private final FileChooser filec = new FileChooser();
    public String FilePath=null, FileName=null;
    public Boolean edited=false;

    //Set App Title
    private void SetAppTitle(){
        MainStage.setTitle((file != null?file.getName():Main.NewFileName)+" - "+Main.AppName);
    }

    //New File Action
    public void NewFileFileBar(){
        if(edited){ if(!UnSavedAlert(false)) SetNewFile();}
        else SetNewFile();
    }

    //Open File Action
    public void OpenFileFileBar() throws IOException {
        //open new file while file saved
        if(!edited) {
            open(); return;
        }
        // open new file while file not saved
        UnSavedAlert(true);
    }

    //Save File Action
    public void SaveFileFileBar() throws IOException {
        //save opened file
        if(FileName!=null) {
            save(); return;
        }
        //save new file
        saveas();
    }

    //Open file
    public void open() throws FileNotFoundException {
        filec.setTitle("Open File");
        filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file","*.txt"));

        if((file = filec.showOpenDialog(MainStage.getScene().getWindow()))!=null) {
            filec.setInitialDirectory(file.getParentFile());
            SetNewFile();
            FileName = file.getName();
            FilePath = file.getPath();
            SetAppTitle();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
                Text_fld.appendText(scanner.nextLine() + "\n");
        }
    }

    //Save file as
    public void saveas() throws IOException {
        filec.setTitle("Save As");
        filec.setInitialFileName("*.txt");
        filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Documents","*.txt"),new FileChooser.ExtensionFilter("All Files","*.*"));

        if((file= filec.showSaveDialog(MainStage)) != null ) {
            filec.setInitialDirectory(file.getParentFile());
            FileName = file.getName();
            FilePath = file.getPath();
            SetAppTitle();
            FileWriter filew = new FileWriter(file.getPath());
            filew.write(Text_fld.getText());
            filew.close();
            edited = false;
        }
    }

    //Save existing file
    public void save() throws IOException {
        FileWriter filew = new FileWriter(FilePath);
        filew.write(Text_fld.getText());
        filew.close();
        SetAppTitle();
        edited = false;
    }

    //Reset
    public void SetNewFile(){
        FileName = null;
        FilePath = null;
        edited = false;
        MainStage.setTitle(Main.NewAppName);
        Text_fld.setText("");
    }

    //Find in text Area
    public void FindEditBar() throws IOException {
        Find = new Stage();
        Find.setTitle("Find");
        Find.setResizable(false);
        Find.initOwner(MainStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Find.fxml"));
        Parent root = loader.load();

        Find FindController = loader.getController(); //Get Controller of Find
        FindController.GetData(Text_fld,Find); //Get Data

        //ShowFind Scene in new Stage
        Scene scene = new Scene(root, 350, 100);
        Find.setScene(scene);
        Find.getIcons().add(new Image("\\images\\images.png"));
        Find.show();

    }
    //Alert for unsaved files
    public Boolean UnSavedAlert(Boolean Message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Text Editor");
        alert.setContentText("Do you want so save changes to "+(FilePath!=null? "\n"+FilePath+"?":"new file?"));
        ButtonType okButton = new ButtonType("Save"),noButton = new ButtonType("Don't Save"),cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            try {
                if (type.getText().equals("Save")) {
                    if(Message){
                        if(FilePath == null) saveas(); else save(); open();
                    }else saveas();
                } else if (type.getText().equals("Don't Save"))
                    if(Message)
                        open();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return alert.getResult().getText().equals("Cancel");
    }
}