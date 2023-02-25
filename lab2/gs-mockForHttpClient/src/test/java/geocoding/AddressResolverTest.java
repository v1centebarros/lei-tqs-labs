package geocoding;

import connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    private ISimpleHttpClient client;

    @InjectMocks
    private AddressResolver resolver;


    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {
        String longitude = "40.64143694391066";
        String latitude = "-8.660728660686342";

        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.634159,\"lng\":-8.65795}},\"locations\":[{\"street\":\"Avenida João Jacinto de Magalhães\",\"adminArea6\":\"Aveiro\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"Aveiro\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-149\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.63416,\"lng\":-8.65795},\"displayLatLng\":{\"lat\":40.63416,\"lng\":-8.65795},\"mapUrl\":\"\",\"roadMetadata\":{\"speedLimitUnits\":\"mph\",\"speedLimit\":31,\"tollRoad\":null}}]}]}";

        Mockito.when(client.doHttpGet(anyString())).thenReturn(response);

        Optional<Address> address = resolver.findAddressForLocation(Double.parseDouble(latitude), Double.parseDouble(longitude));

        assertTrue(address.isPresent());
        assertEquals("Avenida João Jacinto de Magalhães", address.get().getRoad());
        Mockito.verify(client, Mockito.times(1)).doHttpGet(anyString());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {
        String longitude = "420";
        String latitude = "69";

        String response = "{\"info\":{\"statuscode\":400,\"copyright\":{\"text\":\"© 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"© 2022 MapQuest, Inc.\"},\"messages\":[\"Illegal argument from request: Invalid LatLng specified.\"]},\"options\":{\"maxResults\":1,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{},\"locations\":[]}]}";

        Mockito.when(client.doHttpGet(anyString())).thenReturn(response);

        Optional<Address> address = resolver.findAddressForLocation(Double.parseDouble(latitude), Double.parseDouble(longitude));

        assertFalse(address.isPresent());
        Mockito.verify(client, Mockito.times(1)).doHttpGet(anyString());
    }
}