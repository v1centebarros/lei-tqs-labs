export type AirQuality = {
    aqi: number,
    co: number
    pm10: number,
    so2: number,
    pm25: number,
    o3: number,
    no2: number,
    dateTime: string
}

export type City = {
    name: string,
    latitude: number,
    longitude: number,
    displayName: string,
    coordinates: []
}


export type AirQualityForecast = {
    city: City,
    data: AirQuality[]
}

export type CurrentAirQuality = {
    city: City,
    data: AirQuality
}

export type Statistics = {
    airQualityStats: {
        hits: number,
        misses: number,
        puts: number,
        requests: number
    },
    forecastCacheStats: {
        hits: number,
        misses: number,
        puts: number,
        requests: number
    }
}