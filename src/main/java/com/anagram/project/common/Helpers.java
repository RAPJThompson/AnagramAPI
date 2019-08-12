package com.anagram.project.common;

import java.util.Arrays;

public class Helpers {
  public static String sortWord(String word) {
    char[] charArray = word.toCharArray();
    Arrays.sort(charArray);
    return new String(charArray);
  }
}
