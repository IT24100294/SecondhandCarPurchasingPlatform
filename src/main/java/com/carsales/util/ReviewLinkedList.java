package com.carsales.util;

import com.carsales.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewLinkedList {
    private ReviewNode head;
    private int size;

    public ReviewLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void addReview(Review review) {
        ReviewNode newNode = new ReviewNode(review);
        if (head == null) {
            head = newNode;
        } else {
            ReviewNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    public boolean removeReview(int id) {
        if (head == null) return false;

        if (head.getReview().getId() == id) {
            head = head.getNext();
            size--;
            return true;
        }

        ReviewNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().getReview().getId() == id) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean updateReview(Review updatedReview) {
        ReviewNode current = head;
        while (current != null) {
            if (current.getReview().getId() == updatedReview.getId()) {
                current.setReview(updatedReview);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public Review findReview(int id) {
        ReviewNode current = head;
        while (current != null) {
            if (current.getReview().getId() == id) {
                return current.getReview();
            }
            current = current.getNext();
        }
        return null;
    }

    public List<Review> getReviewsForCar(int carId) {
        List<Review> carReviews = new ArrayList<>();
        ReviewNode current = head;

        while (current != null) {
            if (current.getReview().getCarId() == carId) {
                carReviews.add(current.getReview());
            }
            current = current.getNext();
        }

        return carReviews;
    }

    public Review[] getAllReviews() {
        return toArray();
    }

    public double getAverageRatingForCar(int carId) {
        List<Review> carReviews = getReviewsForCar(carId);
        if (carReviews.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (Review review : carReviews) {
            sum += review.getRating();
        }

        return (double) sum / carReviews.size();
    }

    public int size() {
        return size;
    }

    public Review[] toArray() {
        Review[] reviews = new Review[size];
        ReviewNode current = head;
        int index = 0;

        while (current != null) {
            reviews[index++] = current.getReview();
            current = current.getNext();
        }

        return reviews;
    }
}