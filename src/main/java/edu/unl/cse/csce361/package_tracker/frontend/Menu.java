package edu.unl.cse.csce361.package_tracker.frontend;

import edu.unl.cse.csce361.package_tracker.backend.Sender;
import edu.unl.cse.csce361.package_tracker.logic.logicFacade;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static final logicFacade logic = logicFacade.getInstance();
    private static Scanner scnr = new Scanner(System.in);
    private static String userName = UserInterFace.getUserName();
    private static Sender userFile = UserInterFace.getSender();

    public static String getUserName () {
        return userName;
    }

    public static void setUserName (String userName) {
        Menu.userName = userName;
    }

    private static Sender getSender () {
        return userFile;
    }

    public static void adminMenu () {
        boolean programOn = true;
        while (programOn) {
            String userSelect;
            String user;
            String street;
            String city;
            String zipCode;
            String inputTracking;
            String warehouseID;
            Printer.printAdminMenu();
            userSelect = scnr.nextLine();
            switch (userSelect) {
                case "1":
                    logic.getAllPackage();
                    break;
                case "2":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.confirmByAdmin(inputTracking);
                    break;
                case "3":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    Printer.printAskStreet();
                    street = scnr.nextLine();
                    Printer.printAskCity();
                    city = scnr.nextLine();
                    Printer.printAskZipCode();
                    zipCode = scnr.nextLine();
                    logic.editReceiver(inputTracking, street, city, zipCode);
                    break;
                case "4":
                    Printer.printAskUserName();
                    user = scnr.nextLine();
                    Printer.printAskStreet();
                    street = scnr.nextLine();
                    Printer.printAskCity();
                    city = scnr.nextLine();
                    Printer.printAskZipCode();
                    zipCode = scnr.nextLine();
                    logic.editInfo(user, street, city, zipCode);
                    break;
                case "5":
                    editPackage();
                    break;
                case "6":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.fuzzySearch(inputTracking);
                    break;
                case "7":
                    Printer.printAskWarehouse();
                    warehouseID = scnr.nextLine();
                    logic.callDrone(warehouseID);
                    break;
                case "8":
                    programOn = false;
                    break;
                default:
                    Printer.printInvalid();
            }
        }
    }

    public static void editPackage () {
        String input1;
        String street;
        String city;
        String zipCode;
        String inputTracking;
        String status;
        int priorityID;
        int currentLocation;
        Printer.printAskTracking();
        inputTracking = scnr.nextLine();
        Printer.printEditPackage();
        input1 = scnr.nextLine();
        switch (input1) {
            case "1":
                Printer.printWarehouse();
                Printer.printAskInput();
                try {
                    currentLocation = scnr.nextInt();
                    scnr.nextLine();
                    logic.editCurrentLocation(inputTracking, currentLocation);
                } catch (InputMismatchException e) {
                    Printer.printInvalid();
                }
                break;
            case "2":
                Printer.printAskInput();
                try {
                    priorityID = scnr.nextInt();
                    scnr.nextLine();
                    logic.editPriorityID(inputTracking, priorityID);
                } catch (InputMismatchException e) {
                    Printer.printInvalid();
                }
                break;
            case "3":
                Printer.printAskInput();
                status = scnr.nextLine();
                logic.editStatus(inputTracking, status);
                break;
            case "4":
                Printer.printAskStreet();
                street = scnr.nextLine();
                Printer.printAskCity();
                city = scnr.nextLine();
                Printer.printAskZipCode();
                zipCode = scnr.nextLine();
                logic.editReceiver(inputTracking, street, city, zipCode);
                break;
            case "5":
                break;
            default:
                Printer.printInvalid();
        }
    }

    public static void userMenu () {
        boolean programOn = true;
        while (programOn) {
            String input;
            String receiverName;
            String street;
            String city;
            String zipCode;
            String inputTracking;
            String inputCheck;
            Printer.printUserMenu();
            input = scnr.nextLine();
            switch (input) {
                case "1":
                    if (logic.checkAvilability()) {
                        Printer.printAskreceiverName();
                        receiverName = scnr.nextLine();
                        Printer.printAskStreet();
                        street = scnr.nextLine();
                        Printer.printAskCity();
                        city = scnr.nextLine();
                        Printer.printAskZipCode();
                        zipCode = scnr.nextLine();
                        logic.newPackage(userName, receiverName, street, city, zipCode);
                    } else {
                        Printer.printNotAvilable();
                    }
                    break;
                case "2":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.confirmReceive(inputTracking);
                    break;
                case "3":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.returnPackage(inputTracking);
                    break;
                case "4":
                    Printer.printAskStreet();
                    street = scnr.nextLine();
                    Printer.printAskCity();
                    city = scnr.nextLine();
                    Printer.printAskZipCode();
                    zipCode = scnr.nextLine();
                    logic.editInfo(userName, street, city, zipCode);
                    break;
                case "5":
                    Printer.printCheckPackage();
                    inputCheck = scnr.nextLine();
                    switch (inputCheck) {
                        case "1":
                            Printer.printAskTracking();
                            inputTracking = scnr.nextLine();
                            logic.checkPackageByTrackingNumber(inputTracking);
                            break;
                        case "2":
                            logic.checkPackageByUserName(userName);
                            break;
                        case "3":
                            break;
                        default:
                            Printer.printInvalid();
                    }
                    break;
                case "6":
                    logic.becomeVIP(userName);
                    vipMenu();
                    break;
                case "7":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.cancelPackage(inputTracking);
                    break;
                case "8":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.holdAtWarehouse(inputTracking);
                    break;
                case "9":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.estimatePackage(inputTracking);
                    break;
                case "10":
                    Printer.printUserHistory(getSender());
                    break;
                case "11":
                    programOn = false;
                    break;
                default:
                    Printer.printInvalid();
            }
        }
    }

    public static void vipMenu () {
        boolean programOn = true;
        while (programOn) {
            String input;
            String receiverName;
            String street;
            String city;
            String zipCode;
            String inputTracking;
            String inputCheck;
            Printer.printVIPMenu();
            input = scnr.nextLine();
            switch (input) {
                case "1":
                    if (logic.checkAvilability()) {
                        Printer.printAskreceiverName();
                        receiverName = scnr.nextLine();
                        Printer.printAskStreet();
                        street = scnr.nextLine();
                        Printer.printAskCity();
                        city = scnr.nextLine();
                        Printer.printAskZipCode();
                        zipCode = scnr.nextLine();
                        logic.newPackage(userName, receiverName, street, city, zipCode);
                    } else {
                        Printer.printNotAvilable();
                    }
                    break;
                case "2":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.confirmReceive(inputTracking);
                    break;
                case "3":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.returnPackage(inputTracking);
                    break;
                case "4":
                    Printer.printAskStreet();
                    street = scnr.nextLine();
                    Printer.printAskCity();
                    city = scnr.nextLine();
                    Printer.printAskZipCode();
                    zipCode = scnr.nextLine();
                    logic.editInfo(userName, street, city, zipCode);
                    break;
                case "5":
                    Printer.printCheckPackage();
                    inputCheck = scnr.nextLine();
                    switch (inputCheck) {
                        case "1":
                            Printer.printAskTracking();
                            inputTracking = scnr.nextLine();
                            logic.checkPackageByTrackingNumber(inputTracking);
                            break;
                        case "2":
                            logic.checkPackageByUserName(userName);
                            break;
                        case "3":
                            break;
                        default:
                            Printer.printInvalid();
                    }
                    break;
                case "6":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.cancelPackage(inputTracking);
                    break;
                case "7":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.holdAtWarehouse(inputTracking);
                    break;
                case "8":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    logic.estimatePackage(inputTracking);
                    break;
                case "9":
                    Printer.printAskTracking();
                    inputTracking = scnr.nextLine();
                    Printer.printAskStreet();
                    street = scnr.nextLine();
                    Printer.printAskCity();
                    city = scnr.nextLine();
                    Printer.printAskZipCode();
                    zipCode = scnr.nextLine();
                    logic.editReceiver(inputTracking, street, city, zipCode);
                    break;
                case "10":
                    Printer.printUserHistory(getSender());
                    break;
                case "11":
                    programOn = false;
                    break;
                default:
                    Printer.printInvalid();
            }
        }
    }

    public static void register () {
        String user;
        String street;
        String city;
        String zipCode;
        String realName;
        Printer.printAskUserName();
        user = scnr.nextLine();
        Printer.printAskRealName();
        realName = scnr.nextLine();
        Printer.printAskStreet();
        street = scnr.nextLine();
        Printer.printAskCity();
        city = scnr.nextLine();
        Printer.printAskZipCode();
        zipCode = scnr.nextLine();
        logic.register(user, realName, street, city, zipCode);
    }
}
