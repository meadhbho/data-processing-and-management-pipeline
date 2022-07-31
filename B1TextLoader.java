package pipeline;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import helpers.JSONIOHelper;
public class B1TextLoader {
	

	
	ConcurrentHashMap<String, String>documents = new ConcurrentHashMap<String, String>();
	

	public static void main(String[] args) {

		B1TextLoader loader = new B1TextLoader();
		
		
			Scanner r = new Scanner(System.in);
		
			System.out.println("What text file would you like to pipe?");
		
			String nameOfTextFile = r.nextLine();
			loader.LoadTextFile(nameOfTextFile);
		
		
			Scanner a = new Scanner(System.in);
			System.out.println("State name of JSON document");
		
			String nameOfJSONDocument = a.nextLine();
			loader.SaveDocumentsToJSON(nameOfJSONDocument);

	}

	public void LoadTextFile(String filepath) {
		

		try 
		
		{

			System.out.println("Loading file..");
			File f = new File(filepath);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			int counter = 0;
			while(line != null) {
				if(line.trim().length() > 0) {
					documents.put("doc" + counter, line);
					counter++;
				}
				line = br.readLine();
			}
			br.close();
		}
		catch(Exception e)
		{
			System.out.println("File load failed");
		}
		System.out.println("Load Complete. Lines loads: " + documents.size());
		CountWordsInDocuments(documents);
		


	}
	
	public void CountWordsInDocuments(ConcurrentHashMap<String, String>documents) {
		documents.forEach(this::CountWordsInDocument);
	}
	
	public void CountWordsInDocument(String key, String value) {
		String[] words = value.split(" ");
		System.out.println(key + " has " + words.length + "words!");
		
	}
	
	
	public void SaveDocumentsToJSON(String filename) {
		JSONIOHelper JSONIO = new JSONIOHelper();
		JSONIO.CreateBasicsJSONStructure();
		JSONIO.AddDocumentsToJSONStructure(documents);
		JSONIO.SaveJSON(filename);
		
	}
	
}
