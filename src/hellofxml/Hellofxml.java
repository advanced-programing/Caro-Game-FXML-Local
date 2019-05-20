/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//cai javafx_scene builder 2.0
//netbean: tool -> option -> java -> javafx -> chen link da tai scene builder
//hoc: oracle javafx
//id de format: properti
//fx id code: code
//cài netbean 11 -> java mavin -> co san css
//gui thong tin: khi ket noi gui toa do (dang choi), khi nao ket thuc (input stream reader)
//su dung buffer de luu du lieu khi toc do 2 ben khong deu
//khong su dung cong >1024
//2 nguoi choi tren cung 1 may ->cung local host
//tao 2 thread rieng phia client/server
package hellofxml;

import hellofxml.remote.java.NetSenceController;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.java.ConnectController;

/**
 *
 * @author USER
 */
public class Hellofxml extends Application {
 
    public static Stage myStage;
    public static Stage confirmBox;
    public static Stage noticeBox;
    
    
//    @Override
//    public void start(Stage stage) throws Exception {
//        //confirmBox = new Stage();
//        myStage = stage;
//        stage.setScene(new Scene(createContent()));
//        setMainMenu();c
//        myStage.show();
//    }
//
//    public static void setMainMenu() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Hellofxml.class.getResource("FXMLDocument.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        myStage.setScene(scene);
//        myStage.setTitle("Main menu");
//    }
//
//    public static void setGameSizeMenu() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Hellofxml.class.getResource("FXMLGameSize.fxml"));
//        Parent view = loader.load();
//        Scene scene = new Scene(view);
//        myStage.setScene(scene);
//        myStage.setTitle("Game size menu");
//    }
//
////    public static void setGameTypeMenu() throws IOException {
////        FXMLLoader loader = new FXMLLoader();
////        loader.setLocation(Hellofxml.class.getResource("FXMLGameType.fxml"));
////        Parent view = loader.load();
////        Scene scene = new Scene(view);
////        myStage.setScene(scene);
////        myStage.setTitle("Game type menu");
////    }
//    public static void setGameLevelMenu() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Hellofxml.class.getResource("ChooseLevelBox.fxml"));
//        Parent view = loader.load();
//        Scene scene = new Scene(view);
//        myStage.setScene(scene);
//        myStage.setTitle("Game level menu");
//    }
//
//    public static void setGameBoardMenu() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Hellofxml.class.getResource("FXMLGameBoard.fxml"));
//        Parent view = loader.load();
//        Scene scene = new Scene(view);
//        myStage.setScene(scene);
//        myStage.setTitle("Game board menu");
//    }
//
//    public static void setPlayerInforBox() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Hellofxml.class.getResource("PlayerInforBox.fxml"));
//        Parent view = loader.load();
//        Scene scene = new Scene(view);
//        myStage.setScene(scene);
//        myStage.setTitle("User information box");
//    }

    public static void addConfirmBox() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("ConfirmBox.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        confirmBox = new Stage();
        confirmBox.initModality(Modality.APPLICATION_MODAL);
        confirmBox.setScene(scene);
        confirmBox.setTitle("Confirm Box");
        confirmBox.showAndWait();
    }

    public static void addNoticeBox() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Hellofxml.class.getResource("NoticeBox.fxml"));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        noticeBox = new Stage();
        noticeBox.initModality(Modality.APPLICATION_MODAL);
        noticeBox.setScene(scene);
        noticeBox.setTitle("Notice Box");
        noticeBox.showAndWait();
    }

    /**
     * add content play with computer 
     */
    private Group root = new Group();
    public boolean beClient = false;
    public int hubPort;
    public String hubName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show(); 
    }
    private Parent createContent() {
        setMainMenu();
        return root;
    }
    
    public boolean userChoseRemote(boolean ok_selected) {
        
        if (ok_selected) {
            gotoNetworkedScene();
            System.out.println("MA " + "User choose play with remote clients");    
            return true;
        }
        return false;
    }

    public void setGameSizeMenu() {
        try {
            FXMLGameSizeController setGameSizeScene = (FXMLGameSizeController) replaceSceneContent("FXMLGameSize.fxml");
            System.out.println("MA2 " + "User choose play with remote clients");
            setGameSizeScene.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setGameBoardMenu() {
        try {
            FXMLGameBoardController setGameBoardScene = (FXMLGameBoardController) replaceSceneContent("FXMLGameBoard.fxml");
            System.out.println("MA2 " + "User choose play with remote clients");
            setGameBoardScene.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void gotoNetworkedScene() {
        try {
            ConnectController networkedScene = (ConnectController) replaceSceneContent("/net/rsc/Connect.fxml");
            System.out.println("MA2 " + "User choose play with remote clients");
            networkedScene.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setGameTypeMenu() {
        try {
            FXMLGameTypeController gameTypeScene = (FXMLGameTypeController) replaceSceneContent("FXMLGameType.fxml");
            System.out.println("MA2 " + "User choose game type");
            gameTypeScene.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void setPlayerInforBox() {
        try {
            PlayerInforBoxController playerInforScene = (PlayerInforBoxController) replaceSceneContent("PlayerInforBox.fxml");
            System.out.println("MA2 " + "User choose game type");
            playerInforScene.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public boolean userPlayingRemote(boolean createConnect) {
        if (createConnect) {
            gotoNetGameScene(); 
            return true;
        }
        return false;
    }
    public boolean userCancelRemote(boolean exit_selected) {
        if (exit_selected) {
            setMainMenu();
            return true; 
        }
        else 
            return false; 
    }
    
    private void gotoNetGameScene() {
        try {
            NetSenceController netGameScene
                    = (NetSenceController) replaceSceneContent("/hellofxml/remote/rsc/NetSence.fxml");
            System.out.println("MA " + "hubName " + this.hubName);
            System.out.println("MA " + "hubport " + this.hubPort);
            System.out.println("MA " + "hubisListen" + this.beClient);
            netGameScene.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setMainMenu() {
        try {
            FXMLDocumentController homeScene = (FXMLDocumentController)replaceSceneContent("FXMLDocument.fxml");
            homeScene.setApp(this);
        } 
        catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setGameLevelMenu() {
        try {
            ChooseLevelBoxController gameLevelScene = (ChooseLevelBoxController)replaceSceneContent("ChooseLevelBox.fxml");
            gameLevelScene.setApp(this);
        } 
        catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }        
    }
    /**
     * @param args the command line arguments
     */
    
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Hellofxml.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Hellofxml.class.getResource(fxml));
        URL url = Hellofxml.class.getResource(fxml); 
        System.out.println("MA " + url);
        AnchorPane page; 
        try {
            page = (AnchorPane)loader.load(in);
        } finally {
            in.close();
        }
        root.getChildren().clear(); 
        root.getChildren().add(page); 
        return (Initializable)loader.getController(); 
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}
