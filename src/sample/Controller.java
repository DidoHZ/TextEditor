package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.controlsfx.dialog.FontSelectorDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {
    public TextArea Text_fld;
    public BorderPane BPane;
    public String FilePath=null, FileName=null;
    public Boolean edited=false;
    public File file ;
    public FileChooser filec = new FileChooser();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void FileBar(ActionEvent ae) throws IOException {
        MenuItem mi = (MenuItem) ae.getSource();
        String filetype = mi.getText();
        System.out.println(filetype);

        //reset
        if(filetype.equals("New File")){
            if(edited)
                UnSavedMessage(false);
            SetNewFile();
            return;
        }

        //Open New file or create new file
        if(filetype.equals("Open")) {
            //open new file while file saved
            if((FileName==null && !edited) || (FileName!=null && !edited)) {
                open();
                return;
            }
            // open new file while file not saved
            if(FileName == null) {
                saveas();
                return;
            }
            //save opened file (need show message))
            UnSavedMessage(true);
        }

        //Save new file or existing file
        if(filetype.equals("Save")){
            //save opened file
            if(FileName!=null) {
                save();
                return;
            }
            //save new file
            saveas();
        }

        //Exit
        if(filetype.equals("Close")){
            Platform.exit();
        }
    }

    //Open file
    public void open() throws FileNotFoundException {
        Window stage = BPane.getScene().getWindow();
        filec.setTitle("Open File");
        filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file","*.txt"));

        if((file = filec.showOpenDialog(stage)) != null ) {
            filec.setInitialDirectory(file.getParentFile());
            SetNewFile();
            FileName = file.getName();
            FilePath = file.getPath();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
                Text_fld.appendText(scanner.nextLine() + "\n");
        }
    }

    //Save file as
    public void saveas() throws IOException {
        Window stage = BPane.getScene().getWindow();
        filec.setTitle("Save File");
        filec.setInitialFileName("file");
        filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("text file","*.txt"));

        if((file= filec.showSaveDialog(stage)) != null ) {
            filec.setInitialDirectory(file.getParentFile());
            FileName = file.getName();
            FilePath = file.getPath();
            FileWriter filew = new FileWriter(file.getPath());
            filew.write(Text_fld.getText());
            filew.close();
            edited = false;
        }
    }

    //Save existing file
    public void save() throws IOException {
        FileWriter filew = new FileWriter(file.getPath());
        filew.write(Text_fld.getText());
        filew.close();
        edited = false;
    }

    public void SetNewFile(){
        FileName = null;
        FilePath = null;
        edited = false;
        Text_fld.setText("");
    }

    public void verify(KeyEvent event) throws IOException {
        System.out.println(event.getCode()+" == "+KeyCode.S+" && "+ event.isControlDown());
        if (event.getCode() == KeyCode.S && event.isControlDown()){
            System.out.println("Ctrl+S");
            if(FileName!=null && edited){save();}else{saveas();}
            return;
        }
        edited = true;
    }

    public void UnSavedMessage(Boolean Message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Text Editor");
        if(FilePath!=null)
            alert.setContentText("Do you want so save changes to\n"+file.getPath()+"?");
        else
            alert.setContentText("Do you want so save changes to New File?");
        ButtonType okButton = new ButtonType("Save"),noButton = new ButtonType("Don't Save"),cancelButton = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
            System.out.println(type.getText());
            try {
                if (type.getText().equals("Save")) {
                    if(Message){
                        save();
                        open();
                    }else
                        saveas();
                } else if (type.getText().equals("Don't Save"))
                    if(Message)
                        open();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public void WWrap(ActionEvent ae) {
        CheckMenuItem wrap = (CheckMenuItem) ae.getSource();
        Text_fld.setWrapText(wrap.isSelected());
    }

    public void Font(ActionEvent actionEvent) {
        FontSelectorDialog dialog = new FontSelectorDialog(null);
        dialog.setTitle("Choose font from list");
        Optional<Font> selectedfont = dialog.showAndWait();
        Text_fld.setFont(selectedfont.get());
    }
}