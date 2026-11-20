cd to the root directory demo

run the following commands:

docker build --no-cache -t weather-uen-app .

docker run -d --name weather-uen-container -p 8080:8080 weather-uen-app

the website should then be accessible at: http://localhost:8080

to stop it: docker rm -f weather-uen-container
