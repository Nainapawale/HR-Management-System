import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contact = request.getParameter("contact");
        String department = request.getParameter("department");
        String jobTitle = request.getParameter("jobTitle");
        String salary = request.getParameter("salary");
        String dateOfJoining = request.getParameter("dateOfJoining");

        // JDBC connection
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Update with your database URL
        String jdbcUsername = "root"; // Update with your DB username
        String jdbcPassword = "password"; // Update with your DB password
        
        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Prepare SQL query
            String sql = "INSERT INTO employees (name, email, contact, department, job_title, salary, date_of_joining) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, contact);
            statement.setString(4, department);
            statement.setString(5, jobTitle);
            statement.setString(6, salary);
            statement.setString(7, dateOfJoining);

            // Execute the query
            int rows = statement.executeUpdate();

            if (rows > 0) {
                response.getWriter().println("Employee has been added successfully!");
            }

            // Close connection
            statement.close();
            connection.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error occurred: " + e.getMessage());
        }
    }
}
