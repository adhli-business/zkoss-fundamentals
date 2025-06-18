package com.fif.model;

public class Penilaian {
    private int id;
    private String nama;
    private String course;
    private int nilai;

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
