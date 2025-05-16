package com.servlet;

import com.util.CarFileHandler;
import com.util.LinkedList;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CarListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedList cars = CarFileHandler.readCars();
        request.setAttribute("cars", cars);
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-cars.jsp");
        dispatcher.forward(request, response);
    }
}