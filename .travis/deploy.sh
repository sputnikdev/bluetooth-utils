#!/usr/bin/env bash
if [[ "$TRAVIS_BRANCH" == release* ]] && [[ "$TRAVIS_PULL_REQUEST" == 'false' ]] && [[ "$TRAVIS_TAG" == release* ]] ; then
    #mvn deploy -P sign,build-extras --settings .travis/settings.xml
    mvn release:prepare -B -P sign,build-extras --settings .travis/settings.xml
    mvn release:perform -B -P sign,build-extras --settings .travis/settings.xml
fi