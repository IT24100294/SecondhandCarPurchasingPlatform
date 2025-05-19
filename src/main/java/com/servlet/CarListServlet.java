package com.servlet;

import com.util.CarFileHandler;
import com.util.LinkedList;
import com.util.Node;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CarListServlet extends HttpServlet {
    private static final int CARS_PER_PAGE = 6; // Number of cars to show per page

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LinkedList allCars = CarFileHandler.readCars();

        int currentPage = getPageNumber(request);

        int totalPages = calculateTotalPages(allCars);

        currentPage = validateCurrentPage(currentPage, totalPages);

        LinkedList paginatedCars = getPaginatedCars(allCars, currentPage);

        setRequestAttributes(request, paginatedCars, currentPage, totalPages);

        forwardToJSP(request, response);
    }

    private int getPageNumber(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            return 1; // Default to first page
        }
    }

    private int calculateTotalPages(LinkedList allCars) {
        int totalCars = countCars(allCars);
        return (int) Math.ceil((double) totalCars / CARS_PER_PAGE);
    }

    private int countCars(LinkedList cars) {
        int count = 0;
        Node current = cars.getHead();
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    private int validateCurrentPage(int currentPage, int totalPages) {
        if (currentPage < 1) return 1;
        if (currentPage > totalPages && totalPages > 0) return totalPages;
        return currentPage;
    }

    private LinkedList getPaginatedCars(LinkedList allCars, int currentPage) {
        LinkedList paginatedList = new LinkedList();
        int startIndex = (currentPage - 1) * CARS_PER_PAGE;
        int endIndex = startIndex + CARS_PER_PAGE;

        Node current = allCars.getHead();
        int index = 0;
        int addedCount = 0;

        while (current != null && addedCount < CARS_PER_PAGE) {
            if (index >= startIndex) {
                paginatedList.add(current.getData());
                addedCount++;
            }
            current = current.getNext();
            index++;
        }

        return paginatedList;
    }

    private void setRequestAttributes(HttpServletRequest request, LinkedList cars,
                                      int currentPage, int totalPages) {
        request.setAttribute("cars", cars);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        String query = request.getParameter("q");
        if (query != null && !query.isEmpty()) {
            request.setAttribute("query", query);
        }
    }

    private void forwardToJSP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("list-cars.jsp");
        dispatcher.forward(request, response);
    }
}
