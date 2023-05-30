package com.example.demo.repository;

import com.example.demo.model.tables.records.TokenRecord;
import com.example.demo.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.jooq.DSLContext;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

import static com.example.demo.model.Tables.TOKEN;
import static com.example.demo.model.Tables._USER;

@RequiredArgsConstructor
@Repository
public class TokenRepository {

  private final DSLContext context;

  public List<Token> findAllValidTokenByUser(Integer id) {
    return context.select()
            .from(TOKEN)
            .join(_USER)
            .on(TOKEN.USER_ID.eq(id))
            .fetchInto(Token.class);
  }

  public Optional<Token> findByToken(String token) {
    Token dbToken = context.selectFrom(TOKEN).where(TOKEN.TOKEN_.eq(token)).fetchOneInto(Token.class);
    return (ObjectUtils.isEmpty(dbToken)) ? Optional.empty() : Optional.of(dbToken);
  }

  public Token save(Token token) {
    TokenRecord tokenRecord = context.insertInto(TOKEN)
            .set(TOKEN.USER_ID, token.getUser().getId())
            .set(TOKEN.TOKEN_, token.getToken())
            .set(TOKEN.TOKEN_TYPE, token.getTokenType().name())
            .set(TOKEN.EXPIRED, token.isExpired())
            .set(TOKEN.REVOKED, token.isRevoked())
            .returning(TOKEN.ID).fetchOne();

    if (tokenRecord != null) {
      token.setId(tokenRecord.getId());
      return token;
    }
    return null;
  };

  public void saveAll(List<Token> tokens) {
    tokens.forEach(token -> {
      context.insertInto(TOKEN)
              .set(TOKEN.TOKEN_, token.getToken())
              .set(TOKEN.TOKEN_TYPE, token.getTokenType().name())
              .set(TOKEN.EXPIRED, token.isExpired())
              .set(TOKEN.REVOKED, token.isRevoked())
              .returning(TOKEN.ID).fetchOne();

    });
  }
}