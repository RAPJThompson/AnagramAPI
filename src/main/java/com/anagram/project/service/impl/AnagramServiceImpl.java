package com.anagram.project.service.impl;

import com.anagram.project.common.*;
import com.anagram.project.dao.*;
import com.anagram.project.service.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Component
public class AnagramServiceImpl implements AnagramService {

  private AnagramDao anagramDao;

  @Autowired
  public AnagramServiceImpl(AnagramDao anagramDao) {
    this.anagramDao = anagramDao;
  }

  @Override
  public Integer addWords(List<String> words) {
    List<String> lowerCaseWords = words.stream().map(String::toLowerCase)
        .collect(Collectors.toList());
    return anagramDao.addWords(lowerCaseWords);
  }

  @Override
  public List<String> getWordAnagrams(String word, Integer maxResponses) {
    return anagramDao
        .getWordAnagrams(word.toLowerCase(),
            Objects.requireNonNullElse(maxResponses, Integer.MAX_VALUE));
  }

  @Override
  public Boolean deleteWord(String word) {
    return anagramDao.deleteWord(word.toLowerCase());
  }

  @Override
  public Boolean deleteAnagrams(String word) {
    return anagramDao.deleteAnagrams(word.toLowerCase());
  }

  @Override
  public Boolean deleteAllWords() {
    return anagramDao.deleteAllWords();
  }

  @Override
  public Boolean areAnagrams(List<String> words) {
    Integer hash = Helpers.sortWord(words.get(0)).hashCode();
    for (String word : words) {
      if (Helpers.sortWord(word).hashCode() != hash) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<Set<String>> getAnagramsBySize(Integer minSize) {
    return anagramDao.getAnagramsBySize(minSize);
  }


}
