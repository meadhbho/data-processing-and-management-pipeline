package pipeline;

import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import edu.stanford.nlp.simple.*;
import helpers.JSONIOHelper;

public class B2TextCleaning {

	public static void main(String[] args) {
		
		
		B2TextCleaning cleaner = new B2TextCleaning();
		Scanner r = new Scanner(System.in);
		System.out.println("What JSON Datastore would you like to access?");
		String nameOFJSONDataStore = r.nextLine();
		cleaner.StartLemmatisation(nameOFJSONDataStore);
		
	}
	
	
	private void StartLemmatisation(String filePath) {
		
		JSONIOHelper JSONIO = new JSONIOHelper();
		JSONIO.LoadJSON(filePath);
		
		ConcurrentHashMap<String,String>documents = JSONIO.GetDocumentsFromJSONStructure();
		ConcurrentHashMap<String,String>lemmatised = new ConcurrentHashMap<String, String>();
		
		for(Entry<String, String>entry: documents.entrySet()) {
			
			lemmatised.put(entry.getKey(), LemmatiseSingleDocument(entry.getValue()));
		}
		
		JSONIO.AddLemmasToJSONStructure(lemmatised);
		JSONIO.SaveJSON(filePath);
		
	}
	
	

	
	private String LemmatiseSingleDocument(String text) {
		
		
		
		text = text.replaceAll("\\p{Punct}"," ");
		text = text.replaceAll("\\s+"," ");
		text = text.trim();
		text = text.toLowerCase();
		
		Sentence sentence = new Sentence(text);
		
		List<String>lemmas = sentence.lemmas();
		String text1 = String.join(" ",lemmas);
		System.out.println(text1);
		
		return text1;
		
	}
}
