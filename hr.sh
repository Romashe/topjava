#source ~/.zshrc
export TOPJAVA_ROOT=/Users/roman/IdeaProjects/topjava/topjava
mvn -B -s settings.xml -DskipTests=true clean package
java -Dspring.profiles.active="datajpa,heroku" -DDATABASE_URL="postgres://user:password@localhost:5432/topjava" -jar target/dependency/webapp-runner.jar target/*.war