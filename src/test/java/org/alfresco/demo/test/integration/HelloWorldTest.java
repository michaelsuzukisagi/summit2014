package org.alfresco.demo.test.integration;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.alfresco.demo.HelloWorld;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tradeshift.test.remote.Remote;
import com.tradeshift.test.remote.RemoteTestRunner;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context.
 * 
 * This class uses the RemoteTestRunner to try and connect to 
 * localhost:4578 and send the test name and method to be executed on 
 * a running Alfresco. One or more hostnames can be configured in the @Remote
 * annotation.
 * 
 * If there is no available remote server to run the test, it falls 
 * back on local running of JUnits.
 * 
 * For proper functioning the test class file must match exactly 
 * the one deployed in the webapp (either via JRebel or static deployment)
 * otherwise "incompatible magic value XXXXX" class error loading issues will arise.  
 * 
 * @author Gabriele Columbro 
 * @author Maurizio Pillitu
 *
 */

@RunWith(RemoteTestRunner.class)
@Remote(runnerClass=SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:alfresco/application-context.xml")
public class HelloWorldTest 
{
    private static final String ADMIN_USER_NAME = "admin";

    @Autowired
    protected HelloWorld helloWorldComponent;
    
    @Autowired
    @Qualifier("NodeService")
    protected NodeService nodeService;
    
    @Test
    public void testWiring() 
    {
        assertNotNull(helloWorldComponent);
    }
    
    @Before
    public void setup()
    {
        AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
    }
    
    @Test
    public void testGetCompanyHome()
    {
        NodeRef companyHome = helloWorldComponent.getCompanyHome();
        assertNotNull(companyHome);
        String companyHomeName = (String) nodeService.getProperty(companyHome, ContentModel.PROP_NAME);
        assertNotNull(companyHomeName);
        assertEquals("Company Home", companyHomeName);
    }
    
    @Test
    public void testChildNodesCount() 
    {
        NodeRef companyHome = helloWorldComponent.getCompanyHome();
        int childNodeCount = helloWorldComponent.childNodesCount(companyHome);
        assertNotNull(childNodeCount);
        // There are 7 folders by default under Company Home
        assertEquals(7, childNodeCount);
    }

}
