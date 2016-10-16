package util;

public class BackSlashExtractor implements Extractors {
    public String extract(String data) {
        return data.replaceAll("\\/","");
    }
}
