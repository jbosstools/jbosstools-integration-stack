# The JBoss Tools Integration Stack Project

## Summary
The Integration Stack is an aggregation of Eclipse features and plugins comprised of JBoss tooling in support of Data Virtualization, BRMS/BPMS, Fuse Tooling and legacy SOA 5.x development.  It is formed by a Maven project which produces an Eclipse p2 update site, a Mylyn discovery update mechanism augmenting JBoss Central and an aggregation of associated component target dependencies and JBoss Core tooling target dependencies.  The target dependencies take the form of generated .target files (tycho mojo) and a target p2 update repository.

## Components

### JBoss Business Process and Rules Development

* [BPEL Designer](http://tools.jboss.org/features/bpel.html) - Orchestrating your business processes.
* [BPMN2 Modeler](http://tools.jboss.org/features/bpmn2.html) - A graphical modeling tool which allows creation and editing of Business Process Modeling Notation diagrams using graphiti.
* [Drools](http://tools.jboss.org/features/drools.html) - A Business Logic integration Platform which provides a unified and integrated platform for Rules, Workflow and Event Processing.
* [jBPM](http://tools.jboss.org/features/jbpm.html) - A flexible Business Process Management (BPM) suite.

### JBoss Data Virtualization Development

* [Modeshape](http://tools.jboss.org/features/modeshape.html) - A distributed, hierarchical, transactional and consistent data store with support for queries, full-text search, events, versioning, references, and flexible and dynamic schemas. It is very fast, highly available, extremely scalable, and it is 100% open source.
* [Teiid Designer](http://tools.jboss.org/features/teiiddesigner.html) - A visual tool that enables rapid, model-driven definition, integration, management and testing of data services without programming using the Teiid runtime framework.

### JBoss Integration and SOA Development

* All of the Business Process and Rules Development plugins, plus...
* [Fuse Apache Camel Tooling](http://tools.jboss.org/features/apachecamel.html) - A graphical tool for integrating software components that works with Apache ServiceMix, Apache ActiveMQ, Apache Camel and the FuseSource distributions.
* [SwitchYard](http://tools.jboss.org/features/switchyard.html) - A lightweight service delivery framework providing full lifecycle support for developing, deploying, and managing service-oriented applications.

### SOA 5.x Development

* [JBoss ESB](http://www.jboss.org/jbossesb) - An enterprise service bus for connecting enterprise applications and services.
* [jBPM3](http://docs.jboss.com/jbpm/v3.2/userguide/html_single/) - A flexible Business Process Management (BPM) Suite - JBoss Enterprise SOA Platform 5.3.x compatible version.

## Building the JBoss Tools Integration Stack Project

To build the _JBoss Tools Integration Stack_ project requires specific versions of Java, Maven and Tycho. 
The [How to Build JBoss Tools with Maven 3](https://community.jboss.org/wiki/HowToBuildJBossToolsWithMaven3)
document will guide you through that setup.

Fork a copy of the GIT project onto your local disk:

     https://github.com/jbosstools/jbosstools-integration-stack

First build the target platform:

      $ cd .../jbosstools-integration-stack/target-platform
      $ mvn clean install

Second build the community (jbosstools) p2 aggregate and Mylyn discovery:

      $ cd .../jbosstools-integration-stack/jbosstools
      $ mvn clean install

Then look for the integration-stack-*.zip file in your repository.  You can use that zip file directly as your install 
site in the Eclipse/JBDS software install dialog (Help->Install New Software...).

If you just want to check if things compile/ build you can run:

      $ mvn clean verify

## Target Platform Definition Resolution within Maven/Tycho

The JBoss Tools Integration Stack Target Platform project creates two target files:

* An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies.

e.g. aggregate-base.target

* An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies + any other community dependencies.

e.g. aggregate-full.target

These files are promoted to the nexus: (e.g.)

https://repository.jboss.org/nexus/content/repositories/releases/org/jboss/tools/integration-stack/target-platform/4.2.0.Beta2a/

Here's an example of how to reference the derived aggregate base target file:

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

          <!-- Pick up the merged target dependencies of the JBoss Tools core unified target and the
	       full JBoss Tools Integration Stack. -->
          <target>
            <artifact>
              <groupId>org.jboss.tools.integration-stack</groupId>
              <artifactId>target-platform</artifactId>
              <version>${IS_TP_VERSION}</version>
              <type>target</type>
              <classifier>base</classifier>
            </artifact>
          </target>
        </configuration>
      </plugin>

Don't forget to identify the repository where the target file can be found:

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

## Contribute fixes and features

The _JBoss Tools Integration Stack_ project is open source, and we welcome anyone that wants to participate and contribute.

