package com.jwcjlu.oannes.common.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CompositeService extends AbstractService {
    private volatile List<Service> serviceList = new ArrayList<Service>();

    public void addService(Service service) {
        serviceList.add(service);
    }

    @Override
    public void initialize() throws Throwable {
        for (Service service : serviceList) {
            service.initialize();
        }
        super.initialize();
    }

    @Override
    public void boot() {
        getServiceList().forEach((service) -> {
            service.boot();
        });
        super.boot();
    }

    @Override
    public void shutdown() throws IOException {
        for (Service service : serviceList) {
            service.shutdown();
        }
        super.shutdown();
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    @Override
    public void onComplete() {
        serviceList.forEach((service) -> {
            service.onComplete();
        });
        super.onComplete();
    }

}
