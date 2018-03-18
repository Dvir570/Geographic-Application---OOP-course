Geographic-Application---OOP-course
===
Editors:
  Dvir Barzilay &
  Shmuel Shimoni
 
# About the project: #
The program merges CSV files from exported android app called "WiGLE WiFi Wardriving" into one file – taking Wi-Fi networks only and arranging them by time and place. 
For every timestamp it takes the top 10 networks with the strongest signals and arranges them in an ascending order. 

```
"WiGLE WiFi Wardriving" app – it is an open source network observation, positioning and display client from the world's largest queryable database of wireless networks. 
This app can be used for site survey, security analysis and competition with friends. 
It collects networks for personal research (information was taken from the android app store).
https://play.google.com/store/apps/details?id=net.wigle.wigleandroid&hl=en
```

The program also takes an arranged file and creates a KML file using Jak API so that it can be used on the "Google Earth" site.
By uploading  the file to "Google Earth" site, we can look up all the Wi-Fi networks we wanted to see on the map with a timeline option. 
We can choose in the program either to filter the networks' samples list by a specific date and hour, by a specific device ID, or by choosing a point and a radius to show all the networks within this specific radius.

```
"Google Earth" – is a geobrowser that accesses satellite, aerial imagery and other geographic data over the internet to represent the Earth as a three dimensional globe. 
This product has many features one of them is the ability to show mappable data by reading KML files that had been uploaded to it.
https://serc.carleton.edu/sp/library/google_earth/what.html
```
The program gives two another options:
Create a new CSV file with all the strongest locations for every Mac from a merged CSV file.
Create a new CSV file with all the user locations for every sample's scan using a merged CSV file as database and other merged CSV file with no coordinates specified in it. The samples' scans are taken from the second file.

## Instractions for runing program ##
In order to run this application you need to double click on GeoApp.jar file,
this file will open browser on the general page of GeoApp.

Map of the site
==

#### Update by Wiggle Wifi (general page) ####
This page is allowing to upload a Wiggle Wifi's product file to the current database.
  1. Menu
  2. DB clear, DB save csv and DB save kml buttons.
  3. Update database via upload a Wiggle Wifi file, or a directory with some of Wiggle Wifi files.
  4. Details:
      a. number of records.
      b. number of routers.
      c. filter details (if there is no filter on then it will be empty field).
  5. Filters:
      a. set a new filter.
      b. upload a filter from txt file.
      c. apply filter on the current database.
      d. save the filter to txt file.
      e. restore database.
  6. Map display.
  7. Get in touch.
  
#### Update by DB ####
This page is allowing to upload a database file (with 46 columns) to the current database.
  1. Menu
  2. DB clear, DB save csv and DB save kml buttons.
  3. Update database via upload a csv file. The file should be the one with 46 columns.
  4. Details:
      a. number of records.
      b. number of routers.
      c. filter details (if there is no filter on then it will be empty field).
  5. Filters:
      a. set a new filter.
      b. upload a filter from txt file.
      c. apply filter on the current database.
      d. save the filter to txt file.
      e. restore database.
  6. Map display.
  7. Get in touch.
  
#### Insert a Remote SQL ####
This page is allowing to insert a remote database table (with 46 columns minus 2*10 columns, two columns for each WIFI scan - this is because we don't care about ssid and frequency of the WIFI scan) to the current database.
  1. Menu
  2. DB clear, DB save csv and DB save kml buttons.
  3. Update database via insert a remote table. You need to enter url, user, password, name of the remote SQL database and the table name.
  4. Details:
      a. number of records.
      b. number of routers.
      c. filter details (if there is no filter on then it will be empty field).
  5. Filters:
      a. set a new filter.
      b. upload a filter from txt file.
      c. apply filter on the current database.
      d. save the filter to txt file.
      e. restore database.
  6. Map display.
  7. Get in touch.

#### Algorithm I ####
This algorithm will return you an appreciate location of the router.
The appreciate become established from database.
input: a mac address.
output: an appreciate location of the router.
  1. Menu
  2. DB clear, DB save csv and DB save kml buttons.
  3. Algurithm I.
  4. Map display.
  5. Get in touch.
  
#### Algorithm II ####
This algorithm will return you an appreciate location of specific sampling.
The appreciate become established from database.
input: string of specific sampling or up to three mac addresses and signals.
output: an appreciate location of specific sampling.
  1. Menu
  2. DB clear, DB save csv and DB save kml buttons.
  3. Algurithm II.
  4. Map display.
  5. Get in touch.
