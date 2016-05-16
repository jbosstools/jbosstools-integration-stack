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

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.equinox.internal.p2.metadata.repository.CompositeMetadataRepository;
import org.eclipse.equinox.p2.core.ProvisionException;
import org.eclipse.equinox.p2.repository.IRepositoryReference;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepository;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.everyItem;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("restriction")
@RunWith(Parameterized.class)
public class P2RepositoryTest {

	static protected String[] getRootLocations() throws IOException {
		Set<String> rootlocations = new HashSet<String>();
		
		// Production sites
		rootlocations.add("https://devstudio.redhat.com/9.0/stable/updates/integration-stack/");
		rootlocations.add("https://devstudio.redhat.com/9.0/development/updates/integration-stack/");
		rootlocations.add("https://devstudio.redhat.com/9.0/staging/updates/integration-stack/");
		
		rootlocations.add("https://devstudio.redhat.com/updates/8.0/integration-stack/");
		rootlocations.add("https://devstudio.jboss.com/updates/8.0-development/integration-stack/");
		
		rootlocations.add("https://devstudio.jboss.com/updates/7.0/integration-stack/");
		rootlocations.add("https://devstudio.jboss.com/updates/7.0-development/integration-stack/");
		
		// Community sites
		rootlocations.add("http://download.jboss.org/jbosstools/mars/stable/updates/integration-stack");
		rootlocations.add("http://download.jboss.org/jbosstools/mars/development/updates/integration-stack");
		rootlocations.add("http://download.jboss.org/jbosstools/mars/staging/updates/integration-stack");
		
		rootlocations.add("http://download.jboss.org/jbosstools/updates/stable/luna/integration-stack");
		rootlocations.add("http://download.jboss.org/jbosstools/updates/development/luna/integration-stack/");

		rootlocations.add("http://download.jboss.org/jbosstools/updates/stable/kepler/integration-stack");
		rootlocations.add("http://download.jboss.org/jbosstools/updates/development/kepler/integration-stack/");

		// ide-config.properties derived sites
		rootlocations.addAll(getP2ReposFromProperties(IDEPropertiesSanityTest.loadIDEProperties()));
		
		return rootlocations.toArray(new String[0]);
	}

	@SuppressWarnings("rawtypes")
	private static Collection<String> getP2ReposFromProperties(Map properties)
			throws IOException {
		if (properties == null) {
			return Collections.emptySet();
		}
		Set<String> uris = new LinkedHashSet<>();		
		Set entries = properties.entrySet();
		
		for (Object entry : entries) {
			String key = (String) ((Entry)entry).getKey();
				
			if (key.contains(".discovery.site.")) {
				String value = (String) ((Entry)entry).getValue();
				if (value.contains("integration-stack"))
					uris.add(value);
			}
		}
		return uris;
	}

	@Parameters(name = "{0}")
	static public Collection<String[]> getLocations() throws URISyntaxException, IOException {

		String[] params = getRootLocations();
		Collection<String[]> result = new ArrayList<String[]>();

		for (String uri : params) {
			result.add(new String[] { uri });
		}
		return result;
	}

	static IProgressMonitor textProgressMontior() {
		return new NullProgressMonitor() {

			@Override
			public void beginTask(String name, int totalWork) {
				// System.out.println("Begin: " + name + "(" + totalWork + ")");
			}

			@Override
			public void setTaskName(String name) {
				System.out.println(name);
			}

			@Override
			public void subTask(String name) {
				System.out.println(name);
			}
		};
	};

	@Parameter
	public String location;

	private List<IStatus> errors;

	private Map<URI, IMetadataRepository> allrepositories;

	private IMetadataRepository repository;

	@Before
	public void loadContent() throws ProvisionException,
			OperationCanceledException, URISyntaxException {
		IMetadataRepositoryManager repositoryManager = Activator.getRepositoryManager();

		errors = new ArrayList<IStatus>();
		allrepositories = new HashMap<URI, IMetadataRepository>();

		repository = loadRepository(repositoryManager, allrepositories,
			new URI(location), false, errors, textProgressMontior());

	}

	@After
	public void cleanUp() {
		errors = null;
		allrepositories = null;
	}

	@Test
	public void noErrors() throws ProvisionException,
		OperationCanceledException, URISyntaxException {
		assertThat("Errors while loading", errors, IsEmptyCollection.empty());
	}

	@Test
	public void noStreamCrossing() throws URISyntaxException {
		String host = new URI(location).getHost();
		assertThat(
				"All referenced repositories should come from the same base host",
				allrepositories.keySet(), everyItem(hasHost(host)));
	}

	// @Test
	public void validReferenceSites() {
		// assertThat(repository.getReferences(),IsCollectionWithSize<E>);
		for (IRepositoryReference ref : repository.getReferences()) {
			IMetadataRepositoryManager repositoryManager = Activator.getRepositoryManager();
			ArrayList<IStatus> suberrors = new ArrayList<IStatus>();
			HashMap<URI, IMetadataRepository> suballrepositories = new HashMap<URI, IMetadataRepository>();

			loadRepository(repositoryManager, suballrepositories, ref.getLocation(),
					false, suberrors, textProgressMontior());

			assertThat("Errors while loading reference site " + ref.getLocation(),
					suberrors, IsEmptyCollection.empty());
		}
	}

	/** Simple matcher to check if host matches in URI */
	Matcher<URI> hasHost(final String host) {
		return new BaseMatcher<URI>() {
			@Override
			public boolean matches(final Object item) {
				final URI foo = (URI) item;
				return host.equals(foo.getHost());
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("getHost should return ").appendValue(
						host);
			}

			@Override
			public void describeMismatch(final Object item,
					final Description description) {
				description.appendText("was").appendValue(
						((URI) item).getHost());
			}

		};
	}

	protected static IMetadataRepository loadRepository(
			IMetadataRepositoryManager repoMgr,
			Map<URI, IMetadataRepository> allrepositories, URI location,
			boolean refresh, List<IStatus> errors, IProgressMonitor monitor) {
		
		if (!allrepositories.containsKey(location)) {
			try {
				IMetadataRepository repository;
				if (refresh) {
					repository = repoMgr.refreshRepository(location, monitor);
				} else {
					repository = repoMgr.loadRepository(location, monitor);
				}
				
				allrepositories.put(location, repository);

				if (repository instanceof CompositeMetadataRepository) {
					for (URI childUri : ((CompositeMetadataRepository) repository)
							.getChildren()) {
						// composite repository refresh refreshes all child
						// repositories. do not re-refresh children
						// here
						loadRepository(repoMgr, allrepositories, childUri,
								false, errors, monitor);
					}
				}

				return repository;
			} catch (ProvisionException e) {
				errors.add(e.getStatus());
			}
		}

		return null; // already loaded
	}
}
