#!/bin/sh
 
DIR=$1
APP_JAR="workspots.jar"
APP_NAME="WorkSpots"
 
cd "$DIR"
java -Xdock:name="$APP_NAME" -Xdock:icon="$DIR/../Resources/Icon.icns" -cp "$DIR;.;" -jar "$DIR/$APP_JAR"
exit 0
