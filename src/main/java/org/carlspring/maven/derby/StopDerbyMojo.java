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

import java.sql.DriverManager;

/**
 * @author mtodorov
 * @goal            stop
 * @requiresProject false
 */
public class StopDerbyMojo
        extends AbstractDerbyMojo
{


    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            Class.forName(getDriver());

            DriverManager.getConnection(getConnectionURLShutdown()).close();
        }
        catch (Exception e)
        {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
