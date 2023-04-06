import axios from "axios";
import {useState} from "react";
import {AirQuality} from "./Types";

function App() {

    const [city, setCity] = useState<string>('')
    const [loading, setLoading] = useState<boolean>(false)
    const [airQuality, setAirQuality] = useState<AirQuality | null>(null)
    const [error, setError] = useState<boolean>(false)
    const [loadTime, setLoadTime] = useState<number>(0)
    const findAirQuality = async () => {
        let start = new Date().getTime()
        if (city.length < 3) {
            return
        }
        setLoading(true)
        try {
            const response = await axios.get('http://localhost:8080/api/quality?city=' + city)
            if (response.status === 200 && !response.data.hasOwnProperty('error')) {
                setAirQuality(response.data as AirQuality)
                setError(false)
            } else {
                setError(true)
            }
        } catch (e) {
            console.log(e)
        }
        setLoading(false)
        let end = new Date().getTime()
        setLoadTime(end - start)

    }
    return (
        <div className='p-3 bg-base-200 h-screen'>
            <div className="navbar bg-base-100 shadow-xl rounded-full">
                <a className="btn btn-ghost normal-case text-3xl">Air Quality</a>
            </div>
            <div className={"container mx-auto"}>
                <div className={"flex justify-center items-center w-full space-x-2"}>
                    <div className="form-control my-2">
                        <input type="text" placeholder="City" className="input input-bordered"
                               onChange={(event) => setCity(event.target.value)}/>
                    </div>
                    <button className={`btn ${loading && "loading"} btn-secondary`} onClick={() => findAirQuality()}>
                        {loading ? "Loading..." : "Find Air Quality"}
                    </button>
                    <p>{loadTime}</p>
                </div>


                {!loading && !error && airQuality && (
                    <>
                        <div className="flex space-between">
                            <div className={"text-4xl font-bold flex-1"}>{city}</div>
                            <div className={"text-3xl font-bold"}>Overall AQI:</div>
                            <div className={"text-3xl font-light"}> {airQuality.overallAqi}</div>
                        </div>
                        <div className="stats w-full stats-vertical lg:stats-horizontal shadow">
                            <div className="stat place-items-center">
                                <div className="stat-title">CO</div>
                                <div className="stat-value">{airQuality.CO.concentration}</div>
                                <div className="stat-desc">{airQuality.CO.aqi}</div>
                            </div>

                            <div className="stat place-items-center">
                                <div className="stat-title">PM10</div>
                                <div className="stat-value">{airQuality.PM10.concentration}</div>
                                <div className="stat-desc">{airQuality.PM10.aqi}</div>
                            </div>

                            <div className="stat place-items-center">
                                <div className="stat-title">SO2</div>
                                <div className="stat-value">{airQuality.SO2.concentration}</div>
                                <div className="stat-desc">{airQuality.SO2.aqi}</div>
                            </div>

                            <div className="stat place-items-center">
                                <div className="stat-title">PM25</div>
                                <div className="stat-value">{airQuality.PM25.concentration}</div>
                                <div className="stat-desc">{airQuality.PM25.aqi}</div>
                            </div>

                            <div className="stat place-items-center">
                                <div className="stat-title">O3</div>
                                <div className="stat-value">{airQuality.O3.concentration}</div>
                                <div className="stat-desc">{airQuality.O3.aqi}</div>
                            </div>


                            <div className="stat place-items-center">
                                <div className="stat-title">NO2</div>
                                <div className="stat-value">{airQuality.NO2.concentration}</div>
                                <div className="stat-desc">{airQuality.NO2.aqi}</div>
                            </div>


                        </div>
                    </>
                )}

                {!loading && error && (
                    <div className="card w-full max-w-xs">
                        <div className="card-body">
                            <h2 className="card-title">Error</h2>
                            <p>Something went wrong</p>
                        </div>
                    </div>
                )}
            </div>
        </div>
    )
}

export default App
