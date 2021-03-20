import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String currentStr;
        Scanner scanner = new Scanner(System.in);
        currentStr = scanner.nextLine();
        currentStr = parseString(currentStr);
        if (currentStr == null) {
            System.err.println("Invalid string");
            System.exit(1);
        } else System.out.println(currentStr);
    }

    public static String parseString(String currentStr) {
        char[] symbols = currentStr.toCharArray();
        StringBuilder newStr = new StringBuilder();
        if (validateString(currentStr) == null) return null;
        int count = getReiterationsCount(currentStr);
        if (count == 0) return currentStr;
        while (count > 0) {
            newStr = new StringBuilder();
            int multiplier, counter = 0, i = 0;
            while (i < symbols.length) {
                if (symbols[i] == '[') {
                    multiplier = getMultiplier(symbols, i);
                    i++;
                    int first = i;
                    while (symbols[i] != ']' || counter > 0) {
                        if (symbols[i] == '[') counter++;
                        if (counter > 0 && symbols[i] == ']') counter--;
                        i++;
                    }
                    while (multiplier > 0) {
                        for (int j = first; j < i; j++) {
                            newStr.append(symbols[j]);
                        }
                        multiplier--;
                    }
                } else {
                    if (symbols[i] != ']' && (int) symbols[i] - '0' > 10)
                        newStr.append(symbols[i]);
                    i++;
                }
            }
            count--;
            symbols = String.valueOf(newStr).toCharArray();
        }
        return String.valueOf(newStr);
    }

    //Возвращает null если строка невалидна, иначе возвращает строку
    public static String validateString(String str) {
        if (str == null) return null;
        int counter = 0;
        boolean err = false;
        char[] string = str.toCharArray();
        for (int i = 0; i < string.length; i++) {
            if (string[i] >= '0' && string[i] <= '9') {
                if (string[i + 1] != '[')
                    if (i + 1 == string.length || (string[i + 1] >= 'A' && string[i] <= 'Z') || (string[i + 1] >= 'a' && string[i] <= 'z')) {
                        err = true;
                        break;
                    }
            } else if (string[i] == '[') {
                if (i - 1 < 0 || string[i - 1] < '0' || string[i - 1] > '9') {
                    err = true;
                    break;
                }
                counter++;
            } else if (string[i] == ']') counter--;

            if (counter < 0) {
                err = true;
                break;
            }
        }

        if (counter != 0) {
            err = true;
        }

        if (err) {
            return null;
        } else return str;

    }

    public static int getReiterationsCount(String str) {
        if (str == null) return 0;
        char[] string = str.toCharArray();
        int count = 0;
        for (char symbol : string) {
            if (symbol == '[') {
                count++;
            }
        }
        return count;
    }

    public static int getMultiplier(char[] string, int i) {
        int multiplier = 0;
        int power = 0;
        i--;
        while (i >= 0 && string[i] >= '0' && string[i] <= '9') {
            multiplier += ((int) string[i] - '0') * Math.pow(10, power);
            i--;
            power++;
        }
        return multiplier;
    }
}
