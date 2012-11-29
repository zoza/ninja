package ninja;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

/**
 * 
 * @author henningschuetz
 * 
 */
public class NinjaServlet extends HttpServlet {

    private static final long serialVersionUID = -2970027184871507972L;
    private final NinjaEntryPoint ninjaEntryPoint;

    @Inject
    public NinjaServlet(NinjaEntryPoint ninjaEntryPoint) {
        this.ninjaEntryPoint = ninjaEntryPoint;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
    }
}
