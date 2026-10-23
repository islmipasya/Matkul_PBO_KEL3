package model;

public class Buku {
    private final String kode;
    private final String judul;
    private final int kategori;   // indeks pada array kategori
    private final int lantai;     // indeks 0-based
    private final int rak;
    private final int slot;
    private boolean tersedia = true;

    public Buku(String kode, String judul, int kategori, int lantai, int rak, int slot) {
        this.kode = kode;
        this.judul = judul;
        this.kategori = kategori;
        this.lantai = lantai;
        this.rak = rak;
        this.slot = slot;
    }

    public String getKode() { return kode; }
    public String getJudul() { return judul; }
    public int getKategori() { return kategori; }
    public int getLantai() { return lantai; }
    public int getRak() { return rak; }
    public int getSlot() { return slot; }
    public boolean isTersedia() { return tersedia; }
    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }

    public String lokasiTeks() {
        return "Lantai " + (lantai + 1) + " / Rak " + (rak + 1) + " / Slot " + (slot + 1);
    }
}