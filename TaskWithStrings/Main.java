package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {

    public static void main(String[] args) {
        String format = "(second_name first_name middle_name DD.MM.YYYY)";
        System.out.print("Enter parameters " + format + "\n>> ");
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        int age = 0;
        String gender = "";
        Map<String,String> string_info = null;

        while (true) {
            string_info = parseString(string);
            age = calculateAge(string_info.get("Date"));
            if (string_info.size() == 0 || age == -1) {
                System.out.print("Please, try to enter parameters in this format " + format + "\n>> ");
                string = in.nextLine();
                continue;
            }
            gender = determineGender(string_info.get("FirstName"));
            break;
        }
        System.out.println("Name: " + string_info.get("Name"));
        System.out.println("Gender: " + gender);
        System.out.println("Age: " + age);
        in.close();
    }

    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDecimal(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static Map<String,String> parseString(String string) {
        Map<String,String> string_info = new HashMap<String,String>();
        String[] tokens = string.split(" ");
        if (tokens.length != 4) {
            System.out.println("Invalid number of parameters!");
            return string_info;
        }
        String second_name = tokens[0];
        String first_name = tokens[1];
        String middle_name = tokens[2];
        String birth_date = tokens[3];
        String answer = "";
        if (!isAlpha(second_name)) {
            answer += "There is error in last name! ";
        }
        if (!isAlpha(first_name)) {
            answer += "There is error in first name! ";
        }
        if (!isAlpha(middle_name)) {
            answer += "There is error in middle name! ";
        }
        if (!isDecimal(birth_date)) {
            answer += "There is error in birth date! ";
        }
        if (answer.length() > 0) {
            System.out.println(answer);
            return string_info;
        }
        // Last name preprocessing
        second_name = second_name.toLowerCase();
        String first_letter = second_name.charAt(0) + "";
        second_name = first_letter.toUpperCase() + second_name.substring(1, second_name.length());
        // Create string info
        string_info.put("Name", second_name + " " + first_name.toUpperCase().charAt(0) + "." +
                middle_name.toUpperCase().charAt(0));
        string_info.put("Date", birth_date);
        string_info.put("FirstName", first_name);
        return string_info;
    }

    public static int calculateAge(String birth_date) {
        if (birth_date == null || birth_date.split("\\.").length != 3) {
            System.out.println("There is error in birth date! ");
            return -1;
        }

        String[] tokens = birth_date.split("\\.");
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int year = Integer.parseInt(tokens[2]);
        String answer = "";
        if (day < 1 || day > 31) {
            answer += "There is error in days number! ";
        }
        if (month < 1 || month > 12) {
            answer += "There is error in months number! ";
        }
        if (year < 1920 || year > 2020) {
            answer += "There is error in years number! ";
        }
        if (answer.length() > 0) {
            System.out.println(answer);
            return -1;
        }

        Date date_now = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        String date_now_str = formatForDateNow.format(date_now);
        try {
            Date date1 = formatForDateNow.parse(date_now_str);
            Date date2 = formatForDateNow.parse(birth_date);
            long milliseconds = Math.abs(date1.getTime() - date2.getTime());
            return (int)(milliseconds / (24 * 60 * 60 * 1000) / 365);
        } catch (ParseException e) {
            System.out.println("There is error in birth date! ");
            return -1;
        }
    }

    public static String determineGender(String name) {
        String gender = "";
        String api_key = "jxpspsEPzlbSEqgScv";
        try {
            URL url = new URL("https://gender-api.com/get?key=" + api_key + "&name=" + name);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error: " + conn.getResponseCode());
            }

            InputStreamReader input = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(input);

            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            gender = json.get("gender").getAsString();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gender;
    }
}
