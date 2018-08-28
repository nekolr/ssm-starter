package com.nekolr.upms.config;

import com.nekolr.config.AbstractWebInitializer;

/**
 * @author nekolr
 */
public class WebConfig extends AbstractWebInitializer {

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
