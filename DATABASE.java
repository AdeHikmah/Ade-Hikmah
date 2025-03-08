import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class DATABASE {

    private ArrayList<MAHASISWI> DATA = new ArrayList<>();
    private String filename = "src/DATA.csv";
    private Path path = Path.of(filename);

    public DATABASE() {
        open();
    }

    public ArrayList<MAHASISWI> getDATA() {
        return DATA;
    }

    public void open(){
        try {
            List<String> lines = Files.readAllLines(path);
            DATA = new ArrayList<>();
            for (int i = 1; i <lines.size() ; i++) {
                String line = lines.get(i);
                String[]element =line.split( ",");
                String nim = element[0];
                String nama = element[1];
                String alamat = element[2];
                int semester = Integer.parseInt(element[3]);
                int sks = Integer.parseInt(element[4]);
                double ipk = Double.parseDouble(element[5]);
                MAHASISWI mhs = new MAHASISWI(nim, nama, alamat, semester, sks, ipk);
                DATA.add(mhs);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(){
        StringBuilder sb = new StringBuilder();
        sb.append("NIM,NAMA,ALAMAT (KOTA),SEMESTER,SKS,IPK\n");
        if (!DATA.isEmpty()){
            for (int i = 0; i < DATA.size() ; i++) {
                MAHASISWI mhs = DATA.get(i);
                String line = mhs.getNim() + "," + mhs.getNama() + "," + mhs.getAlamat() + "," + mhs.getSemester() + "," + mhs.getSks() + "," + mhs.getIpk() + "\n";
                sb.append(line);
            }
        }
        try {
            Files.writeString(path,sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void view(){
        System.out.println("====================================================================================");
        System.out.printf("| %-8.8S |", "NIM");
        System.out.printf(" %-20.20S |", "NAMA");
        System.out.printf(" %-20.20S |", "ALAMAT");
        System.out.printf(" %-8.8S |", "SEMESTER");
        System.out.printf(" %-3.3S |", "SKS");
        System.out.printf(" %-4.4S |%n", "SKS");
        System.out.println("------------------------------------------------------------------------------------");
        for (MAHASISWI mhs : DATA) {
            System.out.printf("| %-8.8S |", mhs.getNim());
            System.out.printf(" %20.20S | ", mhs.getNama());
            System.out.printf(" %20.20S |", mhs.getAlamat());
            System.out.printf(" %8.8S |", mhs.getSemester());
            System.out.printf(" %3.3S |", mhs.getSks());
            System.out.printf(" %4.4S |", mhs.getIpk());
            System.out.println();
        }
        System.out.println("------------------------------------------------------------------------------------");
    }

    boolean insert(String nim, String nama, String alamat, int semester, int sks, double ipk) {
        boolean status = true;
        //cek primary key
        if (!DATA.isEmpty()) {
            for (int i = 0; i < DATA.size(); i++) {
                if (DATA.get(i).getNim().equalsIgnoreCase(nim)) {
                    status = false;
                    break;
                }
            }
        }
        if (status == true){
            MAHASISWI mhs = new MAHASISWI(nim,nama, alamat, semester, sks, ipk);
            DATA.add(mhs);
            save();
        }
        return status;
    }

    public int search(String nim) {
        int index = -1;
        if (!DATA.isEmpty()){
            for (int i = 0; i < DATA.size(); i++) {
                if (DATA.get(i).getNim().equalsIgnoreCase(nim)) {
                    index =i;
                    break;
                }
            }
        }
        return index;
    }

    public boolean update(int index, String nim, String nama, String alamat, int semester, int sks, double ipk){
        boolean status = false;
        if (!DATA.isEmpty()) {
            //update
            if (index >= 0 && index < DATA.size()) {
                MAHASISWI mhs = new MAHASISWI(nim, nama, alamat, semester, sks, ipk);
                DATA.set(index, mhs);
                save();
                status = true;
            }
        }
        return status;
    }

    public boolean delete(int index) {
        boolean status = false;
        if (!DATA.isEmpty()) {
            DATA.remove(index);
            save();
            status = true;
        }
        return status;
    }
}

