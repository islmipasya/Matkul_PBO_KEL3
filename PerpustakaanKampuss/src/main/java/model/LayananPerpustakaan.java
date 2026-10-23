package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class LayananPerpustakaan {

    private static final int JML_LANTAI = 3;
    private static final int JML_RAK = 2;
    private static final int JML_SLOT = 5;

    // 1. Array 1D
    private final String[] kategori = {"Pemrograman", "Matematika", "Jaringan Komputer", "Basis Data"};
    private final String[] namaHari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

    // 2. Array 2D [kategori][hari]
    private final int[][] rekap = new int[kategori.length][namaHari.length];

    // 3. Array 3D [lantai][rak][slot]
    private final Buku[][][] denah = new Buku[JML_LANTAI][JML_RAK][JML_SLOT];

    // 4. ArrayList
    private final ArrayList<Buku> katalog = new ArrayList<>();

    // 5. Stack
    private final Stack<Transaksi> riwayat = new Stack<>();

    // 6. Queue (offer/poll/peek; addFirst hanya untuk undo peminjaman)
    private final LinkedList<String> antrean = new LinkedList<>();

    public LayananPerpustakaan() {
        siapkanBuku("PRG-01", "Dasar Pemrograman Java", 0);
        siapkanBuku("PRG-02", "Struktur Data dan Algoritma", 0);
        siapkanBuku("MTK-01", "Kalkulus 1", 1);
        siapkanBuku("JKM-01", "Jaringan Komputer Modern", 2);
        siapkanBuku("BSD-01", "Perancangan Basis Data", 3);

        antrean.offer("Rina Putri (NIM 2501001)");
        antrean.offer("Budi Santoso (NIM 2501002)");
    }

    // ---------- Kelas pembantu untuk isi Stack ----------
    private static class Transaksi {

        public enum Jenis { TAMBAH_BUKU, HAPUS_BUKU, PINJAM }

        private final Jenis jenis;
        private final Buku buku;
        private final String peminjam;   // hanya dipakai untuk PINJAM
        private final int hari;          // indeks hari, hanya untuk PINJAM
        private final int indeksKatalog; // posisi buku di ArrayList, untuk HAPUS_BUKU
        private final String keterangan;

        public Transaksi(Jenis jenis, Buku buku, String peminjam, int hari,
                         int indeksKatalog, String keterangan) {
            this.jenis = jenis;
            this.buku = buku;
            this.peminjam = peminjam;
            this.hari = hari;
            this.indeksKatalog = indeksKatalog;
            this.keterangan = keterangan;
        }

        public Jenis getJenis() { return jenis; }
        public Buku getBuku() { return buku; }
        public String getPeminjam() { return peminjam; }
        public int getHari() { return hari; }
        public int getIndeksKatalog() { return indeksKatalog; }
        public String getKeterangan() { return keterangan; }
    }

    // ---------- Method internal ----------

    private void siapkanBuku(String kode, String judul, int kat) {
        int[] pos = cariSlotKosong();
        Buku b = new Buku(kode, judul, kat, pos[0], pos[1], pos[2]);
        katalog.add(b);
        denah[pos[0]][pos[1]][pos[2]] = b;
    }

    private int[] cariSlotKosong() {
        for (int l = 0; l < JML_LANTAI; l++) {
            for (int r = 0; r < JML_RAK; r++) {
                for (int s = 0; s < JML_SLOT; s++) {
                    if (denah[l][r][s] == null) {
                        return new int[]{l, r, s};
                    }
                }
            }
        }
        return null;
    }

    public Buku cariBuku(String kode) {
        for (Buku b : katalog) {
            if (b.getKode().equalsIgnoreCase(kode)) {
                return b;
            }
        }
        return null;
    }

    // ---------- Array 1D ----------

    public void tampilkanKategori() {
        for (int i = 0; i < kategori.length; i++) {
            System.out.println((i + 1) + ". " + kategori[i]);
        }
    }

    // ---------- Array 2D ----------

    public void tampilkanRekap() {
        System.out.printf("%-20s", "Kategori");
        for (String h : namaHari) {
            System.out.printf("%-8s", h);
        }
        System.out.println("Total");
        System.out.println("-".repeat(20 + 8 * namaHari.length + 5));

        for (int i = 0; i < kategori.length; i++) {
            int total = 0;
            System.out.printf("%-20s", kategori[i]);
            for (int j = 0; j < namaHari.length; j++) {
                System.out.printf("%-8d", rekap[i][j]);
                total += rekap[i][j];
            }
            System.out.println(total);
        }
    }

    // ---------- Array 3D ----------

    public void tampilkanDenah() {
        for (int l = 0; l < JML_LANTAI; l++) {
            System.out.println("Lantai " + (l + 1));
            for (int r = 0; r < JML_RAK; r++) {
                System.out.print("  Rak " + (r + 1) + " : ");
                for (int s = 0; s < JML_SLOT; s++) {
                    Buku b = denah[l][r][s];
                    System.out.print("[" + (b == null ? "-----" : b.getKode()) + "] ");
                }
                System.out.println();
            }
        }
    }

    public String cariLokasi(String kode) {
        Buku b = cariBuku(kode);
        if (b == null) {
            return "Buku dengan kode " + kode + " tidak ditemukan.";
        }
        return "'" + b.getJudul() + "' ada di " + b.lokasiTeks();
    }

    // ---------- ArrayList ----------

    public void tampilkanKatalog() {
        if (katalog.isEmpty()) {
            System.out.println("Katalog kosong.");
            return;
        }
        for (int i = 0; i < katalog.size(); i++) {
            Buku b = katalog.get(i);
            System.out.println((i + 1) + ". [" + b.getKode() + "] " + b.getJudul()
                    + " | " + kategori[b.getKategori()]
                    + " | " + b.lokasiTeks()
                    + " | " + (b.isTersedia() ? "Tersedia" : "Dipinjam"));
        }
    }

    public String tambahBuku(String kode, String judul, int nomorKategori) {
        int kat = nomorKategori - 1;
        if (kode.isEmpty() || judul.isEmpty()) {
            return "Kode dan judul tidak boleh kosong.";
        }
        if (kat < 0 || kat >= kategori.length) {
            return "Nomor kategori tidak valid.";
        }
        if (cariBuku(kode) != null) {
            return "Kode " + kode + " sudah dipakai buku lain.";
        }
        int[] pos = cariSlotKosong();
        if (pos == null) {
            return "Rak sudah penuh, buku tidak dapat ditambahkan.";
        }
        Buku b = new Buku(kode, judul, kat, pos[0], pos[1], pos[2]);
        katalog.add(b);
        denah[pos[0]][pos[1]][pos[2]] = b;

        String ket = "Menambah buku [" + kode + "] " + judul;
        riwayat.push(new Transaksi(Transaksi.Jenis.TAMBAH_BUKU, b, null, -1, -1, ket));
        return "Buku ditambahkan dan otomatis ditaruh di " + b.lokasiTeks() + ".";
    }

    public String hapusBuku(String kode) {
        Buku b = cariBuku(kode);
        if (b == null) {
            return "Buku tidak ditemukan.";
        }
        if (!b.isTersedia()) {
            return "Buku sedang dipinjam, tidak dapat dihapus.";
        }
        int indeks = katalog.indexOf(b);
        katalog.remove(indeks);
        denah[b.getLantai()][b.getRak()][b.getSlot()] = null;

        String ket = "Menghapus buku [" + b.getKode() + "] " + b.getJudul();
        riwayat.push(new Transaksi(Transaksi.Jenis.HAPUS_BUKU, b, null, -1, indeks, ket));
        return "Buku dihapus dan slot rak dikosongkan.";
    }

    // ---------- Queue ----------

    public void tampilkanAntrean() {
        if (antrean.isEmpty()) {
            System.out.println("Antrean kosong.");
            return;
        }
        int no = 1;
        for (String nama : antrean) {
            System.out.println(no + ". " + nama + (no == 1 ? "  <- giliran berikutnya" : ""));
            no++;
        }
    }

    public String masukAntrean(String nama) {
        if (nama.isEmpty()) {
            return "Nama tidak boleh kosong.";
        }
        antrean.offer(nama);
        return nama + " masuk antrean di urutan ke-" + antrean.size() + ".";
    }

    // Integrasi: Queue + ArrayList + Array 2D + Stack
    public String prosesPeminjaman(String kode, int nomorHari) {
        int h = nomorHari - 1;
        if (antrean.isEmpty()) {
            return "Antrean kosong, tidak ada yang dilayani.";
        }
        Buku b = cariBuku(kode);
        if (b == null) {
            return "Buku tidak ditemukan.";
        }
        if (!b.isTersedia()) {
            return "Buku sedang dipinjam orang lain.";
        }
        if (h < 0 || h >= namaHari.length) {
            return "Nomor hari tidak valid.";
        }
        String mhs = antrean.poll();
        b.setTersedia(false);
        rekap[b.getKategori()][h]++;

        String ket = mhs + " meminjam [" + b.getKode() + "] " + b.getJudul() + " (" + namaHari[h] + ")";
        riwayat.push(new Transaksi(Transaksi.Jenis.PINJAM, b, mhs, h, -1, ket));
        return ket;
    }

    public void tampilkanHari() {
        for (int i = 0; i < namaHari.length; i++) {
            System.out.println((i + 1) + ". " + namaHari[i]);
        }
    }

    // ---------- Stack ----------

    public void tampilkanRiwayat() {
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        for (int i = riwayat.size() - 1; i >= 0; i--) {
            System.out.println("- " + riwayat.get(i).getKeterangan());
        }
    }

    public String lihatTransaksiTerakhir() {
        if (riwayat.isEmpty()) {
            return "Belum ada transaksi.";
        }
        return riwayat.peek().getKeterangan();
    }

    public String batalkanTransaksiTerakhir() {
        if (riwayat.isEmpty()) {
            return "Tidak ada transaksi yang dapat dibatalkan.";
        }
        Transaksi t = riwayat.pop();
        Buku b = t.getBuku();

        switch (t.getJenis()) {
            case TAMBAH_BUKU:
                katalog.remove(b);
                denah[b.getLantai()][b.getRak()][b.getSlot()] = null;
                break;
            case HAPUS_BUKU:
                katalog.add(t.getIndeksKatalog(), b);
                denah[b.getLantai()][b.getRak()][b.getSlot()] = b;
                break;
            case PINJAM:
                b.setTersedia(true);
                rekap[b.getKategori()][t.getHari()]--;
                antrean.addFirst(t.getPeminjam());
                break;
        }
        return "Dibatalkan: " + t.getKeterangan();
    }
}