package com.servlet;

import com.model.Car;
import com.util.CarFileHandler;
import com.util.LinkedList;
import com.util.Node;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CarDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            LinkedList cars = CarFileHandler.readCars();
            Car found = null;
            Node current = cars.getHead();
            while (current != null) {
                Car car = Car.fromString(current.getData());
                if (car != null && car.getId() == id) {
                    found = car;
                    break;
                }
                current = current.getNext();
            }
            request.setAttribute("car", found);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("car-details.jsp");
        dispatcher.forward(request, response);
    }
}