package com.website.authentication.model;

import java.util.Set;

public class SensitiveFields {
    public static final Set<String> FIELDS = Set.of(
            "password",
            "token",
            "secret",
            "apiKey"
    );
}
