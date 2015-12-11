/*
 * Copyright 2015 Jin Kwon &lt;jinahya_at_gmail.com&gt;.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jinahya.semver.plugin;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@Mojo(defaultPhase = LifecyclePhase.VALIDATE, name = "check")
public class CheckMojo extends AbstractMojo {


    private static final String REGEXP
        = "(\\d+)\\.(\\d+)\\.(\\d+)(-([^\\+]*))?(\\+(.+))?";


    private static final Pattern PATTERN = Pattern.compile(REGEXP);


    private static final String VERSION_IDENTIFIER_REGEX = "0|[1-9][0-9]*";


    private static final Pattern VERSION_IDENTIFIER_PATTERN
        = Pattern.compile(VERSION_IDENTIFIER_REGEX);


    private static final String RELEASE_IDENTIFIER_REGEXP
        = "0|([1-9A-Za-z-][0-9A-Za-z-]*)";


    private static final Pattern RELEASE_IDENTIFIER_PATTERN
        = Pattern.compile(RELEASE_IDENTIFIER_REGEXP);


    private static final String METADATA_IDENTIFIER_REGEXP = "[0-9A-Za-z-]+";


    private static final Pattern METADATA_IDENTIFIER_PATTERN
        = Pattern.compile(METADATA_IDENTIFIER_REGEXP);


    private static String checkVersion(final String value, final String name)
        throws MojoFailureException {

        if (!VERSION_IDENTIFIER_PATTERN.matcher(value).matches()) {
            throw new MojoFailureException(
                name + "(" + value + ") doesn't match to expression("
                + VERSION_IDENTIFIER_REGEX + ")");
        }

        final int number;
        try {
            number = Integer.parseInt(value);
        } catch (final NumberFormatException nfe) {
            throw new MojoFailureException(
                name + "(" + value + ") is not a number");
        }

        if (number < 0) {
            throw new MojoFailureException(
                name + "(" + value + ") is negative");
        }

        return value;
    }


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        final String projectVersion = project.getVersion();
        getLog().debug("Checking Semantic Versioning for " + projectVersion);

        final Matcher matcher = PATTERN.matcher(projectVersion);
        if (!matcher.matches()) {
            throw new MojoFailureException(
                "Project's version(" + projectVersion
                + ") doesn't match to regex(" + REGEXP + ")");
        }

        final String majorVersion = matcher.group(1);
        getLog().debug("Major version: " + majorVersion);
        checkVersion(majorVersion, "Major version");
        getLog().info("Major version(" + majorVersion + ") checked.");

        final String minorVersion = matcher.group(2);
        getLog().debug("Minor version: " + minorVersion);
        checkVersion(matcher.group(2), "Minor version");
        getLog().info("Minor version(" + minorVersion + ") checked.");

        final String patchVersion = matcher.group(3);
        checkVersion(patchVersion, "Patch version");
        getLog().info("Patch version(" + patchVersion + ") checked.");

        final String preReleaseVersion = matcher.group(5);
        if (preReleaseVersion == null) {
            getLog().debug("No pre-release version exist.");
        } else {
            for (final String identifier : preReleaseVersion.split("\\.")) {
                if (!RELEASE_IDENTIFIER_PATTERN.matcher(identifier).matches()) {
                    throw new MojoFailureException(
                        "Invalid pre-release version(" + preReleaseVersion
                        + "). Idenfifier: \"" + identifier + "\"");
                }
            }
        }
        getLog().info("Pre-release version(" + preReleaseVersion
                      + ") checked.");

        final String buildMetadata = matcher.group(7);
        if (buildMetadata == null) {
            getLog().info("No build metadata exist.");
        } else {
            for (final String identifier : buildMetadata.split("\\.")) {
                if (!METADATA_IDENTIFIER_PATTERN
                    .matcher(identifier).matches()) {
                    throw new MojoFailureException(
                        "Invalid build metadata(" + buildMetadata
                        + "). Idenfifier: \"" + identifier + "\"");
                }
            }
        }
        getLog().info("Build metadata(" + buildMetadata + ") checked.");
    }


    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

}

