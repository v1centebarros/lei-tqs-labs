import {AirQuality, AirQualityForecast} from "./Types";
import {CurrentQuality} from "./CurrentQuality";

export function Forecast(props: { airQuality: AirQualityForecast }) {

    return (
        <>
            <div className={"font-bold text-2xl"}>{props.airQuality.data.length} Days Forecast</div>
            {props.airQuality.data.map((item: AirQuality, index) => {
                return (
                    <>
                        <CurrentQuality key={index} airQuality={item}/>
                    </>
                )
            })}
        </>
    )
}