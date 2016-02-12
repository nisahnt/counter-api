package com.counterapi.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.counterapi.util.ParseFile;

@Controller
public class FrequencyCalculator {

	@Autowired
	private ParseFile parsedFile;
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String getFreqCount(@RequestParam("searchText") String[] words, Model model){
		HashMap<String, Integer> mapWords = new HashMap<String, Integer>();
		for(String word: words)
			mapWords.put(word, parsedFile.searchFreq(word));
		model.addAttribute("counts", mapWords);
        return "jsonTemplate";
	}
	@RequestMapping(value = "/top/{count}", method = RequestMethod.GET)
	public String getTop(@PathVariable int count, Model model){
		Map<String, Integer> mapWords = parsedFile.findTopN(count);
		model.addAttribute("top:"+count, mapWords);
        return "jsonTemplate";
	}
	
	
}
