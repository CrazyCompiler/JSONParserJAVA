import util.*;

import java.util.HashMap;

public class JSONParser {
    private Extractors[] extractors = {new EmptyStringExtractor() , new DoubleQuotExtractor(), new BackSlashExtractor()};

    public HashMap parse(String data) {
        HashMap<String,Object> obj= new HashMap<String,Object>();

        for (Extractors extractor : extractors ){
            data = extractor.extract(data);
        }
        data = data.substring(1,data.length()-1);
        String[] splitedData = data.split(",");

        for(String unit : splitedData){
            String key = unit.split(":")[0];
            int indexOfFirstSemicolon = unit.indexOf(":")+1;
            String value = unit.substring(indexOfFirstSemicolon);
            obj.put(key,parseValue(value));
        }

        return obj;
    }

    private Object parseValue(String value){
        if (value.charAt(0) == '{')
            return parseObjectValue(value);
        if (value.charAt(0) == '[')
            return parseArrayValue(value);
        return value;
    }

    private Object parseObjectValue(String value){
        return parse(value);
    }

    private Object parseArrayValue(String value){
        Object[] valueArray;
        System.out.println(value);
        return value;

    }

}
