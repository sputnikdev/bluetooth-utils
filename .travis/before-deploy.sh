#!/usr/bin/env bash
if [[ "$TRAVIS_BRANCH" == release* ]] && [[ "$TRAVIS_PULL_REQUEST" == 'false' ]] && [[ "$TRAVIS_TAG" == release* ]] ; then
    openssl aes-256-cbc -K $encrypted_05df9d2256fa_key -iv $encrypted_05df9d2256fa_iv -in .travis/codesigning.asc.enc -out .travis/codesigning.asc -d
    gpg --fast-import .travis/codesigning.asc
fi