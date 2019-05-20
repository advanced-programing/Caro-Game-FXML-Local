/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hellofxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ChooseLevelBoxController implements Initializable {
    private Hellofxml application; 
    public void setApp(Hellofxml application) {
        this.application = application; 
    }
    /**
     * Initializes the controller class.
     */
    public static int level=1;
    @FXML
    private Button easyLevel;

    @FXML
    private Button mediumLevel;

    @FXML
    private Button hardLevel;

    @FXML
    private Button homeButton;
    
    @FXML 
    private void easyLevelAction(){
        level=1;
    }
    
    @FXML
    private void mediumLevelAction(){
        level=2;
    }
    
    @FXML
    private void hardLevelAction(){
        level=3;
    }
    
    @FXML
    private void goHome() throws IOException{
        application.setMainMenu();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
