package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    Pattern pattern;
    Matcher matcher;
    public int counter = 0;

    private Main main = new Main();
    private Map<String, ArrayList<Item>> groceryMap = new HashMap<String, ArrayList<Item>>();


    public ArrayList<String> parseRawDataIntoStringArray(String rawData) {
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException {

        if (findName(rawItem) == null || findPrice(rawItem) == null) {
            throw new ItemParseException();
        }

        String itemName = findName(rawItem);
        Double itemPrice = Double.parseDouble(findPrice(rawItem));
        String itemType = findType(rawItem);
        String itemExpiration = findExpiration(rawItem);

        return new Item(itemName, itemPrice, itemType, itemExpiration);
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem) {
        String stringPattern = "[;|^]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString) {
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    private String findName(String rawItem){
        String search = "(?<=([Nn][Aa][Mm][Ee][^A-Za-z])).*?(?=[^A-Za-z0])";
        pattern = Pattern.compile(search);
        matcher = pattern.matcher(rawItem);

        if (matcher.find()) {
            if (!matcher.group().equals("")) {
                String name = matcher.group().replaceAll("\\d", "o");
                return name.toLowerCase();
                //return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
            }
        }
        return null;
    }

    private String findPrice(String rawItem){
        pattern = Pattern.compile("(?<=([Pp][Rr][Ii][Cc][Ee][^A-Za-z])).*?(?=[^0-9.])");
        matcher = pattern.matcher(rawItem);

        if (matcher.find()) {
            if (!matcher.group().equals("")) {
                return matcher.group();
            }
        }
        return null;
    }

    public String findType(String rawItem) {
        pattern = Pattern.compile("(?<=([Tt][Yy][Pp][Ee][^A-Za-z])).*?(?=[^A-Za-z0])");
        matcher = pattern.matcher(rawItem);

        if (matcher.find()) {
            return matcher.group().toLowerCase();
        }
        return null;
    }

    public String findExpiration(String rawItem) {
        pattern = Pattern.compile("(?<=([Ee][Xx][Pp][Ii][Rr][Aa][Tt][Ii][Oo][Nn][^A-Za-z]))(.)+[^#]");
        matcher = pattern.matcher(rawItem);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public Map<String, ArrayList<Item>> getMap() throws Exception {

        ArrayList<String> listOfItems = parseRawDataIntoStringArray(main.readRawDataToString());
        for (String anItem : listOfItems) {
            try {
                Item newItem = parseStringIntoItem(anItem);
                if (!groceryMap.containsKey(newItem.getName())) {
                    ArrayList<Item> myItem = new ArrayList<Item>();
                    myItem.add(newItem);
                    groceryMap.put(newItem.getName(), myItem);
                } else {
                    groceryMap.get(newItem.getName()).add(newItem);
                }
            } catch (ItemParseException e) {
                counter++;
            }
        }
        return groceryMap;
    }

    public String display(){
        for(Map.Entry<String, ArrayList<Item>> mapKey : groceryMap.entrySet()) {
            System.out.println(mapKey.getKey());
            for (Item item : mapKey.getValue()) {
                System.out.println(item.getPrice());
            }
        }
        System.out.println("Errors              seen: " + counter + " times");
        return null;
        }

    public
    }

