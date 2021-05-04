package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import org.controlsfx.dialog.FontSelectorDialog;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller extends Functions implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    MainStage = Main.GetStage();
    MainStage.setOnCloseRequest(e -> {
        if(edited) if(UnSavedAlert(false)) e.consume();
    });
    }

    public void FileBar(ActionEvent ae) throws IOException {
        char filetype = ((MenuItem) ae.getSource()).getText().charAt(0);

        //reset
        if(filetype == 'N'){
            NewFileFileBar(); return;
        }
        //Open New file or create new file
        if(filetype == 'O') {
            OpenFileFileBar(); return;
        }
        //Save new file or existing file
        if(filetype == 'S'){
            SaveFileFileBar(); return;
        }
        //Exit
        if(filetype == 'C'){
            if(edited) if(UnSavedAlert(false)) return;
            MainStage.close();
        }
    }

    //Check if filed edited + Save && open listener
    public void verify(KeyEvent event) throws IOException {
        //Ctrl+N
        if(event.getCode() == KeyCode.N && event.isControlDown()){
            NewFileFileBar(); return;
        }
        //Ctrl+O
        if(event.getCode() == KeyCode.O && event.isControlDown()){
            OpenFileFileBar(); return;
        }
        //Ctrl+S
        if (event.getCode() == KeyCode.S && event.isControlDown()){
            if(FileName!=null && edited) save(); else if(FileName==null && edited) saveas(); return;
        }
        //Text area edited
        if(!event.isControlDown()) {
            edited = true;
            String [] Title = MainStage.getTitle().split(" - ");
            if(!Arrays.toString(Title).contains("*"))
                MainStage.setTitle(Title[0]+"* - "+Title[1]);
        }
        if(FileName==null && Text_fld.getText().isEmpty()) { SetAppTitle(); edited = false; }
    }

    //Word Wrap Action
    public void WWrap(ActionEvent ae) {
        CheckMenuItem wrap = (CheckMenuItem) ae.getSource();
        Text_fld.setWrapText(wrap.isSelected());
    }

    //Fond Action
    public void Font() {
        FontSelectorDialog dialog = new FontSelectorDialog(null);
        dialog.setTitle("Choose font from list");
        Optional<Font> selectedfont = dialog.showAndWait();
        selectedfont.ifPresent(font -> Text_fld.setFont(font));
    }

    //Edit Bar Action
    public void EditBar(ActionEvent ae) throws IOException {
        MenuItem EditType = (MenuItem) ae.getSource();
        if(EditType.getText().contains("Un"))
            Text_fld.undo();
        if(EditType.getText().contains("Cu"))
            Text_fld.cut();
        if(EditType.getText().contains("Co"))
            Text_fld.copy();
        if(EditType.getText().contains("Pa"))
            Text_fld.paste();
        if(EditType.getText().contains("De"))
            Text_fld.deleteText(Text_fld.getSelection());
        if(EditType.getText().contains("Fi"))
            FindEditBar();
    }
}