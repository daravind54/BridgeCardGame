package com.gmu.sockets;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
	private final Map<String, Integer> bid=new HashMap<String, Integer>();
	private static Map<String, Integer> suitToInt = new HashMap<String, Integer>();
	static {
		suitToInt.put("S", 0);
		suitToInt.put("H", 1);
		suitToInt.put("D", 2);
		suitToInt.put("C", 3);
		suitToInt.put("P", 4);
		suitToInt.put("N", 5);
	}
	private static Map<Integer,String> suitToString = new HashMap<Integer,String>();
	static {
		suitToString.put(0,"S");
		suitToString.put(1,"H");
		suitToString.put(2,"H");
		suitToString.put(3,"C");
		suitToString.put(4,"P");
		suitToString.put(5,"N");
	}
	
	private static Map<String, Integer> rankToInt = new HashMap<String, Integer>();
	static {
		rankToInt.put("1", 14);
		rankToInt.put("2", 2);
		rankToInt.put("3", 3);
		rankToInt.put("4", 4);
		rankToInt.put("5", 5);
		rankToInt.put("6", 6);
		rankToInt.put("7", 7);
		rankToInt.put("8", 8);
		rankToInt.put("9", 9);
		rankToInt.put("10", 10);
		rankToInt.put("Jack", 11);
		rankToInt.put("Queen", 12);
		rankToInt.put("King", 13);
		rankToInt.put("Ace", 14);
		rankToInt.put("Pass", 0);
	}
	private static Map<Integer,String> rankToString = new HashMap<Integer,String>();
	static {
		rankToString.put(14,"1");
		rankToString.put(2,"2");
		rankToString.put(3,"3");
		rankToString.put(4,"4");
		rankToString.put(5,"5");
		rankToString.put(6,"6");
		rankToString.put(7,"7");
		rankToString.put(8,"8");
		rankToString.put(9,"9");
		rankToString.put(10,"10");
		rankToString.put(11,"Jack");
		rankToString.put(12,"Queen");
		rankToString.put(13,"King");
		rankToString.put(14,"Ace");
		rankToString.put(0,"Pass");
	}
	 public void addSession(Session session) {
	        sessions.add(session);
	        
	        
	    }
	 
	 public void removeSession(Session session) {
	        sessions.remove(session);
	    }
	 
	 public void addUserToGame(JsonObject jsonMessage, Session session)
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
	                 .add("gameType","Bidding Phase")
	                 .build();
	       
	        sessionDetails.put(session.getId(), playerSessionName);
	        if(count>=1)
	        sendToAllConnectedSessions(sessionDetails);
	 }
	 public int calcBidValue(int suit,int rank)
	 {
		 int value=0;
		 if (suit == 5) 
		 {
			 value = 5 + 100 * rank;
		 }
		 else
		 {
			value = (4 - suit) + 100 * rank;
		 }
		 return value;	
		 
			
	 }
	 
	 public void bid(JsonObject jsonMessage, Session session)
	 {
		 int passCount=0;
		  String temp=jsonMessage.getString("bidValue");
		  if("pass".equals(temp))
			  passCount++;
		  else
		  {  
			  String[] suitRank=temp.split(" ");
			  String rank=suitRank[0];
			  String suit=suitRank[1];
			  int bidValue=calcBidValue(suitToInt.get(suit), suitToInt.get(rank));
			  bid.put(jsonMessage.getString("playername"), bidValue);
		  }
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
	    		JsonObject clientMessage=addMessage;
	    		//clientMessage.merge(key, value, remappingFunction)
	    		//clientMessage.putAll(addMessage);
	    		for (int i = 0; i <= 12; i++) {
	    			Card card=deck.get(i);
	    			cardString=suitToString.get(card.getSuit())+"-"+rankToString.get(card.getRank());
	    			cardname="card"+(i+1);
	    			
	    			cardDetails = provider.createObjectBuilder()
	    					.add(cardname, cardString).build();
	    			//clientMessage.putAll(cardDetails);
	    			clientMessage=Utility.mergeProfileSummary(clientMessage, cardDetails);
	    		}
	    		//clientMessage=Utility.mergeProfileSummary(addMessage, cardDetails);
	    		
	            sendToSession(session, clientMessage);
	        }
	    }

	    private void sendToSession(Session session, JsonObject clientMessage) {
	    	try {
	    		System.out.println(clientMessage.toString());
	            session.getBasicRemote().sendText(clientMessage.toString());
	            
	        } catch (IOException ex) {
	            sessions.remove(session);
	            Logger.getLogger(WebSocketSessionHandler.class.getName()).log(Level.ALL, null, ex);
	        }
	    }
}
