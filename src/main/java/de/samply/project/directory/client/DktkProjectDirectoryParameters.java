package de.samply.project.directory.client;

import de.samply.common.http.HttpConnector;

public class DktkProjectDirectoryParameters {

  private String sourceFilePath;
  private String destinationFilePath;
  private HttpConnector httpConnector;

  public String getSourceFilePath() {
    return sourceFilePath;
  }

  public void setSourceFilePath(String sourceFilePath) {
    this.sourceFilePath = sourceFilePath;
  }

  public String getDestinationFilePath() {
    return destinationFilePath;
  }

  public void setDestinationFilePath(String destinationFilePath) {
    this.destinationFilePath = destinationFilePath;
  }

  public HttpConnector getHttpConnector() {
    return httpConnector;
  }

  public void setHttpConnector(HttpConnector httpConnector) {
    this.httpConnector = httpConnector;
  }

}
