# geolocalization
Simple Java 8 `only` software which uses [Google localization Api](https://developers.google.com/maps/documentation/geolocation/intro). 
Information about the surrounding wifi networks taken from other parites software - at Mac from **`airport`**` -s`, while at Windows from 
**`netsh`**` wlan show networks mode=bssid`.

### pre configuration
add system variable `GOOGLE_API_KEY` with your Google Api key 
### build
    mvn clean package
### run
    java -jar target/geolocation-0.0.1-SNAPSHOT.jar
