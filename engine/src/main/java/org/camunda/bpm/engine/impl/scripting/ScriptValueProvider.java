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
package org.camunda.bpm.engine.impl.scripting;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.core.variable.mapping.IoParameter;
import org.camunda.bpm.engine.impl.core.variable.mapping.value.ParameterValueProvider;
import org.camunda.bpm.engine.impl.core.variable.scope.AbstractVariableScope;

/**
 * Makes it possible to use scripts in {@link IoParameter} mappings.
 *
 * @author Daniel Meyer
 *
 */
public class ScriptValueProvider implements ParameterValueProvider {

  protected ExecutableScript script;

  public ScriptValueProvider(ExecutableScript script) {
    this.script = script;
  }

  public Object getValue(AbstractVariableScope variableScope) {
    return Context.getProcessEngineConfiguration()
      .getScriptingEnvironment()
      .execute(script, variableScope);
  }

  public ExecutableScript getScript() {
    return script;
  }

  public void setScript(ExecutableScript script) {
    this.script = script;
  }

}
