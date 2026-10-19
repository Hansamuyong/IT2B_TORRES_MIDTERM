package com.scc.ui;

import com.scc.dao.PassageDao;
import com.scc.model.Passage;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TollMenu {

    private static final String LINE = "=".repeat(52);
    private static final String WIDE_LINE = "=".repeat(80);
    private static final String BORDER =
            "+----+--------------------+------------+--------------+----------------+";

    private final PassageDao passageDao;
    private final Scanner scanner;

    public TollMenu(PassageDao passageDao, Scanner scanner) {
        this.passageDao = passageDao;
        this.scanner = scanner;
    }

    public void display() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println(LINE);
            System.out.println("          EXPRESSWAY TOLLGATE REGISTRY");
            System.out.println(LINE);
            System.out.println("[1] Record Vehicle Passage");
            System.out.println("[2] View Passage Logs");
            System.out.println("[3] Exit System");
            System.out.println(LINE);
            System.out.print("Enter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    recordPassage();
                    break;
                case "2":
                    viewLogs();
                    break;
                case "3":
                    System.out.println();
                    System.out.println("Exiting system... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    private void recordPassage() {
        System.out.println();
        System.out.println("--- RECORD VEHICLE PASSAGE ---");

        String plate = readPlate();
        String vehicleClass = readChoice("Enter Vehicle Class (CLASS_1 / CLASS_2 / CLASS_3): ",
                "CLASS_1", "CLASS_2", "CLASS_3");
        double fee = readToll();
        String payment = readChoice("Enter Payment Method (RFID / CASH): ", "RFID", "CASH");

        System.out.println();
        if (passageDao.addPassage(plate, vehicleClass, fee, payment)) {
            System.out.println("SUCCESS: Passage recorded successfully!");
        } else {
            System.out.println("ERROR: Passage could not be saved.");
        }
        System.out.println();
    }

    private String readPlate() {
        while (true) {
            System.out.print("Enter License Plate Number: ");
            String plate = scanner.nextLine().trim().toUpperCase();
            if (!plate.isEmpty()) {
                return plate;
            }
            System.out.println("Plate number cannot be empty. Try again.");
        }
    }

    private String readChoice(String prompt, String... options) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toUpperCase();
            for (String option : options) {
                if (option.equals(value)) {
                    return option;
                }
            }
            System.out.println("Invalid input. Please choose one of the listed options.");
        }
    }

    private double readToll() {
        while (true) {
            System.out.print("Enter Toll Fee: ");
            String text = scanner.nextLine().trim();
            try {
                double fee = Double.parseDouble(text);
                if (fee > 0) {
                    return fee;
                }
                System.out.println("Toll fee must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Example: 350.00");
            }
        }
    }

    private void viewLogs() {
        List<Passage> passages = passageDao.getAllPassages();

        System.out.println();
        System.out.println(WIDE_LINE);
        System.out.println("                          EXPRESSWAY PASSAGE LOGS");
        System.out.println(WIDE_LINE);
        System.out.println(BORDER);
        System.out.println(String.format("| %-2s | %-18s | %-10s | %-12s | %-14s |",
                "ID", "PLATE NO", "CLASS", "TOLL FEE", "PAYMENT"));
        System.out.println(BORDER);

        for (Passage p : passages) {
            System.out.println(String.format(Locale.US, "| %-2d | %-18s | %-10s | %-12.2f | %-14s |",
                    p.id(), p.plateNo(), p.vehicleClass(), p.tollFee(), p.payment()));
        }

        System.out.println(BORDER);
        System.out.println("Total Records Displayed: " + passages.size());
    }
}