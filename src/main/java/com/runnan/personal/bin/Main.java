package com.runnan.personal.bin;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import com.runnan.personal.action.GetMostVisitedMerchants;
import com.runnan.personal.action.PostTransaction;
import com.runnan.personal.dataManager.DataManager;
import com.runnan.personal.dataManager.MemoryDataManager;
import com.runnan.personal.utils.GsonMessageBodyHandler;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class Main {

  public static void main(String[] args) throws Exception {
    Guice.createInjector(new SofiJerseyModule(args));

    Server server = new Server(8080);
    ServletContextHandler servletHandler = new ServletContextHandler();
    servletHandler.addFilter(GuiceFilter.class, "/*", null);
    servletHandler.addServlet(DefaultServlet.class, "/");

    server.setHandler(servletHandler);
    server.start();
    server.join();
  }

  // class to using guice inject models
  private static class SofiJerseyModule extends JerseyServletModule {

    private String[] args;

    public SofiJerseyModule(String[] args) {
      this.args = args;
    }


    @Override
    protected void configureServlets() {
      bind(PostTransaction.class);
      bind(GetMostVisitedMerchants.class);
      bind(GsonMessageBodyHandler.class).in(Singleton.class);
      bind(DataManager.class).to(MemoryDataManager.class);

      filter("/*").through(ServletFilter.class);

      Map<String, String> params = new HashMap<>();
      params.put("com.sun.jersey.spi.container.ResourceFilters", "com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory");
      params.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
      serve("/*").with(GuiceContainer.class, params);
    }
  }
}
