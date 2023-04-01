export type AirQuality = {
    overallAqi: number,
    CO : Measurement,
    PM10 : Measurement,
    SO2 : Measurement,
    PM25 : Measurement,
    O3 : Measurement,
    NO2 : Measurement
}

type Measurement = {
    concentration: number,
    aqi: number
}