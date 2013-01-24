# The JBoss Integration Tools Project

## Summary

This maven/ tycho project specifies the process for building the JBoss Developer Studio Integration Platform
(a.k.a JBoss Integration Tools, JBoss Integration Stack).  The JBoss Integration Tools are comprised of
layered JBoss Developer Studio (JBDS) features/plugins.  The project provides the ability to 
build both an equinox/p2 update site and a fully resolved aggregate site assembly.  The established Integration 
Tools capture is consistent with its JBoss target dependencies but independently releasable. 

## Components

* [JBoss BPEL Editor](https://access.redhat.com/knowledge/docs/en-US/JBoss_Developer_Studio/4.0/html-single/JBoss_BPEL_User_Guide/index.html)/ [Riftsaw Open Source BPEL Engine](http://www.jboss.org/riftsaw) 
* [Eclipse BPMN2 Modeler](http://eclipse.org/projects/project.php?id=soa.bpmn2-modeler)
* [Drools Business Logic integration Platform/ jBPM 5](http://www.jboss.org/drools/)
* [ESB - Enterprise Service Bus](http://www.jboss.org/jbossesb/)
* [jBPM 3 Legacy Flow Editor](http://www.jboss.org/jbpm/)
* [Modeshape Distributed, Hierarchical, Transactional, and Consistent Data Support](http://www.jboss.org/modeshape)
* [SOA Runtime Detection](https://github.com/jbosstools/jbosstools-runtime-soa)
* [Savara Testable Architecture Tool Support](http://www.jboss.org/savara)/ [Scribble Protocol Language](http://www.jboss.org/scribble)
* [Switchyard Lightweight Service Delivery Framework](http://www.jboss.org/switchyard.html)
* [Teiid Designer](http://www.jboss.org/teiiddesigner)

## Building the JBoss Integration Tools Project

To build the _JBoss Integration Tools_ project requires specific versions of Java and Maven. 
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

Then look for the site_assembly.zip file in your repository.

If you just want to check if things compile/ build you can run:

      $ mvn clean verify -DskipTest=true

## Contribute fixes and features

The _JBoss Integration Tools_ project is open source, and we welcome anyone that wants to participate and contribute.

