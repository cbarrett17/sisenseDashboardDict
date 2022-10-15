package com.calebb;

import org.apache.commons.cli.*;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;


public class SDDict {
    private static final int HELP_OPTION = 0;
    private static final int INVALID_INPUT = 1;
    private static final int FILE_NOT_FOUND = 2;

    private static final String ALL_VALUES = "valuePairs";
    private static final String ACCOUNTS = "Accounts_compositescorecalculation";
    private static final String CATEGORIES = "Gsrm_alert_categories";
    private static final String DOMAIN = "Domain_names";
    private static final String ATARGETS = "Gsrm_alert_targets";
    private static final String ALERT = "Gsrm_alert";
    private static final String PARAM = "Gsrm_paramvalue";
    private static final String SECURITY = "Security_table";
    private static final String TARGETS = "Targets";

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
        String accOpt = strings.getString("accOpt");
        String catOpt = strings.getString("catOpt");
        String domOpt = strings.getString("domOpt");
        String ataOpt = strings.getString("ataOpt");
        String aleOpt = strings.getString("aleOpt");
        String parOpt = strings.getString("parOpt");
        String secOpt = strings.getString("secOpt");
        String tarOpt = strings.getString("tarOpt");

        options = new Options();

        options.addOption("h", "help", false, helpOpt);
        options.addOption("a", "all", false, allOpt);
        options.addOption("b", "accounts", false, accOpt);
        options.addOption("c", "categories", false, catOpt);
        options.addOption("d", "domain", false, domOpt);
        options.addOption("e", "atargets", false, ataOpt);
        options.addOption("f", "alert", false, aleOpt);
        options.addOption("g", "param", false, parOpt);
        options.addOption("i", "security", false, secOpt);
        options.addOption("j", "targets", false, tarOpt);

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

        // Parse options passed in as CLAs
        try {
            CommandLine cmd = parser.parse(options, args);

            // Check if help option is present or not
            if (cmd.hasOption("h")) {
                System.out.printf(
                    helpMsg + "%n%-20s %s%n%-20s %s" +
                        "%n%-20s %s%n%-20s %s" +
                        "%n%-20s %s%n%-20s %s" +
                        "%n%-20s %s%n%-20s %s" +
                        "%n%-20s %s",
                    strings.getString("allMsg"),
                    options.getOption("a").getDescription(),
                    strings.getString("accMsg"),
                    options.getOption("b").getDescription(),
                    strings.getString("catMsg"),
                    options.getOption("c").getDescription(),
                    strings.getString("domMsg"),
                    options.getOption("d").getDescription(),
                    strings.getString("ataMsg"),
                    options.getOption("e").getDescription(),
                    strings.getString("aleMsg"),
                    options.getOption("f").getDescription(),
                    strings.getString("parMsg"),
                    options.getOption("g").getDescription(),
                    strings.getString("secMsg"),
                    options.getOption("i").getDescription(),
                    strings.getString("tarMsg"),
                    options.getOption("j").getDescription()
                );
                System.exit(HELP_OPTION);
            }
            else {
                // Any program run that doesn't include a help command must contain a file name
                if (cmd.hasOption("a")) {
                    fileName = ALL_VALUES + ".csv";
                }
                else if (cmd.hasOption("b")) {
                    fileName = ACCOUNTS + ".csv";
                }
                else if (cmd.hasOption("c")) {
                    fileName = CATEGORIES + ".csv";
                }
                else if (cmd.hasOption("d")) {
                    fileName = DOMAIN + ".csv";
                }
                else if (cmd.hasOption("e")) {
                    fileName = ATARGETS + ".csv";
                }
                else if (cmd.hasOption("f")) {
                    fileName = ALERT + ".csv";
                }
                else if (cmd.hasOption("g")) {
                    fileName = PARAM + ".csv";
                }
                else if (cmd.hasOption("i")) {
                    fileName = SECURITY + ".csv";
                }
                else if (cmd.hasOption("j")) {
                    fileName = TARGETS + ".csv";
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
        HashMap<String, String> dict;
        String filoPilo;

        dict = new HashMap<>();
        filoPilo = "csvs/" + fileName;

        try {
            BufferedReader br;

            br = new BufferedReader(new FileReader(filoPilo));
            dict = populateDict(br);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("\nERROR: FILE NOT FOUND\nSYSTEM EXIT 2\n");
            System.exit(FILE_NOT_FOUND);
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
            while ((line = br.readLine()) != null) {
                pairs = line.split(",");
                key = pairs[0];
                value = pairs[1];
                dict.put(key, value);
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
