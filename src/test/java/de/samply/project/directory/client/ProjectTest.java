package de.samply.project.directory.client;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class ProjectTest {

    private static final String testFile = "https://deployment.ccp-it.dktk.dkfz.de/ccp-it-deployment-static/projects/projects.json";
    private static final String destinationFilePath = "./projects.json";

    @Test
    public void testGetProjects() throws ProjectDirectoryException {
        DktkProjectDirectory manager = new DktkProjectDirectory();
        manager.setSourceFilePath(testFile);
        manager.setDestinationFilePath(destinationFilePath);
        manager.initialize();

        List<Project> projList = manager.getAllProjects();
        assertEquals(projList.size(), 5);

        Project p = manager.getProject("DKTK000002016");
        assertTrue(p.getName().equals("NeoLung"));
    }

}
