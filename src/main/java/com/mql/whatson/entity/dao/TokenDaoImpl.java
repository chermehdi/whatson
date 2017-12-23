package com.mql.whatson.entity.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mql.whatson.entity.Token;

@Stateless
public class TokenDaoImpl implements TokenDao {

	@PersistenceContext
	private EntityManager entityManager;

	public TokenDaoImpl() {
	}

	public Token findById(long tokenId) {
		try {
			return entityManager.find(Token.class, tokenId);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Token> findAll() {
		try {

			TypedQuery<Token> query = entityManager.createQuery("SELECT t FROM TOKEN t" + "ORDER BY t.tokenId",
					Token.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Token save(Token token) {
		try {
			entityManager.persist(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	public void update(Token token) {
		try {
			entityManager.merge(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(Token token) {
		try {
			entityManager.remove(entityManager.merge(token));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
