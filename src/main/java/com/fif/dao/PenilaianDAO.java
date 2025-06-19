package com.fif.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fif.model.Penilaian;

@Repository("penilaianDAO")
@Transactional
public class PenilaianDAO {

    @PersistenceContext(unitName = "myapp")
    private EntityManager entityManager;

    public List<Penilaian> findAll() {
        TypedQuery<Penilaian> query = entityManager.createQuery("SELECT p FROM Penilaian p", Penilaian.class);
        return query.getResultList();
    }

    public void save(Penilaian p) {
        if (p.getId() == 0) {
            entityManager.persist(p);
        } else {
            entityManager.merge(p);
        }
    }

    public void delete(Penilaian p) {
        Penilaian entity = entityManager.find(Penilaian.class, p.getId());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public void update(Penilaian updated) {
        entityManager.merge(updated);
    }

    public Penilaian findById(int id) {
        return entityManager.find(Penilaian.class, id);
    }
}
