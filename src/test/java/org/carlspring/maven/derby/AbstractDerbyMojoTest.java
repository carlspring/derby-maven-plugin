package org.carlspring.maven.derby;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author jstiefel
 * @since 6/11/12
 */
public class AbstractDerbyMojoTest extends AbstractMojoTestCase {

    protected static final String TARGET_TEST_CLASSES = "target/test-classes";
    protected static final String POM_PLUGIN = TARGET_TEST_CLASSES + "/poms/pom-start.xml";

    protected void configureMojo(AbstractDerbyMojo mojo)
    {
        mojo.setDerbyHome(System.getProperty("basedir") + "/target/derby");
        mojo.setConnectionURL("jdbc:derby://localhost:1527/db;user=derby;password=derby");
        mojo.setPort(1527);
        mojo.setUsername("derby");
        mojo.setPassword("derby");
    }

}
