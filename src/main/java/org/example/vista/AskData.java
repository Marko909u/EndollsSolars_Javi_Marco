package org.example.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class AskData {
    private BufferedReader br;

    /**
     * Default Constructor
     *
     */
    public AskData() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Method to ask a string to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @return String with the user's response. Does not allow empty strings.
     */
    public String askString(String msg) {
        System.out.print(msg);
        String answer = "";
        try {
            answer = br.readLine();
            while (answer.isEmpty()) {
                System.out.println("You cann't leave the answer blank.");
                answer = br.readLine();
            }
        } catch (IOException ex) {
            System.out.println("An unexpected error has occurred.");
        }
        return answer;
    }
    /**
     * Ask a String to user that is within a list of accepted String.
     * @param msg message to be displayed when requesting the user's data
     * @param stringsAccepted ArrayList of accepted Strings (lower case)
     * @return String with the user's response.
     */
    public String askString(String msg, ArrayList<String> stringsAccepted) {
        String answer;
        boolean ok = false;
        do {
            answer = askString(msg);
            ok = stringsAccepted.contains(answer.toLowerCase());
            if (!ok) {
                System.out.println("Wrong answer");
                System.out.println("You answer must be:");
                for (String s : stringsAccepted) {
                    System.out.println(s);
                }
            }
        } while (!ok);
        return answer;
    }

    /**
     * Method to ask a int to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @return a int number.
     */
    public int askInt(String msg) {
        int n = 0;
        boolean error = false;
        do {
            try {
                System.out.print(msg);
                n = Integer.parseInt(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("An unexpected error has occurred.");
                error = true; // It should never happen.
            } catch (NumberFormatException e) {
                System.out.println("You have to put an int number.");
                error = true;
            }
        } while (error);
        return n;
    }

    /**
     * Method to ask a int to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @param errorMsg a message to be displayed when the number is less than min
     * @param min Minimum number that the user must int.
     * @return a int number greater or equal than min.
     */
    public int askInt(String msg, String errorMsg, int min) {
        int n = askInt(msg);
        while (n < min) {
            System.out.println(errorMsg);
            n = askInt(msg);
        }
        return n;
    }

    /**
     * Method to ask a int to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @param errorMsg a message to be displayed when the number is less  than min or greater than max
     * @param min Minimum number that the user must int.
     * @param max Max number that the user must int.
     * @return a int number greater or equal than min.
     */
    public int askInt(String msg, String errorMsg, int min, int max) {
        int n = askInt(msg);
        while (n < min || n > max) {
            System.out.println(errorMsg);
            n = askInt(msg);
        }
        return n;
    }

    /**
     * Method to ask a double to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @return a double number.
     */
    public double askDouble(String msg) {
        double n = 0;
        boolean error = false;
        do {
            try {
                System.out.print(msg);
                n = Double.parseDouble(br.readLine());
                error = false;
            } catch (IOException ex) {
                System.out.println("An unexpected error has occurred.");
                error = true; // It should never happen.
            } catch (NumberFormatException e) {
                System.out.println("You have to put an int number.");
                error = true;
            }
        } while (error);
        return n;
    }


    /**
     * Method to ask a int to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @param errorMsg a message to be displayed when the number is less than min
     * @param min Minimum number that the user must double.
     * @return a double number greater or equal than min.
     */
    public double askDouble(String msg, String errorMsg, double min) {
        double n = askDouble(msg);
        while (n < min) {
            System.out.println(errorMsg);
            n = askDouble(msg);
        }
        return n;
    }

    /**
     * Method to ask a double to user
     *
     * @param msg a message to be displayed when requesting the user's data
     * @param errorMsg a message to be displayed when the number is less  than min or greater than max
     * @param min Minimum number that the user must double.
     * @param max Max number that the user must double.
     * @return a double number greater or equal than min.
     */
    public double askDouble(String msg, String errorMsg, double min, double max) {
        double n = askDouble(msg);
        while (n < min || n > max) {
            System.out.println(errorMsg);
            n = askDouble(msg);
        }
        return n;
    }

    public String askNif(String msg, String errorMsg) {
        String nif;
        boolean error;
        do {
            nif = askString(msg).toUpperCase();
            error = !validateNif(nif);
            if (error) {
                System.out.println(errorMsg);
            }
        } while (error);
        return nif;
    }

    private boolean validateNif(String nif) {
        Pattern REGEXP = Pattern.compile("[0-9]{8}[A-Z]");
        String DIGITO_CONTROL = "TRWAGMYFPDXBNJZSQVHLCKE";
        String[] INVALIDOS = new String[]{"00000000T", "00000001R", "99999999R"};

        return Arrays.binarySearch(INVALIDOS, nif) < 0 // <1>
                && REGEXP.matcher(nif).matches() // <2>
                && nif.charAt(8) == DIGITO_CONTROL.charAt(Integer.parseInt(nif.substring(0, 8)) % 23);
    }

}
