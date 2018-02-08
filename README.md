# geolocalization
Simple Java 8 software which uses [Google localization Api](https://developers.google.com/maps/documentation/geolocation/intro). 
Information about the surrounding wifi networks taken from other parites software - here we start with Mac `airport`.

### pre configuration
add system variable `GOOGLE_API_KEY` with your Google Api key 
### build
    mvn clean package
### run
    java -jar target/geolocation-0.0.1-SNAPSHOT.jar
