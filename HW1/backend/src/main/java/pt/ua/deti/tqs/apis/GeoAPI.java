package pt.ua.deti.tqs.apis;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.data.City;

@Service
public interface GeoAPI {
    City fetchCity(String city);
}
