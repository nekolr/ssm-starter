package com.nekolr.upms.server.config;

import com.nekolr.config.AbstractWebInitializer;
import com.nekolr.config.AppConfig;

/**
 * @author nekolr
 */
public class WebConfig extends AbstractWebInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class, ShiroConfig.class};
    }

    @Override
    protected boolean openDruidStatView() {
        return true;
    }

    @Override
    protected void registerCustomFilters() {

    }

    @Override
    protected void registerCustomServlets() {

    }
}
