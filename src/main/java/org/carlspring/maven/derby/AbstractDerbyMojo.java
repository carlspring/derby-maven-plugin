package org.carlspring.maven.derby;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

/**
 * @author mtodorov
 */
public abstract class AbstractDerbyMojo
        extends AbstractMojo
{

    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    public MavenProject project;

    /**
     * The target directory where fas is installed and where the capture should be extracted
     *
     * @parameter expression="${basedir}"
     */
    public String basedir;

    /**
     * @parameter expression="${derby.port}"
     */
    int port;

    /**
     * @parameter expression="${derby.username}" default-value="derby"
     */
    String username;

    /**
     * @parameter expression="${derby.password}" default-value="derby"
     */
    String password;

    /**
     * @parameter expression="${derby.driver}"
     */
    String driver;

    /**
     * @parameter expression="${derby.url}"
     */
    String connectionURL;

    /**
     * @parameter expression="${derby.url.shutdown}"
     */
    String connectionURLShutdown;


    public MavenProject getProject()
    {
        return project;
    }

    public void setProject(MavenProject project)
    {
        this.project = project;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConnectionURL()
    {
        return connectionURL;
    }

    public void setConnectionURL(String connectionURL)
    {
        this.connectionURL = connectionURL;
    }

    public String getConnectionURLShutdown()
    {
        return connectionURLShutdown;
    }

    public void setConnectionURLShutdown(String connectionURLShutdown)
    {
        this.connectionURLShutdown = connectionURLShutdown;
    }

    public String getDriver()
    {
        return driver;
    }

    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public String getBasedir()
    {
        return basedir;
    }

    public void setBasedir(String basedir)
    {
        this.basedir = basedir;
    }

}
