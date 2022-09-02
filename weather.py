import requests

API_KEY = "36b752485e452d8d0a7b40c57dbeca68"
BASE_URL = "http://api.openweathermap.org/data/2.5/weather"

city = input("Enter a city name: ")
request_url = f"{BASE_URL}?appid={API_KEY}&q={city}"
response = requests.get(request_url)

if response.status_code == 200:
    data = response.json()
    weather = data['weather'][0]['description']
    temperature = round(data['main']['temp'] - 273.15, 2)
    feels_like = round(data['main']['feels_like'] - 274.15, 2)
    wind = data['wind']['speed']
    print("Weather:", weather)
    print("Temperature:", temperature, "celcius")
    print("Feels like:", feels_like)
    print("Wind speed:", wind)
    print(data)
else:
    print("An Error Occured")