package com.fif.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Include;


import com.fif.model.Penilaian;
import com.fif.service.PenilaianService;

public class PenilaianController extends SelectorComposer<Component> {
    private PenilaianService service = new PenilaianService();

    @Wire
    private Listbox listboxPenilaian;

    @Wire
    private Textbox nama, course;

    @Wire
    private Intbox nilai;

    private Penilaian selected;

    @Wire
    private Include mainInclude;

    @Listen("onClick = #btnDashboard")
    public void goToDashboard() {
        mainInclude.setSrc("/pages/Dashboard.zul");
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        refreshData();
    }

    public void refreshData() {
        List<Penilaian> list = service.getAll();
        listboxPenilaian.setModel(new ListModelList<>(list));
        listboxPenilaian.setItemRenderer((Listitem item, Penilaian data, int index) -> {
            item.getChildren().clear();
            item.appendChild(new Listcell(data.getNama()));
            item.appendChild(new Listcell(data.getCourse()));
            item.appendChild(new Listcell(String.valueOf(data.getNilai())));

            Listcell aksi = new Listcell();
            Button btnEdit = new Button("Edit");
            Button btnDelete = new Button("Delete");

            btnEdit.addEventListener("onClick", e -> {
                selected = data;
                nama.setValue(data.getNama());
                course.setValue(data.getCourse());
                nilai.setValue(data.getNilai());
            });

            btnDelete.addEventListener("onClick", e -> {
                service.delete(data);
                refreshData();
            });

            aksi.appendChild(btnEdit);
            aksi.appendChild(btnDelete);
            item.appendChild(aksi);
        });
    }

    @Listen("onClick = #btnSimpan")
    public void onSave() {
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
        refreshData();
    }
}
