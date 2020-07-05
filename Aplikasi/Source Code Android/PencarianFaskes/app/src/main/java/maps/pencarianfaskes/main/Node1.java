package maps.pencarianfaskes.main;

/**
 * Created by badaw5 on 6/22/2016.
 */
public class Node1 {

    private String id, nama, alamat;

    public Node1(){

    }

    public Node1(String id, String nama, String alamat) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }


    public String getAlamat() {
        return alamat;
    }


    public String getId() {
        return id;
    }
}
