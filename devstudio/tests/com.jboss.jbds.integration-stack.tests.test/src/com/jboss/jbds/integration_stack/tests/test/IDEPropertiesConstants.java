/*******************************************************************************
 * Copyright (c) 2016 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/

package com.jboss.jbds.integration_stack.tests.test;

import java.util.HashSet;
import java.util.Set;

public interface IDEPropertiesConstants {

   final static String PROPERTIES_LOCATION = "http://download.jboss.org/jbosstools/configuration/ide-config.properties";

   @SuppressWarnings("serial")
   static final Set<String> knownProperties = new HashSet<String>() {
      {
         add("jboss.discovery.site.integration-stack.url");
         add("jboss.discovery.earlyaccess.site.integration-stack.url");
         add("jboss.discovery.site.integration-stack-sap.url");
      }
   };
	
   @SuppressWarnings("serial")
   static final Set<String> knownTargets = new HashSet<String>() {
      {
         add("devstudio");
         add("jbosstools");
      }
   };

   @SuppressWarnings("serial")
   static final Set<String> knownVersionQualifiers = new HashSet<String>() {
      {
         add("Alpha1");
         add("Alpha2");
         add("Beta1");
         add("Beta2");
         add("Beta3");
         add("CR1");
         add("CR1a");
         add("Final");
         add("GA");
      }
   };
	
}
