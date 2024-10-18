import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PayrollServlet")
public class PayrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
    private String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Replace with your DB details
    private String jdbcUsername = "root"; // Replace with your DB username
    private String jdbcPassword = "password"; // Replace with your DB password

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Set the response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get data from the form
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        double basicSalary = Double.parseDouble(request.getParameter("basicSalary"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));
        double deductions = Double.parseDouble(request.getParameter("deductions"));

        // Calculate total salary
        double totalSalary = basicSalary + bonus - deductions;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // SQL query to insert or update payroll details
            String sql = "INSERT INTO payroll (employee_id, basic_salary, bonus, deductions, total_salary) "
                    + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE basic_salary = ?, bonus = ?, deductions = ?, total_salary = ?";

            // Prepare the SQL statement
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, employeeId);
            statement.setDouble(2, basicSalary);
            statement.setDouble(3, bonus);
            statement.setDouble(4, deductions);
            statement.setDouble(5, totalSalary);
            statement.setDouble(6, basicSalary);
            statement.setDouble(7, bonus);
            statement.setDouble(8, deductions);
            statement.setDouble(9, totalSalary);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                out.println("<p>Payroll for Employee ID " + employeeId + " has been successfully updated!</p>");
                out.println("<p>Total Salary: " + totalSalary + "</p>");
            } else {
                out.println("<p>Error in updating payroll.</p>");
            }

            // Close the statement and connection
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        // Close the PrintWriter
        out.close();
    }
}
