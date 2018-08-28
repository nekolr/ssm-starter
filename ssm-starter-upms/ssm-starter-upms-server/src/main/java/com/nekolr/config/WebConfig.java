package com.nekolr.config;

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
