package com.carsales.util;

import com.carsales.model.Car;

public class CarLinkedList {
    private CarNode head;
    private int size;

    public CarLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void addCar(Car car) {
        CarNode newNode = new CarNode(car);
        if (head == null) {
            head = newNode;
        } else {
            CarNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    public boolean removeCar(int id) {
        if (head == null) return false;

        if (head.getCar().getId() == id) {
            head = head.getNext();
            size--;
            return true;
        }

        CarNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().getCar().getId() == id) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public Car findCar(int id) {
        CarNode current = head;
        while (current != null) {
            if (current.getCar().getId() == id) {
                return current.getCar();
            }
            current = current.getNext();
        }
        return null;
    }

    public Car[] searchCars(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return toArray();
        }

        keyword = keyword.toLowerCase();
        int count = 0;
        CarNode current = head;

        while (current != null) {
            Car car = current.getCar();
            if (car.getMake().toLowerCase().contains(keyword) ||
                    car.getModel().toLowerCase().contains(keyword)) {
                count++;
            }
            current = current.getNext();
        }

        Car[] result = new Car[count];
        current = head;
        int index = 0;

        while (current != null && index < count) {
            Car car = current.getCar();
            if (car.getMake().toLowerCase().contains(keyword) ||
                    car.getModel().toLowerCase().contains(keyword)) {
                result[index++] = car;
            }
            current = current.getNext();
        }

        return result;
    }

    public int size() {
        return size;
    }

    public Car[] toArray() {
        Car[] cars = new Car[size];
        CarNode current = head;
        int index = 0;

        while (current != null) {
            cars[index++] = current.getCar();
            current = current.getNext();
        }

        return cars;
    }
}