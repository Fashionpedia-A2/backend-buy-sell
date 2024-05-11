package id.ac.ui.cs.advprog.backendbuysell.enums;

public enum OrderStatus {
    MENUNGGU_PEMBAYARAN,
    MENUNGGU_PROSES,
    DIPROSES,
    DIKIRIM,
    SELESAI,
    DIBATALKAN;

    public static OrderStatus fromString(String order){
        return valueOf(order.toUpperCase());
    }
}
