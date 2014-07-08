package org.carlspring.maven.derby;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author Jason Stiefel (jason@stiefel.io)
 * @author Denis N. Antonioli (denisa@sunrunhome.com)
 * @since 6/11/12
 */
public abstract class AbstractDerbyMojoTest
        extends AbstractMojoTestCase
{

    protected static final String TARGET_TEST_CLASSES = "target/test-classes";
    protected static final String POM_PLUGIN = TARGET_TEST_CLASSES + "/poms/pom-start.xml";

    protected void configureMojo(AbstractDerbyMojo mojo)
    {
        mojo.setDerbyHome("target/derby");
        mojo.setConnectionURL("jdbc:derby://localhost:1527/db;user=derby;password=derby");
        mojo.setPort(1527);
        mojo.setUsername("derby");
        mojo.setPassword("derby");
    }

    protected boolean isDerbyUp(AbstractDerbyMojo mojo)
            throws SQLException
    {
        return DriverManager.getConnection(mojo.getConnectionURL() + ";create=true").isReadOnly();
    }

}
