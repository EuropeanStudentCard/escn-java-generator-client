package eu.escn.generator.client;

import eu.escn.generator.client.exceptions.ApiException;
import eu.escn.generator.client.exceptions.EscnClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EscnClientFactoryIntegrationTest {

    @Test
    public void getEscnShouldReturnValidUUIDWhenPrefixAndPicAreValid() throws Exception {
        String uuidFormat = "(\\w{8}(-\\w{4}){3}-\\w{12}?)";
        Pattern p = Pattern.compile(uuidFormat);

        String returnTest = EscnClientFactory.create().getEscn(66, "999859608");

        Matcher m = p.matcher(returnTest);
        Assertions.assertTrue(m.matches());
        Assertions.assertEquals("066999859608", returnTest.substring(returnTest.length() - 12));
    }

    @Test
    public void getEscnShouldThrowAnEscnExceptionWhenPrefixIsInvalid() throws Exception {
        Assertions.assertThrows(ApiException.class, () -> EscnClientFactory.create().getEscn(6666, "999859608"));
        EscnClientFactory.create().getEscn(6666, "999859608");
    }

    @Test
    public void getEscnShouldThrowAnEscnExceptionWhenPicIsInvalid() throws Exception {
        Assertions.assertThrows(ApiException.class, () -> EscnClientFactory.create().getEscn(666, "InvalidPicOfMoreThan9Chars"));
        EscnClientFactory.create().getEscn(6666, "999859608");
    }

    @Test
    public void getEscnShouldThrowAnEscnExceptionWhenHostIsInvalid() throws Exception {
        Assertions.assertThrows(EscnClientException.class, () -> EscnClientFactory.create("http://wrong.host").getEscn(666, "999859608"));
    }
}