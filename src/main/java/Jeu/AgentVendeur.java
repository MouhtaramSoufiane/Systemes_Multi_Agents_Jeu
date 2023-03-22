package Jeu;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgentVendeur extends Agent {
    private VendeurGui serverGui;
    boolean IsWin=false;
    private List<AID> ListM=new ArrayList<>();
    AID gagnant = new AID();
    int nombreSecret=new Random().nextInt(100);
    @Override
    protected void setup() {
        serverGui=(VendeurGui)getArguments()[0];
        addBehaviour(new CyclicBehaviour() {

         @Override
         public void action() {

             ACLMessage message = receive();


             if (message != null && IsWin==false) {
                 if(ListM.contains(message.getSender())==false){
                     ListM.add(message.getSender());
                 }

                 if (Integer.parseInt(message.getContent()) > nombreSecret) {
                     ACLMessage reponse = new ACLMessage(ACLMessage.INFORM);
                     reponse.setContent("Votre nombre est superieur a nombre secret");
                     reponse.addReceiver(message.getSender());
                     send(reponse);
                 } else if (Integer.parseInt(message.getContent()) < nombreSecret) {
                     ACLMessage reponse = new ACLMessage(ACLMessage.INFORM);
                     reponse.setContent("Votre nombre est inferieur a nombre secret");
                     reponse.addReceiver(message.getSender());
                     send(reponse);
                 } else {
                     ACLMessage reponse = new ACLMessage(ACLMessage.INFORM);
                     reponse.setContent("Bravo .......... Vous avez gagne");
                     IsWin = true;
                     gagnant = message.getSender();
                     reponse.addReceiver(gagnant);
                     send(reponse);
                     if (IsWin == true) {
                         ListM.remove(message.getSender());
                         for (AID s : ListM) {
                             if (s.getLocalName()!=message.getSender().getLocalName()) {
                                 ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
                                 message1.setContent("le Joueur " + message.getSender().getLocalName() + " est gagne");
                                 message1.addReceiver(s);
                                 send(message1);
                             }
                         }
                 }
                 }
             }else {
                 block();
             }
         }} );
    }


}
