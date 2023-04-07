import {AirQuality, AirQualityForecast} from "./Types";
import {CurrentQuality} from "./CurrentQuality";
import React, { useState } from 'react';

export function Forecast(props: { airQuality: AirQualityForecast }) {
    const [visibleForecasts, setVisibleForecasts] = useState(5);

    const handleLoadMore = () => {
        setVisibleForecasts((prevVisibleForecasts) => prevVisibleForecasts + 5);
    };

    return (
        <>
            <div className={'font-bold text-3xl text-primary'}>{props.airQuality.data.length} Forecast Entries</div>
            {props.airQuality.data.slice(0, visibleForecasts).map((item: AirQuality) => {
                return <CurrentQuality key={item.dateTime} airQuality={item} />;
            })}
            {visibleForecasts < props.airQuality.data.length && (
                <button onClick={handleLoadMore} className={"btn btn-primary btn-block pb-2"}>Load More</button>
            )}
        </>
    );
}
