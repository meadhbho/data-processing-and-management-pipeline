package pipeline;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import helpers.JSONIOHelper;

public class B3DescriptiveStatistics {

	public static void main(String[] args) {
		B3DescriptiveStatistics descriptiveStats = new B3DescriptiveStatistics();
		
		Scanner r = new Scanner(System.in);
		System.out.println("What JSON Datastore would you like to access?");
		String nameOFJSONDataStore = r.nextLine();
		descriptiveStats.StartCreatingStatistics(nameOFJSONDataStore);

	}
	
	public void StartCreatingStatistics(String filePath) {
		System.out.println("Testing start creating statistics method");
		
		JSONIOHelper JSONIO = new JSONIOHelper();
		JSONIO.LoadJSON(filePath);
		ConcurrentHashMap<String,String>lemmas = JSONIO.GetLemmasFromJSONStructure();
		CountWordsInCorpus(lemmas);
		CountWordsInDocuments(lemmas);
		
	}
	
	
	private void CountWordsInCorpus(ConcurrentHashMap<String, String> lemmas) {
		
		ArrayList<String> corpus = new ArrayList<String>();
		ConcurrentHashMap<String, Integer>counts =  new ConcurrentHashMap<String, Integer>();
		
		for(Entry<String, String>entry: lemmas.entrySet()) {
			
			String words = entry.getValue();
			String[] words1 = words.split(" ");
			
			for(int i = 0; i < words1.length; i++) {
				corpus.add(words1[i]);
			}
		}
		
			for(String key: corpus) {	
				counts.merge(key, 1, Integer::sum);
			
		}
			
		for (Entry<String, Integer> entry : counts.entrySet()) {
			  String key = entry.getKey();
			  Integer value = entry.getValue();
			  System.out.println(key + "," + value);
		}
		
		
		
		OutputCountsAsCSV(counts, "Counts.csv");
		
		
	}
	
	private void OutputCountsAsCSV(ConcurrentHashMap<String, Integer> counts, String filename) {
		
		
		String CSVOutput = "";
		
		for(Entry<String, Integer>entry: counts.entrySet()) {
			
			CSVOutput += (entry.getKey() + "," + entry.getValue() + System.lineSeparator());	
			
		}
		
		try(FileWriter writer = new FileWriter(filename)) {
			writer.write(CSVOutput);
			System.out.println("CSV file created saved successfully");
			
		}
		
		catch(Exception e) {
			System.out.println("Saving string to CSV...");
		}
	}
	
	
	private void CountWordsInDocuments(ConcurrentHashMap<String, String> lemmas) {
		
		ConcurrentHashMap<String, Integer>counts =  new ConcurrentHashMap<String, Integer>();
		
		for(Entry<String, String>entry: lemmas.entrySet()) {
			int count = 0;
			String words = entry.getValue();
			String[] words1 = words.split(" ");
			count += words1.length;
			counts.put(entry.getKey(), count);		
		
		}
		
		
		OutputCountsAsCSV(counts, "DocumentCounts.csv");

		
		
	}
}


		


