package helpers;

import java.util.concurrent.ConcurrentHashMap;

import java.util.Map.Entry;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JSONIOHelper {
	
	
		JSONObject rootObject;
		JSONObject documentsObject;
		JSONObject lemmasObject;
		
	

	public void CreateBasicsJSONStructure() {
		
		rootObject = new JSONObject();
		documentsObject = new JSONObject();
		lemmasObject = new JSONObject();
		

		rootObject.put("documents", documentsObject);
		rootObject.put("lemmas", lemmasObject);
		
		
	}
	
	
	public void AddDocumentsToJSONStructure(ConcurrentHashMap<String, String>documents) {
		
		for(Entry<String, String>entry: documents.entrySet()) {
			
			documentsObject.put(entry.getKey(), entry.getValue());
		}
	}
	
	
	public void SaveJSON(String filename) {
		String JSONString = rootObject.toJSONString();
		try(FileWriter writer = new FileWriter(filename)) {
			writer.write(JSONString);
			System.out.println("JSON File saved successfully");
			
		}
		
		catch(Exception e) {
			System.out.println("Saving JSON to file failed...");
		}
	}
	
	
	public void LoadJSON(String filename) {
		
		CreateBasicsJSONStructure();
		
		try {
			
			FileReader file = new FileReader(filename);
			JSONParser parser = new JSONParser();
			rootObject = (JSONObject)parser.parse(file);
			
			
			if(rootObject.get("documents") != null) {
				documentsObject = (JSONObject)rootObject.get("documents");
			}
			
			if(rootObject.get("lemmas") !=null) {
				lemmasObject = (JSONObject)rootObject.get("lemmas");			}
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			System.out.println("JSON File Loaded files: " + documentsObject.size());
		}
		

	}
	
	
	public ConcurrentHashMap<String, String>GetDocumentsFromJSONStructure(){
		
		ConcurrentHashMap<String, String>documents = new ConcurrentHashMap<String, String>();
		
		for(String key: (Iterable<String>)documentsObject.keySet()) {
			
			documents.put(key,(String)documentsObject.get(key));
		}
		return documents;
		
	}
	
	
	public void AddLemmasToJSONStructure(ConcurrentHashMap<String,String>lemmas){
		

		for(Entry<String, String>entry: lemmas.entrySet()) {
			
			lemmasObject.put(entry.getKey(), entry.getValue());
		
		}
		
	}
	
	public ConcurrentHashMap<String, String>GetLemmasFromJSONStructure(){
		
		ConcurrentHashMap<String, String>lemmas = new ConcurrentHashMap<String, String>();
		
		for(String key: (Iterable<String>)lemmasObject.keySet()) {
			
			lemmas.put(key,(String)lemmasObject.get(key));
		}
		return lemmas;
		
		
	}
	
	
	
}
