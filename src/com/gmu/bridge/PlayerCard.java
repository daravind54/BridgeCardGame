package com.gmu.bridge;

import javax.json.Json;
import javax.json.JsonObject;

public class PlayerCard 
{
	public JsonObject assignCardsToPlayer(JsonObject toClient) 
	{
		for (int i = 0; i <= 12; i++) {
			Card card=deck.get(i);
			cardString=card.getSuit()+"-"+card.getRank();
			cardname="card"+(i+1);
			
			cardDetails = provider.createObjectBuilder()
					.add(cardname, cardString).build();
			clientMessage.putAll(cardDetails);
		}
	}
}
