package de.samply.project.directory.client;

import java.util.List;

public interface ProjectDirectory {

  Project getProject(String id) throws ProjectDirectoryException;

  List<Project> getAllProjects() throws ProjectDirectoryException;

}
