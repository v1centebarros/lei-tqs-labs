import {AirQuality} from "./Types";
import moment from "moment/moment";

export function CurrentQuality(props: { airQuality: AirQuality }) {
    return (
        <>
            <div className={"text-2xl font-bold"}>{moment(new Date(props.airQuality.dateTime)).format("MMMM Do YYYY h:mm a")}</div>
            <div className="stats w-full stats-vertical lg:stats-horizontal shadow bg-neutral">
                <div className="stat">
                    <div className={"stat-title text-primary aqi"}>Overall AQI</div>
                    <div className={"stat-value aqiValue"}>{props.airQuality.aqi}</div>
                </div>

                <div className="stat">
                    <div className={"stat-title text-primary co"}>CO</div>
                    <div className={"stat-value coValue"}>{props.airQuality.co}</div>
                </div>

                <div className="stat">
                    <div className={"stat-title text-primary no2"}>NO2</div>
                    <div className={"stat-value no2Value"}>{props.airQuality.no2}</div>
                </div>

                <div className="stat">
                    <div className={"stat-title text-primary o3"}>O3</div>
                    <div className={"stat-value o3Value"}>{props.airQuality.o3}</div>
                </div>

                <div className="stat">
                    <div className={"stat-title text-primary pm10"}>PM10</div>
                    <div className={"stat-value pm10Value"}>{props.airQuality.pm10}</div>
                </div>

                <div className="stat">
                    <div className={"stat-title text-primary pm25"}>PM25</div>
                    <div className={"stat-value pm25Value"}>{props.airQuality.pm25}</div>
                </div>

                <div className="stat">
                    <div className={"stat-title text-primary so2"}>SO2</div>
                    <div className={"stat-value so2Value"}>{props.airQuality.so2}</div>
                </div>

            </div>
        </>);
}