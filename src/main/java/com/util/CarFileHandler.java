package com.util;

import com.model.Car;
import java.io.*;

public class CarFileHandler {
    private static final String FILE_PATH = "C:/Users/User/Documents/GitHub/SecondhandCarPurchasingPlatform/src/main/webapp/WEB-INF/resources/cars.txt";

    public static LinkedList readCars() {
        LinkedList cars = new LinkedList();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                Car car = Car.fromString(line);
                if (car != null) cars.add(car.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public static void writeCars(LinkedList cars) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            Node current = cars.getHead();
            while (current != null) {
                bw.write(current.getData());
                bw.newLine();
                current = current.getNext();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}