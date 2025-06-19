package com.fif.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import com.fif.model.Penilaian;
import com.fif.service.PenilaianService;

public class PenilaianController extends SelectorComposer<Component> {

    private final PenilaianService service = new PenilaianService();
    private List<Penilaian> allData;

    @Wire
    private Listbox listboxPenilaian;

    @Wire
    private Textbox searchBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        allData = service.getAll(); // simpan semua data
        renderData(allData);
    }

    public void renderData(List<Penilaian> list) {
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
                allData = service.getAll(); // refresh data
                renderData(allData);
            });

            aksi.appendChild(btnEdit);
            aksi.appendChild(btnDelete);
            item.appendChild(aksi);
        });
    }

    @Listen("onChanging = #searchBox")
    public void onTyping(InputEvent event) {
        String keyword = event.getValue().toLowerCase().trim();
        List<Penilaian> filtered = allData.stream()
                .filter(p -> p.getNama().toLowerCase().contains(keyword)
                        || p.getCourse().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        renderData(filtered);
    }

    @Listen("onClick = #btnSearch")
    public void onSearch() {
        String keyword = searchBox.getValue().toLowerCase().trim();
        List<Penilaian> filtered = allData.stream()
                .filter(p -> p.getNama().toLowerCase().contains(keyword)
                        || p.getCourse().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
        renderData(filtered);
    }

    @Listen("onClick = #btnReset")
    public void onReset() {
        searchBox.setValue("");
        renderData(allData);
    }
}
