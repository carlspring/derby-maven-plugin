package org.carlspring.maven.derby;

/**
 * Copyright 2012 Martin Todorov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.derby.drda.NetworkServerControl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.net.InetAddress;

/**
 * @author Martin Todorov (carlspring@gmail.com)
 * @author Jason Stiefel (jason@stiefel.io)
 * @author Denis N. Antonioli (denisa@sunrunhome.com)
 */
public abstract class AbstractDerbyMojo
        extends AbstractMojo
{

    @Parameter(readonly = true, property = "project", required = true)
    public MavenProject project;

    /**
     * The port to start Derby on.
     */
    @Parameter(property = "derby.port")
    int port;

    /**
     * The username to use when authenticating.
     */
    @Parameter(property = "derby.username", defaultValue = "derby")
    String username;

    /**
     * The password to use when authenticating.
     */
    @Parameter(property = "derby.password", defaultValue = "derby")
    String password;

    /**
     * The absolute class name of the driver.
     */
    @Parameter(property = "derby.driver")
    String driver;

    /**
     * The URL to use when connecting.
     */
    @Parameter(property = "derby.url")
    String connectionURL;

    /**
     * The URL to use when shutting down the database.
     */
    @Parameter(property = "derby.url.shutdown", defaultValue="jdbc:derby:;shutdown=true")
    String connectionURLShutdown;

    /**
     * The directory to place the derby files in.
     */
    @Parameter(property = "derby.home", defaultValue = "${project.build.directory}/derby")
    String derbyHome;

    /**
     * Whether to run Derby with debugging statements.
     */
    @Parameter(property = "derby.debug", defaultValue = "true")
    boolean debugStatements;

    /**
     * Whether to bypass running Derby.
     */
    @Parameter(property = "derby.skip")
    boolean skip;

    /**
     * Shared {@link NetworkServerControl} instance for all mojos.
     */
    protected NetworkServerControl server;


    /**
     * Delegates the mojo execution to {@link #doExecute()} after initializing the {@link NetworkServerControl} for
     * localhost
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        if (skip)
        {
            getLog().info("Skipping Derby execution.");
            return;
        }

        setupDerby();

        doExecute();
    }

    protected void setupDerby()
            throws MojoExecutionException
    {
        System.setProperty("derby.system.home", getDerbyHome());
        System.setProperty("derby.language.logStatementText", String.valueOf(debugStatements));

        try
        {
            final InetAddress localHost = InetAddress.getByAddress("localhost", new byte[]{ 127, 0, 0, 1 });
            getLog().info("Initializing Derby server control for " + localHost);

            server = new NetworkServerControl(localHost,
                                              getPort(),
                                              getUsername(),
                                              getPassword());
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    /**
     * Implement mojo logic here.
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    protected abstract void doExecute() throws MojoExecutionException, MojoFailureException;

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

    public String getDerbyHome()
    {
        return derbyHome;
    }

    public void setDerbyHome(String derbyHome)
    {
        this.derbyHome = derbyHome;
    }

    public boolean isDebugStatements()
    {
        return debugStatements;
    }

    public void setDebugStatements(boolean debugStatements)
    {
        this.debugStatements = debugStatements;
    }

    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip(boolean skip)
    {
        this.skip = skip;
    }

}
