/*
 *  The MIT License
 *
 *  Copyright 2010 Sony Ericsson Mobile Communications.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package com.sonyericsson.hudson.plugins.gerrit.trigger.hudsontrigger.data;

import com.sonymobile.tools.gerrit.gerritevents.dto.attr.Change;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Testing different scenarios if they are interesting.
 * @author Robert Sandell &lt;robert.sandell@sonyericsson.com&gt;
 */
@RunWith(Parameterized.class)
public class GerritProjectInterestingTest {

    private final InterestingScenario scenario;

    /**
     * Constructor.
     * @param scenario scenario
     */
    public GerritProjectInterestingTest(InterestingScenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Tests {@link GerritProject#isInteresting(Change change)}.
     */
    @Test
    public void testInteresting() {
        assertEquals(scenario.expected, scenario.config.isInteresting(
                scenario.change));
    }

    /**
     * The parameters.
     * @return parameters
     */
    @Parameters
    public static Collection getParameters() {

        List<InterestingScenario[]> parameters = new LinkedList<InterestingScenario[]>();

        List<Branch> branches = new LinkedList<Branch>();
        List<Topic> topics = new LinkedList<Topic>();
        List<Hashtag> hashtags = new LinkedList<>();
        Branch branch = new Branch(CompareType.PLAIN, "master");
        branches.add(branch);
        GerritProject config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config, "project", "master", null,
                null, true), });

        branches = new LinkedList<Branch>();
        branch = new Branch(CompareType.ANT, "**/master");
        branches.add(branch);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "origin/master", null, null, true), });

        branches = new LinkedList<Branch>();
        branch = new Branch(CompareType.ANT, "**/master");
        branches.add(branch);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config, "project", "master", null,
                null, true), });

        branches = new LinkedList<Branch>();
        branch = new Branch(CompareType.ANT, "**/master");
        branches.add(branch);
        branch = new Branch(CompareType.REG_EXP, "feature/.*master");
        branches.add(branch);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config, "project", "master", null,
                null, true), });

        branches = new LinkedList<Branch>();
        branch = new Branch(CompareType.PLAIN, "olstorp");
        branches.add(branch);
        branch = new Branch(CompareType.REG_EXP, "feature/.*master");
        branches.add(branch);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "feature/mymaster", null, null, true), });
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "Olstorp", null, null, true), });

        branches = new LinkedList<Branch>();
        branch = new Branch(CompareType.ANT, "**/master");
        branches.add(branch);
        config = new GerritProject(CompareType.ANT, "vendor/**/project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "vendor/semc/master/project", "origin/master", null, null, true), });

        branches = new LinkedList<Branch>();
        branch = new Branch(CompareType.PLAIN, "master");
        branches.add(branch);
        topics = new LinkedList<Topic>();
        Topic topic = new Topic(CompareType.PLAIN, "topic");
        topics.add(topic);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config, "project", "master", "topic",
                null, true), });

        topics = new LinkedList<Topic>();
        topic = new Topic(CompareType.ANT, "**/topic");
        topics.add(topic);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", "team/topic", null, true), });

        topics = new LinkedList<Topic>();
        topic = new Topic(CompareType.REG_EXP, ".*_topic");
        topics.add(topic);
        config = new GerritProject(CompareType.PLAIN, "project", branches, topics,
                null, null, false);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", "team-wolf_topic", null, true), });

        hashtags = new LinkedList<>();
        Hashtag hashtag = new Hashtag(CompareType.PLAIN, "hashtag");
        hashtags.add(hashtag);
        config = new GerritProject(CompareType.PLAIN, "project", branches, null,
                null, null, false);
        config.setHashtags(hashtags);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", null, List.of("hashtag"), true), });

        hashtags = new LinkedList<>();
        hashtag = new Hashtag(CompareType.ANT, "**/hashtag");
        hashtags.add(hashtag);
        config = new GerritProject(CompareType.PLAIN, "project", branches, null,
                null, null, false);
        config.setHashtags(hashtags);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", null, List.of("test/hashtag"), true), });

        hashtags = new LinkedList<>();
        hashtag = new Hashtag(CompareType.REG_EXP, ".*_hashtag");
        hashtags.add(hashtag);
        config = new GerritProject(CompareType.PLAIN, "project", branches, null,
                null, null, false);
        config.setHashtags(hashtags);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", null, List.of("test_hashtag"), true), });

        hashtags = new LinkedList<>();
        hashtag = new Hashtag(CompareType.REG_EXP, "hash.*tag");
        hashtags.add(hashtag);
        config = new GerritProject(CompareType.PLAIN, "project", branches, null,
                null, null, false);
        config.setHashtags(hashtags);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", null, List.of("test_hashtag"), false), });

        hashtags = new LinkedList<>();
        hashtag = new Hashtag(CompareType.ANT, "hash.*tag");
        hashtags.add(hashtag);
        config = new GerritProject(CompareType.PLAIN, "project", branches, null,
                null, null, false);
        config.setHashtags(hashtags);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", null, List.of("test_hashtag"), false), });

        hashtags = new LinkedList<>();
        hashtag = new Hashtag(CompareType.PLAIN, "hash.*tag");
        hashtags.add(hashtag);
        config = new GerritProject(CompareType.PLAIN, "project", branches, null,
                null, null, false);
        config.setHashtags(hashtags);
        parameters.add(new InterestingScenario[]{new InterestingScenario(config,
                "project", "master", null, List.of("test_hashtag"), false), });

        return parameters;
    }

    /**
     * A parameter to a test scenario.
     */
    public static class InterestingScenario {

        GerritProject config;
        Change change;
        boolean expected;

        /**
         * Constructor.
         * @param config config
         * @param project project
         * @param branch branch
         * @param topic topic
         * @param hashtags hashtags
         * @param expected expected
         */
        public InterestingScenario(GerritProject config,
                String project,
                String branch,
                String topic,
                List<String> hashtags,
                boolean expected) {
            this.config = config;
            this.change = new Change();
            this.change.setProject(project);
            this.change.setBranch(branch);
            this.change.setTopic(topic);
            this.change.setHashtags(hashtags);
            this.expected = expected;
        }

        /**
         * Constructor.
         */
        public InterestingScenario() {
        }
    }
}
