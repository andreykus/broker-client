package com.bivgroup.common;

import com.bivgroup.common.monad.Function;
import com.bivgroup.common.monad.Maybe;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bush on 15.07.2016.
 */
public class MaybeTest extends TestCase {

    public void testMap() throws Exception {
        String s = new String("111ddssdsd23333");
        Maybe<String> trst =
                Maybe.instance(s).map(new Function<String, List<String>>() {
                                          public List<String> apply(String s) {
                                              return Arrays.asList(s.split("s"));
                                          }
                                      }
                ).map(new Function<List<String>, String>() {
                          public String apply(List<String> s) {
                              StringBuilder sb = new StringBuilder();
                              for (String ss : s) {
                                  sb.append(ss);
                              }
                              return sb.toString();
                          }
                      }
                );
        Assert.assertEquals(trst.get(), "111dddd23333");

    }
}