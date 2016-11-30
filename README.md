This project consist of 3 modules:
1. REST web service (written in Java using Spring Boot)
2. Single page application (written in JavaScript using AngularJS)
3. Chrome extension
___

To build executable jar run:
```
git clone https://github.com/Igorek2312/LibreLingvo.git
cd LibreLingvo/spa
npm install
bower install
grunt build --force
cd .. 
gradle buildDependents
```
And run it (init profile to init database on startup, otherwise run dev):
```
java -jar -Dspring.profiles.active=init build/libs/libre-lingvo-0.0.1-SNAPSHOT.jar
```
Build extension:
```
cd chrome-extension
npm install
bower install
gulp build
```