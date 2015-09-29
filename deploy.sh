cd application/
mvn nbm:build-installers -q
cd target/
cp *.exe ../../
cd ../../
mv *.exe limo.exe ||true
mvn nbm:autoupdate -P \!all,nbm -q

echo -e "Starting to update modules\n"

  #copy data we're interested in to other place
  cp -R target/netbeans_site $HOME/update

  #go to home and setup git
  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "Travis"

  #using token clone gh-pages branch
  git clone --quiet --branch=gh-pages https://37af99ab2b0cfc8c4b65bd875e42c30a493882ec@github.com/LogisticsImpactModel/LIMO.git  gh-pages > /dev/null
  #go into diractory and copy data we're interested in to that directory
  cd gh-pages
  cp -Rf $HOME/update/* .

  #add, commit and push files
  git add -f .
  git commit -m "Travis build $TRAVIS_BUILD_NUMBER pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Done magic with updates\n"



