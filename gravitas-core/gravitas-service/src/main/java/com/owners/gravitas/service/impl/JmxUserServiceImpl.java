package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.KEY_FOR_PASSWORD_ENC;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.JmxUser;
import com.owners.gravitas.repository.JmxUserRepository;
import com.owners.gravitas.service.JmxUserService;
import com.owners.gravitas.util.EncryptDecryptUtil;

/**
 * The Class JmxUserServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class JmxUserServiceImpl implements JmxUserService {

    /** The jmx user repository. */
    @Autowired
    private JmxUserRepository jmxUserRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.JmxUserService#save(com.owners.gravitas.
     * domain.entity.JmxUser)
     */
    @Override
    @Transactional
    public JmxUser save( final JmxUser jmxUser ) {
        return jmxUserRepository.save( jmxUser );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.JmxUserService#delete(java.lang.String)
     */
    @Override
    @Transactional
    public void delete( final String username ) {
        jmxUserRepository.deleteByUsername( username );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.JmxUserService#findByUsername(java.lang.
     * String)
     */
    @Override
    @Transactional( readOnly = true )
    public JmxUser findByUsername( final String username ) {
        return jmxUserRepository.findByUsername( username );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.JmxUserService#getEncryptedValues(java.lang.
     * String)
     */
    @Override
    public String[] getEncryptedValues( final String newPassword ) {
        final Map< String, String > map = new HashMap< String, String >();
        map.put( KEY_FOR_PASSWORD_ENC, newPassword );
        final java.lang.String[] encryptedVals = EncryptDecryptUtil.encrypt( map );
        return encryptedVals;
    }
}
