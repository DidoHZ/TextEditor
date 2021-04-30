package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextArea;
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
import java.util.Optional;
import java.util.Scanner;

public class Functions {
    public TextArea Text_fld;
    public BorderPane BPane;
    public File file ;
    public FileChooser filec = new FileChooser();
    public String FilePath=null, FileName=null;
    public Boolean edited=false;

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
        filec.setInitialFileName("*");
        filec.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Documents","*.txt"),new FileChooser.ExtensionFilter("All Files","*.*"));

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
        FileWriter filew = new FileWriter(FilePath);
        filew.write(Text_fld.getText());
        filew.close();
        edited = false;
    }

    //Reset
    public void SetNewFile(){
        FileName = null;
        FilePath = null;
        edited = false;
        Text_fld.setText("");
    }

    //Check if filed edited + Save && open listener
    public void verify(KeyEvent event) throws IOException {
        System.out.println(event.getCode()+" == "+ KeyCode.S+" && "+ event.isControlDown());
        if (event.getCode() == KeyCode.S && event.isControlDown()){
            System.out.println("Ctrl+S");
            if(FileName!=null && edited){save();}else{saveas();}
            return;
        }
        edited = true;
    }

    //message for unsaved files
    public void UnSavedMessage(Boolean Message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Text Editor");
        if(FilePath!=null)
            alert.setContentText("Do you want so save changes to\n"+FilePath+"?");
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

    //Word Wrapper
    public void WWrap(ActionEvent ae) {
        CheckMenuItem wrap = (CheckMenuItem) ae.getSource();
        Text_fld.setWrapText(wrap.isSelected());
    }

    //Fond Dialog
    public void Font() {
        FontSelectorDialog dialog = new FontSelectorDialog(null);
        dialog.setTitle("Choose font from list");
        Optional<Font> selectedfont = dialog.showAndWait();
        selectedfont.ifPresent(font -> Text_fld.setFont(font));
    }
}
