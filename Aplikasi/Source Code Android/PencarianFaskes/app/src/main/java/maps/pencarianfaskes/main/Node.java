package maps.pencarianfaskes.main;

/**
 * Created by RG 7 on 15/04/2017.
 */

public class Node {

    private String id, nama, telepon, alamat;

    public Node(){

    }

    public Node(String id, String nama, String telepon, String alamat) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
    }

    public String getNama() {

        return nama;
    }


    public String getTelepon() {
        return telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getId() {
        return id;
    }
}
