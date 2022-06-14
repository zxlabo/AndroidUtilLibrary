package com.labo.lib.tool.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HiLogManager {

    private static HiLogManager instance;
    private List<HiLogPrinter> printers = new ArrayList<>();

    private HiLogManager() {

    }

    public static HiLogManager getInstance() {
        if (instance == null) {
            synchronized (HiLogManager.class) {
                if (instance == null) {
                    instance = new HiLogManager();
                }
            }
        }
        return instance;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void addPrinters(HiLogPrinter[] printers) {
        this.printers.addAll(Arrays.asList(printers));
    }

    public void removePrinter(HiLogPrinter printer) {
        if (printers != null) {
            printers.remove(printer);
        }
    }
}
