import org.junit.Before;
import org.junit.Test;

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
        String data = "{ \"tuple\":{\"state\":{\"some\":\"bar\"}}}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        HashMap firstInnerObject = (HashMap) parse.get("tuple");
        HashMap secondInnerObject = (HashMap) firstInnerObject.get("state");
        HashMap expectedFirstInnerObject = new HashMap();
        HashMap expectedSecondInnerObject = new HashMap();
        expectedSecondInnerObject.put("some","bar");
        expectedFirstInnerObject.put("state",expectedSecondInnerObject);
        assertEquals(expectedFirstInnerObject, firstInnerObject);
        assertEquals(expectedSecondInnerObject,secondInnerObject);
        assertEquals("bar",secondInnerObject.get("some"));
    }

    @Test
    public void parser_should_parse_the_string_with_array_of_object() throws Exception {
        String data = "{ \"foo\":[\"some\",\"man\"],[\"bar\",\"cat\"]}";
        JSONParser parser = new JSONParser();
        HashMap parse = parser.parse(data);
        String[] parsedInnerArray = (String[]) parse.get("foo");
        System.out.println(parsedInnerArray);
    }
}