package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Find implements Initializable {
    @FXML
    public TextField FindTxt;
    private TextArea MyArea;
    private Stage Find;
    private Boolean direction = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //Get Text Area Text Field
    public void GetData(TextArea Area, Stage stage){
        this.MyArea = Area;
        Find = stage;
    }

    //Find Function
    public int[] find(int selection){
        return direction?Down(selection):Up(selection);
    }

    //Find Window Listener
    public void FindAction(MouseEvent me) {
        Button Findbtn = (Button) me.getSource();
        if(Findbtn.getText().equals("Find") && !FindTxt.getText().isEmpty()) {
            int[] index = find(MyArea.getSelection().getStart());
            if(index.length>0)
                MyArea.selectRange(index[0],index[1]);
            else{
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Warning!");
                alert.setContentText("Canno't Find \""+FindTxt.getText()+"\"");
                ButtonType okButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(okButton);
                alert.showAndWait();
            }
        }
        if(Findbtn.getText().equals("Cancel"))
            Find.close();
    }

    //Direction Radio Action
    public void Direction(ActionEvent ae) {
        RadioButton dir = (RadioButton) ae.getSource();
        direction = dir.getText().equals("Down");
    }

    //Down
    private int[] Down(int start){
        String Area = MyArea.getText().toLowerCase(), find = FindTxt.getText().toLowerCase();
        int findex=start+1,sindex;
        boolean equal = true;
        while(findex < Area.length()){
            if(Area.charAt(findex)==find.charAt(0)) {
                sindex = findex;
                for (int i = 0; i < find.length(); i++) {
                    if(Area.charAt(sindex++)==find.charAt(i))
                        continue;
                    equal = false;
                }
                if(equal) return new int[]{ findex , sindex};
                equal = true;
            }
            findex++;
        }
        return new int[]{};
    }

    //Up
    private int[] Up(int start){
        String Area = MyArea.getText().toLowerCase(), find = FindTxt.getText().toLowerCase();
        int findex = start-1,sindex;
        boolean equal = true;

        while(findex > -1){
            if(Area.charAt(findex)==find.charAt(0)) {
                sindex = findex;
                for (int i = 0; i < find.length(); i++) {
                    if(Area.charAt(sindex++)==find.charAt(i))
                        continue;
                    equal = false;
                }
                if(equal) return new int[]{ findex , sindex};
                equal = true;
            }
            findex--;
        }
        return new int[]{};
    }

}
