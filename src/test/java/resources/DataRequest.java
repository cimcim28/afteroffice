package resources;

import java.util.HashMap;
import java.util.Map;

public class DataRequest {

    //Berisi data test
    /*
     * Mapping berisi 2 komponen
     * 1. Key
     * 2. Value
     */

    public Map<String, String> addItemCollection(){
        Map<String, String> dataCollection = new HashMap<>();

        dataCollection.put("addItem", """
        {
            "name": "Macbook Pro",
            "data": {
                "year": 2025,
                "price": 10000000,
                "CPU model": "Intel Core",
                "Hard disk size": "500GB"
            }
        }
         """);


        dataCollection.put("addItem2", """
        {
            "name": "Macbook Pro",
            "data": {
                "year": 2025,
                "price": 10000000,
                "CPU model": "Intel Core",
                "Hard disk size": "500GB"
            }
        }
         """);


        return dataCollection;
    }

    public Map<String, String> updateItemCollection(){
        Map<String, String> dataCollection = new HashMap<>();

        dataCollection.put("updateItem", """
          {
                  "name": "Macbook Pro Update",
                  "data": {
                     "year": 2026,
                     "price": 12000000,
                     "CPU model": "Intel Core Update",
                     "Hard disk size": "500GB Update"
        }
                     }
         """);

         dataCollection.put("updateItem2", """
          {
                  "name": "Macbook Pro Update",
                  "data": {
                     "year": 2026,
                     "price": 12000000,
                     "CPU model": "Intel Core Update",
                     "Hard disk size": "500GB Update"
        }
                     }
         """);

        return dataCollection;
    }
}



