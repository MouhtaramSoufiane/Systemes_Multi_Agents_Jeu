package Jeu;

import com.sun.beans.decoder.ValueObject;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;


public class JoueurGui extends Application {
    private AgentJoueur1 agentJoueur1;
    boolean CorrectFormat=false;
    protected ObservableList<Label> data= FXCollections.observableArrayList();

    public ObservableList<Label> getData() {
        return data;
    }

    int nombre;
    public void setAgentJoueur1(AgentJoueur1 agentJoueur1) {
        this.agentJoueur1 = agentJoueur1;

    }

    public static void main(String[] args) throws ControllerException {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartContainer();
        BorderPane borderPane=new BorderPane();
        ListView<Label> listView=new ListView<>(data);
        Button buttonSend=new Button("Send");
        buttonSend.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;-fx-font-weight:bold");
        TextField textField=new TextField();
        Label labelText=new Label("Taper un nombre");
        labelText.setPadding(new Insets(5));
        labelText.setStyle("-fx-font-weight:bold; -fx-text-fill: white;");
        Alert a=new Alert(Alert.AlertType.NONE);
        a.setContentText("Inserez vous un nombre entre 0 et 100 !!!!");
        EventHandler<ActionEvent> event = new
                EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        String text=textField.getText();
                           try {
                               nombre=Integer.parseInt(text);
                               CorrectFormat=true;

                           }catch (NumberFormatException ex){
                               CorrectFormat=false;
                           }
                            if( CorrectFormat==false) {
                                a.setAlertType(Alert.AlertType.WARNING);
                                a.show();
                            }else{
                                GuiEvent guiEvent=new GuiEvent(this,1);
                                guiEvent.addParameter(nombre);
                                Label label=new Label();
                                label.setText(textField.getText());
                                textField.setText(null);
                                label.setStyle("-fx-background-color: lightblue;-fx-background-size:30px; -fx-text-fill: black;-fx-font-weight:bold;-fx-border-radius: 10px");
                                label.setLayoutX(100);
                                agentJoueur1.onGuiEvent(guiEvent);
                                data.add(label);
                            }
                        }
                };
                buttonSend.setOnAction(event);
                textField.setOnAction(event);
        HBox hBox=new HBox(labelText,textField,buttonSend);
        borderPane.setCenter(listView);
        File file=new File("./src/main/java/Jeu/secret.jpg");
        Image image=new Image(file.getAbsoluteFile().toURI().toString());
        borderPane.setBottom(hBox);
        Scene scene=new Scene(borderPane,500,600);
        listView.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));
        hBox.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT)));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Joueur");
        primaryStage.show();

    }
    private void StartContainer() throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController agentController1=container.createNewAgent("agent","Jeu.AgentJoueur1",new Object[]{this});
        agentController1.start();

    }
    public void ShowMessage(ACLMessage resp){

        agentJoueur1.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage RESP=resp;
                if(resp!=null){
                    Label label=new Label();
                    label.setText("                                                                   "+resp.getContent());
                    label.setStyle("-fx-background-color: white; -fx-text-fill: red;-fx-font-weight:bold");
                    data.add(label);

                }else{
                    block();}}

            }
        );

        }
    }
