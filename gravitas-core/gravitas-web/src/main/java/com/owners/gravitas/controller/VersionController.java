package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.Constants.PERIOD;
import static com.owners.gravitas.constants.UserPermission.UPDATE_APP_VERSION;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.VersionBusinessService;
import com.owners.gravitas.dto.request.ClientVersionRequest;
import com.owners.gravitas.dto.request.VersionRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.exception.InvalidArgumentException;

/**
 *
 * The Class VersionController.
 *
 * @author nishak
 */
@RestController
public class VersionController extends BaseWebController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( VersionController.class );

    /** VersionBusinessService instance. */
    @Autowired
    private VersionBusinessService versionBusinessService;

    /**
     * Adds/Updates the application version info.
     *
     * @param versionList
     *            the version list
     * @return the new version info response
     */
    @CrossOrigin
    @RequestMapping( value = "/app/version", method = RequestMethod.POST )
    @Secured( { UPDATE_APP_VERSION } )
    @PerformanceLog
    public @ResponseBody BaseResponse setClientVersion( @RequestBody @Valid final ClientVersionRequest versionList ) {
        LOGGER.debug( "Add or update app version information!!" );
        validateRequest( versionList );
        versionBusinessService.updateClientVersion( versionList.getVersionList() );
        return new BaseResponse();
    }

    /**
     * Validate request.
     *
     * @param versionList
     *            the version list
     */
    private void validateRequest( final ClientVersionRequest versionList ) {
        String minVersion = null;
        String currentVersion = null;
        final List< String > errors = new ArrayList< >();
        for ( final VersionRequest request : versionList.getVersionList() ) {
            minVersion = request.getMinVersion();
            currentVersion = request.getCurrentVersion();
            if (versionCompare( minVersion, currentVersion )) {
                errors.add( "error.invalid.version.value" );
            }
        }
        if (!errors.isEmpty()) {
            throw new InvalidArgumentException( "Invalid versions set in request", errors );
        }
    }

    /**
     * Version compare.
     *
     * @param minVersion
     *            the min version
     * @param currentVersion
     *            the current version
     * @return the int
     */
    private boolean versionCompare( final String minVersion, final String currentVersion ) {
        boolean result = false;
        final String[] minVersionValues = minVersion.split( "\\" + PERIOD );
        final String[] currentVersionValues = currentVersion.split( "\\" + PERIOD );
        int i = 0;
        while ( i < minVersionValues.length && i < currentVersionValues.length
                && minVersionValues[i].equals( currentVersionValues[i] ) ) {
            i++;
        }
        if (i < minVersionValues.length && i < currentVersionValues.length) {
            int diff = Integer.valueOf( minVersionValues[i] ).compareTo( Integer.valueOf( currentVersionValues[i] ) );
            result = Integer.signum( diff ) == 1;
        } else {
            result = Integer.signum( minVersionValues.length - currentVersionValues.length ) == 1;
        }
        return result;
    }
}
