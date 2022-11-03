package io.jexxa.openapi.integration.applicationservice;

import io.jexxa.openapi.OpenAPI;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.jexxa.infrastructure.drivingadapter.rest.JexxaWebProperties.JEXXA_REST_PORT;
import static io.jexxa.jexxatest.JexxaTest.getJexxaTest;
import static kong.unirest.ContentType.APPLICATION_JSON;
import static kong.unirest.HeaderNames.CONTENT_TYPE;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenAPIIT
{
    static private String restPath;

    @BeforeEach
    void initBeforeEach()
    {
        var jexxaTest = getJexxaTest(OpenAPI.class);

        restPath = "http://localhost:" + jexxaTest.getProperties().getProperty(JEXXA_REST_PORT);

        //Wait until application was started (using 10 seconds should be sufficient to start large applications)
        await().atMost(10, TimeUnit.SECONDS)
                .pollDelay(100, TimeUnit.MILLISECONDS)
                .ignoreException(UnirestException.class)
                .until(OpenAPIIT::contextIsRunning);

    }


    @Test
    void testStartupApplication()
    {
        //Arrange
        var uriContextName = restPath +"/BoundedContext/contextName";

        //Act
        var result = Unirest.get(uriContextName)
                .header(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                .asObject(String.class)
                .getBody();

        //Assert
        assertEquals(OpenAPI.class.getSimpleName(), result);
    }


    static public boolean contextIsRunning()
    {
        var uriContextName = restPath +"/BoundedContext/isRunning";

        return Unirest.get(uriContextName)
                .header(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                .asObject(Boolean.class)
                .getBody();
    }

    @AfterAll
    static void tearDown()
    {
        Unirest.shutDown();
    }
}
