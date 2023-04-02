package pt.ua.deti.tqs.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Data
public class AirQualityOld {

    private double lat;
    private double lon;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private AirQualityIndex qualityIndex;
    private Double co;
    private Double no;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double pm25;
    private Double pm10;
    private Double nh3;

    @JsonProperty("list")
    @SuppressWarnings("unchecked")
    private void unpackNested(List<Map<String, Object>> list) {
        Map<String, Object> map = list.get(0);
        Map<String, Number> components = (Map<String, Number>) map.get("components");

        this.date = LocalDateTime.ofEpochSecond(((Number) map.get("dt")).longValue(), 0, ZoneOffset.UTC);
        this.co =  components.get("co").doubleValue();
        this.no = components.get("no").doubleValue();
        this.no2 = components.get("no2").doubleValue();
        this.o3 = components.get("o3").doubleValue();
        this.so2 = components.get("so2").doubleValue();
        this.pm25 = components.get("pm2_5").doubleValue();
        this.pm10 = components.get("pm10").doubleValue();
        this.nh3 = components.get("nh3").doubleValue();
        Map<String,Integer> main = (Map<String, Integer>) map.get("main");
        this.qualityIndex = AirQualityIndex.fromValue(main.get("aqi"));
    }

    @JsonProperty("coord")
    private void unpackCoord(Map<String, Number> coord) {
        this.lat = coord.get("lat").longValue();
        this.lon = coord.get("lon").longValue();
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", date=" + date +
                ", qualityIndex=" + qualityIndex +
                ", co=" + co +
                ", no=" + no +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", so2=" + so2 +
                ", pm2_5=" + pm25 +
                ", pm10=" + pm10 +
                ", nh3=" + nh3 +
                '}';
    }
}
