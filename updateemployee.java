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

@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC connection parameters
    private String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Update your DB URL
    private String jdbcUsername = "root"; // Update with your DB username
    private String jdbcPassword = "password"; // Update with your DB password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Set the response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get employee data from the form
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String jobTitle = request.getParameter("jobTitle");
        int salary = Integer.parseInt(request.getParameter("salary"));

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // SQL update statement
            String sql = "UPDATE employees SET name = ?, department = ?, job_title = ?, salary = ? WHERE id = ?";

            // Use a PreparedStatement to set the employee details
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, department);
            statement.setString(3, jobTitle);
            statement.setInt(4, salary);
            statement.setInt(5, employeeId);

            // Execute the update
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("<p>Employee details updated successfully!</p>");
            } else {
                out.println("<p>Employee ID not found or no changes made.</p>");
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
