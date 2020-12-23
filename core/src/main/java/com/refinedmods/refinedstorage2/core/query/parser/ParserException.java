package com.refinedmods.refinedstorage2.core.query.parser;

import com.refinedmods.refinedstorage2.core.query.lexer.Token;

public class ParserException extends RuntimeException {
    private final transient Token token;

    public ParserException(String message, Token token) {
        super(message);

        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
