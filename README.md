# The JBoss Tools Integration Stack Project

## Summary
The Integration Stack for JBoss Developer Studio is a set of features and plugins for Eclipse that further enhances the IDE development functionality provided by JBoss Developer Studio. It’s where the Fuse Tooling, DataVirt Tooling and BRMS tooling is aggregated.  It is formed by three Maven projects which produce Eclipse p2 update sites, Mylyn discovery update mechanisms augmenting JBoss Central and an aggregation of associated component target dependencies and JBoss Core tooling target dependencies.  The p2 update sites are comprised of Final/GA and early-access repositories and offline zips files.  The target dependencies take the form of generated .target files (tycho mojo) and a target p2 update repositories split along Final/early-access and community/production lines.  This site also produces a stand-alone installer containing all of JBossTools core and the Integration Stack components as well as a stand-alone installer which contains core, IS and a supplemental runtime server.

## Components/ Frameworks

### JBoss Business Process and Rules Development

JBoss Business Process and Rules Development plug-ins provide design, debug and testing tooling for developing business processes for Red Hat JBoss BRMS and Red Hat JBoss BPM Suite.

* [BPEL Designer](http://tools.jboss.org/features/bpel.html) - Orchestrating your business processes.
* [BPMN2 Modeler](http://tools.jboss.org/features/bpmn2.html) - A graphical modeling tool which allows creation and editing of Business Process Modeling Notation diagrams using graphiti.
* [Drools](http://tools.jboss.org/features/drools.html) - A Business Logic integration Platform which provides a unified and integrated platform for Rules, Workflow and Event Processing.
* [jBPM](http://tools.jboss.org/features/jbpm.html) - A flexible Business Process Management (BPM) suite.

### JBoss Data Virtualization Development

JBoss Data Virtualization Development plug-ins provide a graphical interface to manage various aspects of Red Hat JBoss Data Virtualization instances, including the ability to design virtual databases and interact with associated governance repositories.

* [Teiid Designer](http://tools.jboss.org/features/teiiddesigner.html) - A visual tool that enables rapid, model-driven definition, integration, management and testing of data services without programming using the Teiid runtime framework.

### JBoss Fuse Development

An easy way to create integration applications using Apache Camel/ Component Library and to deploy them to JBoss Fuse or JBoss EAP.

* [Fuse Tooling] - JBoss Fuse Development provides tooling for Red Hat JBoss Fuse.  It features the latest versions of the Fuse Data Transformation tooling, SwitchYard and access to the Fuse SAP Tool Suite.

### JBoss Integration and SOA Development

* All of the Business Process and Rules Development plugins, plus...
* [Fuse Apache Camel Tooling](http://tools.jboss.org/features/apachecamel.html) - An easy way to create integration applications using Apache Camel/ Component Library and to deploy them to JBoss Fuse or JBoss EAP.
* [SwitchYard](http://tools.jboss.org/features/switchyard.html) - A lightweight service delivery framework providing full lifecycle support for developing, deploying, and managing service-oriented applications.

## Building the JBoss Tools Integration Stack Project

To build the _JBoss Tools Integration Stack_ project requires specific versions of Java, Maven and Tycho. 
The [How to Build JBoss Tools with Maven 3](https://community.jboss.org/wiki/HowToBuildJBossToolsWithMaven3)
document will guide you through that setup.

Fork a copy of the GIT project onto your local disk:

     https://github.com/jbosstools/jbosstools-integration-stack

### Building the JBoss Tools Integration Stack Target Platform Project (not required)

      $ cd .../jbosstools-integration-stack/target-platform
      $ mvn clean install  # specific -Pmirror if you'd like the TP repositories generated

The JBoss Tools Integration Stack Target Platform project creates four target files:

* An aggregate of the JBoss Tools Core target dependencies and JBoss Tools multiple dependencies + released Integration Stack base target dependencies (base).


----
	1. released integration-stack specific target dependencies:   integration-stack-base.target
	2. the JBoss Tools core target dependencies:                + core-base.target
	3. the JBoss Tools unified core target dependencies:        + jbosstools-multiple.target
                                                                   -----------------------------
        to generate an aggregate base target dependencies file:    aggregate-base.target
----
e.g.  http://download.jboss.org/jbosstools/builds/staging/JBTIS-target-platform/4.2.1.Final/aggregate-base.target

* An aggregate of the JBoss Tools Core target dependencies and JBoss Tools multiple dependencies + early access Integration Stack base target dependencies (base-ea).


----
	1. released integration-stack specific target dependencies:   integration-stack-base-ea.target
	2. the JBoss Tools core target dependencies:                + core-base.target
	3. the JBoss Tools unified core target dependencies:        + jbosstools-multiple.target
                                                                   --------------------------------
        to generate an aggregate base target dependencies file:    aggregate-base-ea.target
----
e.g.  http://download.jboss.org/jbosstools/builds/staging/JBTIS-target-platform/4.2.1.Final/aggregate-base-ea.target

* An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies + any other community dependencies (full).


----
	1. the previously generated aggregate base EA target file:    aggregate-base.target
	2. the community-specific target dependencies:              + community.target
                                                                   ---------------------
        to generate the full community target dependencies file:   aggregate-full.target
----
e.g.  http://download.jboss.org/jbosstools/builds/staging/JBTIS-target-platform/4.2.1.Final/aggregate-full.target

* An aggregate of the JBoss Tools Core target dependencies + early access Integration Stack base target dependencies + any other community dependencies (full-ea).


----
	1. the previously generated aggregate base EA target file:    aggregate-base-ea.target
	2. the community-specific target dependencies:              + community.target
                                                                   ------------------------
        to generate the full community target dependencies file:   aggregate-full-ea.target
----
e.g.  http://download.jboss.org/jbosstools/builds/staging/JBTIS-target-platform/4.2.1.Final/aggregate-full-ea.target

The JBoss Tools Integration Stack Target Platform project also creates four repositories:

e.g.  target-platform.target.repo - http://download.jboss.org/jbosstools/targetplatforms/jbtistarget/4.2.1.Final/jbtis/REPO/

e.g.  target-platform-ea.target.repo - http://download.jboss.org/jbosstools/targetplatforms/jbtistarget/4.2.1.Final/jbtis/earlyaccess/REPO/

e.g.  target-platform-devstudiois.target.repo - http://download.jboss.org/jbosstools/targetplatforms/jbtistarget/4.4.1.Final/devstudiois/REPO/

e.g.  target-platform-devstudiois-ea.target.repo - http://download.jboss.org/jbosstools/targetplatforms/jbtistarget/4.4.1.Final/devstudiois/earlyaccess/REPO/

The devstudiois repositories are built separately so as to avoid pulling in any community bits.

The integration-stack tycho target dependency (.target) files are deployed to the JBoss nexus releases repository for use by the integration stack component projects.  If you are an integration stack component developer or potentially a QE test developer, your maven POM target-platform-configuration should reference one of these as your target-platform artifact.

e.g.  https://repository.jboss.org/nexus/content/repositories/releases/org/jboss/tools/integration-stack/target-platform/4.2.1.Final/

Target artifacts local to the IS:

* integration-stack-base.target - integration stack component required dependencies
* core.base.target - JBoss tools core required dependencies
* community.target - dependencies specific to the community (not required for DEVSTUDIO)

Target artifacts drawn from org.jboss.tools.targetplatforms:

* jbosstools-multiple.target

### Building the JBoss Tools Integration Stack Community (jbosstools) Aggregate p2 Update Sites and JBoss Central Discovery

      $ cd .../jbosstools-integration-stack/jbosstools
      $ mvn clean install -Pstable -Pearlyaccess

This results in populating the site, site-final and site-ea target directories with a live repository, target platform repository and an associated zip install site.  The site directory is used for creating the JBoss Central discovery update artifacts.  The site-final directory contains only .Final released components.  The site-ea directory contains only early-access components.

### Building the JBoss Tools Integration Stack Production (devstudio) Aggregate p2 Update Sites and JBoss Central Discovery

      $ cd .../jbosstools-integration-stack/devstudio
      $ mvn clean install -Pstable -Pearlyaccess

This results in populating the site, site-ga and site-ea target directories with a live repository, target platform repository and an associated zip install site.  The site directory is used for creating the JBoss Central discovery update artifacts.  The site-ga directory contains only .Final released components.  The site-ea directory contains only early-access components.

## Examples

Here's an example of how to reference the derived aggregate base target file:


----
<build>
    <sourceDirectory>src</sourceDirectory>
    <plugins>

      <plugin>
        <groupId>org.eclipse.tycho</groupId>
        <artifactId>tycho-maven-plugin</artifactId>
        <version>${tycho-version}</version>
        <extensions>true</extensions>
      </plugin>

      <plugin>
        <groupId>org.eclipse.tycho</groupId>
        <artifactId>target-platform-configuration</artifactId>
        <version>${tycho-version}</version>

        <configuration>
	  <environments>
	    <environment>
	      <os>macosx</os>
	      <ws>cocoa</ws>
	      <arch>x86</arch>
	    </environment>
	    <environment>
	      <os>macosx</os>
	      <ws>cocoa</ws>
	      <arch>x86_64</arch>
	    </environment>
	    <environment>
	      <os>win32</os>
	      <ws>win32</ws>
	      <arch>x86</arch>
	    </environment>
	    <environment>
	      <os>win32</os>
	      <ws>win32</ws>
	      <arch>x86_64</arch>
	    </environment>
	    <environment>
	      <os>linux</os>
	      <ws>gtk</ws>
	      <arch>x86</arch>
	    </environment>
	    <environment>
	      <os>linux</os>
	      <ws>gtk</ws>
	      <arch>x86_64</arch>
	    </environment>
	  </environments>

          <!-- Pick up the merged target dependencies of the JBoss Tools core multiple target and the
	       base early-access JBoss Tools Integration Stack target platform dependencies. -->
          <target>
            <artifact>
              <groupId>org.jboss.tools.integration-stack</groupId>
              <artifactId>target-platform</artifactId>
              <version>${IS_TP_VERSION}</version>
              <type>target</type>
              <classifier>base-ea</classifier>
            </artifact>
          </target>
        </configuration>
      </plugin>
----
Don't forget to identify the repository where the target file can be found:


----
<distributionManagement>

    <repository>
      <id>jboss-releases-repository</id>
      <name>JBoss Releases Repository</name>
      <uniqueVersion>false</uniqueVersion>
      <url>https://repository.jboss.org/nexus/content/repositories/releases/</url>
      <layout>default</layout>
    </repository>

    <snapshotRepository>
      <id>jboss-snapshots-repository</id>
      <name>JBoss Snapshot Repository</name>
      <uniqueVersion>false</uniqueVersion>
      <url>https://repository.jboss.org/nexus/content/repositories/snapshots/</url>
      <layout>default</layout>
    </snapshotRepository>

</distributionManagement>
----

## Contribute fixes and features

The _JBoss Tools Integration Stack_ project is open source, and we welcome anyone that wants to participate and contribute.

