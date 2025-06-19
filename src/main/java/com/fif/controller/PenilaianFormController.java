package com.fif.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.fif.model.Penilaian;
import com.fif.service.PenilaianService;

public class PenilaianFormController extends SelectorComposer<Component> {

    private PenilaianService service = new PenilaianService();

    @Wire
    private Textbox nama, course;

    @Wire
    private Intbox nilai;

    private Penilaian selected;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Selectors.wireComponents(comp, this, false);
        // Cek parameter id untuk edit
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                for (Penilaian p : service.getAll()) {
                    if (p.getId() == id) {
                        selected = p;
                        nama.setValue(p.getNama());
                        course.setValue(p.getCourse());
                        nilai.setValue(p.getNilai());
                        break;
                    }
                }
            } catch (NumberFormatException ignored) {
            }
        }
    }

    @Listen("onClick = #btnSimpan")
    public void onSave() {
        if (nama == null || course == null || nilai == null) {
            Messagebox.show("Form tidak lengkap atau salah halaman!", "Error", Messagebox.OK, Messagebox.ERROR);
            return;
        }
        if (selected == null) {
            Penilaian p = new Penilaian();
            p.setNama(nama.getValue());
            p.setCourse(course.getValue());
            p.setNilai(nilai.getValue());
            service.save(p);
        } else {
            selected.setNama(nama.getValue());
            selected.setCourse(course.getValue());
            selected.setNilai(nilai.getValue());
            service.update(selected);
            selected = null;
        }
        nama.setValue("");
        course.setValue("");
        nilai.setValue(null);
        Messagebox.show("Data berhasil disimpan!", "Info", Messagebox.OK, Messagebox.INFORMATION);
    }
}
