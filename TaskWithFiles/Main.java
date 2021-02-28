package com.company;

import java.io.*;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int attempts = 5, attempts_counter = 0;
        String file_name = "";
        System.out.print("Enter the file name:\n>> ");
        while (attempts_counter < attempts) {
            try {
                file_name = bufferedReader.readLine();
            }
            catch (IOException e) {
                System.out.println("Unable to read data from keyboard");
            }
            if (countLettersInFile(file_name)) {
                break;
            }
            attempts_counter++;
        }
    }

    public static boolean countLettersInFile(String fileName) {
        int[] buffer = new int[26];
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                line = line.toLowerCase();
                int size = line.length();
                for (int i = 0; i < size; i++) {
                    if ((int) line.charAt(i) >= 97 && (int) line.charAt(i) <= 122) {
                        buffer[(int) line.charAt(i) - 97] += 1;
                    }
                }
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.print("ERROR: File not found! Try to enter the correct file name / path!\n>> ");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter("result.txt", false)) {
            for (int i = 0; i < 26; i++) {
                String line = "" + (char) (i + 97) + " - " + buffer[i] + "\n";
                writer.write(line);
            }
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
}
