package com.fif.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fif.dao.PenilaianDAO;
import com.fif.model.Penilaian;

@Service("penilaianService")
@Transactional
public class PenilaianService {

    @Autowired
    @Qualifier("penilaianDAO")
    private PenilaianDAO penilaianDAO;

    public List<Penilaian> getAll() {
        return penilaianDAO.findAll();
    }

    public void insert(Penilaian p) {
        penilaianDAO.save(p);
    }

    public void delete(Penilaian p) {
        penilaianDAO.delete(p);
    }

    public void update(Penilaian p) {
        penilaianDAO.update(p);
    }

    public Penilaian getById(int id) {
        return penilaianDAO.findById(id);
    }
}
