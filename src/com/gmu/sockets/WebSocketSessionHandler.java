package com.gmu.sockets;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;





import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.websocket.Session;



@ApplicationScoped
public class WebSocketSessionHandler 
{
	static int count=0;
	private final Set<Session> sessions = new HashSet<>();
	
	 public void addSession(Session session) {
	        sessions.add(session);
	        
	        
	    }
	 
	 public void removeSession(Session session) {
	        sessions.remove(session);
	    }
	 
	 public void addUser(String message, Session session)
	 {
		 	++count;
	        System.out.println(count);
	        if(count==4)
	        sendToAllConnectedSessions(message);
	 }
	 private void sendToAllConnectedSessions(String message) {
	    	for (Session session : sessions) {
	            sendToSession(session, message);
	        }
	    }

	    private void sendToSession(Session session, String message) {
	    	try {
	            session.getBasicRemote().sendText("Hello World");
	        } catch (IOException ex) {
	            sessions.remove(session);
	            Logger.getLogger(WebSocketSessionHandler.class.getName()).log(Level.ALL, null, ex);
	        }
	    }
}