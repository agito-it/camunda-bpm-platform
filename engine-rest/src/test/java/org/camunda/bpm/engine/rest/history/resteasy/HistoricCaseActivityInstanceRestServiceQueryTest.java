package org.camunda.bpm.engine.rest.history.resteasy;

import org.camunda.bpm.engine.rest.history.AbstractHistoricCaseActivityInstanceRestServiceQueryTest;
import org.camunda.bpm.engine.rest.history.AbstractHistoricCaseInstanceRestServiceQueryTest;
import org.camunda.bpm.engine.rest.util.EmbeddedServerBootstrap;
import org.camunda.bpm.engine.rest.util.ResteasyServerBootstrap;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class HistoricCaseActivityInstanceRestServiceQueryTest extends AbstractHistoricCaseActivityInstanceRestServiceQueryTest {

  protected static EmbeddedServerBootstrap serverBootstrap;

  @BeforeClass
  public static void setUpEmbeddedRuntime() {
    serverBootstrap = new ResteasyServerBootstrap();
    serverBootstrap.start();
  }

  @AfterClass
  public static void tearDownEmbeddedRuntime() {
    serverBootstrap.stop();
  }
}
