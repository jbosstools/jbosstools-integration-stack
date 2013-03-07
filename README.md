# The JBoss Tools Integration Stack Project

## Summary

This Maven/ Tycho project specifies the process for building the JBoss Tools Integration Stack project.  The JBoss Tools 
Integration Stack is comprised of layered JBoss Developer Studio (JBDS) features/plugins.  The project provides the ability to 
build a fully resolved Equinox/p2 update repository as well as the ability to build the Mylyn JBoss Central Discovery update mechanism.  
The established Integration Stack capture is consistent with its JBoss core and target third-party dependencies but is independently 
releasable.

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

To build the _JBoss Tools Integration Stack_ project requires specific versions of Java, Maven and Tycho. 
The [How to Build JBoss Tools with Maven 3](https://community.jboss.org/wiki/HowToBuildJBossToolsWithMaven3)
document will guide you through that setup.

Fork a copy of the GIT project onto your local disk:

     https://github.com/jbosstools/jbosstools-integration-stack

First build the target platform:

      $ cd .../jbosstools-integration-stack/target-platform
      $ mvn clean install

Second build either the community (jbt) or production (jbds) aggregate and Mylyn discovery:

      $ cd .../jbosstools-integration-stack/jbt
      $ mvn clean install

Then look for the integration-stack-*.zip file in your repository.  You can use that zip file directly as your install 
site in the Eclipse/JBDS software install dialog (Help->Install New Software...).

If you just want to check if things compile/ build you can run:

      $ mvn clean verify -DskipTest=true

## Target Platform Definition Resolution within Maven/Tycho

The JBoss Tools Integration Stack Target Platform project creates two target files:

* An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies.

e.g. target-platform-4.0.0-base.target

* An aggregate of the JBoss Tools Core target dependencies + Integration Stack base target dependencies + any other community dependencies.

e.g. target-platform-4.0.0-full.target

These files are promoted to the nexus: (e.g.)

https://repository.jboss.org/nexus/content/groups/public/org/jboss/tools/integration-stack/target-platform/4.0.0-SNAPSHOT/

Here's an example of how to reference the derived aggregate base target file:

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

           <!-- Pick up the merged target dependencies of the JBoss Tools core unified target and the full
                JBoss Tools Integration Stack (with community). -->
           <target>
             <artifact>
               <groupId>org.jboss.tools.integration-stack</groupId>
               <artifactId>target-platform</artifactId>
               <version>4.0.0-SNAPSHOT</version>
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

