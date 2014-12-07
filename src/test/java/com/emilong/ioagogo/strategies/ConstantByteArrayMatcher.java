package com.emilong.ioagogo.strategies;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConstantByteArrayMatcher extends TypeSafeMatcher<byte[]> {
  private final int value;

  public ConstantByteArrayMatcher(int value) {
    this.value = value;
  }

  @Override
  public boolean matchesSafely(byte[] array) {
    for (int i = 0; i < array.length; i++) {
      if ((array[i] & 0xFF) != value) {
        return false;
      }
    }

    return true;
  }

  public void describeTo(Description description) {
    description.appendText("not all values of the array match the given value");
  }

  @Factory
  public static <T> Matcher<byte[]> containsOnly(int value) {
    return new ConstantByteArrayMatcher(value);
  }

}
