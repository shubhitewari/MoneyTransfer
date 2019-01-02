package com.moneytransfer.exception;

public class DuplicateAccountIdException extends RuntimeException {

  public DuplicateAccountIdException(String message) {
    super(message);
  }
}
