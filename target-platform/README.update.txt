To update the versions of IUs included in a given .target file:

1. open the .target file

2. replace the older URL with a newer one

3. run this:

targetFile=integration-stack-base.target
mvn -U org.jboss.tools.tycho-plugins:target-platform-utils:0.16.0-SNAPSHOT:fix-versions -DtargetFile=${targetFile}
rm -f ${targetFile} *_update_hints.txt
mv -f ${targetFile}{_fixedVersion.target,}
