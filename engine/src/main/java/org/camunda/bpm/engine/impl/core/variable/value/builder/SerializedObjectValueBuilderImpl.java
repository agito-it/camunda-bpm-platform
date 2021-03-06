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
package org.camunda.bpm.engine.impl.core.variable.value.builder;

import org.camunda.bpm.engine.impl.core.variable.value.ObjectValueImpl;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.builder.SerializedObjectValueBuilder;

/**
 * @author Daniel Meyer
 *
 */
public class SerializedObjectValueBuilderImpl implements SerializedObjectValueBuilder {

  protected ObjectValueImpl variableValue;

  public SerializedObjectValueBuilderImpl() {
    variableValue = new ObjectValueImpl(null, null, null, null, false);
  }

  public SerializedObjectValueBuilderImpl(ObjectValue value) {
    variableValue = (ObjectValueImpl) value;
  }

  public SerializedObjectValueBuilderImpl serializationDataFormat(String dataFormatName) {
    variableValue.setSerializationDataFormat(dataFormatName);
    return this;
  }

  public ObjectValue create() {
    return variableValue;
  }

  public SerializedObjectValueBuilder objectTypeName(String typeName) {
    variableValue.setObjectTypeName(typeName);
    return this;
  }

  public SerializedObjectValueBuilder serializedValue(String value) {
    variableValue.setSerializedValue(value);
    return this;
  }

}
