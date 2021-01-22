package de.samply.project.directory.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.samply.common.http.HttpConnector;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

public class DktkProjectDirectory implements ProjectDirectory {

  private String source = "Url"; // Url for remote, File for local file
  private URL url;


  private String sourceFilePath = "https://deployment.ccp-it.dktk.dkfz.de/ccp-it-deployment-static/projects/projects.json";
  private String destinationFilePath = "src/tmp/projects.json";
  private HttpConnector httpConnector;

  private HashMap<String, Project> projectMap;

  public DktkProjectDirectory() {
  }

  /**
   * TODO.
   * @param dktkProjectDirectoryParameters TODO
   */
  public DktkProjectDirectory(DktkProjectDirectoryParameters dktkProjectDirectoryParameters) {

    this.sourceFilePath = dktkProjectDirectoryParameters.getSourceFilePath();
    this.destinationFilePath = dktkProjectDirectoryParameters.getDestinationFilePath();
    this.httpConnector = dktkProjectDirectoryParameters.getHttpConnector();

  }

  /**
   * TODO.
   * @throws ProjectDirectoryException TODO
   */
  public void initialize() throws ProjectDirectoryException {
    try {
      url = new URL(sourceFilePath);
    } catch (Exception e) {
      throw new ProjectDirectoryException(e);
    }
    readFromSource();
  }

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

  /**
   * TODO.
   * @param id TODO
   * @return TODO
   * @throws ProjectDirectoryException TODO
   */
  public Project getProject(String id) throws ProjectDirectoryException {

    if (projectMap == null) {
      initialize();
    }

    return projectMap.get(id);
  }

  /**
   * TODO.
   * @throws ProjectDirectoryException TODO
   */
  public void readFromSource() throws ProjectDirectoryException {

    projectMap = new HashMap<>();
    try {
      JsonParser jsonParser = new JsonParser();
      JsonArray projList;

      if (source.equals("File")) {
        projList = jsonParser.parse(new FileReader(sourceFilePath)).getAsJsonArray();
      } else {
        CloseableHttpClient httpClient = getHttpClient(url);
        download(httpClient, url);
        projList = jsonParser.parse(new FileReader(destinationFilePath)).getAsJsonArray();
      }

      for (int i = 0; i < projList.size(); i++) {
        JsonObject projJson = (JsonObject) projList.get(i);
        String id = projJson.get("id").getAsString();
        String name = projJson.get("name").getAsString();
        String title = projJson.get("title").getAsString();
        String type = projJson.get("type").getAsString();
        List<String> dataCategory = new ArrayList<>();
        JsonArray jsonData = projJson.get("data_category").getAsJsonArray();
        for (int j = 0; j < jsonData.size(); j++) {
          dataCategory.add(jsonData.get(j).getAsString());
        }
        projectMap.put(id, new Project(id, name, title, type, dataCategory));
      }

    } catch (Exception e) {
      throw new ProjectDirectoryException(e);
    }

  }

  /**
   * TODO.
   * @return TODO
   * @throws ProjectDirectoryException TODO
   */
  public List<Project> getAllProjects() throws ProjectDirectoryException {

    if (projectMap == null) {
      initialize();
    }

    return new ArrayList<Project>(projectMap.values());

  }

  // Simple HttpConnector and download file utils
  // TODO: Add configuration to httpConnector

  private CloseableHttpClient getHttpClient(URL url) {

    HttpConnector httpConnector = getHttpConnector();
    return httpConnector.getHttpClient(url);

  }

  private void download(CloseableHttpClient httpClient, URL url)
      throws IOException, URISyntaxException {

    try (CloseableHttpResponse httpResponse = getHttpResponse(httpClient, url)) {
      download(httpResponse.getEntity().getContent());
    }

  }

  private void download(InputStream inputStream) throws IOException {

    try (OutputStream outputStream = new BufferedOutputStream(
        new FileOutputStream(destinationFilePath))) {

      byte[] chunk = new byte[1024];
      int chunkSize;
      while ((chunkSize = inputStream.read(chunk)) != -1) {
        outputStream.write(chunk, 0, chunkSize);
      }

      outputStream.flush();

    }

  }

  private CloseableHttpResponse getHttpResponse(CloseableHttpClient httpClient, URL url)
      throws URISyntaxException, IOException {

    HttpGet httpGet = new HttpGet(url.toURI());
    return httpClient.execute(httpGet);

  }

  private HttpConnector getHttpConnector() {

    if (httpConnector == null) {
      httpConnector = createHttpConnector();
    }

    return httpConnector;

  }

  public void setHttpConnector(HttpConnector httpConnector) {
    this.httpConnector = httpConnector;
  }

  private HttpConnector createHttpConnector() {

    HashMap config = new HashMap();
    HttpConnector httpConnector = new HttpConnector(config);
    return httpConnector;

  }


}
