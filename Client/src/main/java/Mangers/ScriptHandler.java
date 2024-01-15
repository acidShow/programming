package Mangers;

import InputData.Coordinates;
import InputData.FuelType;
import InputData.Vehicle;
import InputData.VehicleType;
import Managers.Container;
import Managers.ContainerHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ScriptHandler {

    static ArrayList<String> files = new ArrayList<>();


    public static void handle(ContainerHandler containerHandler, String path) throws InterruptedException, IOException, ClassNotFoundException {

        Scanner scn;

        try {
            for (String str: files) {
                if(str.equals(path)){
                    System.err.println("This file create infinity cycle");
                    Thread.sleep(100);
                    files.clear();
                    return;
                }
            }

            scn = new Scanner(new File(path));
            files.add(path);

        } catch (FileNotFoundException e) {
            System.err.println("No such file");
            Thread.sleep(100);
            return;

        }

        while (scn.hasNext()) {
            Container container;
            try {
                String command = scn.nextLine();
                String[] commandList = command.toLowerCase().trim().split(" ");
                container = null;
                if (commandList[0].trim().equals("insert")) {
                    ConcurrentHashMap<Integer, Vehicle> hashMap = new ConcurrentHashMap<>();
                    hashMap.put(0, new Vehicle(
                            scn.nextLine().trim(),
                            new Coordinates(
                                    Float.parseFloat(scn.nextLine().trim().toLowerCase()),
                                    Double.parseDouble(scn.nextLine().toLowerCase().trim())),
                            Integer.parseInt(scn.nextLine().trim().toLowerCase()),
                            Double.parseDouble(scn.nextLine().toLowerCase().trim()),
                            VehicleType.valueOf(scn.nextLine().toUpperCase().trim()),
                            FuelType.valueOf(scn.nextLine().trim().toUpperCase())));
                    container = new Container(CommandManager.getCommand(commandList[0].trim()), commandList[1], hashMap);
                } else {
                    container = Validator.validateData(command);
                }
                RunClient.afterValidationStage(containerHandler, container);

            } catch (InvalidParameterException exception) {
                System.err.println("This command requires an argument");
                Thread.sleep(100);
                return;

            } catch (NumberFormatException exception) {
                System.err.println("Argument must be number");
                Thread.sleep(100);
                return;

            } catch (IllegalArgumentException exception) {
                System.err.println("No such command");
                Thread.sleep(100);
                return;

            }

        }

        files.remove(path);
    }
}