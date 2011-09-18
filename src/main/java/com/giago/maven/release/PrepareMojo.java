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
 * @goal prepare
 * 
 */
public class PrepareMojo extends AbstractMojo {

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
  private String projectVersion;

//  /**
//   * @component
//   * @required
//   */
//  private ScmManager manager;

//  /**
//   * @component
//   * @required
//   */
//  private ScmProvider provider;

//  /**
//   * @parameter
//   * @required
//   */
//  private String connectionUrl;

//  /**
//   * List of provider implementations.
//   * 
//   * @parameter
//   * @since 2.0-beta-6
//   */
//  private Map providerImplementations;

  private AndroidManifestManipulator manipulator = new AndroidManifestManipulator();

  public void execute() throws MojoExecutionException, MojoFailureException {
//    setScmImplementation();
    File manifestFile = new File(basedir, ANDORID_MANIFEST_NAME);
    if (!manifestFile.exists()) {
      return;
    }
    updateVersion(manifestFile);
//    pushChanges();
  }

//  private void pushChanges() throws MojoExecutionException {
//    try {
//      ScmFileSet files = new ScmFileSet(basedir, ANDORID_MANIFEST_NAME);
//      ScmRepository repo = getRepository();
//      repo.checkIn(, files, "new version for android manifest");
//    } catch (ScmException e) {
//      throw new MojoExecutionException("Problem checking in files : " + e.getMessage());
//    } catch (IOException e) {
//      throw new MojoExecutionException("Problem checking in files : " + e.getMessage());
//    }
//  }

  private void updateVersion(File manifestFile) throws MojoFailureException {
    LineIterator it = null;
    try {
      String manifest = FileUtils.readFileToString(manifestFile);
      if (manifest == null) {
        throw new MojoFailureException("Content of the android manifest is null");
      }
      manifest = manipulator.replaceVersionName(manifest, projectVersion);
      int currentCode = manipulator.getVersionCode(manifest);
      manifest = manipulator.replaceVersionCode(manifest, ++currentCode);
      FileUtils.writeStringToFile(manifestFile, manifest);
    } catch (Exception e) {
      throw new MojoFailureException("Problem reading manifest " + e.getMessage());
    } finally {
      LineIterator.closeQuietly(it);
    }
  }

//  public ScmRepository getRepository() throws MojoExecutionException {
//    try {
//      return manager.makeScmRepository(connectionUrl);
//    } catch (Exception e) {
//      throw new MojoExecutionException("Unable to obtain SCM repositorys", e);
//    }
//  }

//  private void setScmImplementation() {
//    if (providerImplementations != null) {
//      Iterator i = providerImplementations.keySet().iterator();
//      while (i.hasNext()) {
//        String providerType = (String) i.next();
//        String providerImplementation = (String) providerImplementations.get(providerType);
//        getLog().info(
//            "Change the default '" + providerType + "' provider implementation to '"
//                + providerImplementation + "'.");
//        manager.setScmProviderImplementation(providerType, providerImplementation);
//      }
//    }
//  }

}
