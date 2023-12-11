package com.informatica.b2btools.smbp.exception;

/**
 * The SMBPException class represents an exception specific to the SMB Profiler application.
 * It extends the standard Java Exception class and provides constructors for creating instances
 * with various error messages and causes.
 * @author asmishra
 * @since 11-12-2023
 */
public class SMBPException extends Exception {
    public SMBPException() {
        super();
    }

    public SMBPException(String message) {
        super(message);
    }

    public SMBPException(String message, Throwable cause) {
        super(message, cause);
    }

    public SMBPException(Throwable cause) {
        super(cause);
    }

    protected SMBPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
