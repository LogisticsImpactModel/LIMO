cd application/
mvn nbm:build-installers
cd target/
cp *.exe ../../
cd ../../
mv *.exe limo.exe ||true



