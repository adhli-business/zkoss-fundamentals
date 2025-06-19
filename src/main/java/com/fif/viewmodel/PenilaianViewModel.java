package com.fif.viewmodel;

import java.util.List;
import java.util.stream.Collectors;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.fif.model.Penilaian;
import com.fif.service.PenilaianService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PenilaianViewModel {

    @WireVariable("penilaianService")
    private PenilaianService service;
    private ListModelList<Penilaian> penilaianList;
    private String searchKeyword = "";
    private List<Penilaian> allData;
    private Penilaian selectedPenilaian;

    @Init
    @NotifyChange({"penilaianList", "allData"})
    public void init() {
        loadData();

        // Check if we need to refresh from a previous save
        Boolean refreshRequired = (Boolean) Executions.getCurrent().getAttribute("REFRESH_REQUIRED");
        if (refreshRequired != null && refreshRequired) {
            Executions.getCurrent().removeAttribute("REFRESH_REQUIRED");
            refreshData();
        }
    }

    @Command
    @NotifyChange({"penilaianList", "allData"})
    public void refreshData() {
        loadData();
    }

    private void loadData() {
        allData = service.getAll();
        penilaianList = new ListModelList<>(allData);
    }

    @Command
    @NotifyChange({"penilaianList", "searchKeyword"})
    public void search(@BindingParam("keyword") String keyword) {
        this.searchKeyword = keyword;
        if (keyword == null || keyword.trim().isEmpty()) {
            penilaianList = new ListModelList<>(allData);
        } else {
            String searchTerm = keyword.toLowerCase().trim();
            List<Penilaian> filtered = allData.stream()
                    .filter(p -> p.getNama().toLowerCase().contains(searchTerm)
                    || p.getCourse().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList());
            penilaianList = new ListModelList<>(filtered);
        }
    }

    @Command
    @NotifyChange("penilaianList")
    public void reset() {
        searchKeyword = "";
        penilaianList = new ListModelList<>(allData);
    }

    @Command
    public void edit(@BindingParam("penilaian") Penilaian penilaian) {
        org.zkoss.zk.ui.Executions.sendRedirect("form.zul?id=" + penilaian.getId());
    }

    @Command
    @NotifyChange("penilaianList")
    public void delete(@BindingParam("penilaian") Penilaian penilaian) {
        service.delete(penilaian);
        allData = service.getAll();
        penilaianList = new ListModelList<>(allData);
        Clients.showNotification("Data berhasil dihapus!", "info", null, "middle_center", 2000);
    }

    public ListModelList<Penilaian> getPenilaianList() {
        return penilaianList;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public Penilaian getSelectedPenilaian() {
        return selectedPenilaian;
    }

    public void setSelectedPenilaian(Penilaian selectedPenilaian) {
        this.selectedPenilaian = selectedPenilaian;
    }
}
