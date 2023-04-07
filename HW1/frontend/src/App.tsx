import {useState} from "react";
import axios from "axios";
import {AirQuality, AirQualityForecast, CurrentAirQuality} from "./Types";
import {CurrentQuality} from "./CurrentQuality";
import {ErrorToaster} from "./ErrorToaster";
import {Forecast} from "./Forecast";


function App() {
    const [loading, setLoading] = useState(false)
    const [city, setCity] = useState('')
    const [airQuality, setAirQuality] = useState<null | CurrentAirQuality | AirQualityForecast>(null)
    const [error, setError] = useState(false)
    const [loadTime, setLoadTime] = useState(0)
    const [errorText, setErrorText] = useState('')
    const [option, setOption] = useState('Current')
    const findAirQuality = async () => {
        if (city.length < 3) {
            setError(true)
            setErrorText('Please enter a valid city name')
            return
        }
        let start = new Date().getTime()
        setLoading(true)
        const response = await axios.get('http://localhost:8080/api/quality?city=' + city)

        if (response.status === 200 && !response.data.hasOwnProperty('message')) {
            setAirQuality(response.data as CurrentAirQuality)
            setError(false)
        } else {
            setError(true)
            setErrorText(response.data.message)
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
        }
        setLoading(false)
        let end = new Date().getTime()
        setLoadTime(end - start)
    }

    const requestData = async () => {
        switch (option) {
            case 'Current':
                await findAirQuality()
                break
            case 'Forecast':
                await findForecast()
                break
            default:
                break
        }
    }

    const changeOption = (value: string) => {
        setOption(value)
        setAirQuality(null)

    }


    return (
        <div className='p-3 bg-base-100 h-screen'>
            <div className="navbar bg-base-300 rounded-full">
                <a className="btn btn-ghost normal-case text-xl rounded-full">AirQuality</a>
            </div>
            <div className={'flex flex-row items-center justify-center my-4 space-x-4'}>
                <select className="select select-primary w-1/6 rounded-full"
                        onChange={(event) => changeOption(event.target.value)}
                >
                    <option defaultValue>Current</option>
                    <option>Forecast</option>
                    <option>NinjaAPI</option>
                    <option>OpenWeatherAPI</option>
                </select>
                <input type="text" placeholder="Enter City"
                       className="input input-bordered input-primary w-1/4 rounded-full"
                       onChange={(event) => setCity(event.target.value)}
                />
                <button className={`btn ${loading && "loading"} btn-primary rounded-full w-1/5`}
                        onClick={() => requestData()}>
                    {loading ? "Loading..." : "Find Air Quality"}
                </button>
            </div>
            <div className="divider"></div>

            <div className={'container mx-auto'}>
                {!loading && !error && airQuality && (
                    <div className={"space-y-4"}>
                        <div className={"flex flex-row content-center items-end space-x-2"}>
                            <div className={"text-4xl font-bold"}>{airQuality.city.displayName}</div>
                            <div
                                className={"text-2xl font-extralight flex-1"}>({airQuality.city.latitude},{airQuality.city.longitude})
                            </div>
                            <div><span className={"text-2xl font-bold"}>Load Time:</span> <span
                                className={"text-2xl font-extralight"}> {loadTime}ms</span></div>
                        </div>
                        {option === 'Current' && (
                            <CurrentQuality airQuality={airQuality.data as AirQuality}/>
                        )}
                        {option === 'Forecast' && (
                            <Forecast airQuality={airQuality as AirQualityForecast}/>
                        )}

                    </div>
                )
                }
                {!loading && error && (
                    <ErrorToaster errorText={errorText} setError={setError}/>
                )}
            </div>
        </div>
    )
}

export default App
