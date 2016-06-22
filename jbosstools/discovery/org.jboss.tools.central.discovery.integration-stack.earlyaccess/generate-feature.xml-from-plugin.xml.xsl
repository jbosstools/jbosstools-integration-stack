<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xalan="http://xml.apache.org/xalan" xmlns:saxon="http://saxon.sf.net/" xmlns:xhtml="http://www.w3.org/1999/xhtml" 	extension-element-prefixes="saxon" exclude-result-prefixes="xsl xalan saxon xhtml">
<!-- you can run this transformation on the commandline like this (requires xsltproc):

cd jbosstools-integration-stack/jbosstools/discovery/org.jboss.tools.central.discovery.integration-stack;
for d in bpr ds soa fuse; do 
  mkdir -p ../../features/jboss.integration-stack.bundle.${d};
  xsltproc \-\-stringparam connectorDescriptorId jboss.integration-stack.bundle.${d} \-\-stringparam featureVersion 4.3.1.qualifier generate-feature.xml-from-plugin.xml.xsl plugin.xml | tee ../../features/jboss.integration-stack.bundle.${d}/feature.xml;
done 

-->
	<xsl:output method="xml" indent="yes" omit-xml-declaration="no" encoding="UTF-8"/>
  <xsl:strip-space elements="*"/>
 	<xsl:param name="connectorDescriptorId">jboss.integration-stack.bundle.fuse</xsl:param>
 	<xsl:param name="featureVersion">0.0.0.qualifier</xsl:param>
	<xsl:template match="//connectorDescriptor">
    <xsl:for-each select=".">
		    <xsl:choose>
          <xsl:when test="contains(@id,$connectorDescriptorId)">
						<feature id="{@id}" version="{$featureVersion}" label="%featureName" provider-name="%providerName"><xsl:text>&#xa;</xsl:text>
							<copyright>%copyright</copyright><xsl:text>&#xa;</xsl:text>
  						<license url="%licenseURL">%license</license><xsl:text>&#xa;</xsl:text>
  						<requires><xsl:text>&#xa;</xsl:text>
									<xsl:apply-templates select="*"/>
						  </requires><xsl:text>&#xa;</xsl:text>
						</feature>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>
	</xsl:template>
	<xsl:template match="iu">
		<import feature="{@id}"/><xsl:text>&#xa;</xsl:text>
	</xsl:template>
</xsl:stylesheet>
