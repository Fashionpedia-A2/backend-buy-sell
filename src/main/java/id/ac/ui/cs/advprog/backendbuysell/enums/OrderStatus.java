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

    public static String getString() {
        StringBuilder result = new StringBuilder("[");
        boolean isFirst = true;
        for (OrderStatus status : OrderStatus.values()) {
            if (!isFirst) {
                result.append(", ");
            }
            result.append(status.name());
            isFirst = false;
        }
        result.append("]");
        return result.toString();
    }
}
