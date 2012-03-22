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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * @author mtodorov
 * @goal            start
 * @requiresProject false
 */
public class StartDerbyMojo
        extends AbstractDerbyMojo
{

    /**
     * @parameter expression="${derby.debug}" default-value="true"
     */
    boolean debugStatements;


    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            System.setProperty("derby.system.home", getDerbyHome());

            System.setProperty("derby.language.logStatementText", String.valueOf(debugStatements));

            NetworkServerControl server = new NetworkServerControl(InetAddress.getLocalHost(),
                                                                   getPort(),
                                                                   getUsername(),
                                                                   getPassword());
            server.start(new PrintWriter(System.out));

            long maxSleepTime = 60000;
            long sleepTime = 0;
            boolean pong = false;

            while (!pong && sleepTime < maxSleepTime)
            {
                try
                {
                    server.ping();
                    pong = true;
                }
                catch (Exception e)
                {
                    sleepTime += 1000;
                    Thread.sleep(1000);
                }
            }

            if (pong)
            {
                getLog().info("Derby ping-pong: [OK]");
            }
            else
            {
                getLog().info("Derby ping-pong: [FAILED]");
                throw new MojoFailureException("Failed to start the NetworkServerControl." +
                                               " The server did not respond with a pong withing 60 seconds.");
            }
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
