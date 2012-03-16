package org.carlspring.maven.derby;

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
