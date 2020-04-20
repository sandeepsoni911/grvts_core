package com.owners.gravitas.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.owners.gravitas.exception.ApplicationException;

/**
 * This class is used for encrption and decryption.
 *
 * @author ankusht
 *
 */
public final class EncryptDecryptUtil {

    /*
    public static void main( final String[] args ) {
        final Map< String, String > mainMap = new LinkedHashMap<>();
        mainMap.put( "Shikhar@123", "shikhar.parakh@owners.com" );
        mainMap.put( "Lavjeet@123", "lavjeet.khanuja@owners.com" );
        mainMap.put( "Anthony@123", "anthony.thilak@owners.com" );
        mainMap.put( "Manjunath@123", "manjunath.muniyappa@owners.com" );
        mainMap.put( "Sunil@123", "sunilkumar.kk@owners.com" );

        final AtomicInteger count = new AtomicInteger( 0 );
        mainMap.entrySet().forEach( entry -> {
            final String password = entry.getKey();
            final String email = entry.getValue();
            final Map< String, String > map = new HashMap<>();
            map.put( "password", password );
            final String[] encrypt = encrypt( map );
            final String query = "INSERT INTO `gr_jmx_user` (`ID`, `USERNAME`, `PASSWORD`, `IV`, `CREATED_BY`, `CREATED_ON`, `LAST_UPDATED_BY`, `LAST_UPDATED_ON`) SELECT MAX(ID) + 1, '%s', '%s', '%s', 'GRAVITAS', now(), 'GRAVITAS', now() from gr_jmx_user;";
            //final String query = "UPDATE `gr_jmx_user` SET `PASSWORD`='" + encrypt[0] + "', `IV`='" + encrypt[1]
            //        + "' WHERE username like '%" + email + "%';";
            System.out.println( String.format( query, email, encrypt[0], encrypt[1] ) );
        } );
    }
    */

    /** The key string. */
    /* dev private static final String KEY_STRING = "sd>f#$@(*&)*;Alt!%$EHUB"; */
    private static final String KEY_STRING = PropertiesUtil.getProperty( "security.key" );

    /** Constant Byte Array Size. **/
    private static final int ARRAY_SIZE = 16;

    /** The key. */
    private static final byte[] KEY = new byte[ARRAY_SIZE];

    /** The Constant UTF_8. */
    public static final String UTF_8 = "UTF-8";

    /**
     * Instantiates a new encrypt decrypt util.
     */
    private EncryptDecryptUtil() {

    }

    /**
     * Encrypts and encodes the Object and vector.
     *
     * @param obj
     *            the values to encrypt
     * @return the encrypted value and related vector
     */
    public static String[] encrypt( final Map< String, String > obj ) {
        try {
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            final ObjectOutput out = new ObjectOutputStream( stream );
            try {
                // Serialize the object
                out.writeObject( obj );
                final byte[] serialized = stream.toByteArray();

                // Setup the cipher and Init Vector
                final Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
                final byte[] initVector = new byte[cipher.getBlockSize()];
                new SecureRandom().nextBytes( initVector );
                final IvParameterSpec ivSpec = new IvParameterSpec( initVector );

                // Hash the key with SHA-256 and trim the output to 128-bit for
                // the key
                final MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
                digest.update( KEY_STRING.getBytes( UTF_8 ) );

                System.arraycopy( digest.digest(), 0, KEY, 0, KEY.length );
                final SecretKeySpec keySpec = new SecretKeySpec( KEY, "AES" );

                // encrypt
                cipher.init( Cipher.ENCRYPT_MODE, keySpec, ivSpec );

                // Encrypt & Encode the input
                final byte[] encrypted = cipher.doFinal( serialized );
                final byte[] base64Encoded = Base64.encodeBase64( encrypted );
                final String base64String = new String( base64Encoded, UTF_8 );

                // Encode the Init Vector
                final byte[] base64IV = Base64.encodeBase64( initVector );
                final String base64IVString = new String( base64IV, UTF_8 );

                return new String[] { base64String, base64IVString };
            } finally {
                stream.close();
                out.close();
            }
        } catch ( final Exception e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Decrypts the String and serializes the object.
     *
     * @param base64Data
     *            the base64 data
     * @param base64IV
     *            the base64 iv
     * @return the decrypted values
     */
    @SuppressWarnings( "unchecked" )
    public static Map< String, String > decrypt( final String base64Data, final String base64IV ) {
        try {
            // Decode the data
            final byte[] encryptedData = Base64.decodeBase64( base64Data.getBytes( UTF_8 ) );

            // Decode the Init Vector
            final byte[] rawIV = Base64.decodeBase64( base64IV.getBytes( UTF_8 ) );

            // Configure the Cipher
            final Cipher cipher = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
            final IvParameterSpec ivSpec = new IvParameterSpec( rawIV );
            final MessageDigest digest = MessageDigest.getInstance( "SHA-256" );
            digest.update( KEY_STRING.getBytes( UTF_8 ) );

            System.arraycopy( digest.digest(), 0, KEY, 0, KEY.length );
            final SecretKeySpec keySpec = new SecretKeySpec( KEY, "AES" );
            cipher.init( Cipher.DECRYPT_MODE, keySpec, ivSpec );

            // Decrypt the data..
            final byte[] decrypted = cipher.doFinal( encryptedData );

            // Deserialize the object
            final ByteArrayInputStream stream = new ByteArrayInputStream( decrypted );
            final ObjectInput inputStream = new ObjectInputStream( stream );
            Object obj = null;
            try {
                obj = inputStream.readObject();
            } finally {
                stream.close();
                inputStream.close();
            }
            Map< String, String > map = new HashMap< String, String >();
            map = ( Map< String, String > ) obj;
            return map;
        } catch ( final Exception e ) {
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Generate email link.
     *
     * @param contextUrl
     *            the context url
     * @param values
     *            the values
     * @param urlMapping
     *            the url mapping
     * @return the string
     */
    public static String generateEmailLink( final String contextUrl, final Map< String, String > values,
            final String urlMapping ) {
        final String[] encryptedData = EncryptDecryptUtil.encrypt( values );
        StringBuilder url;
        try {
            url = new StringBuilder( contextUrl ).append( urlMapping + "?a=" )
                    .append( URLEncoder.encode( encryptedData[0], UTF_8 ) ).append( "&b=" )
                    .append( URLEncoder.encode( encryptedData[1], UTF_8 ) );
            return url.toString();
        } catch ( final UnsupportedEncodingException uee ) {
            throw new ApplicationException( uee.getLocalizedMessage(), uee );
        }
    }
}
