package com.minesweeper.utils;

public class CommonUtils {
    public static int getInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static char numberToAlphabet(int number) {
        return (char) ('A' + (number - 1));
    }
    
    public static int alphabetToNumber(char alphabet) {
        return alphabet - 'A' + 1;
    }
}
