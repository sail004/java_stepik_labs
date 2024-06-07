package servlets;

import accountServer.AccountServerI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageServlet extends HttpServlet {
    public static final String PAGE_URL = "/home";
    private static final Logger logger = LogManager.getLogger(HomePageServlet.class);
    private final AccountServerI accountServer;

    public HomePageServlet(AccountServerI accountServer) {
        this.accountServer = accountServer;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String remove = request.getParameter("remove");

        if (remove != null) {
            accountServer.removeUser();
            logger.info("User removed.");
            response.getWriter().println("Hasta la vista!");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        int limit = accountServer.getUsersLimit();
        int count = accountServer.getUsersCount();

        logger.info("Limit: {}. Count: {}", limit, count);

        if (limit > count) {
            logger.info("User pass");
            accountServer.addNewUser();
            response.getWriter().print("Hello, world!");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.info("User rejected");
            response.getWriter().println("Server is closed for maintenance!");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}