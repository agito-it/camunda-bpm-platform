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
package org.camunda.bpm.engine.impl.cmmn.entity.repository;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.query.QueryProperty;

/**
 * @author Roman Smirnov
 *
 */
public class CaseDefinitionQueryProperty implements QueryProperty {

  private static final long serialVersionUID = 1L;

  private static final Map<String, CaseDefinitionQueryProperty> properties = new HashMap<String, CaseDefinitionQueryProperty>();

  public static final CaseDefinitionQueryProperty CASE_DEFINITION_ID = new CaseDefinitionQueryProperty("RES.ID_");
  public static final CaseDefinitionQueryProperty CASE_DEFINITION_KEY = new CaseDefinitionQueryProperty("RES.KEY_");
  public static final CaseDefinitionQueryProperty CASE_DEFINITION_NAME = new CaseDefinitionQueryProperty("RES.NAME_");
  public static final CaseDefinitionQueryProperty CASE_DEFINITION_VERSION = new CaseDefinitionQueryProperty("RES.VERSION_");
  public static final CaseDefinitionQueryProperty CASE_DEFINITION_CATEGORY = new CaseDefinitionQueryProperty("RES.CATEGORY_");
  public static final CaseDefinitionQueryProperty DEPLOYMENT_ID = new CaseDefinitionQueryProperty("RES.DEPLOYMENT_ID_");

  private String name;

  public CaseDefinitionQueryProperty(String name) {
    this.name = name;
    properties.put(name, this);
  }

  public String getName() {
    return name;
  }

  public static CaseDefinitionQueryProperty findByName(String propertyName) {
    return properties.get(propertyName);
  }

}
