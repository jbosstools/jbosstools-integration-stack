<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <artifactId>site-ga</artifactId>

  <parent>
    <groupId>com.jboss.jbds.integration-stack</groupId>
    <artifactId>parent</artifactId>
    <version>8.0.2-SNAPSHOT</version>
  </parent>

  <name>JBDS Integration Stack Aggregate - GA</name>
  <packaging>eclipse-repository</packaging>

  <description>
    Aggregated JBoss Developer Studio Integration Stack Eclipse GA update site.

    This Maven project will create a GA update site for the layered release of the JBoss Developer Studio Integration Stack by mirroring 
    artifacts from the composite site.  The category.xml defines the content.
  </description>

  <properties>
    <update.site.name>JBoss Developer Studio Integration Stack</update.site.name>
    <update.site.version>JBDSIS-${VERSION}-${BUILD_ALIAS}</update.site.version>
    <siteTemplateFolder>./siteTemplateFolder</siteTemplateFolder>
    <UPDATE_SITE>http://www.qa.jboss.com/binaries/RHDS/builds/staging/JBDSIS-aggregate-disc/all/repo/</UPDATE_SITE>
  </properties>
<!--
  <repositories>
    <repository>
      <id>jbdsis-ea-update-and-targetplatform-site</id>
      <layout>p2</layout>
      <url>${EA_UPDATE_SITE}</url>
    </repository>
  </repositories>
-->
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

          <!-- Pick up the merged target dependencies of the JBoss Tools core unified target and the base
	       JBoss Tools Integration Stack. -->
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

      <plugin>
        <groupId>org.eclipse.tycho</groupId>
        <artifactId>tycho-packaging-plugin</artifactId>
        <version>${tycho-version}</version>

        <configuration>
          <format>'v'yyyyMMdd-HHmm'-H${BUILD_NUMBER}-${BUILD_ALIAS}'</format>
          <archiveSite>true</archiveSite>
        </configuration>
      </plugin>

      <plugin>
        <groupId>org.jboss.tools.tycho-plugins</groupId>
        <artifactId>repository-utils</artifactId>
        <version>${jboss-tycho-version}</version>

        <executions>
          <execution>
            <id>generate-facade</id>
            <phase>package</phase>
            <goals>
              <goal>generate-repository-facade</goal>
            </goals>

            <configuration>
              <referenceStrategy>compositeReferences</referenceStrategy>
              <siteTemplateFolder>${siteTemplateFolder}</siteTemplateFolder>
              <removeDefaultCategory>true</removeDefaultCategory>
              <symbols>
                <update.site.name>${update.site.name}</update.site.name>
                <update.site.description>${update.site.description}</update.site.description>
                <update.site.version>${update.site.version}</update.site.version>
                <target.eclipse.version>${target.eclipse.version}</target.eclipse.version>
              </symbols>
            </configuration>
          </execution>
        </executions>
      </plugin>

      <plugin>
        <artifactId>maven-assembly-plugin</artifactId>
        <executions>
          <execution>
            <id>assemble-site</id>
            <phase>package</phase>
            <goals>
              <goal>single</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <descriptors>
            <descriptor>src/main/assembly/descriptor.xml</descriptor>
          </descriptors>
        </configuration>
      </plugin>

      <plugin>
        <groupId>org.eclipse.tycho.extras</groupId>
        <artifactId>tycho-p2-extras-plugin</artifactId>
        <version>${tycho-version}</version>
        <executions>
          <execution>
            <phase>prepare-package</phase>
            <goals>
              <goal>mirror</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
          <source>
            <repository>
              <url>${targetplatform.url}</url>
              <layout>p2</layout>
            </repository>
          </source>
         <destination>${project.build.directory}/targetplatform</destination>
        </configuration>
      </plugin>

    </plugins>

  </build>

</project>
