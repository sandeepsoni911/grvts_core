
package com.owners.gravitas.service.util;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The rest client service implementation.
 * 
 */
@Service("restServiceUtil")
public class RestServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceUtil.class);
	/** {@link RestTemplate} instance. */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * This method posts the json request to post rest url
	 * 
	 * @param url
	 *            - the URL of the post api
	 * @param jsonValue
	 *            - the jsonValue of the entity to be posted.
	 */

	public void postEntity(final String url, final String jsonValue) {
		LOGGER.debug("Hitting post url : {} with json : {}", url, jsonValue);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LOGGER.debug("Posting entity : {}  to url {} ",jsonValue , url);
		try {
			final HttpEntity<String> entity = new HttpEntity<>(jsonValue, headers);
			restTemplate.postForLocation(url, entity);
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while posting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}   errorCause : {} ",url, errorCause, rce);
			}
			throw rce;
		}
		LOGGER.debug("Hit for post url : {} is done", url);
	}

	public void deleteEntity(final String url) {
		LOGGER.debug("Hitting delte url : {}", url);
		try {
			restTemplate.delete(url);
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while deleting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {} & errorCause : {} ",url, errorCause, rce);
			}
			throw rce;
		}
		LOGGER.debug("Hit for delete url : {} is done", url);
	}

	public <T> T postEntity(final String url, final String jsonValue, final Class<T> clazz) {
		LOGGER.debug("Hitting post url : {} with json : {}", url, jsonValue);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LOGGER.debug("Posting entity : {} to url: {} ", jsonValue , url);
		try {
			final HttpEntity<String> entity = new HttpEntity<>(jsonValue, headers);
			final ResponseEntity<T> response = restTemplate.postForEntity(url, entity, clazz);
			return response.getBody();
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while posting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}  errorCause : {} " ,url, errorCause, rce);
			}
			throw rce;
		}
	}

	/**
	 * Get the entity by type of object.
	 * 
	 * @param url
	 *            - the URL for get entity.
	 * @param clazz
	 *            - the type of class for output.
	 */

	public <E> Object getEntityByType(final String url, final Class<E> clazz) {
		final E outputObject = restTemplate.getForObject(url, clazz);
		return outputObject;

	}

	public ResponseEntity<String> getEntity(final URI uri, final Class<String> clazz) {
		LOGGER.debug("Hitting get url : {} with class : {} ", uri, clazz);
		ResponseEntity<String> searchResponseEntity = null;
		try {
			/*
			 * final String authUser = "user"; final String authPassword =
			 * "password"; Authenticator.setDefault( new Authenticator() {
			 * public PasswordAuthentication getPasswordAuthentication() {
			 * return new PasswordAuthentication( authUser,
			 * authPassword.toCharArray()); } } );
			 * System.setProperty("http.proxyHost", "asinproxy.ascorp.com");
			 * System.setProperty("http.proxyPort", "80");
			 * System.setProperty("http.proxyUser", authUser);
			 * System.setProperty("http.proxyPassword", authPassword);
			 */
			searchResponseEntity = restTemplate.getForEntity(uri, clazz);
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while hitting aws uri: {} ", uri.getPath(), rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}  errorCause : {} " ,uri.getPath(), errorCause, rce);
			}
			throw rce;
		}
		return searchResponseEntity;
	}

	public <E> E getJsonEntity(final String url, final Class<E> clazz) {
		LOGGER.debug("Hitting get url : {} ", url);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LOGGER.debug("Getting entity : to url : {} " , url);
		try {
			final HttpEntity<String> entity = new HttpEntity<>(headers);
			final ResponseEntity<E> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
			return response.getBody();
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while posting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}  errorCause : {} " , url, errorCause, rce);
			}
			throw rce;
		}
	}

	public <T> T postEntity(final String url, final Object jsonObject, final Class<T> clazz) {
		final ObjectMapper objectMapper = new ObjectMapper();
		String json;
		try {
			json = objectMapper.writeValueAsString(jsonObject);
			return postEntity(url, json, clazz);
		} catch (final JsonProcessingException e) {
			LOGGER.error("Error while serializing object : {} " , jsonObject, e);
		}
		return null;
	}

	public <T> T postEntityWithGenericResponse(final String url, final Object jsonObject, final ParameterizedTypeReference<T> clazz) {
		final ObjectMapper objectMapper = new ObjectMapper();
		String json;
		try {
			json = objectMapper.writeValueAsString(jsonObject);
			return postEntityWithGenericResponse(url, json, clazz);
		} catch (final JsonProcessingException e) {
			LOGGER.error("Error while serializing object : {} " , jsonObject, e);
		}
		return null;
	}

	public <T> T postEntityWithGenericResponse(final String url, final String jsonValue,
			final ParameterizedTypeReference<T> clazz) {
		LOGGER.info("Hitting post url : {} with json : {} ", url, jsonValue);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LOGGER.debug("Posting entity : {}  to url {} " ,jsonValue, url);
		try {
			final HttpEntity<String> entity = new HttpEntity<>(jsonValue, headers);
			final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, clazz);
			return response.getBody();
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while posting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}  errorCause : {} " ,url, errorCause, rce);
			}
			throw rce;
		}
	}

	public <T> T getEntityWithGenericResponse(final String url, final ParameterizedTypeReference<T> clazz) {
		LOGGER.debug("Hitting get url : {}", url);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			final HttpEntity<String> entity = new HttpEntity<>(headers);
			final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
			return response.getBody();
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while getting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}  errorCause : {} " ,url, errorCause, rce);
			}
			throw rce;
		}
	}

	public <T> T deleteEntityWithGenericResponse(final String url, final ParameterizedTypeReference<T> clazz) {
		LOGGER.debug("Hitting get url : {}", url);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			final HttpEntity<String> entity = new HttpEntity<>(headers);
			final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, clazz);
			return response.getBody();
		} catch (final RestClientException rce) {
			LOGGER.error("Got exception while deleting entity to : {} " , url, rce);
			if (rce instanceof HttpStatusCodeException) {
				final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
				LOGGER.error("Error while hitting url : {}  errorCause : {} " ,url, errorCause, rce);
			}
			throw rce;
		}
	}

    public < T > T patchEntityWithGenericResponse( final String url, final Object jsonObject,
            final ParameterizedTypeReference< T > clazz ) {
        final ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString( jsonObject );
            return patchEntityWithGenericResponse( url, json, clazz );
        } catch ( final JsonProcessingException e ) {
            LOGGER.error( "Error while serializing object : {} " , jsonObject, e );
        }
        return null;
    }

    public < T > T patchEntityWithGenericResponse( final String url, final String jsonValue,
            final ParameterizedTypeReference< T > clazz ) {
        LOGGER.debug( "Hitting get url : {}", url );
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        try {
            final HttpEntity< String > entity = new HttpEntity<>( jsonValue, headers );
            final ResponseEntity< T > response = restTemplate.exchange( url, HttpMethod.PATCH, entity, clazz );
            return response.getBody();
        } catch ( final RestClientException rce ) {
            LOGGER.error( "Got exception while patching entity to : {} ", url, rce );
            if (rce instanceof HttpStatusCodeException) {
                final String errorCause = ( ( HttpStatusCodeException ) rce ).getResponseBodyAsString();
                LOGGER.error( "Error while hitting url : {}  errorCause : {} " ,url, errorCause, rce );
            }
            throw rce;
        }
    }
    
    public <T> T putEntityWithGenericResponse(final String url, final ParameterizedTypeReference<T> clazz) {
        LOGGER.debug("Hitting put url : {}", url);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            final HttpEntity<String> entity = new HttpEntity<>(headers);
            final ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, clazz);
            return response.getBody();
        } catch (final RestClientException rce) {
            LOGGER.error("Got exception while deleting entity to : {} ", url, rce);
            if (rce instanceof HttpStatusCodeException) {
                final String errorCause = ((HttpStatusCodeException) rce).getResponseBodyAsString();
                LOGGER.error("Error while hitting url : {}  errorCause : {} ", url, errorCause, rce);
            }
            throw rce;
        }
    }
    
    public < T > T putEntityWithGenericResponse( final String url, final Object jsonObject,
            final ParameterizedTypeReference< T > clazz ) {
        final ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            objectMapper.setSerializationInclusion(Include.NON_NULL);
            json = objectMapper.writeValueAsString( jsonObject );
            return putEntityWithGenericResponse( url, json, clazz );
        } catch ( final JsonProcessingException e ) {
            LOGGER.error( "Error while serializing object : {} " , jsonObject, e );
        }
        return null;
    }
    
    public < T > T putEntityWithGenericResponse( final String url, final String jsonValue,
            final ParameterizedTypeReference< T > clazz ) {
        LOGGER.info( "Hitting put url : {} with json {}", url, jsonValue );
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        try {
            final HttpEntity< String > entity = new HttpEntity<>( jsonValue, headers );
            final ResponseEntity< T > response = restTemplate.exchange( url, HttpMethod.PUT, entity, clazz );
            return response.getBody();
        } catch ( final RestClientException rce ) {
            LOGGER.error( "Got exception while putting entity to : {} ", url, rce );
            if (rce instanceof HttpStatusCodeException) {
                final String errorCause = ( ( HttpStatusCodeException ) rce ).getResponseBodyAsString();
                LOGGER.error( "Error while hitting url : {}  errorCause : {} " ,url, errorCause, rce );
            }
            throw rce;
        }
    }
}
