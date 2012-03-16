package org.carlspring.maven.derby;

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


    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
    {
        try
        {
            System.setProperty("derby.system.home", getBasedir() + "/target/derby");

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
