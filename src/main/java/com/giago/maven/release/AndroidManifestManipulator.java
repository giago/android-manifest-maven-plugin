package com.giago.maven.release;

import org.apache.maven.plugin.MojoFailureException;

public class AndroidManifestManipulator {

  private static final String EMPTY = "";
  private static final String SNAPSHOT = "-SNAPSHOT";
  private static final String ATTRIBUTE_END = "\"";
  private static final String VERSION_CODE_PREFIX = "versionCode=\"";
  private static final String VERSION_NAME_PREFIX = "versionName=\"";
  private static final String VERSION_NAME = "versionName=\"[0-9]*.[0-9]*.[0-9]*(-SNAPSHOT)?\"";
  private static final String VERSION_CODE = "versionCode=\"[0-9]*\"";
  
  public int getVersionCode(String manifest) throws MojoFailureException {
    int start = getStartIndexOfCodeAttributeValue(manifest);
    int end = getEndOfCodeAttributeValue(manifest, start);
    return getCode(manifest, start, end);
  }

  public String replaceVersionName(String manifest, String version) {
    return manifest.replaceAll(VERSION_NAME, VERSION_NAME_PREFIX + version + ATTRIBUTE_END);
  }
  
  public String replaceVersionNameWithouhSnapshot(String manifest, String version) {
    version = version.replaceFirst(SNAPSHOT, EMPTY);
    return replaceVersionName(manifest, version);
  }

  public String replaceVersionCode(String manifest, int code) {
    return manifest.replaceAll(VERSION_CODE, VERSION_CODE_PREFIX + code + ATTRIBUTE_END);
  }
  
  private int getCode(String manifest, int start, int end) throws MojoFailureException {
    String codeString = manifest.substring(start, end);
    try {
      return Integer.valueOf(codeString);
    } catch (Throwable t) {
      throw new MojoFailureException("Problem evaluating the current version code");
    }
  }
  
  private int getStartIndexOfCodeAttributeValue(String manifest) throws MojoFailureException {
    int start = manifest.lastIndexOf(VERSION_CODE_PREFIX);
    if(start == -1) {
      throw new MojoFailureException("Can't get the start of the version code prefix");
    }
    return start + VERSION_CODE_PREFIX.length();
  }
  
  private int getEndOfCodeAttributeValue(String manifest, int startingIndexOfCode)
      throws MojoFailureException {
    int end = manifest.indexOf(ATTRIBUTE_END, startingIndexOfCode);
    if(end == -1) {
      throw new MojoFailureException("Can't get the end of the version code prefix");
    }
    return end;
  }

}
