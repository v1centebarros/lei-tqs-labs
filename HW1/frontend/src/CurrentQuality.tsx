import {AirQuality} from "./Types";
import moment from "moment/moment";

export function CurrentQuality(props: { airQuality: AirQuality }) {
    return (
        <>
            <div className={"text-2xl font-bold"}>{moment(new Date(props.airQuality.dateTime)).format("MMMM Do YYYY, h:mm:ss a")}</div>
            <div className="stats w-full stats-vertical lg:stats-horizontal shadow bg-neutral">
                <div className="stat">
                    <div className="stat-title text-primary">Overall AQI</div>
                    <div className="stat-value">{props.airQuality.aqi}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">CO</div>
                    <div className="stat-value">{props.airQuality.co}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">NO2</div>
                    <div className="stat-value">{props.airQuality.no2}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">O3</div>
                    <div className="stat-value">{props.airQuality.o3}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">PM10</div>
                    <div className="stat-value">{props.airQuality.pm10}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">PM25</div>
                    <div className="stat-value">{props.airQuality.pm25}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">SO2</div>
                    <div className="stat-value">{props.airQuality.so2}</div>
                </div>

            </div>
        </>);
}