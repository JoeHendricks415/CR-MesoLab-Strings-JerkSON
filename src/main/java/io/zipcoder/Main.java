package io.zipcoder;

import com.sun.javafx.geom.Edge;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();
        //System.out.println(output);
        // TODO: parse the data in output into items, and display to console.

        ItemParser test = new ItemParser();
        Main mainTest = new Main();

        ArrayList<String> arrayTest = test.parseRawDataIntoStringArray(output);


        System.out.println(test.parseRawDataIntoStringArray(mainTest.readRawDataToString()));

    }

}

