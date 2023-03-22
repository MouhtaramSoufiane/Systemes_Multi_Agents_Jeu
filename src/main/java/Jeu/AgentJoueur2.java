package Jeu;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.scene.control.Label;
public class AgentJoueur2 extends GuiAgent {
    public Joueur1Gui joueur1Gui;
    @Override
    protected void setup() {
        joueur1Gui = (Joueur1Gui) getArguments()[0];
        joueur1Gui.setAgentJoueur2(this);

                ACLMessage resp=receive();
                joueur1Gui.ShowMessage(resp);



    }
    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        ACLMessage message=new ACLMessage(ACLMessage.INFORM);
        message.setContent(guiEvent.getParameter(0).toString());
        message.addReceiver(new AID("serveur",AID.ISLOCALNAME));
        send(message);
    }
}
