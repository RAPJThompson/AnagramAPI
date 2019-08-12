package com.anagram.dao;

import java.util.*;

public interface AnagramDao {

  Integer addWords(List<String> words);

  List<String> getWordAnagrams(String word, Integer maxResponses);

  Boolean deleteWord(String word);

  Boolean deleteAnagrams(String word);

  Boolean deleteAllWords();

  List<Set<String>> getAnagramsBySize(Integer minSize);
}
