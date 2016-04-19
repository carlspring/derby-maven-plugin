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

import java.sql.SQLException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Assert;

/**
 * @author Martin Todorov (carlspring@gmail.com)
 * @author Jason Stiefel (jason@stiefel.io)
 * @author Denis N. Antonioli (denisa@sunrunhome.com)
 */
public class StartDerbyMojoTest
        extends AbstractDerbyMojoTest
{

    StartDerbyMojo startMojo;
    StopDerbyMojo stopMojo;


    protected void setUp()
            throws Exception
    {
        super.setUp();

        startMojo = (StartDerbyMojo) lookupConfiguredMojo("start", POM_PLUGIN);
        stopMojo = (StopDerbyMojo) lookupConfiguredMojo("stop", POM_PLUGIN);
    }

    public void testMojoDefaultSettings()
    {
        Assert.assertFalse(startMojo.isSkip());
    }
    
    public void testNormalExecution()
            throws MojoExecutionException, MojoFailureException, InterruptedException, SQLException
    {
        startMojo.execute();
        Thread.sleep(CONNECTION_DELAY);

        assertDerbyIsUp();

        shutdownDerby();
    }

    public void testSkipOption()
            throws MojoExecutionException, MojoFailureException, InterruptedException
    {
        startMojo.setSkip(true);

        startMojo.execute();
        Thread.sleep(CONNECTION_DELAY);

        try
        {
            connectToDerby();  // should fail because Derby startup is skipped

            shutdownDerby();
            Assert.fail("Derby should not have been started.");
        }
        catch(SQLException ignored)
        {
            // expected
        }
    }

    private void shutdownDerby() 
            throws InterruptedException, MojoExecutionException, MojoFailureException {
        stopMojo.execute();
    }

}
