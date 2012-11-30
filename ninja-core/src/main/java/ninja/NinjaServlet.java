package ninja;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ninja.utils.NinjaConstant;
import ninja.utils.NinjaPropertiesImpl;

/**
 * A simple servlet that allows us to run Ninja inside any servlet
 * container as servlet. Uses {@link NinjaEntryPoint} to do the actual dispatching.
 * 
 * Quite new and not widely used in production yet, but might be helpful if you want to combine Ninja with other servlet
 * filters.
 * 
 * This dispatcher targets Servlet 2.5.
 * 
 * @author henningschuetz
 * 
 */
public class NinjaServlet extends HttpServlet {

    private static final long serialVersionUID = -2970027184871507972L;
    private NinjaEntryPoint ninjaEntryPoint;
    private final String serverName;

    /**
     * Special constructor for usage in JUnit tests.
     * 
     * We are using an embeded jetty for quick server testing. The problem is
     * that the port will change.
     * 
     * Therefore we inject the server name here:
     * 
     * @param serverName
     *            The injected server name. Will override property serverName in
     *            Ninja properties.
     */
    public NinjaServlet(String serverName) {
        this.serverName = serverName;
    }

    /**
     * Default constructor used in PROD and DEV modes.
     * Especially serverName will be set from application.conf.
     */
    public NinjaServlet() {
        this.serverName = null; // intentionally null.
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        NinjaPropertiesImpl ninjaProperties = new NinjaPropertiesImpl();
        // force set serverName when in test mode:
        if (serverName != null) {
            ninjaProperties.setProperty(NinjaConstant.serverName, serverName);
        }

        ninjaEntryPoint = new NinjaEntryPoint(ninjaProperties);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ninjaEntryPoint.handle(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ninjaEntryPoint.handle(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ninjaEntryPoint.handle(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ninjaEntryPoint.handle(req, resp);
    }

    @Override
    public void destroy() {
        ninjaEntryPoint.destroy();
    }
}
