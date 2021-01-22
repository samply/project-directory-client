package de.samply.project.directory.client;

import java.util.List;

public class Project {

  private final String id;
  private final String name;
  private final String title;
  private final String type;
  private final List<String> dataCategory;

  /**
   * TODO.
   * @param id TODO
   */
  public Project(String id) {
    this.id = id;
    this.name = "";
    this.title = "";
    this.type = "";
    this.dataCategory = null;
  }

  /**
   * TODO.
   * @param id TODO
   * @param name TODO
   * @param title TODO
   * @param type TODO
   * @param dataCategory TODO
   */
  public Project(String id, String name, String title, String type, List<String> dataCategory) {
    this.id = id;
    this.name = name;
    this.title = title;
    this.type = type;
    this.dataCategory = dataCategory;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getTitle() {
    return title;
  }

  public String getType() {
    return type;
  }

  public List<String> getDataCategory() {
    return dataCategory;
  }
}
