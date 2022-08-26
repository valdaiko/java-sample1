package com.company;

import java.io.Console;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        Random rand = new Random();
        int randomNum = rand.nextInt(1000);

        String url = "http://numbersapi.com/" + randomNum + "/trivia";
        System.out.println("Source url is " + url);


        String sourceString = null;
        try {
            sourceString = Download(url);
            System.out.println("Source string is " + sourceString);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        if (sourceString != null) {
            TreeMap<Character, Integer> treeMap = ParseString(sourceString);

            int sum = 0;
            for (Map.Entry<Character, Integer> entry : treeMap.entrySet()) {
                char key = entry.getKey();
                int value = entry.getValue();
                sum += value;

                System.out.println(key + " - " + value + " entries");
            }

            float avg = sum / (float)treeMap.size();
            System.out.println("Average entries is " + avg);

        } else {
            System.out.println("text was empty");
        }

    }


    public static String Download(String url) throws Exception {
        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream response = connection.getInputStream();
            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            return responseBody;
        } catch (Exception ex) {
            throw new Exception("Error at downloading. Exception: " + ex.getMessage());
        }
    }

    public static TreeMap<Character, Integer> ParseString(String data) {
        if (data == null) {
            return null;
        }

        TreeMap<Character, Integer> map = new TreeMap<Character, Integer>();

        for (char ch : data.toLowerCase().toCharArray()) {
            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) + 1);
            } else {
                map.put(ch, 1);
            }
        }

        return map;
    }
}
