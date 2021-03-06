/* Licensed under the Apache License, Version 2.0 (the "License");
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
package org.camunda.bpm.engine.rest.history.jersey;

import org.camunda.bpm.engine.rest.history.AbstractUserOperationLogRestServiceQueryTest;
import org.camunda.bpm.engine.rest.util.JerseyServerBootstrap;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author Danny Gräf
 */
public class UserOperationLogRestServiceQueryTest extends AbstractUserOperationLogRestServiceQueryTest {

  protected static JerseyServerBootstrap server;

  @BeforeClass
  public static void setUpEmbeddedRuntime() {
    server = new JerseyServerBootstrap();
    server.start();
  }

  @AfterClass
  public static void tearDownEmbeddedRuntime() {
    server.stop();
  }

}
