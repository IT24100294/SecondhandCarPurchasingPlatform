package com.carsales.util;

import com.carsales.model.Car;

/**
 * A simplified implementation of Merge Sort specifically for sorting cars by price.
 */
public class MergeSort {
    
    public enum SortOrder {
        ASCENDING, DESCENDING
    }

    /**
     * Sorts an array of Car objects by price in ascending order.
     * @param cars The array of cars to sort
     */
    public static void sort(Car[] cars) {
        sort(cars, SortOrder.ASCENDING);
    }

    /**
     * Sorts an array of Car objects by price in the specified order.
     * @param cars The array of cars to sort
     * @param order The sort order (ASCENDING or DESCENDING)
     */
    public static void sort(Car[] cars, SortOrder order) {
        if (cars == null || cars.length <= 1) return;
        sort(cars, 0, cars.length - 1, order);
    }

    /**
     * Recursive merge sort implementation
     */
    private static void sort(Car[] cars, int left, int right, SortOrder order) {
        if (left < right) {
            int mid = left + (right - left) / 2; // Avoid potential overflow
            sort(cars, left, mid, order);
            sort(cars, mid + 1, right, order);
            merge(cars, left, mid, right, order);
        }
    }

    /**
     * Merges two sorted subarrays
     */
    private static void merge(Car[] cars, int left, int mid, int right, SortOrder order) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Car[] leftArray = new Car[n1];
        Car[] rightArray = new Car[n2];

        // Copy data to temporary arrays
        System.arraycopy(cars, left, leftArray, 0, n1);
        System.arraycopy(cars, mid + 1, rightArray, 0, n2);

        // Merge the temporary arrays
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // Handle null cases
            if (leftArray[i] == null) {
                cars[k] = rightArray[j];
                j++;
            } else if (rightArray[j] == null) {
                cars[k] = leftArray[i];
                i++;
            } else {
                double leftPrice = leftArray[i].getPrice();
                double rightPrice = rightArray[j].getPrice();
                
                boolean shouldTakeLeft = (order == SortOrder.ASCENDING) ? 
                    leftPrice <= rightPrice : leftPrice >= rightPrice;

                if (shouldTakeLeft) {
                    cars[k] = leftArray[i];
                    i++;
                } else {
                    cars[k] = rightArray[j];
                    j++;
                }
            }
            k++;
        }

        // Copy remaining elements
        while (i < n1) {
            cars[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < n2) {
            cars[k] = rightArray[j];
            j++;
            k++;
        }
    }
}