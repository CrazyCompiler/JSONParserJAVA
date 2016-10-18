import util.*;

import java.util.*;

public class JSONParser {
    private Extractors[] extractors = {new EmptyStringExtractor(), new DoubleQuotExtractor(), new BackSlashExtractor()};

    public HashMap parse(String data) {

        for (Extractors extractor : extractors) {
            data = extractor.extract(data);
        }

        data = data.substring(1);
        HashMap[] result = parseObject(data);
        return result[0];
    }

    private HashMap[] parseObject(String data) {
        HashMap<String, Object> obj = new HashMap<String, Object>();
        HashMap<String, String> remainingData = new HashMap<String, String>();
        HashMap[] result = new HashMap[2];

        String key = "";
        Object valueObject = new Object();
        String value = "";
        boolean isKey = true;
        boolean isString = true;

        while (data.length() != 0){
            char eachChar = data.charAt(0);
//            System.out.println(data + "_______" + eachChar);

            if (data.length() > 1){
                data = data.substring(1);
            }

            switch (eachChar) {
                case ':':
                    isKey = false;
                    break;

                case ',':
                    if (value.length() > 0)
                        obj.put(key, value);
                    else
                        obj.put(key, valueObject);
                    key = "";
                    value = "";
                    isKey = true;
                    isString = true;
                    break;

                case '{':
                    isString = false;
                    HashMap[] resultObject = parseObject(data);
                    valueObject = resultObject[0];

                    data = (String) resultObject[1].get("remainingData");
                    break;

                case '}':
                    if (value.length() > 0)
                        obj.put(key, value);
                    else
                        obj.put(key, valueObject);

                    remainingData.put("remainingData", data);
                    result[0] = obj;
                    result[1] = remainingData;
                    return result;

                case '[' :
                    isString = false;
                    HashMap resultArrayObject = parseArray(data);
                    valueObject = resultArrayObject.get("result");

                    data = (String) resultArrayObject.get("remainingData");
                    break;

                default:
                    if (isKey) {
                        key += eachChar;
                    } else if(isString){
                        value += eachChar;
                    }
            }
        }
        remainingData.put("remainingData", data);
        result[0] = obj;
        result[1] = remainingData;
        return result;
    }

    private HashMap parseArray(String data){

        HashMap<String, Object> arrayResultObject = new HashMap<String, Object>();
        ArrayList resultArray = new ArrayList();
        String value = "";
        boolean isString = true;
        while (data.length() != 0) {
            char eachChar = data.charAt(0);

            if (data.length() > 1) {
                data = data.substring(1);
            }

            switch (eachChar) {
                case ']':
                    resultArray.add(value);
                    arrayResultObject.put("remainingData",data);
                    arrayResultObject.put("result",resultArray);
                    return arrayResultObject;

                case ',':
                    resultArray.add(value);
                    value = "";
                    break;

                default:
                    if(isString){
                        value += eachChar;
                    }
            }

        }
        return arrayResultObject;
    }
}