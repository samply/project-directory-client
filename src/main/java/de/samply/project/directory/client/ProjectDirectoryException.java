package de.samply.project.directory.client;

public class ProjectDirectoryException extends Exception {

  public ProjectDirectoryException() {
  }

  public ProjectDirectoryException(String message) {
    super(message);
  }

  public ProjectDirectoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProjectDirectoryException(Throwable cause) {
    super(cause);
  }

  public ProjectDirectoryException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
