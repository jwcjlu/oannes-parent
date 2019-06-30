package com.oannes.common;

import com.jwcjlu.oannes.common.services.BootServiceManager;

public abstract class AbstractProtocol implements Protocol{

    @Override
    public void onComplete() {
      BootServiceManager.INSTANCE.findBootService(ProtocolFactory.class).registerProtocol(getName(),this);
    }
    public abstract String getName();
}
