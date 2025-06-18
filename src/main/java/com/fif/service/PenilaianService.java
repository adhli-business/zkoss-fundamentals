package com.fif.service;

import java.util.List;

import com.fif.dao.PenilaianDAO;
import com.fif.model.Penilaian;

public class PenilaianService {
    public List<Penilaian> getAll() {
        return PenilaianDAO.findAll();
    }

    public void save(Penilaian p) {
        PenilaianDAO.save(p);
    }

    public void delete(Penilaian p) {
        PenilaianDAO.delete(p);
    }

    public void update(Penilaian p) {
        PenilaianDAO.update(p);
    }
}
