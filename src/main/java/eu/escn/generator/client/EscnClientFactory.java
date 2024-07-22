package eu.escn.generator.client;

import com.fasterxml.jackson.core.type.TypeReference;
import eu.escn.generator.client.utils.EscnRestRequestBuilder;
import org.apache.hc.core5.http.ContentType;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This class is used to generate unique European Student Card Number (ESCN) for the "european student card"
 * <p>
 * This number is an UUID of 16 bytes (128 bits) and the generation algorithm is described in RFC 4122 :
 * <p>
 * Octet 0-3: time_low The low field of the timestamp
 * <p>
 * Octet 4-5: time_mid The middle field of the timestamp
 * <p>
 * Octet 6-7: time_hi_and_version The high field of the timestamp multiplexed with the version number
 * <p>
 * Octet 8: clock_seq_hi_and_reserved The high field of the clock sequence multiplexed with the variant
 * <p>
 * Octet 9: clock_seq_low The low field of the clock sequence
 * <p>
 * Octet 10-15: node The spatially unique node identifier
 *
 */
public class EscnClientFactory {

    private final String host;

    private EscnClientFactory(String host) {
        this.host = host;
    }

    public static EscnClientFactory create() {
        return new EscnClientFactory("http://router.europeanstudentcard.eu");
    }

    public static EscnClientFactory create(String host) {
        return new EscnClientFactory(host);
    }

    /**
     * This method calculates a UUID from 2 parameters.
     *
     * @param prefix To distinguish servers of the same institution.
     * @param pic    Participant Identification Code.
     * @return A unique ESCN as a String.
     * @throws IOException         If there is an I/O error.
     * @throws URISyntaxException If the URI is not correctly formatted.
     */
    public String getEscn(Integer prefix, String pic) throws IOException, URISyntaxException {
        return getEscn(prefix, pic, 1).get(0);
    }

    /**
     * This method calculates a list of UUIDs from 2 parameters.
     *
     * @param prefix       To distinguish servers of the same institution.
     * @param pic          Participant Identification Code.
     * @param numberOfESCN The number of ESCNs to generate.
     * @return A list of unique ESCNs.
     * @throws IOException         If there is an I/O error.
     * @throws URISyntaxException If the URI is not correctly formatted.
     */
    public List<String> getEscn(Integer prefix, String pic, Integer numberOfESCN) throws IOException, URISyntaxException {

        // Initialize the request builder
        EscnRestRequestBuilder builder = new EscnRestRequestBuilder("GET", host.concat("/api/v2/cards/generate-escn"));

        // Add query parameters
        builder.addHeaders(null, ContentType.APPLICATION_JSON)
                .addQueryParam("prefix", prefix)
                .addQueryParam("pic", pic)
                .addQueryParam("numberOfESCN", numberOfESCN);

        // Build and execute the request
        return builder.buildRequest(new TypeReference<List<String>>() {}).execute();

    }

}