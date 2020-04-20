package com.owners.gravitas.audit;

import static com.owners.gravitas.constants.Constants.GRAVITAS;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.owners.gravitas.dto.ApiUser;

/**
 * This is auditor class which provides email address of currently logged in
 * user to
 * update audit records.
 *
 * @author vishwanathm
 *
 */
@Component
public class Auditor implements AuditorAware< String > {

    /**
     * Get the current logged in user from security context and returns the
     * email address if available otherwise the IP address.
     *
     * @return the current auditor
     */
    @Override
    public String getCurrentAuditor() {
        String auditor = null;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (ApiUser.class.isAssignableFrom( principal.getClass() )) {
                auditor = ( ( ApiUser ) principal ).getEmail();
            } else if (User.class.isAssignableFrom( principal.getClass() )) {
                auditor = ( ( User ) principal ).getUsername();
            } else {
                auditor = String.valueOf( principal );
            }
        } else {
            auditor = getRemoteUser();
        }
        return auditor;
    }

    /**
     * Retrieve the remote user IP.
     *
     * @return remote user IP address
     */
    private String getRemoteUser() {
        ServletRequestAttributes requestAttributes = ( ServletRequestAttributes ) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes != null ? requestAttributes.getRequest().getRemoteAddr() : GRAVITAS;
    }

}
