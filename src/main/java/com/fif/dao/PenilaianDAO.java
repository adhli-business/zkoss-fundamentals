package com.fif.dao;

import java.util.ArrayList;
import java.util.List;

import com.fif.model.Penilaian;

public class PenilaianDAO {

    private static final List<Penilaian> data = new ArrayList<>();
    private static int counter = 1;

    public static List<Penilaian> findAll() {
        return new ArrayList<>(data);
    }

    public static void save(Penilaian p) {
        p.setId(counter++);
        data.add(p);
    }

    public static void delete(Penilaian p) {
        data.removeIf(d -> d.getId() == p.getId());
    }

    public static void update(Penilaian updated) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == updated.getId()) {
                data.set(i, updated);
                return;
            }
        }
    }

    public static Penilaian findById(int id) {
        return data.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
