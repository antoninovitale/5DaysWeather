# 5DaysWeather
App that shows the weather forecast using OpenWeatherMap API

<img src="https://raw.githubusercontent.com/antoninovitale/5DaysWeather/master/artwork/screenshot.png" width="300" height="533">

<br>This app shows the current weather information for a location of your choice and a 3 hour forecast for the next 5 days.<br>
It makes use of OpenWeatherMap API (https://openweathermap.org/api) to retrieve the weather data and it also uses Flickr API to download a geolocalised image shown as a wallpaper every time a city is selected.<br>
The city can be selected via the autocomplete search widget provided by the Google Places API (results are filtered and only cities will be shown).<br><br>

You can download the apk file from this <a href="https://github.com/antoninovitale/5DaysWeather/raw/master/fivedaysweather-app-release.apk">link</a>.<br>
Once downloaded, you can install the app on your device (unknown sources option must be enabled from settings/security).<br>

Alternatively, you can clone or download this repository and open the project with Android Studio.<br>
It can be executed on emulators running Android with API level 16+, hence make sure to solve eventual configuration issues in your environment and create an AVD matching that target to run the app.
<br>You have to <a href="https://console.developers.google.com/">setup a google API project<a/>, enable Google Places and Google Maps API for Android and download the google-services.json file to copy to the app folder of the project.
<br>Remember to get also an API key for OpenWeatherMap API at this <a href="http://openweathermap.org/appid">link</a> and for Flickr API at this <a href="https://www.flickr.com/services/apps/create/apply/">link</a>.
