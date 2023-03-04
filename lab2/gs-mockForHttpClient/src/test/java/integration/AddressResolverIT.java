package integration;

import connection.ISimpleHttpClient;
import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressResolverIT {

    private ISimpleHttpClient client;
    private AddressResolver resolver;

    @BeforeEach
    public void init(){
        client = new TqsBasicHttpClient();
        resolver = new AddressResolver(client);
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        double latitude = 40.634159;
        double longitude =  -8.65795;

        Address result = resolver.findAddressForLocation(latitude, longitude).get();

        Address expected = new Address("Avenida João Jacinto de Magalhães", "Aveiro", "Aveiro", "Portugal", "3810-149");

        assertEquals(expected.getRoad(), result.getRoad());
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {
        double latitude = 420;
        double longitude = 69;

        Optional<Address> address = resolver.findAddressForLocation(latitude, longitude);

        assertThrows(NoSuchElementException.class, () -> address.get().getRoad());
    }

    @Test
    public void usingTheWrongCoordinates() throws IOException, URISyntaxException, ParseException {
        double latitude = 41.634159;
        double longitude =  -9.65795;

        Address result = resolver.findAddressForLocation(latitude, longitude).get();

        Address expected = new Address("Avenida João Jacinto de Magalhães", "Aveiro", "Aveiro", "Portugal", "3810-149");

        assertNotEquals(expected.getRoad(), result.getRoad());

    }

}
