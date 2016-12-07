package com.gmu.sockets;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import com.gmu.bridge.Card;
import com.gmu.bridge.Deck;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;



@ApplicationScoped
public class WebSocketSessionHandler 
{
	static int count=0;
	static int passCount=0;
	static int trickcount=0;
	static int tricksWonbyNorth=0;
	static int tricksWonbySouth=0;
	static int tricksWonbyEast=0;
	static int tricksWonbyWest=0;
	static String trickSuit=null;
	private final Set<Session> sessions = new HashSet<>();
	private final Map<String, JsonObject> sessionDetails=new HashMap<String, JsonObject>();
	private final Deck deck=new Deck();
	private final Map<String, Integer> bidRank=new HashMap<String, Integer>();
	private final Map<String, String> actualbid=new HashMap<String, String>();
	private final Map<String, Integer> cardRank=new HashMap<String, Integer>();
	private final Map<String, String> actualCard=new HashMap<String, String>();
	private static Map<String, Integer> suitToInt = new HashMap<String, Integer>();
	private static Map<Session, JsonObject> clientData=new HashMap<Session, JsonObject>();
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
		suitToString.put(2,"D");
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
	private static Map<String, Integer> bidRankToInt = new HashMap<String, Integer>();
	static {
		bidRankToInt.put("1", 1);
		bidRankToInt.put("2", 2);
		bidRankToInt.put("3", 3);
		bidRankToInt.put("4", 4);
		bidRankToInt.put("5", 5);
		bidRankToInt.put("6", 6);
		bidRankToInt.put("7", 7);
		
	}
	private static Map<Integer,String> bidRankToString = new HashMap<Integer,String>();
	static {
		bidRankToString.put(1,"1");
		bidRankToString.put(2,"2");
		bidRankToString.put(3,"3");
		bidRankToString.put(4,"4");
		bidRankToString.put(5,"5");
		bidRankToString.put(6,"6");
		bidRankToString.put(7,"7");
		
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
	                 .add("turn", "South's")
	                 .build();
	       
	        sessionDetails.put(session.getId(), playerSessionName);
	        if(count==4)
	        sendCardsToAllConnectedSessions(sessionDetails);
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
		 System.out.println(value);
		 return value;	
		 
			
	 }
	 public int calcCardValue(int suit,int rank)
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
		 System.out.println(value);
		 return value;	
		 
			
	 }
	 public String compareBid()
	 {
		 /*TreeMap<String, Integer> tempTree=new TreeMap<String, Integer>(bidRank);
		 
		 return tempTree.lastKey();*/
		 Set<Integer> tempset=new TreeSet<Integer>();
		 tempset.addAll(bidRank.values());
		 Integer lastElement=null;
		 Iterator<Integer> it=tempset.iterator();
		 while(it.hasNext())
		 {
			 lastElement=it.next();
		 }
		 Iterator<String> is=bidRank.keySet().iterator();
		 String temp=null;
		 while(is.hasNext())
		 {
			 String key=is.next();
			 if(lastElement==bidRank.get(key))
			 {
				 temp=key;
			 }
		 }
		 return temp;
	 }
	 public String compareCardsInTricks()
	 {
		 /*TreeMap<String, Integer> tempTree=new TreeMap<String, Integer>(cardRank);
		 
		 return tempTree.lastKey();*/
		 Set<Integer> tempset=new TreeSet<Integer>();
		 tempset.addAll(cardRank.values());
		 Integer lastElement=null;
		 Iterator<Integer> it=tempset.iterator();
		 while(it.hasNext())
		 {
			 lastElement=it.next();
		 }
		 Iterator<String> is=cardRank.keySet().iterator();
		 String temp=null;
		 while(is.hasNext())
		 {
			 String key=is.next();
			 if(lastElement==cardRank.get(key))
			 {
				 temp=key;
			 }
		 }
		 return temp;
	 }
	 public void bid(JsonObject jsonMessage, Session session)
	 {
		 //System.out.println(sessions.toString());
		 String nextPlayer=null;
		 Integer bidValue=null;
		 clientData.put(session, jsonMessage);
		 if(jsonMessage.getString("playerName").equals("South"))
			 nextPlayer="West's";
		 if(jsonMessage.getString("playerName").equals("West"))
			 nextPlayer="North's";
		 if(jsonMessage.getString("playerName").equals("North"))
			 nextPlayer="East's";
		 if(jsonMessage.getString("playerName").equals("East"))
			 nextPlayer="South's";
		 String winner=null;
		  String temp=jsonMessage.getString("bidValue");
		  actualbid.put(jsonMessage.getString("playerName"), temp);
		  System.out.println(actualbid.toString());
		  if(temp.equals("pass")||temp.equals("PASS"))
		  {
			  passCount++;
		  }
		  else
		  {  
			  passCount=0;
			  String[] suitRank=temp.split(" ");
			  String rank=suitRank[0];
			  String suit=suitRank[1];
			  System.out.println(suit+" "+rank);
			  System.out.println(suitToInt.get(suit) +" "+bidRankToInt.get(rank));
			  bidValue=calcBidValue(suitToInt.get(suit), bidRankToInt.get(rank));
			  
			  bidRank.put(jsonMessage.getString("playerName"), bidValue);
		  }
		  JsonProvider provider = JsonProvider.provider();
  		JsonObject data=provider.createObjectBuilder()
				.add("turn", nextPlayer)
				.build();
  		jsonMessage=Utility.mergeProfileSummary(jsonMessage, data);
  		System.out.println(jsonMessage.toString());
		  if(passCount==3)
		  {
			  String dummy=null;
			  String trump;
			  int tricksToWin;
			  String winnerBid;
			  winner=compareBid();
			  if(winner.equals("North"))
			  {
				  dummy="South";
				  nextPlayer="East's";
			  }
			  if(winner.equals("South"))
			  {
				  dummy="North";
				  nextPlayer="West's";
			  }
			  if(winner.equals("East"))
			  {
				  dummy="West";
				  nextPlayer="South's";
			  }
			  if(winner.equals("West"))
			  {
				  dummy="East";
				  nextPlayer="North's";
			  }
			  winnerBid=actualbid.get(winner);
			  String[] temp1=winnerBid.split(" ");
			  tricksToWin=6 + Integer.parseInt(temp1[0]);
			  trump=temp1[1];
		  	  sendWinnerDetailsToAllConnectedSessions(jsonMessage, winner, Integer.toString(tricksToWin), trump, nextPlayer, dummy);
		  }
		  else
		  {
			  clientData.put(session, jsonMessage);
			  String playerBidName=jsonMessage.getString("playerName")+"BidOrCard";
			  sendBidToAllConnectedSessions(jsonMessage, nextPlayer,playerBidName, temp);
		  }
		  
	 }
	 public void game(JsonObject jsonMessage, Session session)
	 {
		 String nextPlayer=null;
		 Integer cardValue=null;
		 clientData.put(session, jsonMessage);
		 if(jsonMessage.getString("playerName").equals("South"))
			 nextPlayer="West's";
		 if(jsonMessage.getString("playerName").equals("West"))
			 nextPlayer="North's";
		 if(jsonMessage.getString("playerName").equals("North"))
			 nextPlayer="East's";
		 if(jsonMessage.getString("playerName").equals("East"))
			 nextPlayer="South's";
		 String tempCard=jsonMessage.getString("cardValue");
		 String[] suitRank=tempCard.split("-");
		 actualCard.put(jsonMessage.getString("playerName"), tempCard);
		 String suit=suitRank[0];
		 String rank=suitRank[1];
		 /*if(jsonMessage.getString("trickStarter").equals(jsonMessage.getString("playerName")+"'s"))
		 {
			 cardValue=calcCardValue(suitToInt.get(suit), rankToInt.get(rank));
			 cardRank.put(jsonMessage.getString("playerName"), cardValue);
		  	 String playerBidName=jsonMessage.getString("playerName")+"BidOrCard";
		  	 sendCardToAllConnectedSessions(jsonMessage, nextPlayer,playerBidName, tempCard,suit);
		 }
		 else
		 {
			 cardValue=calcCardValue(suitToInt.get(suit), rankToInt.get(rank));
			 cardRank.put(jsonMessage.getString("playerName"), cardValue);
		  	 String playerBidName=jsonMessage.getString("playerName")+"BidOrCard";
		  	 sendCardToAllConnectedSessions(jsonMessage, nextPlayer,playerBidName, tempCard,suit);
		 }*/
		 trickcount++;
		 
		 if(jsonMessage.getString("trickStarter").equals(jsonMessage.getString("playerName")+"'s"))
		 {
			 trickSuit=suit;
			 cardValue=calcCardValue(suitToInt.get(suit), rankToInt.get(rank));
		 }
			 
		 else if(!(jsonMessage.getString("suitForTrick").equals(suit)) )
		 {
			 if(suit.equals(jsonMessage.getString("trump")))
				 cardValue=calcCardValue(suitToInt.get("N"), rankToInt.get(rank));
			 else
				 cardValue=calcCardValue(suitToInt.get("P"), rankToInt.get("Pass"));
		 }
		 else
			 cardValue=calcCardValue(suitToInt.get(suit), rankToInt.get(rank));
		 
		 cardRank.put(jsonMessage.getString("playerName"), cardValue);
		 
		 
		 if(trickcount==4)
		 {
			 trickSuit=null;
			 trickcount=0;
			 String trickWinner=null;
			 trickWinner=compareCardsInTricks();
			 System.out.println(trickWinner);
			 nextPlayer=trickWinner+"'s";
			 String trickStarter=null;
			 System.out.println("South won:"+tricksWonbySouth);
			 if(trickWinner.equals("North"))
			 {
				 tricksWonbyNorth++;
				 trickStarter="North's";
				 if(jsonMessage.getString("bidWinner").equals("North") || jsonMessage.getString("dummyPlayer").equals("North"))
				 {
					 if((tricksWonbyNorth+tricksWonbySouth)>=Integer.parseInt(jsonMessage.getString("tricksToWin")))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="South-North";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 }
				 }
				 else
				 {
					 if((tricksWonbyNorth+tricksWonbySouth)>=(14-Integer.parseInt(jsonMessage.getString("tricksToWin"))))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="South-North";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 
					 }
				 }
				 
			 }
			 if(trickWinner.equals("South"))
			 {
				 tricksWonbySouth++;
				 trickStarter="South's";
				 if(jsonMessage.getString("bidWinner").equals("North") || jsonMessage.getString("dummyPlayer").equals("South"))
				 {
					 if((tricksWonbyNorth+tricksWonbySouth)>=Integer.parseInt(jsonMessage.getString("tricksToWin")))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="South-North";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 }
				 }
				 else
				 {
					 if((tricksWonbyNorth+tricksWonbySouth)>=(14-Integer.parseInt(jsonMessage.getString("tricksToWin"))))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="South-North";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 
					 }
				 }
			 }
			 if(trickWinner.equals("East"))
			 {
				 tricksWonbyEast++;
				 trickStarter="East's";
				 if(jsonMessage.getString("bidWinner").equals("East") || jsonMessage.getString("dummyPlayer").equals("East"))
				 {
					 if((tricksWonbyEast+tricksWonbyWest)>=Integer.parseInt(jsonMessage.getString("tricksToWin")))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="East-West";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 }
				 }
				 else
				 {
					 if((tricksWonbyEast+tricksWonbyWest)>=(14-Integer.parseInt(jsonMessage.getString("tricksToWin"))))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="East-West";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 
					 }
				 }
			 }
			 if(trickWinner.equals("West"))
			 {
				 tricksWonbyWest++;
				 trickStarter="West's";
				 if(jsonMessage.getString("bidWinner").equals("West") || jsonMessage.getString("dummyPlayer").equals("West"))
				 {
					 if((tricksWonbyEast+tricksWonbyWest)>=Integer.parseInt(jsonMessage.getString("tricksToWin")))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="East-West";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 }
				 }
				 else
				 {
					 if((tricksWonbyEast+tricksWonbyWest)>=(14-Integer.parseInt(jsonMessage.getString("tricksToWin"))))
					 {
						 String gameStatus="Game Won";
						 String gameWinner="East-West";
						 sendGameWinnerToAllConnectedSessions(jsonMessage, nextPlayer, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter,gameStatus,gameWinner);
					 
					 }
				 }
			 }
			 String playerBidName=jsonMessage.getString("playerName")+"BidOrCard";
			 jsonMessage=removeCard(jsonMessage,tempCard);
			 clientData.put(session, jsonMessage);
			 sendTrickWinnerToAllConnectedSessions(jsonMessage, nextPlayer, playerBidName, tempCard, suit, Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast),trickStarter);
			 
		 }
		 else
		 {
			 
		  	 String playerBidName=jsonMessage.getString("playerName")+"BidOrCard";
		  	 jsonMessage=removeCard(jsonMessage,tempCard);
		  	 clientData.put(session, jsonMessage);
		  	 sendCardToAllConnectedSessions(jsonMessage, nextPlayer,playerBidName, tempCard,trickSuit,Integer.toString(tricksWonbyNorth),Integer.toString(tricksWonbyWest),Integer.toString(tricksWonbySouth),Integer.toString(tricksWonbyEast));
		 }
		 
		 
	 }
	 private void sendGameWinnerToAllConnectedSessions(JsonObject jsonMessage,
			String nextPlayer, String tempCard, String suit, String string,
			String string2, String string3, String string4,
			String trickStarter, String gameStatus, String gameWinner) {
		 for (Session session : sessions) {
			 jsonMessage=clientData.get(session);
	    		
	    		JsonProvider provider1 = JsonProvider.provider();
		  		JsonObject data1=provider1.createObjectBuilder()
						.add("turn", nextPlayer)
						.add("suitForTrick", suit)
						.add("gameType", "Game Phase")
						.add("tricksWonW",string2)
						.add("tricksWonE",string4)
						.add("tricksWonN",string)
						.add("tricksWonS",string3)
						.add("trickStarter",trickStarter)
						.add("gameType", gameStatus)
						.add("gameWinner",gameWinner)
						.build();
		  		jsonMessage=Utility.mergeProfileSummary(jsonMessage, data1);
		  		clientData.put(session, jsonMessage);
	    		sendToSession(session, clientData.get(session));
	    	}
		 System.out.println(cardRank.toString());
		
	}

	private JsonObject removeCard(JsonObject jsonMessage, String tempCard) {
		Iterator<String> it=jsonMessage.keySet().iterator();
		JsonProvider provider1 = JsonProvider.provider();
  		JsonObject data1 = null,data2 = provider1.createObjectBuilder()
				.add("sessionId", jsonMessage.getString("sessionId"))
				.build();
				
		while(it.hasNext())
		{
			String temp=it.next();
			System.out.println(temp);
			System.out.println(jsonMessage.getString(temp.toString()));
			String temp1=jsonMessage.getString(temp.toString());
			System.out.println(temp1);
			if(!(temp1.equals(tempCard)))
			{
				data1=provider1.createObjectBuilder()
						.add(temp,temp1 )
						.build();
				data2=Utility.mergeProfileSummary(data2, data1);
			}
			
				
		  		
			
				
		}
		return data2;
	}

	private void sendCardToAllConnectedSessions(JsonObject jsonMessage,
			String nextPlayer, String playerBidName, String tempCard,String trickSuit, 
			String string, String string2, String string3, String string4) 
	{
		 for (Session session : sessions) {
			 jsonMessage=clientData.get(session);
	    		
	    		JsonProvider provider1 = JsonProvider.provider();
		  		JsonObject data1=provider1.createObjectBuilder()
						.add("turn", nextPlayer)
						.add(playerBidName,tempCard)
						.add("suitForTrick", trickSuit)
						.add("gameType", "Game Phase")
						.add("tricksWonW",string2)
						.add("tricksWonE",string4)
						.add("tricksWonN",string)
						.add("tricksWonS",string3)
						.build();
		  		jsonMessage=Utility.mergeProfileSummary(jsonMessage, data1);
		  		clientData.put(session, jsonMessage);
	    		sendToSession(session, clientData.get(session));
	    	}
		 System.out.println(cardRank.toString());
		
	}
	 private void sendTrickWinnerToAllConnectedSessions(JsonObject jsonMessage,
				String nextPlayer, String playerBidName, String tempCard,String suit, 
				String string, String string2, String string3, String string4, String trickStarter) 
		{
			 for (Session session : sessions) {
				 jsonMessage=clientData.get(session);
		    		
		    		JsonProvider provider1 = JsonProvider.provider();
			  		JsonObject data1=provider1.createObjectBuilder()
							.add("turn", nextPlayer)
							.add(playerBidName,tempCard)
							.add("suitForTrick", "N")
							.add("gameType", "Game Phase")
							.add("tricksWonW",string2)
							.add("tricksWonE",string4)
							.add("tricksWonN",string)
							.add("tricksWonS",string3)
							.add("trickStarter",trickStarter)
							.build();
			  		jsonMessage=Utility.mergeProfileSummary(jsonMessage, data1);
			  		clientData.put(session, jsonMessage);
		    		sendToSession(session, clientData.get(session));
		    	}
			 System.out.println(cardRank.toString());
			
		}

	private void sendWinnerDetailsToAllConnectedSessions(JsonObject clientMessage,String winner,String tricksToWin,String trump, String nextPlayer, String dummy) {
	    	for (Session session : sessions) {
	    		clientMessage=clientData.get(session);
	    		
	    		JsonProvider provider1 = JsonProvider.provider();
		  		JsonObject data1=provider1.createObjectBuilder()
						.add("bidWinner", winner)
						.add("tricksToWin", tricksToWin)
						.add("trump", trump)
						.add("turn", nextPlayer)
						.add("trickStarter",nextPlayer)
						.add("dummyPlayer",dummy)
						.add("gameType", "Bid Complete")
						.add("tricksWonN","0")
						.add("tricksWonS","0")
						.add("tricksWonW","0")
						.add("tricksWonE","0")
						.add("suitForTrick","N")
						.build();
		  		clientMessage=Utility.mergeProfileSummary(clientMessage, data1);
		  		clientData.put(session, clientMessage);
	    		sendToSession(session, clientData.get(session));
	    	}
	 }
	 private void sendBidToAllConnectedSessions(JsonObject clientMessage, String nextPlayer, String playerBidName, String temp) {
	    	for (Session session : sessions) {
	    		clientMessage=clientData.get(session);
	    		
	    		JsonProvider provider1 = JsonProvider.provider();
		  		JsonObject data1=provider1.createObjectBuilder()
						.add("turn", nextPlayer)
						.add(playerBidName,temp)
						.build();
		  		clientMessage=Utility.mergeProfileSummary(clientMessage, data1);
		  		clientData.put(session, clientMessage);
	    		sendToSession(session, clientData.get(session));
	    	}
	 }
	 private void sendCardsToAllConnectedSessions(Map<String, JsonObject> sessionDetails) {
		 	int k=0;
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
	    		for (int i=0;i <= 12; i++) {
	    			Card card=deck.get(i+k);
	    			System.out.println(card.getRank()+" "+card.getSuit());
	    			cardString=suitToString.get(card.getSuit())+"-"+rankToString.get(card.getRank());
	    			cardname="card"+(i+1);
	    			
	    			cardDetails = provider.createObjectBuilder()
	    					.add(cardname, cardString).build();
	    			//clientMessage.putAll(cardDetails);
	    			clientMessage=Utility.mergeProfileSummary(clientMessage, cardDetails);
	    		}
	    		k=k+13;
	    		
	    		//clientMessage=Utility.mergeProfileSummary(addMessage, cardDetails);
	    		//j=j+14;
	            sendToSession(session, clientMessage);
	        }
	    }

	    private void sendToSession(Session session, JsonObject clientMessage) {
	    	try {
	    		System.out.println(clientMessage.toString());
	    		clientData.put(session, clientMessage);
	            session.getBasicRemote().sendText(clientMessage.toString());
	            
	        } catch (IOException ex) {
	            sessions.remove(session);
	            Logger.getLogger(WebSocketSessionHandler.class.getName()).log(Level.ALL, null, ex);
	        }
	    }
}
