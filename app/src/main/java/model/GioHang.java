package model;

public class GioHang {
    public int idsp;
    public String tensp;
    public long Giasp;
    public String Hinhsp;
    public int Soluong;

    public GioHang(int idsp, String tensp, long giasp, String hinhsp, int soluong) {
        this.idsp = idsp;
        this.tensp = tensp;
        Giasp = giasp;
        Hinhsp = hinhsp;
        Soluong = soluong;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return Giasp;
    }

    public void setGiasp(long giasp) {
        Giasp = giasp;
    }

    public String getHinhsp() {
        return Hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        Hinhsp = hinhsp;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }
}
