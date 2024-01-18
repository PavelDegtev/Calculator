package ru.pavel.calc;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class Calculator {
    static boolean arabic;

    public static void main(String[] args) throws Exception{
        while (true) {
            String input = getInputString();
            if (input.equals("exit")) {
                exit(0);
            }
            boolean check = checkCorrectInput(input);
            if (!check) {
                System.out.println("Not correct input!");
                throw (new Exception("Not correct input"));
                //exit(1);
            }
            String answer = calc(input);
            System.out.println("RESULT: " + answer);
        }
    }

    static String getInputString() {
        String s = "";
        System.out.print("Enter a mathematical expression (or \"exit\" to quit) :");
        Scanner console = new Scanner(System.in);
        s = console.nextLine();
        return s;
    }

    static boolean checkCorrectInput(String input) {
        input = input.replaceAll("\\s+", "");
        boolean res = false;
        if (input.matches("-?\\d{1,2}(\\+|\\-|\\*|/)\\d{1,2}")) {
            arabic = true;
            res = true;
        }
        if (input.matches("(I|II|III|IV|V|VI|VII|VIII|IX|X)(\\+|-|\\*|/)(I|II|III|IV|V|VI|VII|VIII|IX|X)")) {
            arabic = false;
            res = true;
        }
        return res;
    }

    public static String calc(String input) throws Exception{
        int res = 0;
        input = input.replaceAll("\\s+", "");
        if (arabic) {
            String firstSign = "+";
            if (input.startsWith("-")) {
                firstSign = "-";
                input = input.substring(1);
            }
            String reg = "([\\+|\\*|\\-|/])";
            String[] digs = input.split(reg);
            String sign = input.substring(input.indexOf(digs[0]) + digs[0].length(), input.indexOf(digs[0]) + digs[0].length() +1);
            System.out.println("first sign is: " + firstSign);
            System.out.println(Arrays.toString(digs));
            System.out.println(sign);

            int a = Integer.parseInt(digs[0]);
            int b = Integer.parseInt(digs[1]);
            if(a > 10 || b > 10) {
                throw(new Exception("Both or one parameter is more than 10!"));
            }
            int firstSignInt = (firstSign.equals("-") ? -1 : 1);
            switch (sign) {
                case "+":
                    res = firstSignInt * a + b;
                    break;
                case "-":
                    res = firstSignInt * a - b;
                    break;
                case "*":
                    res = firstSignInt * a * b;
                    break;
                case "/":
                    res = firstSignInt * a / b;
                    break;
            }
            return Integer.toString(res);
        } else {


            //Roman
            String reg = "([\\+|\\*|\\-|/])";
            String[] digs = input.split(reg);
            System.out.println(Arrays.toString(digs));
            String sign = input.substring(digs[0].length(), digs[0].length() + 1);
            System.out.println("sign: " + sign);
            int a = fromRomanToInt(digs[0]);
            int b = fromRomanToInt(digs[1]);
            System.out.println("a: " + a);
            System.out.println("b: " + b);
            switch (sign) {
                case "+":
                    res = a + b;
                    break;
                case "-":
                    res = a - b;
                    break;
                case "*":
                    res = a * b;
                    break;
                case "/":
                    res = a / b;
                    break;
            }
        }
        if (res<1) {
            throw(new Exception("Roman result < 1"));
        }
        return arabicToRoman(res);
    }


    static int fromRomanToInt(String romanDigit) {
        int res = 0;
        String[] romanEtalon = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (int i = 0; i < romanEtalon.length; i++) {
            if (romanEtalon[i].equals(romanDigit)) {
                res = i + 1;
            }
        }
        return res;
    }


    /*Взято из интернета*/
    public static String arabicToRoman(int number) {
        if (number < 0 || number > 3999) {
            return "This number cannot be converted";
        }

        String romanOnes = romanDigit(number % 10, "I", "V", "X");
        number /= 10;

        String romanTens = romanDigit(number % 10, "X", "L", "C");
        number /= 10;

        String romanHundreds = romanDigit(number % 10, "C", "D", "M");
        number /= 10;

        String romanThousands = romanDigit(number % 10, "M", "", "");
        number /= 10;

        String result = romanThousands + romanHundreds + romanTens + romanOnes;
        return result;
    }

    public static String romanDigit(int n, String one, String five, String ten) {

        if (n >= 1) {
            if (n == 1) {
                return one;
            } else if (n == 2) {
                return one + one;
            } else if (n == 3) {
                return one + one + one;
            } else if (n == 4) {
                return one + five;
            } else if (n == 5) {
                return five;
            } else if (n == 6) {
                return five + one;
            } else if (n == 7) {
                return five + one + one;
            } else if (n == 8) {
                return five + one + one + one;
            } else if (n == 9) {
                return one + ten;
            }

        }
        return "";
    }


}



