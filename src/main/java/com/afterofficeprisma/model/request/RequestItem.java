package com.afterofficeprisma.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestItem {

    /*
     * {
                "name": "Macbook Pro",
                "data": {
                    "year": 2025,
                    "price": 10000000,
                    "CPU model": "Intel Core",
                    "Hard disk size": "500GB"
                }
            }
     */

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public DataItem dataItem;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class DataItem{

        @JsonProperty("color")
        public String color;

        @JsonProperty("capacity")
        public String capacity;

        @JsonProperty("year")
        public int year;

        @JsonProperty("price")
        public double price;

        @JsonProperty("CPU model")
        public String cpuModel;

        @JsonProperty("Hard disk size")
        public String hardDiskSize;
    }
}