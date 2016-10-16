package util;

import util.Extractors;
import org.junit.Test;

import static org.junit.Assert.*;

public class DoubleQuotExtractorTest {
    @Test
    public void extract_should_extract_all_double_quots() throws Exception {
        String data = "bla\"bla\" ";
        DoubleQuotExtractor doubleQuotExtractor = new DoubleQuotExtractor();
        String extractedString = doubleQuotExtractor.extract(data);
        assertFalse(extractedString.contains("\""));
    }
}