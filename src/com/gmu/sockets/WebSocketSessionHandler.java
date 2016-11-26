package com.gmu.sockets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import com.gmu.bridge.Card;
import com.gmu.bridge.Deck;



@ApplicationScoped
public class WebSocketSessionHandler 
{
	static int count=0;
	private final Set<Session> sessions = new HashSet<>();
	private final Map<String, JsonObject> sessionDetails=new HashMap<String, JsonObject>();
	private final Deck deck=new Deck();
	
	 public void addSession(Session session) {
	        sessions.add(session);
	        
	        
	    }
	 
	 public void removeSession(Session session) {
	        sessions.remove(session);
	    }
	 
	 public void addUserToGame(String message, Session session)
	 {
		 	++count;
		 	String playerName=null;
		 	if(count==1)
		 		playerName="South";
		 	if(count==2)
		 		playerName="West";
		 	if(count==3)
		 		playerName="North";
		 	if(count==4)
		 		playerName="East";
	        System.out.println(count);
	        JsonProvider provider = JsonProvider.provider();
	         JsonObject playerSessionName = provider.createObjectBuilder()
	                 .add("sessionId", session.getId())
	                 .add("playerName", playerName)	                            
	                 .build();
	       
	        sessionDetails.put(session.getId(), playerSessionName);
	        if(count>=1)
	        sendToAllConnectedSessions(sessionDetails);
	 }
	 private void sendToAllConnectedSessions(Map<String, JsonObject> sessionDetails) {
	    	for (Session session : sessions) {
	    		//System.out.println(session.getId());
	    		//List<Card> hand=new ArrayList<Card>();
	    		String cardString=null;
	    		String cardname=null;
	    		JsonObject addMessage=sessionDetails.get(session.getId());
	    		JsonProvider provider = JsonProvider.provider();
	    		JsonObject cardDetails=null;
	    		JsonObject clientMessage=null;
	    		//clientMessage.merge(key, value, remappingFunction)
	    		//clientMessage.putAll(addMessage);
	    		for (int i = 0; i <= 12; i++) {
	    			Card card=deck.get(i);
	    			cardString=card.getSuit()+"-"+card.getRank();
	    			cardname="card"+(i+1);
	    			
	    			cardDetails = provider.createObjectBuilder()
	    					.add(cardname, cardString).build();
	    			//clientMessage.putAll(cardDetails);
	    			
	    		}
	    		JsonObject clientMessage=Utility.mergeProfileSummary(addMessage, cardDetails);
	    		System.out.println(clientMessage.toString());
	            sendToSession(session, clientMessage);
	        }
	    }

	    private void sendToSession(Session session, JsonObject addMessage) {
	    	try {
	    		System.out.println(addMessage.toString());
	            session.getBasicRemote().sendText(addMessage.toString());
	            
	        } catch (IOException ex) {
	            sessions.remove(session);
	            Logger.getLogger(WebSocketSessionHandler.class.getName()).log(Level.ALL, null, ex);
	        }
	    }
}
