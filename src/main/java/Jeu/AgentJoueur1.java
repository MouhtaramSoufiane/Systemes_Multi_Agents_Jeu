package Jeu;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.scene.control.Label;
public class AgentJoueur1 extends GuiAgent {
    public JoueurGui joueurGui;
    @Override
    protected void setup() {
        joueurGui = (JoueurGui) getArguments()[0];
        joueurGui.setAgentJoueur1(this);


                ACLMessage resp=receive();
                joueurGui.ShowMessage(resp);

    }
    @Override
    public void onGuiEvent(GuiEvent guiEvent) {
        ACLMessage message=new ACLMessage(ACLMessage.INFORM);
        message.setContent(guiEvent.getParameter(0).toString());
        message.addReceiver(new AID("serveur",AID.ISLOCALNAME));
        send(message);
    }
}
