package com.fif.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.fif.model.User;

@Repository
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional(Transactional.TxType.SUPPORTS)
    public User get(String username) throws NoResultException {
        TypedQuery<User> query = em.createQuery(
            "SELECT u FROM User u WHERE u.username = :username", User.class
        );
        query.setParameter("username", username);
        return query.getSingleResult();
    }
}