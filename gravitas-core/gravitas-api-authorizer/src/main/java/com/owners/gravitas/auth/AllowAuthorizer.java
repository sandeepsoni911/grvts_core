package com.owners.gravitas.auth;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.Statement.Effect;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.owners.gravitas.auth.dto.AuthorizationRequest;
import com.owners.gravitas.auth.dto.AuthorizationResponse;
import com.owners.gravitas.auth.dto.LambdaApiAction;

public class AllowAuthorizer {

    /** The gson. */
    private Gson gson = new Gson();

    /** The parser. */
    private JsonParser parser = new JsonParser();

    /**
     * This handler allows all requests.
     *
     * @param inputStream
     *            the input stream
     * @param outputStream
     *            the output stream
     * @param context
     *            the context
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void handleRequest( final InputStream inputStream, final OutputStream outputStream, final Context context )
            throws IOException {
        final LambdaLogger logger = context.getLogger();

        // Parse input
        AuthorizationRequest request = parseInput( inputStream );
        logger.log( "Authorization: [" + request.getAuthorizationToken() + "], Type: [" + request.getType()
                + "], ARN: [" + request.getMethodArn() + "]" );

        // Validate token
        Effect effect = Effect.Allow;
        String validatedToken = "principalid-token";

        // Build response
        Statement stmt = new Statement( effect ).withResources( new Resource( request.getMethodArn() ) )
                .withActions( new LambdaApiAction() );
        Policy policy = new Policy().withStatements( stmt );

        // Write response
        JsonObject object = ( JsonObject ) gson.toJsonTree( new AuthorizationResponse( validatedToken ) );
        object.add( "policyDocument", parser.parse( policy.toJson() ) );
        outputStream.write( object.toString().getBytes() );

    }

    /**
     * Parses the input.
     *
     * @param inputStream
     *            the input stream
     * @return the authorization request
     */
    private AuthorizationRequest parseInput( final InputStream inputStream ) {
        final BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream, UTF_8 ) );
        return gson.fromJson( reader, AuthorizationRequest.class );
    }

}
