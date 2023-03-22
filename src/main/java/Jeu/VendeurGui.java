package Jeu;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VendeurGui extends Application {
    private ObservableList<Label> data= FXCollections.observableArrayList();

    public static void main(String[] args) throws ControllerException {
      launch(args);
    }



    public void StartContainer() throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController agentController3=container.createNewAgent("serveur","Jeu.AgentServeur",new Object[]{this});
        agentController3.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartContainer();


    }

}
