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
package org.camunda.bpm.engine.impl.form.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.form.FormType;
import org.camunda.bpm.engine.impl.el.StartProcessVariableScope;
import org.camunda.bpm.engine.impl.form.FormFieldImpl;
import org.camunda.bpm.engine.impl.form.type.AbstractFormFieldType;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.variable.VariableMap;

/**
 * @author Daniel Meyer
 *
 */
public class FormFieldHandler {

  protected String id;
  protected Expression label;
  protected AbstractFormFieldType type;
  protected Expression defaultValueExpression;
  protected Map<String, String> properties = new HashMap<String, String>();
  protected List<FormFieldValidationConstraintHandler> validationHandlers = new ArrayList<FormFieldValidationConstraintHandler>();

  public FormField createFormField(ExecutionEntity executionEntity) {
    FormFieldImpl formField = new FormFieldImpl();

    // set id
    formField.setId(id);

    // set label (evaluate expression)
    VariableScope variableScope = executionEntity != null ? executionEntity : StartProcessVariableScope.getSharedInstance();
    Object labelValueObject = label.getValue(variableScope);
    if(labelValueObject != null) {
      formField.setLabel(labelValueObject.toString());
    }

    // set type
    formField.setType(type);

    // set default value (evaluate expression)
    if(defaultValueExpression != null) {
      Object defaultValue = defaultValueExpression.getValue(variableScope);
      if(defaultValue != null) {
        formField.setDefaultValue(type.convertFormValueToModelValue(defaultValue));
      } else {
        formField.setDefaultValue(null);
      }
    }

    // properties
    formField.setProperties(properties);

    // validation
    List<FormFieldValidationConstraint> validationConstraints = formField.getValidationConstraints();
    for (FormFieldValidationConstraintHandler validationHandler : validationHandlers) {
      // do not add custom validators
      if(!"validator".equals(validationHandler.name)) {
        validationConstraints.add(validationHandler.createValidationConstraint(executionEntity));
      }
    }

    return formField;
  }

  // submit /////////////////////////////////////////////

  public void handleSubmit(VariableScope variableScope, VariableMap values, VariableMap allValues) {
    Object submittedValue = values.remove(id);

    // update variable(s)
    Object modelValue = null;
    if (submittedValue != null) {
      final Object propertyValue = submittedValue;
      if (type != null) {
        modelValue = type.convertFormValueToModelValue(propertyValue);
      } else {
        modelValue = propertyValue;
      }
    } else if (defaultValueExpression != null) {
      final Object expressionValue = defaultValueExpression.getValue(variableScope);
      if (type != null && expressionValue != null) {
        modelValue = type.convertFormValueToModelValue(expressionValue.toString());
      } else if (expressionValue != null) {
        modelValue = expressionValue.toString();
      }
    }

    // perform validation
    for (FormFieldValidationConstraintHandler validationHandler : validationHandlers) {
      validationHandler.validate(modelValue, allValues, this, variableScope);
    }

    if (modelValue != null) {
      if (id != null) {
        variableScope.setVariable(id, modelValue);
      }
    }
  }

  // getters / setters //////////////////////////////////

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Expression getLabel() {
    return label;
  }

  public void setLabel(Expression name) {
    this.label = name;
  }

  public void setType(AbstractFormFieldType formType) {
    this.type = formType;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public FormType getType() {
    return type;
  }

  public Expression getDefaultValueExpression() {
    return defaultValueExpression;
  }

  public void setDefaultValueExpression(Expression defaultValue) {
    this.defaultValueExpression = defaultValue;
  }

  public List<FormFieldValidationConstraintHandler> getValidationHandlers() {
    return validationHandlers;
  }

  public void setValidationHandlers(List<FormFieldValidationConstraintHandler> validationHandlers) {
    this.validationHandlers = validationHandlers;
  }

}
