import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchEmployeeServlet")
public class SearchEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Set the response content type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the search term (employee ID or name) from the request
        String searchTerm = request.getParameter("employeeId");

        // JDBC connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Update your DB URL
        String jdbcUsername = "root"; // Update with your DB username
        String jdbcPassword = "password"; // Update with your DB password

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // SQL query to search for employee performance based on employee ID or name
            String sql = "SELECT * FROM employee_performance WHERE employee_id = '" + searchTerm + 
                         "' OR employee_name LIKE '%" + searchTerm + "%'";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Output the employee performance data in a table format
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt("employee_id") + "</td>");
                out.println("<td>" + resultSet.getString("employee_name") + "</td>");
                out.println("<td>" + resultSet.getString("department") + "</td>");
                out.println("<td>" + resultSet.getString("job_title") + "</td>");
                out.println("<td>" + resultSet.getInt("performance_score") + "</td>");
                out.println("<td>" + resultSet.getDate("review_date") + "</td>");
                out.println("<td>" + resultSet.getString("comments") + "</td>");
                out.println("</tr>");
            }

            // Close the resultSet, statement, and connection
            resultSet.close();
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
