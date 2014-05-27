1. Build:

cd ~/tru/jbosstools-integration-stack/jbosstools
mvn clean install -DTARGET_PLATFORM=kepler -DBUILD_TYPE=integration -DIS_TP_VERSION=4.1.8.Final -DVERSION=4.1.5.Final -DBUILD_NUMBER=111 -DBUILD_ALIAS=CI-2013-08-21_20-10-23-B111 -DCOMPOSITE_SITE=file://`pwd`/site/target/repository       -DUPSTREAM_DIRECTORY_XML=http://download.jboss.org/jbosstools/updates/development/kepler/jbosstools-directory.xml       -DCOMPOSITE_URLS=http://download.jboss.org/jbosstools/updates/development/kepler/,http://download.jboss.org/jbosstools/updates/stable/kepler/integration-stack/aggregate/4.1.5.Final/,http://download.jboss.org/jbosstools/targetplatforms/jbtistarget/4.1.8.Final/REPO/

2. Fix generated directory.xml to point to locally-built jar, eg., use this

{code}
<entry url="org.jboss.tools.central.discovery.integration-stack_4.1.5.Final.CI-2013-08-21_20-10-23-B111-v20130101-0001-B111.jar" permitCategories="true"/>
{code}

instead of 

{code}
<entry url="http://download.jboss.org/jbosstools/discovery/stable/integration-stack/4.1.5.Final/org.jboss.tools.central.discovery.integration-stack_4.1.5.Final.CI-2013-08-21_20-10-23-B111-v20130101-0001-B111.jar" permitCategories="true"/>
{code}

2. Launch a local nanohttpd server [1] to host the directory.xml / discovery plugin, and the Discovery composite site.

{code}
cd ~/tru/jbosstools-integration-stack/jbosstools/discovery/generation/target/
/opt/sun-java2-7.0/bin/java NanoHTTPD -d `pwd` -p 8080
{code}


[1] https://github.com/NanoHttpd/nanohttpd/blob/master/core/src/main/java/fi/iki/elonen/NanoHTTPD.java

4. install Eclipse Kepler JEE bundle; launch at fresh workspace

5. install JBoss Central into Eclipse from http://localhost:8081/discovery-site/

6. re-launch Eclipse with 

{code}
./eclipse -vmargs \
-Djboss.discovery.directory.url=http://localhost:8080/plugins/jbosstools-integration-stack-directory.xml \
-Djboss.discovery.site.url=http://localhost:8081/discovery-site/
{code}

