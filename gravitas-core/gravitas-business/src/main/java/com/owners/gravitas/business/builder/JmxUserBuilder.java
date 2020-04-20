package com.owners.gravitas.business.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.JmxUser;
import com.owners.gravitas.dto.JmxUserDto;
import com.owners.gravitas.service.JmxUserService;

/**
 * The Class JmxUserBuilder.
 * 
 * @author ankusht
 */
@Component
public class JmxUserBuilder extends AbstractBuilder< JmxUserDto, JmxUser > {

    /** The jmx user service. */
    @Autowired
    private JmxUserService jmxUserService;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public JmxUser convertTo( final JmxUserDto source, final JmxUser destination ) {
        JmxUser jmxUser = destination;
        if (source != null) {
            if (jmxUser == null) {
                jmxUser = new JmxUser();
            }
            jmxUser.setUsername( source.getUsername() );
            final String[] encryptedValues = jmxUserService.getEncryptedValues( source.getPassword() );
            jmxUser.setPassword( encryptedValues[0] );
            jmxUser.setIv( encryptedValues[1] );
        }
        return jmxUser;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public JmxUserDto convertFrom( final JmxUser source, final JmxUserDto destination ) {
        throw new UnsupportedOperationException( "convertFrom operation is not supported" );
    }

}
