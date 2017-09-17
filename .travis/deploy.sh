#!/usr/bin/env bash
ARTIFACT_NAME=$( echo "$TRAVIS_REPO_SLUG" | sed 's:.*/::' )
if [ "$TRAVIS_PULL_REQUEST" == 'false' ] && [ ! -z "$TRAVIS_TAG" ]
then
    echo "on a tag -> set pom.xml <version> to $TRAVIS_TAG"
    mvn --settings .travis/settings.xml org.codehaus.mojo:versions-maven-plugin:2.1:set -DnewVersion=$ARTIFACT_NAME-$TRAVIS_TAG 1>/dev/null 2>/dev/null
    mvn deploy -P sign,build-extras --settings .travis/settings.xml
fi