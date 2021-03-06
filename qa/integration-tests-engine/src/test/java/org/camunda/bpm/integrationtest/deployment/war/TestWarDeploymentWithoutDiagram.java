/**
 * Copyright (C) 2011, 2012 camunda services GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.integrationtest.deployment.war;

import java.io.IOException;

import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.camunda.bpm.integrationtest.util.TestHelper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Christian Lipphardt
 */
@RunWith(Arquillian.class)
public class TestWarDeploymentWithoutDiagram extends AbstractFoxPlatformIntegrationTest {

  @Deployment
  public static Archive<?> processArchive() {
    WebArchive archive = initWebArchiveDeployment()
            .addClass(TestHelper.class)
            .addAsResource("org/camunda/bpm/integrationtest/testDeployProcessArchive.bpmn20.xml");

    return processArchiveDeployment(archive);
  }
  
  @Test
  public void testDeployProcessArchiveDiagramCreationDisabled() throws IOException {
    String expectedDiagramResource = "/org/camunda/bpm/integrationtest/testDeployProcessArchive.png";
    String processDefinitionKey = "testDeployProcessArchive";
    TestHelper.assertDiagramIsDeployed(false, getClass(), expectedDiagramResource, processDefinitionKey);
  }
    
}
