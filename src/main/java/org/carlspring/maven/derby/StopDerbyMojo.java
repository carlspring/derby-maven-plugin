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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author mtodorov
 */
@Mojo(name = "stop", requiresProject = false)
public class StopDerbyMojo
        extends AbstractDerbyMojo
{

    /**
     * Whether to fail, if Derby is not running.
     */
    @Parameter(property = "derby.fail.if.already.running", defaultValue = "true")
    boolean failIfNotRunning;


    @Override
    public void doExecute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            try
            {
                server.ping();
            }
            catch (Exception e)
            {
                if (failIfNotRunning)
                {
                    throw new MojoExecutionException("Failed to stop the Derby server, no server running!", e);
                }

                getLog().error("Derby server was already stopped.");
                return;
            }

            try
            {
                DriverManager.getConnection(getConnectionURLShutdown());
            }
            catch (SQLException e)
            {
                getLog().error(e.getMessage());
                // Apparently this prints out a bunch of stuff we're not currently interested in,
                // we just want it to shutdown properly.
                // Perhaps further handling might be required at a future point in time.
            }

            server.shutdown();

            // TODO: - investigate - isn't this done by server.shutdown() already?
            while (true)
            {
                Thread.sleep(1000);
                try
                {
                    server.ping();
                }
                catch (Exception e)
                {
                    getLog().info("Derby has stopped!");
                    break;
                }
            }

            System.getProperties().remove("derby.system.home");
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    public boolean isFailIfNotRunning()
    {
        return failIfNotRunning;
    }

    public void setFailIfNotRunning(boolean failIfNotRunning)
    {
        this.failIfNotRunning = failIfNotRunning;
    }

}
