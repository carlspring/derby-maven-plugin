package org.carlspring.maven.derby;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.maven.execution.DefaultMavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuilder;
import org.apache.maven.project.ProjectBuildingRequest;

/**
 * @author Jason Stiefel (jason@stiefel.io)
 * @author Denis N. Antonioli (denisa@sunrunhome.com)
 * @since 6/11/12
 */
public abstract class AbstractDerbyMojoTest
        extends AbstractMojoTestCase
{

    protected static final String TARGET_TEST_CLASSES = "target/test-classes";
    protected static final String POM_PLUGIN = TARGET_TEST_CLASSES + "/poms/pom-start.xml";

    protected Mojo lookupConfiguredMojo(String goal, String basedir)
            throws Exception
    {
        MavenProject project = readMavenProject(new File(basedir));
        Mojo mojo = lookupConfiguredMojo(project, goal);
        return mojo;
    }

    private MavenProject readMavenProject(File pom)
            throws Exception
    {
        MavenExecutionRequest request = new DefaultMavenExecutionRequest();
        request.setBaseDirectory(pom.getParentFile());
        ProjectBuildingRequest configuration = request.getProjectBuildingRequest();
        MavenProject project = lookup(ProjectBuilder.class).build(pom, configuration).getProject();
        return project;
    }

    protected boolean isDerbyUp(AbstractDerbyMojo mojo)
            throws SQLException
    {
        return DriverManager.getConnection("jdbc:derby://localhost:1527/db;user=derby;password=derby;create=true").isReadOnly();
    }

}
