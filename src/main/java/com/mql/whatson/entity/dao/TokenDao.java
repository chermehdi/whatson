package com.mql.whatson.entity.dao;

import java.util.List;

import com.mql.whatson.entity.Token;

public interface TokenDao {

    Token findById(long tokenId);

    List<Token> findAll();

    Token save(Token token);

    void update(Token token);

    void remove(Token token);
}
