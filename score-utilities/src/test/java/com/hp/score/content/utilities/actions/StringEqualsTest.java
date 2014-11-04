package com.hp.score.content.utilities.actions;

import com.hp.score.content.utilities.utils.Constants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by persdana on 11/4/2014.
 */
public class StringEqualsTest {

    private static final String MATCHES = "Matches";
    private static final String DO_NOT_MATCH = "Does not Match";

    @Test
     public void testWithEqualsStrings() {
        StringEquals toTest = new StringEquals();
        String str1 = "abc";
        String str2 = "abc";

        Map<String, String> result = toTest.compareStrings(str1, str2, false);
        String returnResult = result.get(Constants.OutputNames.RETURN_RESULT);

        Assert.assertEquals(MATCHES, returnResult);
    }

    @Test
    public void testWithNotEqualsStrings() {
        StringEquals toTest = new StringEquals();
        String str1 = "abc";
        String str2 = "abC";

        Map<String, String> result = toTest.compareStrings(str1, str2, false);
        String returnResult = result.get(Constants.OutputNames.RETURN_RESULT);

        Assert.assertEquals(DO_NOT_MATCH, returnResult);
    }

    @Test
    public void testWithEqualsStringsIgnoreCase() {
        StringEquals toTest = new StringEquals();
        String str1 = "abc";
        String str2 = "abC";

        Map<String, String> result = toTest.compareStrings(str1, str2, true);
        String returnResult = result.get(Constants.OutputNames.RETURN_RESULT);

        Assert.assertEquals(MATCHES, returnResult);
    }

    @Test
    public void testWithNotEqualsStringsIgnoreCase() {
        StringEquals toTest = new StringEquals();
        String str1 = "abc";
        String str2 = "abcd";

        Map<String, String> result = toTest.compareStrings(str1, str2, false);
        String returnResult = result.get(Constants.OutputNames.RETURN_RESULT);

        Assert.assertEquals(DO_NOT_MATCH, returnResult);
    }


}
