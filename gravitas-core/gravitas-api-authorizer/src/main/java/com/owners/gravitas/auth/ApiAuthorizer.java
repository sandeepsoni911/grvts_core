package com.owners.gravitas.auth;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.Statement.Effect;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.owners.gravitas.auth.dto.AuthorizationRequest;
import com.owners.gravitas.auth.dto.AuthorizationResponse;
import com.owners.gravitas.auth.dto.LambdaApiAction;
import com.owners.gravitas.auth.util.FirebaseConfig;

/**
 * The Class ApiAuthorizer authorizes the gravitas API requests.
 */
public class ApiAuthorizer {

    /** The Constant TOKEN_KEY. */
    private static final String TOKEN_KEY = "Token ";

    /** The is firebase initialized. */
    private boolean isFirebaseInitialized;

    /** The gson. */
    private Gson gson = new Gson();

    /** The parser. */
    private JsonParser parser = new JsonParser();

    /** The firebase config. */
    private FirebaseConfig firebaseConfig = new FirebaseConfig();

    /**
     * Handle request.
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

        // Initialize firebase instance
        if (!isFirebaseInitialized) {
            logger.log( "Initializing firebase for function:" + context.getFunctionName() );
            initializeFirebase( context.getFunctionName() );
            isFirebaseInitialized = true;
        }

        // Parse input
        AuthorizationRequest request = parseInput( inputStream );
        logger.log( "Authorization: [" + request.getAuthorizationToken() + "], Type: [" + request.getType()
                + "], ARN: [" + request.getMethodArn() + "]" );

        // Validate token
        Effect effect = Effect.Allow;
        String validatedToken = validateToken( request.getAuthorizationToken() );
        if (validatedToken == null) {
            effect = Effect.Deny;
        }
        logger.log( "Principal: [" + validatedToken + "], Effect: [" + effect + "]" );

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
     * Initialize firebase.
     *
     * @param functionName
     *            the function name
     */
    private void initializeFirebase( final String functionName ) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount( firebaseConfig.getAccessConfig( functionName ) ).build();
        FirebaseApp.initializeApp( options );
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

    /**
     * Validate token.
     *
     * @param idToken
     *            the id token
     * @return the string
     */
    private String validateToken( final String idToken ) {
        String validatedToken = null;
        if (idToken != null && idToken.startsWith( TOKEN_KEY )) {
            String tokenValue = idToken.substring( idToken.indexOf( TOKEN_KEY ) + TOKEN_KEY.length() );
            Task< FirebaseToken > authTask = FirebaseAuth.getInstance().verifyIdToken( tokenValue );
            try {
                Tasks.await( authTask );
            } catch ( ExecutionException | InterruptedException e ) {
                // Do nothing
            }
            if (authTask.isSuccessful()) {
                FirebaseToken decodedToken = authTask.getResult();
                validatedToken = decodedToken.getUid() + ":" + decodedToken.getEmail();
            } else if (authTask.getException() != null) {
                authTask.getException().printStackTrace();
            }
        }
        return validatedToken;
    }

}
