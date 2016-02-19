#!/bin/bash
deploy_dir=./deploy
mvn -Dmaven.test.skip=true package
rm -Rf ${deploy_dir}
mkdir -p ${deploy_dir}
cp -r conf target/*dependencies* ${deploy_dir}/
