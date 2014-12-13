package com.emilong.ioagogo.strategies;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher that expects exactly one of the given value bytes,
 * preceded only by 0 bytes.
 */
public class TaggedByteArrayMatcher extends TypeSafeMatcher<byte[]> {
  private final int value;

  public TaggedByteArrayMatcher(int value) {
    this.value = value;
  }

  @Override
  public boolean matchesSafely(byte[] array) {
    for (int i = 0; i < array.length; i++) {
      int current = (array[i] & 0xFF);
      if (current == value) {
        return true;
      }
      else if (current != 0) {
        return false;
      }
    }

    return false;
  }

  public void describeTo(Description description) {
    description.appendText("array does not contain the tag or contains invalid values");
  }

  @Factory
  public static <T> Matcher<byte[]> taggedWith(int value) {
    return new TaggedByteArrayMatcher(value);
  }

}
