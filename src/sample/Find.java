package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Find {
    @FXML
    private TextField FindTxt;
    private TextArea MyArea;
    private Stage Find;
    private String Area,find;
    private Boolean direction = true,matchcase=false;

    //Get Text Area Text Field
    public void GetData(TextArea Area, Stage stage){
        this.MyArea = Area; Find = stage;
    }

    //set quel que chose
    private void SetString(){
        Area = matchcase?MyArea.getText()+" ":MyArea.getText().toLowerCase()+" ";
        find = matchcase?FindTxt.getText():FindTxt.getText().toLowerCase();
    }

    //Find Function
    private int[] find(int selection){
        return direction?Down(selection+1):Up(selection-1);
    }

    //Find Button Action
    public void FindAction(MouseEvent me) {
        if(((Button) me.getSource()).getText().equals("Find") && !FindTxt.getText().isEmpty()) {
            SetString();
            int[] index = find(MyArea.getSelection().getStart());
            if(index.length>0)
                MyArea.selectRange(index[0],index[1]);
            else{
                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Warning!");
                alert.setContentText("Cannot Find \""+FindTxt.getText()+"\"");
                ButtonType okButton = new ButtonType("Ok");
                alert.getButtonTypes().setAll(okButton);
                alert.show();
            }
        }
        if(((Button) me.getSource()).getText().equals("Cancel"))
            Find.hide();
    }

    //Direction Radio Action
    public void Direction(ActionEvent ae) {
        direction = ((RadioButton) ae.getSource()).getText().equals("Down");
    }

    //Match Case CheckBox Action
    public void MatchCaseAction(ActionEvent ae) {
        matchcase = ((CheckBox) ae.getSource()).isSelected();
    }

    //Down
    private int[] Down(int findex){
        boolean equal = true;

        while(findex < Area.length()){ //different
            if(Area.charAt(findex)==find.charAt(0)) {
                int sindex = findex;
                for (int i = 0; i < find.length(); i++) {
                    if(Area.charAt(sindex++)==find.charAt(i))
                        continue;
                    equal = false; break;
                }
                if(equal) return new int[]{ findex , sindex};
                equal = true;
            }
            findex++;
        }
        return new int[]{};
    }

    //Up
    private int[] Up(int findex){
        boolean equal = true;

        while(findex > -1){ //different
            if(Area.charAt(findex)==find.charAt(0)) {
                int sindex = findex;
                for (int i = 0; i < find.length(); i++) {
                    if(Area.charAt(sindex++)==find.charAt(i))
                        continue;
                    equal = false; break;
                }
                if(equal) return new int[]{ findex , sindex};
                equal = true;
            }
            findex--;
        }
        return new int[]{};
    }
}