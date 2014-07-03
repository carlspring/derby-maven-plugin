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

/**
 * @author Martin Todorov (carlspring@gmail.com)
 * @author Jason Stiefel (jason@stiefel.io)
 */
public class StopDerbyMojoTest
        extends AbstractDerbyMojoTest
{

    StopDerbyMojo stopMojo;


    protected void setUp()
            throws Exception
    {
        super.setUp();

        stopMojo = (StopDerbyMojo) lookupMojo("stop", POM_PLUGIN);
        configureMojo(stopMojo);
    }

    public void testFailIfNotRunningAndShouldFail()
            throws Exception
    {
        stopMojo.setFailIfNotRunning(true);
        try
        {
            stopMojo.execute();
            fail("The failIfNotRunning attribute did not cause an exception as expected");
        }
        catch (MojoExecutionException ignored)
        {
        }
    }

    public void testFailIfNotRunningAndShouldNotFail()
            throws Exception
    {
        stopMojo.setFailIfNotRunning(false);
        stopMojo.execute();
    }

}
