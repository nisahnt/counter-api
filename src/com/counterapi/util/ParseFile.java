package com.counterapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

@Repository
public class ParseFile {

	private Map<String, Integer> sortedMap;

	public ParseFile(){		
		try{
			HashMap<String, Integer> mapWords = new HashMap<String, Integer>();
			InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("/"+Constant.filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
			String strLine = null;
			String [] tokens;
			while ((strLine = br.readLine()) != null)   {
				tokens = strLine.split("\\s+");
				for(String token : tokens){
					if(mapWords.containsKey(token))
						mapWords.put(token, mapWords.get(token)+1);
					else
						mapWords.put(token, 1);
				}
			}
			sortedMap = sortByValue(mapWords);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	private Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	class ValueComparator implements Comparator {

		Map map;

		public ValueComparator(Map map) {
			this.map = map;
		}

		public int compare(Object keyA, Object keyB) {
			Comparable valueA = (Comparable) map.get(keyA);
			Comparable valueB = (Comparable) map.get(keyB);
			return valueB.compareTo(valueA);
		}
	}
	public Integer searchFreq(String token){

		return sortedMap.get(token);
	}
	public Map<String, Integer> findTopN(int count){
		HashMap<String, Integer> mapTopN = new HashMap<String, Integer>();
		for(Map.Entry<String,Integer> entry : sortedMap.entrySet()) {
			if(count>0)
				count--;
			else
				break;
			mapTopN.put(entry.getKey(),entry.getValue());
		}
		
		return sortByValue(mapTopN);
	}
}
