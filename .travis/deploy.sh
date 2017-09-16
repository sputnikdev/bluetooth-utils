#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ] && [ ! -z "$TRAVIS_TAG" ]; then
    #mvn deploy -P sign,build-extras --settings .travis/settings.xml
    mvn release:prepare -B -P sign,build-extras --settings .travis/settings.xml
    mvn release:perform -B -P sign,build-extras --settings .travis/settings.xml
fi