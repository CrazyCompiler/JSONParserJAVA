import util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONParser {
    private Extractors[] extractors = {new EmptyStringExtractor() , new DoubleQuotExtractor(), new BackSlashExtractor()};

    public HashMap parse(String data) {

        for (Extractors extractor : extractors ){
            data = extractor.extract(data);
        }

        data = data.substring(1);

        ArrayList<Character> splitedData = new ArrayList<Character>();

        for(char c : data.toCharArray()){
            splitedData.add(c);
        }

        HashMap[] result =  parseObject(splitedData);
        return result[0];
    }

    private HashMap[] parseObject(List<Character> data){
        HashMap<String,Object> obj = new HashMap<String,Object>();
        HashMap<String,List> remainingData = new HashMap<String,List>();
        HashMap[] result = new HashMap[2];

        String key = "";
        Object valueObject = new Object();
        String value = "";

        boolean isValueObject = false;

        boolean isKey = true;
        int numberOfChar = 0;

        for(char eachChar : data){
            numberOfChar++;
            switch (eachChar){
                case ':' :
                    isKey = false;
                    break;
                case ',' :
                    if(value.length() > 0)
                        obj.put(key,value);
                    else
                        obj.put(key,valueObject);
                    key = "";
                    value = "";
                    isKey = true;
                    break;
                case '}' :
                    if(isValueObject)
                        obj.put(key,valueObject);
                    else
                        obj.put(key,value);
                    if (numberOfChar > data.size())
                        remainingData.put("remainingData",data);
                    else
                        remainingData.put("remainingData",data.subList(numberOfChar,data.size()));
                    result[0] = obj;
                    result[1] = remainingData;
                    return result;
                case '{':
                    isValueObject = true;
                    System.out.println(data);
                    if(data.indexOf('}') > 0){
                        HashMap[] resultObject = parseObject(data.subList(numberOfChar,data.size()));
                        valueObject = resultObject[0];
                        data = (List<Character>) resultObject[1].get("remainingData");
                    }
                    break;
                default :
                    if(isKey) {
                        key += eachChar;
                    }
                    else{
                        value += eachChar;
                    }
            }
        }
        remainingData.put("remainingData",data.subList(numberOfChar,data.size()));
        result[0] = obj;
        result[1] = remainingData;
        return result;
    }
}
