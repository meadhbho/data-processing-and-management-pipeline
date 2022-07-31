package helpers;

import java.util.concurrent.ConcurrentHashMap;

import java.util.Map.Entry;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



 
//Helper class to help load data into JSON files

public class JSONIOHelper {
	
		// Data structure will be a JSON object
			// Want to have two levels (parent - child(x2)) so will create three objects
	
		JSONObject rootObject;
		JSONObject documentsObject;
		JSONObject lemmasObject;
		
	
	//Create a method to create the JSON structure of two objects being the children of another object

	public void CreateBasicsJSONStructure() {
		
		rootObject = new JSONObject();
		documentsObject = new JSONObject();
		lemmasObject = new JSONObject();
		
		// documents object is the child of the rootObject
		// key = "documents"
		// value = documentsObject
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
		//FileWriter class
		try(FileWriter writer = new FileWriter(filename)) {
			writer.write(JSONString);
			System.out.println("JSON File saved successfully");
			
		}
		
		catch(Exception e) {
			System.out.println("Saving JSON to file failed...");
		}
	}
	
	
// 	LoadJSON method does the following:	
//	Initialise the basic JSON structure
//	Open the JSON file and load in the text within
//	Parse that text back into Java.
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
			// TODO Auto-generated catch block
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
	
	
	//Block 2 - Text cleaning
	public void AddLemmasToJSONStructure(ConcurrentHashMap<String,String>lemmas){
		

		for(Entry<String, String>entry: lemmas.entrySet()) {
			
			lemmasObject.put(entry.getKey(), entry.getValue());
		
		}
		
	}
	
	//Block 2 - Text cleaning
	public ConcurrentHashMap<String, String>GetLemmasFromJSONStructure(){
		
		ConcurrentHashMap<String, String>lemmas = new ConcurrentHashMap<String, String>();
		
		for(String key: (Iterable<String>)lemmasObject.keySet()) {
			
			lemmas.put(key,(String)lemmasObject.get(key));
		}
		return lemmas;
		
		
	}
	
	
	
}
