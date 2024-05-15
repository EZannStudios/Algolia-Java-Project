package com.challenge.module.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class HtmlDecodeDeserializer extends StdDeserializer<String> {

    public HtmlDecodeDeserializer() {
        this(null);
    }

    public HtmlDecodeDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String encodedValue = node.asText();
        return org.apache.commons.text.StringEscapeUtils.unescapeHtml4(encodedValue);
    }
}

