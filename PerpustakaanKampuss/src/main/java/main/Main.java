package main;

import model.LayananPerpustakaan;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final LayananPerpustakaan layanan = new LayananPerpustakaan();

    public static void main(String[] args) {
        int pilihan;
        do {
            tampilkanMenuUtama();
            pilihan = bacaAngka("Pilih menu (0-5): ");
            switch (pilihan) {
                case 1:
                    menuKategoriDanRekap();
                    break;
                case 2:
                    menuRak();
                    break;
                case 3:
                    menuKatalog();
                    break;
                case 4:
                    menuAntrean();
                    break;
                case 5:
                    menuRiwayat();
                    break;
                case 0:
                    System.out.println("Program selesai. Terima kasih.");
                    break;
                default:
                    System.out.println("Menu tidak dikenal.");
            }
        } while (pilihan != 0);
    }

    // ---------- Menu utama ----------

    private static void tampilkanMenuUtama() {
        System.out.println("\n========== PERPUSTAKAAN KAMPUS ==========");
        System.out.println("1. Kategori & Rekap Peminjaman  (Array 1D & 2D)");
        System.out.println("2. Rak Buku                     (Array 3D)");
        System.out.println("3. Katalog Buku                 (ArrayList)");
        System.out.println("4. Antrean & Peminjaman         (Queue)");
        System.out.println("5. Riwayat Transaksi            (Stack)");
        System.out.println("0. Keluar");
        System.out.println("=========================================");
    }

    // ---------- Submenu ----------

    private static void menuKategoriDanRekap() {
        int aksi;
        do {
            System.out.println("\n--- KATEGORI & REKAP PEMINJAMAN ---");
            System.out.println("1. Lihat daftar kategori");
            System.out.println("2. Lihat rekap peminjaman per hari");
            System.out.println("0. Kembali");
            aksi = bacaAngka("Pilih (0-2): ");
            System.out.println();
            switch (aksi) {
                case 1:
                    layanan.tampilkanKategori();
                    break;
                case 2:
                    layanan.tampilkanRekap();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        } while (aksi != 0);
    }

    private static void menuRak() {
        int aksi;
        do {
            System.out.println("\n--- RAK BUKU ---");
            System.out.println("1. Lihat denah seluruh rak");
            System.out.println("2. Cari lokasi buku");
            System.out.println("0. Kembali");
            aksi = bacaAngka("Pilih (0-2): ");
            System.out.println();
            switch (aksi) {
                case 1:
                    layanan.tampilkanDenah();
                    break;
                case 2:
                    System.out.println(layanan.cariLokasi(bacaTeks("Kode buku: ")));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        } while (aksi != 0);
    }

    private static void menuKatalog() {
        int aksi;
        do {
            System.out.println("\n--- KATALOG BUKU ---");
            System.out.println("1. Lihat katalog");
            System.out.println("2. Tambah buku");
            System.out.println("3. Hapus buku");
            System.out.println("0. Kembali");
            aksi = bacaAngka("Pilih (0-3): ");
            System.out.println();
            switch (aksi) {
                case 1:
                    layanan.tampilkanKatalog();
                    break;
                case 2:
                    String kode = bacaTeks("Kode buku: ");
                    String judul = bacaTeks("Judul buku: ");
                    layanan.tampilkanKategori();
                    int kat = bacaAngka("Nomor kategori: ");
                    System.out.println(layanan.tambahBuku(kode, judul, kat));
                    break;
                case 3:
                    System.out.println(layanan.hapusBuku(bacaTeks("Kode buku yang dihapus: ")));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        } while (aksi != 0);
    }

    private static void menuAntrean() {
        int aksi;
        do {
            System.out.println("\n--- ANTREAN & PEMINJAMAN ---");
            System.out.println("1. Lihat antrean");
            System.out.println("2. Tambah mahasiswa ke antrean");
            System.out.println("3. Layani antrean terdepan & pinjam buku");
            System.out.println("0. Kembali");
            aksi = bacaAngka("Pilih (0-3): ");
            System.out.println();
            switch (aksi) {
                case 1:
                    layanan.tampilkanAntrean();
                    break;
                case 2:
                    System.out.println(layanan.masukAntrean(bacaTeks("Nama & NIM mahasiswa: ")));
                    break;
                case 3:
                    layanan.tampilkanAntrean();
                    String kode = bacaTeks("Kode buku yang dipinjam: ");
                    layanan.tampilkanHari();
                    int hari = bacaAngka("Nomor hari peminjaman: ");
                    System.out.println(layanan.prosesPeminjaman(kode, hari));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        } while (aksi != 0);
    }

    private static void menuRiwayat() {
        int aksi;
        do {
            System.out.println("\n--- RIWAYAT TRANSAKSI ---");
            System.out.println("1. Lihat seluruh riwayat");
            System.out.println("2. Lihat transaksi terakhir");
            System.out.println("3. Batalkan transaksi terakhir");
            System.out.println("0. Kembali");
            aksi = bacaAngka("Pilih (0-3): ");
            System.out.println();
            switch (aksi) {
                case 1:
                    layanan.tampilkanRiwayat();
                    break;
                case 2:
                    System.out.println(layanan.lihatTransaksiTerakhir());
                    break;
                case 3:
                    System.out.println(layanan.batalkanTransaksiTerakhir());
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Pilihan tidak dikenal.");
            }
        } while (aksi != 0);
    }

    // ---------- Pembaca input ----------

    private static String bacaTeks(String pesan) {
        System.out.print(pesan);
        return input.nextLine().trim();
    }

    private static int bacaAngka(String pesan) {
        while (true) {
            System.out.print(pesan);
            String s = input.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }
}