package br.frico.bagarita.web;

import br.frico.bagarita.config.RootConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by Felipe Rico on 8/15/2016.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static Logger LOG = LogManager.getLogger();

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        LOG.info("Initializing servlet mappings...");
        return new String[] {"/*"};
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[] {new DelegatingFilterProxy("testIdFilter")};
//    }
}
