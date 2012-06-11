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

import junit.framework.Assert;

/**
 * @author Jason Stiefel (jason@stiefel.io)
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

        runMojo = (RunDerbyMojo) lookupMojo("run", POM_PLUGIN);
        configureMojo(runMojo);
        stopMojo = (StopDerbyMojo) lookupMojo("stop", POM_PLUGIN);
        configureMojo(stopMojo);
        stopMojo.setFailIfNotRunning(true);
    }

    public void testMojo()
            throws Exception
    {

        RunningThread rt = new RunningThread(runMojo);
        System.out.println("Starting the run thread ...");
        rt.start();

        Thread.sleep(3000);

        System.out.println("Stopping the server ...");
        stopMojo.execute();

        Thread.sleep(3000);
        Assert.assertTrue("Running thread does not report ended.", rt.ended);
        Assert.assertFalse(rt.isAlive());

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
