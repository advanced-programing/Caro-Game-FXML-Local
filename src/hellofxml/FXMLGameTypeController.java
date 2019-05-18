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
public class FXMLGameTypeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static int type=1;
    
    @FXML
    private Button home;
    
    @FXML
    private Button playerTypeButton;
    
    @FXML
    private Button computerTypeButton;
    
    @FXML
    private void playerTypeAction(){
        type = 1;
    }
    
    @FXML
    private void computerTypeAction() throws IOException{
        type = 2;
        Hellofxml.setGameLevelMenu();
    }
    
    @FXML
    private void goHome() throws IOException{
        Hellofxml.setMainMenu();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
