package pipeline;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import helpers.JSONIOHelper;
public class B1TextLoader {
	
	//Part 2
	// Loading data into the Data structure
		// Implementing a ConcurrentHashMap
			//HasMap - like a dict Data structure with Key, value pairs
			//ConcurrentHashMap allows for multithreading
		// Arguments on both sides need to be the same and represent the data types for Key-value pairs.
	
	ConcurrentHashMap<String, String>documents = new ConcurrentHashMap<String, String>();
	

	public static void main(String[] args) {
		// 2) Create an instance of the class
		B1TextLoader loader = new B1TextLoader();
		
		
		//Use a scanner object to receive the name of the text file as a command line argument
			Scanner r = new Scanner(System.in);
		
			System.out.println("What text file would you like to pipe?");
		
			String nameOfTextFile = r.nextLine();
			loader.LoadTextFile(nameOfTextFile);
		
		
			//Use a scanner object to receive the name of the JSON data store as a command line argument
			Scanner a = new Scanner(System.in);
			System.out.println("State name of JSON document");
		
			String nameOfJSONDocument = a.nextLine();
			loader.SaveDocumentsToJSON(nameOfJSONDocument);

	}
	// Part 1
	// Load data
	// 1) Create a method that will hold our loading code.
	//
	public void LoadTextFile(String filepath) {
		
		// 3d) Try/catch required when reading in files in case there's an issue
			// generally would have a different catch statement for different scenarios but will just use one in this case.
		try 
		
		{
			// 3) Loading data into Java
				// Use BufferedReader to load a file in line-by-line
			System.out.println("Loading file..");
			// 3a) Create a Java File object which will contain a reference to the file we want to load
			File f = new File(filepath);
			// 3b) Create a BufferedReader which will let us access the contents of the file
			// decorator design pattern
			BufferedReader br = new BufferedReader(new FileReader(f));
			// readLine() returns a string representing the contents of the line and null if we have reached the end of the file
			String line = br.readLine();
			int counter = 0;
			// 3c) Iterate through the file until we get a null line returned which signals EOF
			while(line != null) {
				// .trim removes leading or trailing whitespace so ensure we also remove any lines of whitespace
				// .length ensures that we don't load a blank line, if we find one, it'll just skip it
				if(line.trim().length() > 0) {
					// key will be doc01 etc as the string "doc" and counter will be concat together
					// the value of the K,V pair will be the line that's read in and stored by String line variable
					documents.put("doc" + counter, line);
					counter++;
				}
				// read in next line to be added to documents ConcurrentHashMap
				line = br.readLine();
			}
			// good practice to close the reader object otherwise takes up unnecessary time
			br.close();
		}
		catch(Exception e)
		{
			System.out.println("File load failed");
		}
		// .size() method is similar length - it will return how many items are stored in documents
		System.out.println("Load Complete. Lines loads: " + documents.size());
		// Putting this here ensures it runs AFTER we've finished loading
		CountWordsInDocuments(documents);
		


	}
	
	// Part 3 (optional)
	// Interrogate data 
		//Counting words
	
	
	public void CountWordsInDocuments(ConcurrentHashMap<String, String>documents) {
		// for each element in the documents data structure we want to pass the information about it to the method CountWordsinDocument.
		// this:: tells Java that the method we want to use is in the same class
		// foreach allows us to iterate through each item in a ConcurrentHashMap and deal with each pf them individually
		documents.forEach(this::CountWordsInDocument);
	}
	
	public void CountWordsInDocument(String key, String value) {
		String[] words = value.split(" ");
		System.out.println(key + " has " + words.length + "words!");
		
	}
	
	
	// Part 4
	//Create a method and call on helper class to create JSON structure, add data to the structure and then save to JSON
	public void SaveDocumentsToJSON(String filename) {
		JSONIOHelper JSONIO = new JSONIOHelper();
		JSONIO.CreateBasicsJSONStructure();
		JSONIO.AddDocumentsToJSONStructure(documents);
		JSONIO.SaveJSON(filename);
		
	}
	
}
