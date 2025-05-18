package com.util;

public class LinkedList {
    private Node head;

    public LinkedList() {
        head = null;
    }

    public void add(String data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(newNode);
        }
    }

    public boolean remove(String data) {
        if (head == null) return false;

        if (head.getData().equals(data)) {
            head = head.getNext();
            return true;
        }

        Node current = head;
        while (current.getNext() != null && !current.getNext().getData().equals(data)) {
            current = current.getNext();
        }

        if (current.getNext() == null) return false;

        current.setNext(current.getNext().getNext());
        return true;
    }

    public String display() {
        StringBuilder result = new StringBuilder();
        Node temp = head;
        while (temp != null) {
            result.append(temp.getData()).append("<br>");
            temp = temp.getNext();
        }
        return result.toString();
    }

    public Node getHead() {
        return head;
    }
}