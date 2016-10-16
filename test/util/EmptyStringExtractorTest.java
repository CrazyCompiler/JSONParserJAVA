package util;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyStringExtractorTest {
    @Test
    public void extract_should_extract_all_empty_strings() throws Exception {
        String data = " ";
        EmptyStringExtractor emptyStringExtractor = new EmptyStringExtractor();
        String extractedString = emptyStringExtractor.extract(data);
        assertFalse(extractedString.contains(" "));
    }
}