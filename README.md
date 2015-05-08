# Image Haiku

Prototype utilizing Dropwizard, AngularJS, Bootstrap and MongoDB used for educational purposes in a Brown Bag Series (one hour track).

#### How do I build this mess?

- configure proxy for npm and bower
   add path variables
   `http_proxy=http://host:port`
   and
   `https_proxy=https://host:port`
- execute command: `npm install`
- execute command: `bower install`
- execute command: `gradlew shadowJar` or `gradlew.bat shadowJar`

(Feel free to optimize this process, `gradlew shadowJar` should be the only required step)