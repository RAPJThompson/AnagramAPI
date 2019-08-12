package com.anagram.project.dao.impl;

import com.anagram.project.common.*;
import com.anagram.project.dao.*;
import java.io.*;
import java.nio.charset.*;
import java.util.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Component
public class DictionaryDaoImpl implements AnagramDao {

  private static final String DICTIONARY_NAME = "dictionary.txt";
  private HashMap<Integer, Set<String>> dictionary;
  private Integer totalWords;

  public DictionaryDaoImpl() {
    this.dictionary = new HashMap<>();
    this.totalWords = 0;

    try {
      InputStream inputStream =
          Thread.currentThread().getContextClassLoader().getResourceAsStream(DICTIONARY_NAME);
      BufferedReader br = new BufferedReader(new InputStreamReader(
          inputStream,
          StandardCharsets.UTF_8)
      );
      String line = br.readLine();
      System.out.println("Reading in dictionary");
      while (line != null) {
        addToDictionary(line);
        line = br.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addToDictionary(String word) {
    String sortedWord = Helpers.sortWord(word);
    Integer hashedWord = sortedWord.hashCode();

    Set<String> anagrams = dictionary.get(hashedWord);
    if (anagrams != null) {
      if (!anagrams.contains(word)) {
        anagrams.add(word);
        this.totalWords++;
      }
    } else {
      anagrams = new HashSet<>();
      anagrams.add(word);
      this.totalWords++;
      dictionary.put(hashedWord, anagrams);
    }
  }

  private LinkedHashMap<Integer, List<Set<String>>> sortAnagramsBySize() {
    LinkedHashMap<Integer, List<Set<String>>> setSizeMap = new LinkedHashMap<>();
    Set<Integer> keySet = this.dictionary.keySet();
    for (Integer key : keySet) {
      Set<String> anagrams = this.dictionary.get(key);
      List<Set<String>> list = setSizeMap.get(anagrams.size());
      if (list == null) {
        list = new ArrayList<>();
        setSizeMap.put(anagrams.size(), list);
      }
      list.add(anagrams);
    }
    return setSizeMap;
  }

  @Override
  public Integer addWords(List<String> words) {
    for (String word : words) {
      addToDictionary(word);
    }
    return this.totalWords;
  }

  @Override
  public List<String> getWordAnagrams(String word, Integer maxResponses) {
    Integer wordHash = Helpers.sortWord(word).hashCode();
    Set<String> foundAnagrams = this.dictionary.get(wordHash);
    List<String> anagramsToReturn;
    if (foundAnagrams != null) {
      anagramsToReturn = new ArrayList<>(foundAnagrams);
    } else {
      anagramsToReturn = new ArrayList<>();
    }
    anagramsToReturn.remove(word);
    if (anagramsToReturn.size() > maxResponses) {
      return anagramsToReturn.subList(0, maxResponses);
    } else {
      return anagramsToReturn;
    }
  }

  @Override
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public Boolean deleteWord(String word) {
    Integer wordHash = Helpers.sortWord(word).hashCode();
    Set<String> anagrams = this.dictionary.get(wordHash);
    if (anagrams != null) {
      anagrams.remove(word);
      this.totalWords--;
    }
    return true;
  }

  @Override
  public Boolean deleteAnagrams(String word) {
    Set<String> anagrams = this.dictionary.get(Helpers.sortWord(word).hashCode());
    anagrams.clear();
    this.totalWords -= anagrams.size();
    return true;
  }

  @Override
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public Boolean deleteAllWords() {
    this.dictionary = new HashMap<>();
    this.totalWords = 0;
    return true;
  }

  @Override
  public List<Set<String>> getAnagramsBySize(Integer minSize) {
    LinkedHashMap<Integer, List<Set<String>>> sortedSets = sortAnagramsBySize();
    Set<Integer> orderedSetSized = sortedSets.keySet();

    List<Set<String>> returnList = new ArrayList<>();
    LinkedList<Integer> list = new LinkedList<>(orderedSetSized);
    Iterator<Integer> itr = list.descendingIterator();
    if (minSize == null) {
      return sortedSets.get(itr.next());
    } else {
      while (itr.hasNext()) {
        Integer numAnagrams = itr.next();
        if (numAnagrams >= minSize) {
          returnList.addAll(sortedSets.get(numAnagrams));
        }
      }
    }
    return returnList;
  }
}
