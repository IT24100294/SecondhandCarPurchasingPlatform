package com.carsales.util;

import com.carsales.model.Car;
import java.util.Comparator;

public class MergeSort {

    public enum SortCriteria {
        PRICE_ASC, PRICE_DESC, YEAR_ASC, YEAR_DESC, MAKE_ASC, MODEL_ASC
    }

    public static Car[] sort(Car[] cars, SortCriteria criteria) {
        if (cars.length <= 1) return cars;

        Comparator<Car> comparator = getComparator(criteria);
        return mergeSort(cars, comparator);
    }

    public static Car[] sort(Car[] cars) {
        return sort(cars, SortCriteria.PRICE_ASC);
    }

    private static Comparator<Car> getComparator(SortCriteria criteria) {
        switch (criteria) {
            case PRICE_DESC:
                return (c1, c2) -> Double.compare(c2.getPrice(), c1.getPrice());
            case YEAR_ASC:
                return (c1, c2) -> Integer.compare(c1.getYear(), c2.getYear());
            case YEAR_DESC:
                return (c1, c2) -> Integer.compare(c2.getYear(), c1.getYear());
            case MAKE_ASC:
                return (c1, c2) -> c1.getMake().compareToIgnoreCase(c2.getMake());
            case MODEL_ASC:
                return (c1, c2) -> c1.getModel().compareToIgnoreCase(c2.getModel());
            case PRICE_ASC:
            default:
                return (c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice());
        }
    }

    private static Car[] mergeSort(Car[] cars, Comparator<Car> comparator) {
        if (cars.length <= 1) return cars;

        int mid = cars.length / 2;
        Car[] left = new Car[mid];
        Car[] right = new Car[cars.length - mid];

        System.arraycopy(cars, 0, left, 0, mid);
        System.arraycopy(cars, mid, right, 0, cars.length - mid);

        left = mergeSort(left, comparator);
        right = mergeSort(right, comparator);

        return merge(left, right, comparator);
    }

    private static Car[] merge(Car[] left, Car[] right, Comparator<Car> comparator) {
        Car[] result = new Car[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }
}