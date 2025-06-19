package com.fif.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;

import com.fif.model.Penilaian;
import com.fif.service.PenilaianService;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PenilaianFormViewModel {

    @WireVariable("penilaianService")
    private PenilaianService service;
    private Penilaian penilaian;

    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            penilaian = service.getById(id);
        } else {
            penilaian = new Penilaian();
        }
    }

    @Command
    public void save() {
        try {
            if (!penilaian.isValid()) {
                String errorMessage = penilaian.getValidationMessage();
                Clients.showNotification(errorMessage, "error", null, "middle_center", 3000);
                return;
            }

            if (penilaian.getId() == 0) {
                service.insert(penilaian);
            } else {
                service.update(penilaian);
            }
            Clients.showNotification("Data berhasil disimpan!", "info", null, "middle_center", 2000);

            // Redirect and ensure list is refreshed
            Executions.getCurrent().setAttribute("REFRESH_REQUIRED", true);
            Executions.sendRedirect("index2.zul");
        } catch (Exception e) {
            Clients.showNotification("Error: " + e.getMessage(), "error", null, "middle_center", 3000);
        }
    }

    public Penilaian getPenilaian() {
        return penilaian;
    }

    public void setPenilaian(Penilaian penilaian) {
        this.penilaian = penilaian;
    }

    @Command
    @NotifyChange("penilaian")
    public void reset() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            penilaian = service.getById(id);
        } else {
            penilaian = new Penilaian();
        }
    }
}
