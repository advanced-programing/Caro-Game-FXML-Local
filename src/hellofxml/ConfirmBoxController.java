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
public class ConfirmBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public static boolean answer;
    @FXML
    private Button yesButton;
    
    @FXML
    private Button noButton;
    
    @FXML
    private void yesAction() throws IOException{
        answer = true;
        System.out.println("answer: "+ answer);
        Hellofxml.setMainMenu();
        Hellofxml.confirmBox.close();
    }
    
    @FXML
    private void noAction(){
        answer = false;
        System.out.println("answer: "+ answer);
        Hellofxml.confirmBox.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}