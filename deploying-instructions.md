#Deploying to heroku

##Prerequisite
### Install Heroku CLI
link : ```https://devcenter.heroku.com/articles/heroku-cli#download-and-install```

1. Open any command prompt
2. run ```gradle build```
3. After build is complete run ```heroku deploy:jar ./build/libs/ForMonk2-4.jar infinite-peak-58942```
4. Check the swagger at ```https://infinite-peak-58946.herokuapp.com/docApi/swagger-ui.html```