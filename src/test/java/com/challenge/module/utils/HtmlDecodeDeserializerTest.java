package com.challenge.module.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class HtmlDecodeDeserializerTest {
    private HtmlDecodeDeserializer deserializer;

    private DeserializationContext deserializationContext;

    @BeforeEach
    void setUp() {
        deserializer = new HtmlDecodeDeserializer();
        deserializationContext = mock(DeserializationContext.class);
    }

    @Test
    void testDeserialize() throws IOException {
        JsonParser jsonParser = getJsonParser("\"&lt;div&gt;Test&lt;/div&gt;\"");
        String result = deserializer.deserialize(jsonParser, deserializationContext);

        assertEquals("<div>Test</div>", result);
    }

    @Test
    void testDeserializeAmpersand() throws IOException {
        JsonParser jsonParser = getJsonParser("\"Tom &amp; Jerry\"");
        String result = deserializer.deserialize(jsonParser, deserializationContext);

        assertEquals("Tom & Jerry", result);
    }

    private JsonParser getJsonParser(String content) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonParser jsonParser = new JsonFactory().createParser(content);
        jsonParser.setCodec(objectMapper);
        return jsonParser;
    }
}

