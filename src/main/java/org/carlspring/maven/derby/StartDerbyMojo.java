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
            System.setProperty("derby.system.home", getBasedir() + "/target/derby");

            if (debugStatements)
                System.setProperty("derby.language.logStatementText", "true");

            NetworkServerControl server = new NetworkServerControl(InetAddress.getLocalHost(),
                                                                   getPort(),
                                                                   getUsername(),
                                                                   getPassword());
            server.start(new PrintWriter(System.out));
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
