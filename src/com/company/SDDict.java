package com.company;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class SDDict {
    private static final int HELP_OPTION = 0;
    private static final int INVALID_INPUT = 1;

    private static final String ALL_VALUES = "valuePairs.txt";

    // main method of the program
    public static void main(String[] args) {
        // create new options
        Locale locale;
        ResourceBundle strings;
        Options options;
        String fileName;
        String userInput = "";
        String definition;
        HashMap<String, String> dictionary;

        // determine the locale
        locale = Locale.getDefault();
        // load localized string resources
        strings = ResourceBundle.getBundle("SDDict", locale);

        // create options
        options = createOptions(strings);

        // read options
        fileName = parseCMDOptions(options, args, strings);

        // select correct file
        dictionary = readAndPopulate(fileName);
            // populate hashMap

        // get value name
        while (!dictionary.containsKey(userInput)) {
            userInput = userInput();
        }

        // print out value definition(s)
        definition = dictionary.get(userInput);
        System.out.println(definition);
    }

    private static Options createOptions(ResourceBundle strings) {
        Options options;

        String helpOpt = strings.getString("helpOpt");
        String allOpt = strings.getString("allOpt");

        options = new Options();

        options.addOption("h", "help", false, helpOpt);
        options.addOption("a", "all", false, allOpt);

        return options;
    }

    private static String parseCMDOptions(Options options, String[] args, ResourceBundle strings) {
        String helpMsg;
        String fileName = "";
        CommandLineParser parser;

        // get help message
        helpMsg = strings.getString("helpMsg");

        // Create a parser
        parser = new DefaultParser();

        // Parse options passed in as CLA
        try {
            CommandLine cmd = parser.parse(options, args);

            // Check if help option is present or not
            if (cmd.hasOption("h")) {
                System.out.printf(helpMsg + "\n-a, --all:  %s",
                    options.getOption("a").getDescription()
                );
                System.exit(HELP_OPTION);
            }
            else {
                // Any program run that doesn't include a help command must contain a file name
                if (cmd.hasOption("a")) {
                    fileName = ALL_VALUES;
                }
                else {
                    System.err.println("\nERROR: INVALID COMMAND GIVEN\nSYSTEM EXIT 1\n");
                    System.exit(INVALID_INPUT);
                }
            }
        }
        catch (org.apache.commons.cli.ParseException p) {
            System.err.println("\nERROR: INVALID COMMAND GIVEN\nSYSTEM EXIT 1\n");
            System.exit(INVALID_INPUT);
        }

        return fileName;
    }

    private static HashMap<String, String> readAndPopulate(String fileName) {
        File file;
        HashMap<String, String> dict;

        file = new File(fileName);
        dict = new HashMap<>();

        try {
            BufferedReader br;

            br = new BufferedReader(new FileReader(file));
            dict = populateDict(br);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dict;
    }

    private static HashMap<String, String> populateDict(BufferedReader br) {
        HashMap<String, String> dict;
        String[] pairs;
        String line;
        String key;
        String value;

        dict = new HashMap<>();

        try {
            line = br.readLine();
            while (line != null) {
                pairs = line.split(",");
                key = pairs[0];
                value = pairs[1];
                dict.put(key, value);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dict;
    }

    private static String userInput() {
        String input;
        Scanner sc;

        sc = new Scanner(System.in);

        System.out.println("Value: ");
        input = sc.nextLine();

        return input;
    }
}
