package com.giago.maven.release;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Goal which change the version of the android manifest during the maven
 * release.
 * 
 * @goal release
 * 
 */
public class ReleaseMojo extends AbstractMojo {

  private static final String ANDORID_MANIFEST_NAME = "AndroidManifest.xml";

  /**
   * Location of the file.
   * 
   * @parameter expression="${basedir}"
   * @required
   */
  private File basedir;

  /**
   * Version of the project.
   * 
   * @parameter expression="${project.version}"
   * @required
   */
  protected String projectVersion;

  protected AndroidManifestManipulator manipulator = new AndroidManifestManipulator();

  public void execute() throws MojoExecutionException, MojoFailureException {
    File manifestFile = new File(basedir, ANDORID_MANIFEST_NAME);
    if (!manifestFile.exists()) {
      return;
    }
    updateVersion(manifestFile);
  }

  private void updateVersion(File manifestFile) throws MojoFailureException {
    LineIterator it = null;
    try {
      String manifest = FileUtils.readFileToString(manifestFile);
      if (manifest == null) {
        throw new MojoFailureException("Content of the android manifest is null");
      }
      manifest = replaceVersionName(manifest);
      int currentCode = manipulator.getVersionCode(manifest);
      manifest = manipulator.replaceVersionCode(manifest, ++currentCode);
      FileUtils.writeStringToFile(manifestFile, manifest);
    } catch (Exception e) {
      throw new MojoFailureException("Problem reading manifest " + e.getMessage());
    } finally {
      LineIterator.closeQuietly(it);
    }
  }
  
  protected String replaceVersionName(String manifest) {
    return manipulator.replaceVersionNameWithouhSnapshot(manifest, projectVersion);
  }

}
