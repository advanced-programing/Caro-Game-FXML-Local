package hellofxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PlayerInforBoxController implements Initializable{
    static String name1;
    static String name2;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private Hellofxml application; 
    public void setApp(Hellofxml application) {
        this.application = application; 
    }
    @FXML
    private Label playerName1;

    @FXML
    private TextField nameInput1;

    @FXML
    private Label playerName2;

    @FXML
    private TextField nameInput2;

    @FXML
    private Button startButton;

    @FXML
    private Button cancelButton;
    
    @FXML
    private void startAction() throws IOException{
         if(isValid(nameInput1) && isValid(nameInput2)){
                name1 = nameInput1.getText();
                name2 = nameInput2.getText();
                application.setGameBoardMenu();
            }
    }
    
    @FXML
    private void cancelAction() throws IOException{
        application.setMainMenu();
    }
    
    private static boolean isValid(TextField name){
        if(name.getText().isEmpty()==false){
            System.out.println("Player's name: " + name.getText());
            return true;
        }
        System.out.println("Player's name can not be empty");
        return false;
    }
}
