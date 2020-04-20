package com.owners.gravitas.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.data.repository.query.parser.PartTree;

/**
 * This class is util for repository testing class.
 *
 * @author Harshad Shinde
 */
public final class RepositoryTestFactory {

    /**
     * Default constructor.
     */
    private RepositoryTestFactory() {
        // do not instantiate
    }

    /**
     * This method test the function and call its handler function.
     *
     * @param <T>
     *            - Class Type
     * @param repositoryInterface
     *            is the repository interface class.
     * @return result for test.
     */
    @SuppressWarnings( "unchecked" )
    public static < T > T test( final Class< T > repositoryInterface ) {
        return ( T ) Proxy.newProxyInstance( repositoryInterface.getClassLoader(), new Class[] { repositoryInterface },
                handler( new DefaultRepositoryMetadata( repositoryInterface ).getDomainType() ) );
    }

    /**
     * This is handler for testing repository.
     *
     * @param domainClass
     *            is the domain class to test.
     * @return null.
     */
    private static InvocationHandler handler( final Class< ? > domainClass ) {
        return new InvocationHandler() {

            @Override
            public Object invoke( final Object proxy, final Method method, final Object[] args ) throws Throwable {
                if (method.getAnnotation( Query.class ) != null) {
                    throw new AssertionError(
                            String.format( "This utility can't test %s.%s; it is annotated with @Query",
                                    method.getDeclaringClass().getSimpleName(), method.getName() ) );
                }
                new PartTree( method.getName(), domainClass );
                return null;
            }
        };
    }
}
