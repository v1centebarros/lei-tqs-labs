import {useState} from "react";
import axios from "axios";
import {AirQuality, AirQualityForecast, CurrentAirQuality, Statistics} from "./Types";
import {CurrentQuality} from "./CurrentQuality";
import {ErrorToaster} from "./ErrorToaster";
import {Forecast} from "./Forecast";
import {StatisticsCmp} from "./Statistics";


function App() {
    const [loading, setLoading] = useState(false)
    const [city, setCity] = useState('')
    const [airQuality, setAirQuality] = useState<null | CurrentAirQuality | AirQualityForecast>(null)
    const [statistics, setStatistics] = useState<null | Statistics>(null)
    const [error, setError] = useState(false)
    const [loadTime, setLoadTime] = useState(0)
    const [errorText, setErrorText] = useState('')
    const [option, setOption] = useState('Current')
    const findAirQuality = async (url:string) => {
        if (city.length < 3) {
            setError(true)
            setErrorText('Please enter a valid city name')
            return
        }
        let start = new Date().getTime()
        setLoading(true)
        const response = await axios.get(url + city)
        console.log(response)
        if (response.status === 200 && !response.data.hasOwnProperty('message')) {
            setAirQuality(response.data as CurrentAirQuality)
            setError(false)
        } else {
            setError(true)
            setErrorText(response.data.message)
            setAirQuality(null)
        }
        setLoading(false)
        let end = new Date().getTime()
        setLoadTime(end - start)
    }

    const findForecast = async () => {
        if (city.length < 3) {
            setError(true)
            setErrorText('Please enter a valid city name')
            return
        }
        let start = new Date().getTime()
        setLoading(true)
        const response = await axios.get('http://localhost:8080/api/forecast?city=' + city)

        if (response.status === 200 && !response.data.hasOwnProperty('message')) {
            setAirQuality(response.data as AirQualityForecast)
            setError(false)
        } else {
            setError(true)
            setErrorText(response.data.message)
            setAirQuality(null)
        }
        setLoading(false)
        let end = new Date().getTime()
        setLoadTime(end - start)
    }

    const findStatistics = async () => {
        let start = new Date().getTime()
        setLoading(true)
        const response = await axios.get('http://localhost:8080/api/cache/stats')

        if (response.status === 200 && !response.data.hasOwnProperty('message')) {
            setStatistics(response.data as Statistics)
            setError(false)
        } else {
            setError(true)
            setErrorText(response.data.message)
            setStatistics(null)
        }
        setLoading(false)
        let end = new Date().getTime()
        setLoadTime(end - start)

    }

    const requestData = async () => {
        switch (option) {
            case 'Current':
                await findAirQuality('http://localhost:8080/api/quality?city=')
                break
            case 'Forecast':
                await findForecast()
                break
            case 'NinjaAPI':
                await findAirQuality('http://localhost:8080/api/ninja?city=')
                break
            case 'OpenWeatherAPI':
                await findAirQuality('http://localhost:8080/api/openweather?city=')
                break

            case 'Statistics':
                await findStatistics()
                break
        }
    }

    const changeOption = (value: string) => {
        setOption(value)
        setAirQuality(null)
        setStatistics(null)
    }


    return (
        <div className='p-3 bg-base-100 h-screen'>
            <div className="navbar bg-base-300 rounded-full" id={"navbar"}>
                <div className={"flex-1"} id={"logo"}>
                    <a className="btn btn-ghost normal-case text-xl rounded-full"><img src={"./logo.png"} className={"h-full"}/>💨</a>
                </div>
                <div className={"flex-none"}>
                    <p className="btn btn-ghost normal-case text-xl rounded-full">Vicente Barros 97787</p>
                </div>
            </div>
            <div className={'flex flex-row items-center justify-center my-4 space-x-4'}>
                <select className="select select-primary w-1/6 rounded-full"
                        id={"selectOption"}
                        onChange={(event) => changeOption(event.target.value)}
                >
                    <option>Current</option>
                    <option>Forecast</option>
                    <option>NinjaAPI</option>
                    <option>OpenWeatherAPI</option>
                    <option>Statistics</option>
                </select>

                {option !== 'Statistics' && (
                    <input type="text" placeholder="Enter City"
                           id={"cityInput"}
                           className="input input-bordered input-primary w-1/4 rounded-full"
                           onChange={(event) => setCity(event.target.value)}
                    />
                )}
                <button className={`btn ${loading && "loading"} btn-primary rounded-full w-1/5`}
                        id={"submitBtn"}
                        onClick={() => requestData()}>
                    {loading ? "Loading..." : option === "Statistics" ? "Check Statistics" : "Find Air Quality"}
                </button>
            </div>
            <div className="divider"></div>

            <div className={'container mx-auto pb-4'}>
                {!loading && !error && airQuality && (
                    <div className={"space-y-4"}>
                        <div className={"flex flex-row content-center items-end space-x-2"}>
                            <div className={"text-4xl font-bold"} id={"displayName"}>{airQuality.city.displayName}</div>
                            <div
                                className={"text-2xl font-extralight flex-1"} id={"coordinates"}>({airQuality.city.latitude},{airQuality.city.longitude})
                            </div>
                            <div id={"loadTime"}><span className={"text-2xl font-bold"}>Load Time:</span> <span
                                className={"text-2xl font-extralight"}> {loadTime}ms</span></div>
                        </div>
                        {(option === 'Current' || option === 'NinjaAPI' || option === 'OpenWeatherAPI') && (
                            <CurrentQuality airQuality={airQuality.data as AirQuality}/>
                        )}

                        {option === 'Forecast' && (
                            <Forecast airQuality={airQuality as AirQualityForecast}/>
                        )}
                    </div>
                )
                }
                {!loading && !error && statistics && (
                    <StatisticsCmp statistics={statistics}/>
                )}


                {!loading && error && (
                    <ErrorToaster errorText={errorText} setError={setError}/>
                )}
            </div>
        </div>
    )
}

export default App
