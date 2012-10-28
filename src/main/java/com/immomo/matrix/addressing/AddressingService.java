package com.immomo.matrix.addressing;

import java.util.List;

import com.immomo.matrix.exception.URINotFoundException;

/**
 * Service addressing service.
 * <p>
 * You can add runtime service change notification and load balance on this
 * interface.
 * 
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public interface AddressingService {

    /**
     * Get all addresses of a service.
     */
    List<String> getAddresses(String applicationName, String serviceName);

    /**
     * Get an address of a service.
     */
    String getAddress(String applicationName, String serviceName) throws URINotFoundException;

}
