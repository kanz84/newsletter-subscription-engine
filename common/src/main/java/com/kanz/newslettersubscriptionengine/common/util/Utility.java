package com.kanz.newslettersubscriptionengine.common.util;

import org.springframework.transaction.TransactionSystemException;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;


public class Utility {
    public static boolean isConstraintViolationException(Exception exception) {
        if (!(exception instanceof TransactionSystemException)) return false;
        if (!(exception.getCause() instanceof RollbackException)) return false;
        return (exception.getCause().getCause() instanceof ConstraintViolationException);
    }

}
