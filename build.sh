mvn clean package -DskipTests=true

rm -rf build
mkdir build

rm -rf logs/*

path_target=/Users/alioo/work/gitstudy/homeserver/target/
path_output=/Users/alioo/work/gitstudy/homeserver/build/


cp $path_target/homeserver-0.0.1-SNAPSHOT.jar $path_output
cp shell/* $path_output

chmod 777 $path_output/*.sh
