/*
 * Copyright (C) 2005-2012 Alfresco Software Limited.
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.alfresco.demo;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.nodelocator.NodeLocatorService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;


/**
 * A basic controller that will display a message which is number of directories
 * in the company home folder.
 * Uses the NodeLocatorService to easily find nodes and the 
 * NodeService to display them
 * 
 * @author Michael Suzuki
 */
public class HelloWorld extends DeclarativeWebScript
{
    Log log = LogFactory.getLog(HelloWorld.class);
    private NodeService nodeService;
    private NodeLocatorService nodeLocatorService;

    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }
    
    public void setNodeLocatorService(NodeLocatorService nodeLocatorService) 
    {
        this.nodeLocatorService = nodeLocatorService;
    }
    
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status )
    {
        NodeRef companyHome = getCompanyHome();
        String companyHomeName = (String) nodeService.getProperty(companyHome, ContentModel.PROP_NAME);
        int directoryCount = childNodesCount(companyHome);
        HelloWorldMessage msg = new HelloWorldMessage(companyHomeName, directoryCount);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("demoMessage", msg);
        return model;
    }
    /**
     * This is a demo service interaction with Alfresco Foundation API.
     * This sample method returns the number of child nodes of a certain type 
     * under a certain node.
     * 
     * @return int folder count
     */
    public int childNodesCount(NodeRef nodeRef)
    {
        return nodeService.countChildAssocs(nodeRef, true);
    }
    
    /**
     * Returns the NodeRef of "Company Home"
     * 
     * @return {@link NodeRef} of company home
     */
    public NodeRef getCompanyHome()
    {
        return nodeLocatorService.getNode("companyhome", null, null);
    }
}
