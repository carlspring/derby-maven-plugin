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
 * @author Jason Stiefel (jason@stiefel.io)
 * @author Denis N. Antonioli (denisa@sunrunhome.com)
 */
public class RunDerbyMojoTest
        extends AbstractDerbyMojoTest
{

    RunDerbyMojo runMojo;
    StopDerbyMojo stopMojo;


    protected void setUp()
            throws Exception
    {
        super.setUp();

        runMojo = (RunDerbyMojo) lookupConfiguredMojo("run", POM_PLUGIN);
        stopMojo = (StopDerbyMojo) lookupConfiguredMojo("stop", POM_PLUGIN);
        stopMojo.setFailIfNotRunning(true);
    }

    public void testMojoDefaultSettings()
    {
        Assert.assertFalse(runMojo.isSkip());
    }
    
    public void testNormalExecution()
            throws Exception
    {
        RunningThread rt = startDerbyInBackground(runMojo);

        Thread.sleep(CONNECTION_DELAY);
        assertDerbyIsUp();

        stopMojo.execute();

        Thread.sleep(CONNECTION_DELAY);
        assertTrue("Running thread does not report ended.", rt.ended);
        assertFalse("Running thread is still alive.", rt.isAlive());
    }

    public void testSkipOption()
            throws Exception
    {
        runMojo.setSkip(true);

        startDerbyInBackground(runMojo);
        Thread.sleep(3000);

        try
        {
            connectToDerby();  // should fail because Derby startup is skipped

            shutdownDerby();
            Assert.fail("Derby should not have been started.");
        }
        catch (SQLException ignored)
        {
            // expected
        }
    }

    private RunningThread startDerbyInBackground(RunDerbyMojo runMojo) {
        RunningThread rt = new RunningThread(runMojo);
        System.out.println("Starting the run thread ...");
        rt.start();
        return rt;
    }

    private void shutdownDerby() 
            throws InterruptedException, MojoExecutionException, MojoFailureException {
        stopMojo.execute();
    }

    class RunningThread extends Thread
    {

        RunDerbyMojo runMojo;
        boolean ended = false;

        RunningThread(RunDerbyMojo runMojo)
        {
            this.runMojo = runMojo;
        }

        @Override
        public void run()
        {
            try
            {
                runMojo.execute();
                ended = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}
