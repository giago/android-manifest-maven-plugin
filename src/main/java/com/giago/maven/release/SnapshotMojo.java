package com.giago.maven.release;


/**
 * Goal which change the version of the android manifest during the maven
 * release.
 * 
 * @goal snapshot
 * 
 */
public class SnapshotMojo extends ReleaseMojo {
  
  protected String replaceVersionName(String manifest) {
    return manipulator.replaceVersionName(manifest, projectVersion);
  }

}
