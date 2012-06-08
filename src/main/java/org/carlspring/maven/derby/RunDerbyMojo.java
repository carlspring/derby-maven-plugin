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

/**
 * Runs the Derby server, blocking until the server no longer responds to pings.
 *
 * @author kphonik
 * @goal run
 * @requiresProject false
 */
public class RunDerbyMojo
        extends StartDerbyMojo {

    @Override
    public void doExecute()
            throws MojoExecutionException, MojoFailureException {
        try {

            super.doExecute();
            getLog().info("Blocking to wait for connections, use the @stop goal to kill.");

            while (true) {
                try {
                    server.ping();
                } catch (Exception e) {
                    getLog().info("Derby server is not responding to pings, exiting ...");
                    return;
                }
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            getLog().info("Server polling thread was killed, you should use the @stop goal instead.");
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

}
