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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ConfirmBoxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    Image image = new Image(getClass().getResourceAsStream("/image/q.png"));
    
    public static boolean answer;
    
    @FXML
    private Label notice;
    
    @FXML
    private Button yesButton;
    
    @FXML
    private Button noButton;
    
    private Hellofxml application; 
    public void setApp(Hellofxml application) {
        this.application = application; 
    }
    @FXML
    private void yesAction() throws IOException{
        answer = true;
        System.out.println("answer: "+ answer);
        application.setMainMenu();
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

        notice.setGraphic(new ImageView(image));
    }    
    
}
