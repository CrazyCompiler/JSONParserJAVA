package util;

public class DoubleQuotExtractor implements Extractors {
    public String extract(String data) {
        return data.replace("\"","");
    }

}
