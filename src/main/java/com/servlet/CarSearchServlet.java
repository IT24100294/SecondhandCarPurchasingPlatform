package com.servlet;

import com.model.Car;
import com.util.Node;
import com.util.CarFileHandler;
import com.util.LinkedList;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

public class CarSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");
        LinkedList cars = CarFileHandler.readCars();
        LinkedList results = new LinkedList();
        if (query != null && !query.trim().isEmpty()) {
            String q = query.toLowerCase(Locale.ROOT);
            Node current = cars.getHead();
            while (current != null) {
                Car car = Car.fromString(current.getData());
                if (car != null &&
                        (car.getBrand().toLowerCase(Locale.ROOT).contains(q) ||
                                car.getModel().toLowerCase(Locale.ROOT).contains(q) ||
                                String.valueOf(car.getYear()).contains(q))) {
                    results.add(car.toString());
                }
                current = current.getNext();
            }
        }
        request.setAttribute("results", results);
        request.setAttribute("query", query);
        RequestDispatcher dispatcher = request.getRequestDispatcher("search.jsp");
        dispatcher.forward(request, response);
    }
}