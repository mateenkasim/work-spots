#!/bin/sh
 
# Set the working directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

echo "Launcher run"

# Run the application
exec "$DIR/wsRunner.sh" $DIR
exit 0
