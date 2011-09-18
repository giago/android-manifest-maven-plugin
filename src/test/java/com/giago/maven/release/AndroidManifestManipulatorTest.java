package com.giago.maven.release;

import static org.junit.Assert.assertEquals;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class AndroidManifestManipulatorTest {

  private static String MANIFEST_FRAGMENT = "package=\"com.giago.imgsearch\" a:versionCode=\"13\" a:versionName=\"0.1.0\" a:installLocation=\"auto\"";
  private static String FRAGMENT_VERSION_NAME_TEST = "package=\"com.giago.imgsearch\" a:versionCode=\"13\" a:versionName=\"1.2.3\" a:installLocation=\"auto\"";
  private static String FRAGMENT_VERSION_CODE_TEST = "package=\"com.giago.imgsearch\" a:versionCode=\"23\" a:versionName=\"0.1.0\" a:installLocation=\"auto\"";
  
  @Test
  public void SHOULD_getTheCurrentVersionCode() throws MojoFailureException {
    assertEquals(13, new AndroidManifestManipulator().getVersionCode(MANIFEST_FRAGMENT));
  }

  @Test
  public void SHOULD_getChangeVersionName() {
    assertEquals(FRAGMENT_VERSION_NAME_TEST, new AndroidManifestManipulator().replaceVersionName(MANIFEST_FRAGMENT, "1.2.3"));
  }
  
  @Test
  public void SHOULD_getChangeVersionCode() {
    assertEquals(FRAGMENT_VERSION_CODE_TEST, new AndroidManifestManipulator().replaceVersionCode(MANIFEST_FRAGMENT, 23));
  }
  
}
