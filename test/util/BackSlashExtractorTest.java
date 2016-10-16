package util;

import util.Extractors;
import org.junit.Test;

import static org.junit.Assert.*;

public class BackSlashExtractorTest {
    @Test
    public void extract_should_extract_all_BackSlashes() throws Exception {
        String data = "bla\"bla\" ";
        BackSlashExtractor backSlashExtractor = new BackSlashExtractor();
        String extractedString = backSlashExtractor.extract(data);
        assertFalse(extractedString.contains("\\"));
    }

}