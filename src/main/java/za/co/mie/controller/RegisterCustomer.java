package za.co.mie.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import za.co.mie.dao.CustomerDAO;
import za.co.mie.dao.FlightDAO;
import za.co.mie.dao.impl.CustomerDAOImpl;
import za.co.mie.dao.impl.FlightDAOImpl;
import za.co.mie.manager.DbManager;
import za.co.mie.model.Customer;
import za.co.mie.model.Flight;

@WebServlet(name = "RegisterCustomer", urlPatterns = {"/registerCustomer"})
public class RegisterCustomer extends HttpServlet {

    private CustomerDAO customerDAO;
    private DbManager db;
    private FlightDAO fd;

    @Override
    public void init() throws ServletException {
        super.init();
        //db = new DbManager();
        db = DbManager.getInstance();
        customerDAO = new CustomerDAOImpl(db.getConnection());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("customerRegistration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        db = DbManager.getInstance();
        fd = new FlightDAOImpl(db.getConnection());
        if (db == null) {System.out.println("DB in registerCustomer IS Null");}
        if (fd == null) {System.out.println("flightdao in registerCustomer is null");}

        String action = request.getParameter("action");

        if ("cancel".equals(action)) {
            response.sendRedirect("registrationCancelled.jsp");
        } else if ("proceed".equals(action)) {
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String creditCardNumber = request.getParameter("creditCard");

            if (customerDAO.isAddressExists(address)) {
                request.setAttribute("errorMessage", "Email Address already exists. Please enter a different address.");
                request.getRequestDispatcher("addressError.jsp").forward(request, response);
            } else {
                List<Flight> flights = fd.getAllFlights();
                request.setAttribute("flights", flights);

                Customer newCustomer = new Customer(0, name, address, creditCardNumber);
                int generatedCustomerId = customerDAO.insertCustomer(newCustomer);

                newCustomer.setId(generatedCustomerId);
                if (generatedCustomerId != 0) {
                    HttpSession session = request.getSession();
                    session.setAttribute("registeredCustomer", newCustomer);

                    request.setAttribute("customerId", generatedCustomerId);
                    request.getRequestDispatcher("processReservation.jsp").forward(request, response);
                } else {
                    response.sendRedirect("errorPage.jsp");
                }
            }
        } else {
            response.sendRedirect("errorPage.jsp");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        db.closeConnection();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
