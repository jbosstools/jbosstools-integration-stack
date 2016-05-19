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

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static com.jboss.jbds.integration_stack.tests.test.
   IDEPropertiesConstants.KNOWN_PROPERTIES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * This test iterates each integration-stack key,value in the ide-config
 * properties file and validates their values, targets and version are
 * known/sane.
 *
 * If you see failures reported by this it is either because:
 *
 *  a) something is inconsistent in ide-config.properties - fix the
 *     ide-config properties file.
 *  b) a valid value have been added that this test does not know about - fix
 *     the test.
 *
 * @author max
 *
 */
@RunWith(Parameterized.class)
public class IDEPropertiesSanityTest {

    /** Version specifier - major. */
    static final int MAJOR = 1;
    /** Version specifier - minor. */
    static final int MINOR = 2;
    /** Version specifier - micro. */
    static final int MICRO = 3;
    /** Version specifier - qualifier. */
    static final int QUAL  = 4;

    @SuppressWarnings({ "rawtypes" })
    @Parameters(name = "{0}")
    /**
     * @return
     * @throws IOException
     */
    static public Collection<String[]> getIDEProperties()
        throws IOException {

        Map rawProperties = loadIDEProperties();
		List<String[]> result =
           new ArrayList<String[]>(rawProperties.size());

		for (Iterator iterator = rawProperties.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			String propertyKey = (String) entry.getKey();
			if (propertyKey.contains("integration-stack")) {
			    result.add(new String[] {propertyKey,
                   (String) entry.getValue()});
			}
		}
		return result;
    }

	/** Unseen keys. */
	static private Set<String> unseenKeys = new HashSet<String>();

    /**
     * Add all known properties to the unseen keys.
     */
    @BeforeClass
	static public void setupAllKnownProperties() {
		unseenKeys.addAll(IDEPropertiesConstants.KNOWN_PROPERTIES);
	}

	/**
	 *
	 */
	@AfterClass
	static public void checkAllPropertiesBeenSeen() {
		//assertThat(unseenKeys, Matchers.)
		assertEquals("Did not see " + unseenKeys, unseenKeys.isEmpty(),
           true);
	}

    /** unprocessed ide-config key. */
	@Parameter
	public String rawkey;

    /** ide-config key value. */
	@Parameter(value = 1)
	public String value;
	
    /** ide-config property key. */
	String key;
    /** ide-config property target. */
	String target;
    /** ide-config property version. */
	String version;

    /** Set up/ split the raw key into its constituent parts. */
	@Before
	public void setup() {
		String[] elements = rawkey.split("\\|");

		assertTrue("Should minimum have 1 part", elements.length >= 1);

		if (elements.length >= 1) {
			key = elements[0];
		}

		if (elements.length >= 2) {
			target = elements[1];
		}

		if (elements.length >= 3) {
			version = elements[2];
		}

		assertTrue("Should maximum have 3 parts", elements.length <= 3);
	}
	
    /** Validate the key against known properties. */
	@Test
	public void testIsKnownProperty() {
		assertTrue(key + " is not a known property", KNOWN_PROPERTIES.contains(key));
		unseenKeys.remove(key);
	}
	
    /** Validate the target against known targets. */	/** Validate the target against known targets. */
	@Test
	public void testIsKnownTarget() {
		if (target != null) {
			assertTrue(target + " is not a known target",
					IDEPropertiesConstants.KNOWN_TARGETS.contains(target));
		}
	}
	
    /** Validate the version format. */
	@Test
	public void testIsValidVersion() {
		if (version != null) {
			String[] parts = version.split("\\.");
			assertThat("Version should have between 1 to 4 parts",
					parts.length,
					anyOf(equalTo(MAJOR), equalTo(MINOR), equalTo(MICRO), equalTo(QUAL)));
			if (parts.length >= 4) {
				assertThat("Qualifier is not known",
						IDEPropertiesConstants.KNOWN_VERSION_QUALIFIERS,
						hasItem(parts[3]));
			}
		}
	}

	@SuppressWarnings("rawtypes")
    /**
     * @return
     * @throws IOException
     */
	static public Map loadIDEProperties() throws IOException {
		URL url = new URL(IDEPropertiesConstants.PROPERTIES_LOCATION);
		Properties rawProperties = new Properties();
		try (InputStream in = url.openStream()) {
			Reader reader = new InputStreamReader(in, "UTF-8");
			rawProperties.load(reader);
		}
		return rawProperties;
	}
}
