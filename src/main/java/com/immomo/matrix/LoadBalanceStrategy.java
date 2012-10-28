package com.immomo.matrix;

import java.util.List;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public interface LoadBalanceStrategy {

    String getTargetURI(List<String> targetURIs);

}
