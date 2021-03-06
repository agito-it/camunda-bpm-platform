package org.camunda.bpm.integrationtest.functional.ejb.local;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.integrationtest.functional.ejb.local.bean.CallbackBean;
import org.camunda.bpm.integrationtest.functional.ejb.local.bean.InvokeStartProcessDelegateSLSB;
import org.camunda.bpm.integrationtest.functional.ejb.local.bean.StartProcessInterface;
import org.camunda.bpm.integrationtest.functional.ejb.local.bean.StartProcessSLSB;
import org.camunda.bpm.integrationtest.util.AbstractFoxPlatformIntegrationTest;
import org.camunda.bpm.integrationtest.util.TestContainer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 *
 * This Test deploys two processes:
 * - LocalSLSBInvocationTest.testStartProcess.bpmn20.xml  (1)
 * - LocalSLSBInvocationTest.callbackProcess.bpmn20.xml (2)
 *
 * Two applications are deployed:
 * <ul>
 * <li>test.war - Process Application providing Processes (1+2)</li>
 * <li>service.war - application providing a Local SLSB starting Process (2)</li>
 * </ul>
 *
 *
 * Expected Control flow:
 *
 * <pre>
 *    test.war                                 service.war
 *    ========                                 ===========
 *
 * start (unit test)
 *   Process (1)
 *      |
 *      v
 *   InvokeStartProcessDelegateSLSB  ---->    StartProcessSLSB
 *                                               start Process (2)
 *                                                  |
 *                                                  V
 *       CallbackBean         <-----------  Process Engine
 *  </pre>
 *
 *
 *
 * @author Daniel Meyer
 *
 */
@RunWith(Arquillian.class)
public class LocalSLSBInvocationWithCallbackTest extends AbstractFoxPlatformIntegrationTest {

  @Deployment(name="pa", order=2)
  public static Archive<?> processArchive() {
    WebArchive archive = initWebArchiveDeployment()
      .addClass(InvokeStartProcessDelegateSLSB.class)
      .addClass(CallbackBean.class)
      .addAsResource("org/camunda/bpm/integrationtest/functional/ejb/local/LocalSLSBInvocationTest.testStartProcess.bpmn20.xml")
      .addAsResource("org/camunda/bpm/integrationtest/functional/ejb/local/LocalSLSBInvocationTest.callbackProcess.bpmn20.xml")
      .addAsWebInfResource("org/camunda/bpm/integrationtest/functional/ejb/local/jboss-deployment-structure.xml","jboss-deployment-structure.xml");

    return processArchiveDeployment(archive);
  }

  @Deployment(order=1)
  public static Archive<?> delegateDeployment() {
    WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "service.war")
      .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
      .addClass(AbstractFoxPlatformIntegrationTest.class)
      .addClass(StartProcessSLSB.class)
      .addClass(StartProcessInterface.class)
      .addAsManifestResource(new StringAsset("Dependencies: org.camunda.bpm.camunda-engine"), "MANIFEST.MF"); // get access to engine classes

    TestContainer.addContainerSpecificResourcesForNonPa(webArchive);

    return processArchiveDeployment(webArchive);
  }

  @Test
  @OperateOnDeployment("pa")
  public void testInvokeBean() throws Exception{

    ProcessInstance pi = runtimeService.startProcessInstanceByKey("testInvokeBean");

    Assert.assertEquals(true, runtimeService.getVariable(pi.getId(), "result"));

    taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult().getId());
  }

}
