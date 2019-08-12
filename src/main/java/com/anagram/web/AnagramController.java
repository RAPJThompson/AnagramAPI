package com.anagram.web;

import com.anagram.service.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnagramController {

  private static final String maxResponses = Integer.toString(Integer.MAX_VALUE);
  private final AnagramService anagramService;

  @Autowired
  public AnagramController(AnagramService anagramService) {
    this.anagramService = anagramService;
  }

  @RequestMapping(
      value = "/words.json",
      method = RequestMethod.POST
  )
  @ResponseStatus(value = HttpStatus.CREATED)
  void addWords(@RequestBody IOObject anagrams) {
    anagramService.addWords(anagrams.getWords());
  }

  @RequestMapping(
      value = "/anagrams/{word}.json",
      method = RequestMethod.GET
  )
  public IOObject getWordAnagrams(@PathVariable String word,
      @RequestParam(required = false) Integer limit) {
    return new IOObject().setWords(anagramService.getWordAnagrams(word, limit));
  }

  @RequestMapping(
      value = "/words/{word}.json",
      method = RequestMethod.DELETE
  )
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  void deleteWord(@PathVariable String word) {
    if (!anagramService.deleteWord(word)) {
      System.out.println("Word " + word + " not found in known words.");
    }
  }

  @RequestMapping(
      value = "/words/anagrams/{word}.json",
      method = RequestMethod.DELETE
  )
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  void deleteAnagrams(@PathVariable String word) {
    if (!anagramService.deleteAnagrams(word)) {
      System.out.println("Word " + word + " not found in known words.");
    }
  }

  @RequestMapping(
      value = "/words.json",
      method = RequestMethod.DELETE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  void deleteAllWords() {
    if (!anagramService.deleteAllWords()) {
      System.out.println("Words unsuccessfully deleted.");
    }
  }

  @RequestMapping(
      value = "/words/check",
      method = RequestMethod.POST
  )
  Boolean checkWordsForAnagrams(@RequestBody IOObject words) {
    return anagramService.areAnagrams(words.getWords());
  }

  @RequestMapping(
      value = "/anagrams/size",
      method = RequestMethod.GET
  )
  List<IOObject> getAnagramsBySize(@RequestParam(required = false) Integer minSize) {
    List<IOObject> returnList = new ArrayList<>();
    for (Set<String> set : anagramService.getAnagramsBySize(minSize)) {
      returnList.add(new IOObject().setWords(new ArrayList<>(set)));
    }
    return returnList;
  }

}