package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.INVALID_USER_ID_PARAMETER;;

public class InvalidUserIdException extends ApplicationException {

    private static final long serialVersionUID = -2365497896391451749L;

    public InvalidUserIdException( String message ) {
        super( message, INVALID_USER_ID_PARAMETER );

    }
}
