package com.gmu.sockets;

public class Utility 
{
	private JsonObject mergeProfileSummary(JsonObject oldJsonObject, JsonObject newJsonObject) {
        JsonObjectBuilder jsonObjectBuilder =Json.createObjectBuilder();
 
        for (String key : oldJsonObject.keySet()){
            jsonObjectBuilder.add(key, oldJsonObject.get(key));
        }
        for (String key : newJsonObject.keySet()){
            jsonObjectBuilder.add(key, newJsonObject.get(key));
        }
 
        return jsonObjectBuilder.build();
    }
}
