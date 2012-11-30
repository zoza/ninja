package ninja;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ninja.utils.NinjaPropertiesImpl;

import com.google.inject.Injector;

/**
 * This is where all the Ninja dispatching starts. Can e.g. be used in a filter or servlet. Most of the code originally
 * lived in {@link NinjaServletDispatcher}, but now there is a {@link NinjaServlet} too, so it has been moved here.
 * 
 * Targets Servlet 2.5
 * 
 * @author ra, henningschuetz
 * 
 */
public class NinjaEntryPoint {

    /**
     * Main injector for the class.
     */
    private Injector injector;

    /**
     * Our implementation for Ninja. Handles the complete lifecycle of the app.
     * Dispatches routes. Applies filters and so on.
     */
    private Ninja ninja;

    public NinjaEntryPoint(NinjaPropertiesImpl ninjaPropertiesImpl) {
        NinjaBootup ninjaBootup = new NinjaBootup(ninjaPropertiesImpl);
        injector = ninjaBootup.getInjector();
        ninja = injector.getInstance(Ninja.class);
        ninja.start();
    }

    public void handle(ServletRequest req,
            ServletResponse resp) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // We generate a Ninja compatible context element
        ContextImpl context = (ContextImpl) injector.getProvider(Context.class)
                .get();

        // And populate it
        context.init(request, response);

        // And invoke ninja on it.
        // Ninja handles all defined routes, filters and much more:
        ninja.invoke(context);
    }

    public void destroy() {
        ninja.shutdown();
        // We don't need the injector and ninja any more. Destroy!
        injector = null;
        ninja = null;
    }
}
