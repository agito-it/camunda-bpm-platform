package com.camunda.fox.platform.test.functional.ejb.request.beans;

import javax.ejb.EJB;
import javax.inject.Named;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import com.camunda.fox.platform.test.util.JndiConstants;

/**
 * 
 * @author Daniel Meyer
 *
 */
@Named
public class InvocationCounterDelegateBean implements JavaDelegate {
  
  @EJB(lookup="java:global/" +
              JndiConstants.APP_NAME +
              "service/" +
              "InvocationCounterServiceBean!com.camunda.fox.platform.test.functional.ejb.request.beans.InvocationCounterService")
  private InvocationCounterService invocationCounterService;

  public void execute(DelegateExecution execution) throws Exception {    
    execution.setVariable("invocationCounter", invocationCounterService.getNumOfInvocations());      
  }
  
  
}