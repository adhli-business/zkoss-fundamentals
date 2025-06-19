package com.fif.controller;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import com.fif.model.Penilaian;
import com.fif.service.PenilaianService;

public class PenilaianController extends SelectorComposer<Component> {

    private final PenilaianService service = new PenilaianService();

    @Wire
    private Listbox listboxPenilaian;

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
                org.zkoss.zk.ui.Executions.sendRedirect("index.zul?id=" + data.getId());
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
}
