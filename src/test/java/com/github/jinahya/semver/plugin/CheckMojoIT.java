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


import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
public class CheckMojoIT {


    private void test(final String basedirPath) throws Exception {

        final String basedir = ResourceExtractor.simpleExtractResources(
            getClass(), basedirPath).getAbsolutePath();
        logger.debug("basedir: {}", basedir);

        final Verifier verifier = new Verifier(basedir);
        logger.debug("verifier: {}", verifier);

        verifier.executeGoal("verify");
        verifier.verifyTextInLog(basedirPath);
        //verifier.resetStreams();
    }


    @Test(expectedExceptions = VerificationException.class)
    public void majorNonInteger() throws Exception {

        test("/majorNonInteger");
    }


    @Test(expectedExceptions = VerificationException.class)
    public void majorLeadingZeros() throws Exception {

        test("/majorLeadingZeros");
    }


    private transient final Logger logger = LoggerFactory.getLogger(getClass());

}

