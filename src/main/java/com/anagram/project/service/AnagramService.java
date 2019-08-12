package com.anagram.project.service;

import java.util.*;

public interface AnagramService {

  Integer addWords(List<String> words);

  List<String> getWordAnagrams(String word, Integer maxResponses);

  Boolean deleteWord(String word);

  Boolean deleteAnagrams(String word);

  Boolean deleteAllWords();

  Boolean areAnagrams(List<String> words);

  List<Set<String>> getAnagramsBySize(Integer minSize);
}
