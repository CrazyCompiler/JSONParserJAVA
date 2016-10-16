package util;

public class EmptyStringExtractor implements Extractors {
    public String extract(String data) {
        return data.replaceAll("\\s+","");
    }
}
