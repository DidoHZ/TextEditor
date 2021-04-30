package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Functions implements Initializable {

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
}