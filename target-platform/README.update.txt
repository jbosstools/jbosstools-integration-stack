To update the versions of IUs included in a given .target file:

1. open the .target file

2. replace the older URL with a newer one

3. run this:

for tf in *.target; do 
  mvn -U org.jboss.tools.tycho-plugins:target-platform-utils:0.19.0-SNAPSHOT:fix-versions -DtargetFile=${tf}
  if [[ -f ${tf}_fixedVersion.target ]]; then rm -f ${tf} *_update_hints.txt; mv -f ${tf}{_fixedVersion.target,}; fi
done
