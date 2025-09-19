#!/usr/bin/env sh
set -e

./gradlew :Fabric:runCommonDatagen :Forge:runData :Fabric:runFabricDatagen || exit 1

STATUS="$(git status --porcelain common/src/generated/resources fabric/src/generated/resources forge/src/generated/resources)"
if [ -z "$STATUS" ]
then
  echo "Datagen ok"
else
  echo "Generated resources are dirty after running data generators. Please make sure you committed generated files. Dirty files:"
  echo "$STATUS"
  exit 1
fi

