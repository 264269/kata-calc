package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println(calc(in.readLine()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String calc(String input) throws IllegalArgumentException {
        String result = null;

        String[] arguments = input.strip().split(" +");
        if (arguments.length != 3)
            throw new IllegalArgumentException("Wrong number of arguments");

        int x = 0, y = 0;
        boolean arabic = false;

        try {
            x = Integer.parseInt(arguments[0]);
            y = Integer.parseInt(arguments[2]);
            arabic = true;
        } catch (Exception ignored) { }

        if (!arabic) {
            x = romanToArabic(arguments[0]);
            y = romanToArabic(arguments[2]);
        }

        if ((x < 1) || (x > 10) || (y < 1) || (y > 10))
            throw new IllegalArgumentException("Number are not in range [1; 10]");

        switch (arguments[1]) {
            case "+":
                result = String.valueOf(x + y);
                break;
            case "-":
                result = String.valueOf(x - y);
                break;
            case "*":
                result = String.valueOf(x * y);
                break;
            case "/":
                result = String.valueOf(x / y);
                break;
            default:
                throw new IllegalArgumentException("Wrong operation argument");
        }

        if (!arabic)
            result = arabicToRoman(Integer.parseInt(result));
        return result;
    }

    static int romanToArabic(String rNum) throws IllegalArgumentException {
        rNum = rNum.toUpperCase();
        int result = 0;

        for (RomanNumeral numeral : RomanNumeral.getReverseSortedValues()) {
            while (rNum.startsWith(numeral.name())) {
                result += numeral.getValue();
                rNum = rNum.substring(numeral.name().length());
            }
        }

        if (rNum.length() != 0) throw new IllegalArgumentException("Number parsing unsuccessful");

        return result;
    }

    static String arabicToRoman(int number) {
        if (number <= 0)
            throw new IllegalArgumentException(number + " is less than 1");

        StringBuilder result = new StringBuilder();

        for (RomanNumeral numeral : RomanNumeral.getReverseSortedValues()) {
            while (numeral.getValue() <= number) {
                result.append(numeral);
                number -= numeral.getValue();
            }
        }

        return result.toString();
    }

    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);

        int value;

        RomanNumeral(int val) {
            this.value = val;
        }

        int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}