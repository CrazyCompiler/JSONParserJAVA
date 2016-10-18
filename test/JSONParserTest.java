import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class JSONParserTest {

    JSONParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new JSONParser();
    }

    @Test
    public void parser_should_parse_the_string_and_return_hashmap_for_object_string() throws Exception {
        String data = "{ foo:bar, bar:foo }";
        HashMap parse = parser.parse(data);
        assertEquals("bar", parse.get("foo"));
        assertEquals("foo", parse.get("bar"));
    }

    @Test
    public void parser_should_parse_the_string_with_backslashes_emptyString_doublequots() throws Exception {
        String data = "{ \"foo\":\"bar\", \"bar\":\"foo\" }";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        assertEquals("bar", parse.get("foo"));
        assertEquals("foo", parse.get("bar"));
    }

    @Test
    public void parser_should_parse_the_string_with_object_of_object() throws Exception {
        String data = "{ \"foo\":{\"some\":\"bar\"}}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        HashMap parsedInnerObject = (HashMap) parse.get("foo");
        assertEquals("bar", parsedInnerObject.get("some"));
    }

    @Test
    public void parser_should_parse_the_string_with_tree_object_of_object() throws Exception {
        String data = "{ \"tuple\":{\"state\":{\"some\":\"bar\"},\"full\":\"half\",\"man\":\"women\"},\"some\":{\"thing\":\"works\"}}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        System.out.println(parse);
        HashMap firstInnerObject = (HashMap) parse.get("tuple");
        HashMap nextObject = (HashMap) parse.get("some");
        HashMap expectedNextObject = new HashMap();
        HashMap expectedNextInnerObject = new HashMap();
        expectedNextInnerObject.put("thing","works");
        expectedNextObject.put("some",expectedNextInnerObject);
        HashMap secondInnerObject = (HashMap) firstInnerObject.get("state");
        HashMap expectedFirstInnerObject = new HashMap();
        HashMap expectedSecondInnerObject = new HashMap();
        expectedSecondInnerObject.put("some","bar");
        expectedFirstInnerObject.put("state",expectedSecondInnerObject);
        expectedFirstInnerObject.put("full","half");
        expectedFirstInnerObject.put("man","women");
        assertEquals(expectedFirstInnerObject, firstInnerObject);
        assertEquals(expectedSecondInnerObject,secondInnerObject);
        assertEquals(expectedNextInnerObject,nextObject);
        assertEquals("bar",secondInnerObject.get("some"));
        assertEquals("works",expectedNextInnerObject.get("thing"));
    }

    @Test
    public void parser_should_parse_the_string_with_array_of_strings() throws Exception {
        String data = "{ \"foo\":[\"bar\",\"cat\",\"man\",\"name\"]}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        ArrayList result = (ArrayList) parse.get("foo");
        ArrayList expectedResult = new ArrayList();
        expectedResult.add("bar");
        expectedResult.add("cat");
        expectedResult.add("man");
        expectedResult.add("name");
        assertEquals("bar",result.get(0));
        assertEquals("cat",result.get(1));
        assertEquals("man",result.get(2));
        assertEquals("name",result.get(3));
        assertEquals(expectedResult,result);
    }

    @Test
    public void parser_should_parse_the_string_with_array_of_objects() throws Exception {
        String data = "{ \"foo\":[{\"bar\":{\"some\":\"bar\"}},{\"man\":\"name\"}]}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        ArrayList fooObject = (ArrayList) parse.get("foo");
        ArrayList expectedFooObject = new ArrayList();
        HashMap firstExpectedInnerObject = new HashMap();
        HashMap secondExpectedInnerObject = new HashMap();
        secondExpectedInnerObject.put("some","bar");
        firstExpectedInnerObject.put("bar",secondExpectedInnerObject);
        HashMap nextExpectedInnerObject = new HashMap();
        nextExpectedInnerObject.put("man","name");
        expectedFooObject.add(firstExpectedInnerObject);
        expectedFooObject.add(nextExpectedInnerObject);
        assertEquals(expectedFooObject,fooObject);
        assertEquals(firstExpectedInnerObject,fooObject.get(0));
        assertEquals(nextExpectedInnerObject,fooObject.get(1));
    }


    @Test
    public void parser_should_parse_the_string_with_array_inside_array() throws Exception {
        String data = "{ \"foo\":[[\"bar\"],[\"hello\",\"woman\"]]}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        ArrayList result = (ArrayList) parse.get("foo");
        ArrayList expectedResult = new ArrayList();
        ArrayList expectedFirstArrayList = new ArrayList();
        expectedFirstArrayList.add("bar");
        ArrayList expectedSecondArrayList = new ArrayList();
        expectedSecondArrayList.add("hello");
        expectedSecondArrayList.add("woman");
        expectedResult.add(expectedFirstArrayList);
        expectedResult.add(expectedSecondArrayList);
        assertEquals(expectedFirstArrayList,result.get(0));
        assertEquals(expectedSecondArrayList,result.get(1));
        assertEquals(expectedResult,result);

    }
}