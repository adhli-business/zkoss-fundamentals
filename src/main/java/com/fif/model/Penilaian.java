package com.fif.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "penilaian")
public class Penilaian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nama;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private int nilai;

    public boolean isValid() {
        return nama != null && !nama.trim().isEmpty()
                && course != null && !course.trim().isEmpty()
                && nilai >= 0 && nilai <= 100;
    }

    public String getValidationMessage() {
        if (nama == null || nama.trim().isEmpty()) {
            return "Nama harus diisi";
        }
        if (course == null || course.trim().isEmpty()) {
            return "Course harus diisi";
        }
        if (nilai < 0 || nilai > 100) {
            return "Nilai harus antara 0 dan 100";
        }
        return null;
    }

    public Penilaian() {
    }

    public Penilaian(int id, String nama, String course, int nilai) {
        this.id = id;
        this.nama = nama;
        this.course = course;
        this.nilai = nilai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }
}
