package com.immomo.matrix.config.listener;

import java.util.concurrent.Executor;

import com.immomo.matrix.config.IConfigListener;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public abstract class ConfigListenerAdapter implements IConfigListener {

    @Override
    public Executor getExecutor() {
        return null;
    }

}
