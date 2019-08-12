package com.anagram.common;

import java.util.*;

public class Helpers {

  public static String sortWord(String word) {
    char[] charArray = word.toCharArray();
    Arrays.sort(charArray);
    return new String(charArray);
  }
}
