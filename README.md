# The JBoss Tools Integration Stack Project

## Summary

This maven/ tycho project specifies the process for building the JBoss Tools Integration Stack project.  The JBoss Tools 
Integration Stack is comprised of layered JBoss Developer Studio (JBDS) features/plugins.  The project provides the ability to 
build a fully resolved equinox/p2 update repository.  The established Integration Stack capture is consistent with its JBoss 
core and target third-party dependencies but is independently releasable.  The project also provides the ability to build the
JBoss Central Discovery infrastructure.

## Components

* [JBoss BPEL Editor](https://access.redhat.com/knowledge/docs/en-US/JBoss_Developer_Studio/4.0/html-single/JBoss_BPEL_User_Guide/index.html)/ [Riftsaw Open Source BPEL Engine](http://www.jboss.org/riftsaw) 
* [Eclipse BPMN2 Modeler](http://eclipse.org/projects/project.php?id=soa.bpmn2-modeler)
* [Drools Business Logic integration Platform/ jBPM 5](http://www.jboss.org/drools/)
* [ESB - Enterprise Service Bus](http://www.jboss.org/jbossesb/)
* [Fuse IDE](http://fusesource.com/products/fuse-ide/)
* [jBPM 3 Legacy Flow Editor](http://www.jboss.org/jbpm/)
* [Modeshape Distributed, Hierarchical, Transactional, and Consistent Data Support](http://www.jboss.org/modeshape)
* [SOA Runtime Detection](https://github.com/jbosstools/jbosstools-runtime-soa)
* [Savara Testable Architecture Tool Support](http://www.jboss.org/savara)/ [Scribble Protocol Language](http://www.jboss.org/scribble)
* [Switchyard Lightweight Service Delivery Framework](http://www.jboss.org/switchyard.html)
* [Teiid Designer](http://www.jboss.org/teiiddesigner)

## Building the JBoss Tools Integration Stack Project

To build the _JBoss Tools Integration Stack_ project requires specific versions of Java and Maven. 
The [How to Build JBoss Tools with Maven 3](https://community.jboss.org/wiki/HowToBuildJBossToolsWithMaven3)
document will guide you through that setup.

Fork a copy of the GIT project onto your local disk:

     https://github.com/jbosstools/jbosstools-integration-stack

First build the target platform:

      $ cd .../jbosstools-integration-stack/target-platform
      $ mvn clean install

Second build the aggregate:

      $ cd .../jbosstools-integration-stack/aggregate-site
      $ mvn clean install

Then look for the integration-stack-master*.zip file in your repository.

You may also build the JBoss Central Discovery plugin and associated mylyn directory XML:

      $ cd .../jbosstools-integration-stack/discovery
      $ # example
      $ mvn clean install -DPREFIX=jbosstools-integration-stack -DVERSION=4.0.0 -DBUILD_TYPE=integration -DBUILD_NUMBER=99 -DBUILD_ALIAS=integration -DBUILD_TS=v20130212-1334

If you just want to check if things compile/ build you can run:

      $ mvn clean verify -DskipTest=true

## Target Platform Definition resolution within Maven/Tycho

The JBoss Tools Integration Stack Target Platform project creates two target files:

    * An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies.

      aggregate-base.target

    * An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies + any other community dependencies.

      aggregate-full.target

Here's an example of how to reference the derived aggregate-full.target file:

      <plugin>
        <groupId>org.eclipse.tycho</groupId>
        <artifactId>target-platform-configuration</artifactId>
        <version>${tycho-version}</version>

        <configuration>
          <environments>
            <environment>
              <os>linux</os>
              <ws>gtk</ws>
              <arch>x86</arch>
            </environment>
          </environments>

          <!-- Pick up the merged target dependencies of the JBoss Tools core unified target and the full
	       JBoss Tools Integration Stack (with community). -->
          <target>
            <artifact>
              <groupId>org.jboss.tools.integration-stack</groupId>
              <artifactId>target-platform</artifactId>
              <version>4.0.0-SNAPSHOT</version>
              <type>target</type>
              <classifier>full</classifier>
            </artifact>
          </target>
        </configuration>
      </plugin>

## Contribute fixes and features

The _JBoss Tools Integration Stack_ project is open source, and we welcome anyone that wants to participate and contribute.

