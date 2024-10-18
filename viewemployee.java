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

@WebServlet("/ViewEmployeesServlet")
public class ViewEmployeesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Set the response type
        response.setContentType("text/html");

        // Get the PrintWriter to write the response
        PrintWriter out = response.getWriter();

        // JDBC connection parameters
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Update with your DB URL
        String jdbcUsername = "root"; // Update with your DB username
        String jdbcPassword = "password"; // Update with your DB password

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // SQL query to select all employees
            String sql = "SELECT * FROM employees";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Dynamically generate table rows with employee data
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getInt("id") + "</td>");
                out.println("<td>" + resultSet.getString("name") + "</td>");
                out.println("<td>" + resultSet.getString("email") + "</td>");
                out.println("<td>" + resultSet.getString("contact") + "</td>");
                out.println("<td>" + resultSet.getString("department") + "</td>");
                out.println("<td>" + resultSet.getString("job_title") + "</td>");
                out.println("<td>" + resultSet.getDouble("salary") + "</td>");
                out.println("<td>" + resultSet.getDate("date_of_joining") + "</td>");
                out.println("</tr>");
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error occurred: " + e.getMessage() + "</p>");
        }

        // Close the PrintWriter
        out.close();
    }
}
