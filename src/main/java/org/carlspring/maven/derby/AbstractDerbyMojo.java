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
     * @parameter expression="${basedir}"
     */
    public String basedir;

    /**
     * The port to start Derby on.
     *
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

    /**
     * @parameter expression="${derby.home}" default-value="${project.build.directory}/derby"
     */
    String derbyHome;


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

    public String getDerbyHome()
    {
        return derbyHome;
    }

    public void setDerbyHome(String derbyHome)
    {
        this.derbyHome = derbyHome;
    }

}
