package io.cloudslang.content.xml.actions;

import io.cloudslang.content.xml.utils.Constants;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.Map;

/**
 * Created by markowis on 25/02/2016.
 */
public class AddAttributeTest {

    private AddAttribute addAttribute;
    String xml;

    @Before
    public void setUp() throws Exception{
        addAttribute = new AddAttribute();
        URI resource = getClass().getResource("/xml/test.xml").toURI();
        xml = FileUtils.readFileToString(new File(resource));
    }

    @After
    public void tearDown(){
        addAttribute = null;
        xml = null;
    }

    @Test
    public void testAddAttribute() {
        String xPathQuery = "/root/element1";
        String attributeName = "newAttr";
        String value = "New Value";

        Map<String, String> result = addAttribute.execute(xml, xPathQuery, attributeName, value, "false");

        Assert.assertEquals(Constants.SUCCESS, result.get(Constants.OutputNames.RESULT_TEXT));
        Assert.assertEquals(Constants.SuccessMessages.ADD_ATTRIBUTE_SUCCESS, result.get(Constants.OutputNames.RETURN_RESULT));
    }

    @Test
    public void testNotFound() {
        String xPathQuery = "/element1";
        String attributeName = "newAttr";
        String value = "New Value";

        Map<String, String> result = addAttribute.execute(xml, xPathQuery, attributeName, value, "false");

        Assert.assertEquals(Constants.FAILURE, result.get(Constants.OutputNames.RESULT_TEXT));
        Assert.assertEquals(Constants.ErrorMessages.GENERAL_ERROR + Constants.ErrorMessages.ELEMENT_NOT_FOUND,
                result.get(Constants.OutputNames.RETURN_RESULT));
    }

    @Test
    public void testNonElementXPathQuery() {
        String xPathQuery = "//element1/@attr";
        String attributeName = "newAttr";
        String value = "New Value";

        Map<String, String> result = addAttribute.execute(xml, xPathQuery, attributeName, value, "false");

        Assert.assertEquals(Constants.FAILURE, result.get(Constants.OutputNames.RESULT_TEXT));
        Assert.assertEquals(Constants.ErrorMessages.GENERAL_ERROR + Constants.ErrorMessages.ADD_ATTRIBUTE_FAILURE +
                Constants.ErrorMessages.NEED_ELEMENT_TYPE, result.get(Constants.OutputNames.RETURN_RESULT));
    }
}
