package com.owners.gravitas.dao.impl;

import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.owners.gravitas.dao.VersionDao;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;
import com.owners.gravitas.dto.request.ClientVersionRequest;

/**
 * The Class VersionDaoImpl.
 *
 * @author nishak
 */
@Repository
public class VersionDaoImpl extends BaseDaoImpl implements VersionDao {

    /**
     * Saves the application version info.
     *
     * @param clientVersionInfo
     *            the client version info
     * @param oldVersionInfo
     *            the old version info
     */
    @Override
    public void saveVersion( final Map< String, Map< String, ClientVersion > > clientVersionInfo,
            final Map< String, Version > oldVersionInfo ) {
        String reqUrl = buildFirebaseURL( "UIClient" );
        getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ),
                        clientVersionInfo ),
                ClientVersionRequest.class ).getBody();

        if (!CollectionUtils.isEmpty( oldVersionInfo )) {
            reqUrl = buildFirebaseURL( "/" );
            getRestTemplate().exchange( reqUrl, HttpMethod.PATCH,
                    buildRequest( createHttpHeader( getAuthenticator().authenticate().getAccessToken() ),
                            oldVersionInfo ),
                    ClientVersionRequest.class ).getBody();
        }
    }
}
