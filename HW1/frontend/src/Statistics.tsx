import {Statistics} from "./Types";

export function StatisticsCmp(props: { statistics: Statistics }) {

    return (
        <>
            <div className={'font-bold text-2xl'}>Air Quality Current Statistics</div>

            <div className="stats w-full stats-vertical lg:stats-horizontal shadow bg-neutral">
                <div className="stat">
                    <div className="stat-title text-primary">Hits</div>
                    <div className="stat-value">{props.statistics.airQualityStats.hits}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">Misses</div>
                    <div className="stat-value">{props.statistics.airQualityStats.misses}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">Puts</div>
                    <div className="stat-value">{props.statistics.airQualityStats.puts}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">Requests</div>
                    <div className="stat-value">{props.statistics.airQualityStats.requests}</div>
                </div>
            </div>
            <div className={"divider"}></div>


            <div className={'font-bold text-2xl'}>Forecast Cache Statistics</div>

            <div className="stats w-full stats-vertical lg:stats-horizontal shadow bg-neutral">
                <div className="stat">
                    <div className="stat-title text-primary">Hits</div>
                    <div className="stat-value">{props.statistics.forecastCacheStats.hits}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">Misses</div>
                    <div className="stat-value">{props.statistics.forecastCacheStats.misses}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">Puts</div>
                    <div className="stat-value">{props.statistics.forecastCacheStats.puts}</div>
                </div>

                <div className="stat">
                    <div className="stat-title text-primary">Requests</div>
                    <div className="stat-value">{props.statistics.forecastCacheStats.requests}</div>
                </div>
            </div>

        </>);
}
