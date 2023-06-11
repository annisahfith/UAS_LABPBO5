/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package uaslabpbo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Pasien {
    protected String nama;
    protected int umur;
    protected String penyakit;
    protected LocalDate tgladministrasi;
    protected double biayaPerHari;

    public Pasien(String name, int age, String illness, LocalDate admissionDate, double treatmentCost) {
        this.nama = name;
        this.umur = age;
        this.penyakit = illness;
        this.tgladministrasi = admissionDate;
        this.biayaPerHari = treatmentCost;
    }

    public void printPatientInfo(LocalDate currentDate) {
        System.out.println("Nama: " + nama);
        System.out.println("Usia: " + umur);
        System.out.println("Penyakit: " + penyakit);
        System.out.println("Lama Perawatan: " + getDaysInHospital(currentDate) + " hari");
        System.out.println("Biaya Perawatan: " + calculateTreatmentCost(currentDate));
    }

    public int getDaysInHospital(LocalDate currentDate) {
        return (int) ChronoUnit.DAYS.between(tgladministrasi, currentDate) + 1;
    }

    public double calculateTreatmentCost(LocalDate currentDate) {
        int daysInHospital = getDaysInHospital(currentDate);
        return daysInHospital * biayaPerHari;
    }
}

class Inpatient extends Pasien {
    private String Nokamar;

    public Inpatient(String name, int age, String illness, LocalDate admissionDate, String Nokamar, double treatmentCost) {
        super(name, age, illness, admissionDate, treatmentCost);
        this.Nokamar = Nokamar;
    }

    @Override
    public void printPatientInfo(LocalDate currentDate) {
        super.printPatientInfo(currentDate);
        System.out.println("Nomor Kamar: " + Nokamar);
        System.out.println("Status: Rawat Inap");
    }

    public String getRoomNumber() {
        return Nokamar;
    }
}

class Outpatient extends Pasien {
    private String Namadokter;
    private int registrationNumber;

    public Outpatient(String name, int age, String illness, LocalDate admissionDate, String doctorName, int registrationNumber, double treatmentCost) {
        super(name, age, illness, admissionDate, treatmentCost);
        this.Namadokter = doctorName;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public void printPatientInfo(LocalDate currentDate) {
        super.printPatientInfo(currentDate);
        System.out.println("Dokter: " + Namadokter);
        System.out.println("Nomor Pendaftaran: " + registrationNumber);
        System.out.println("Status: Rawat Jalan");
    }

    public String getDoctorName() {
        return Namadokter;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }
}

public class HospitalManagementSystem {
    private List<Pasien> patients;
    private List<Pasien> dischargedInpatients;
    private List<Pasien> dischargedOutpatients;
    private Map<String, Pasien> inpatientsByRoomNumber;
    private Map<Integer, Pasien> outpatientsByRegistrationNumber;
    private Scanner scanner;

    public HospitalManagementSystem() {
        patients = new ArrayList<>();
        dischargedInpatients = new ArrayList<>();
        dischargedOutpatients = new ArrayList<>();
        inpatientsByRoomNumber = new HashMap<>();
        outpatientsByRegistrationNumber = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addInpatient();
                    break;
                case 2:
                    addOutpatient();
                    break;
                case 3:
                    printAllPatients();
                    break;
                case 4:
                    dischargeInpatient();
                    break;
                case 5:
                    dischargeOutpatient();
                    break;
                case 6:
                    printDischargedPatients();
                    break;
                case 7:
                    isRunning = false;
                    System.out.println("Terimakasih Telah Menggunakan Layanan Ini, Stay Healthy and Happy !");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Sistem Manajemen Rumah Sakit ===");
        System.out.println("--------------------------------------");
        System.out.println("1. Tambahkan Pasien Rawat Inap");
        System.out.println("2. Tambahkan Pasien Rawat Jalan");
        System.out.println("3. Cetak Daftar Pasien");
        System.out.println("4. Pemulangan Pasien Rawat Inap");
        System.out.println("5. Pemulangan Pasien Rawat Jalan");
        System.out.println("6. Cetak Daftar Pasien yang Sudah Pulang");
        System.out.println("7. Keluar");
        System.out.print("Pilih menu: ");
    }

    private void addInpatient() {
        System.out.println("\n--- Tambahkan Pasien Rawat Inap ---");
        System.out.print("Nama: ");
        String name = scanner.nextLine();
        System.out.print("Usia: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Penyakit: ");
        String illness = scanner.nextLine();
        System.out.print("Tanggal Administrasi (yyyy-MM-dd): ");
        String admissionDateStr = scanner.nextLine();
        System.out.print("Nomor Kamar: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Biaya Per Hari: ");
        double treatmentCost = scanner.nextDouble();

        LocalDate admissionDate = LocalDate.parse(admissionDateStr, DateTimeFormatter.ISO_DATE);
        Inpatient inpatient = new Inpatient(name, age, illness, admissionDate, roomNumber, treatmentCost);
        patients.add(inpatient);
        inpatientsByRoomNumber.put(roomNumber, inpatient);

        System.out.println("Pasien Rawat Inap berhasil ditambahkan.");
    }

    private void addOutpatient() {
        System.out.println("\n--- Tambahkan Pasien Rawat Jalan ---");
        System.out.print("Nama: ");
        String name = scanner.nextLine();
        System.out.print("Usia: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
        System.out.print("Penyakit: ");
        String illness = scanner.nextLine();
        System.out.print("Tanggal Administrasi (yyyy-MM-dd): ");
        String admissionDateStr = scanner.nextLine();
        System.out.print("Nama Dokter: ");
        String doctorName = scanner.nextLine();
        System.out.print("Nomor Pendaftaran: ");
        int registrationNumber = scanner.nextInt();
        System.out.print("Biaya Per Hari: ");
        double treatmentCost = scanner.nextDouble();

        LocalDate admissionDate = LocalDate.parse(admissionDateStr, DateTimeFormatter.ISO_DATE);
        Outpatient outpatient = new Outpatient(name, age, illness, admissionDate, doctorName, registrationNumber, treatmentCost);
        patients.add(outpatient);
        outpatientsByRegistrationNumber.put(registrationNumber, outpatient);

        System.out.println("Pasien Rawat Jalan berhasil ditambahkan.");
    }

    private void printAllPatients() {
        System.out.println("\n--- Daftar Pasien ---");
        for (Pasien patient : patients) {
            patient.printPatientInfo(LocalDate.now());
            System.out.println("--------------------");
        }
    }

    private void dischargeInpatient() {
        System.out.println("\n--- Pemulangan Pasien Rawat Inap ---");
        System.out.print("No. Kamar: ");
        String roomNumber = scanner.nextLine();

        if (inpatientsByRoomNumber.containsKey(roomNumber)) {
            Inpatient dischargedInpatient = (Inpatient) inpatientsByRoomNumber.get(roomNumber);
            dischargedInpatients.add(dischargedInpatient);
            patients.remove(dischargedInpatient);
            inpatientsByRoomNumber.remove(roomNumber);
            System.out.println("Pasien Rawat Inap dengan No. Kamar " + roomNumber + " telah dipulangkan.");
        } else {
            System.out.println("Pasien Rawat Inap dengan No. Kamar " + roomNumber + " tidak ditemukan.");
        }
    }

    private void dischargeOutpatient() {
        System.out.println("\n--- Pemulangan Pasien Rawat Jalan ---");
        System.out.print("No. Pendaftaran : ");
        int registrationNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (outpatientsByRegistrationNumber.containsKey(registrationNumber)) {
            Outpatient dischargedOutpatient = (Outpatient) outpatientsByRegistrationNumber.get(registrationNumber);
            dischargedOutpatients.add(dischargedOutpatient);
            patients.remove(dischargedOutpatient);
            outpatientsByRegistrationNumber.remove(registrationNumber);
            System.out.println("Pasien Rawat Jalan dengan No. Pendaftaran " + registrationNumber + " telah dipulangkan.");
        } else {
            System.out.println("Pasien Rawat Jalan dengan No. Pendaftaran " + registrationNumber + " tidak ditemukan.");
        }
    }

    private void printDischargedPatients() {
        System.out.println("\n--- Daftar Pasien yang Sudah Pulang ---");
        System.out.println("--- Pasien Rawat Inap ---");
        for (Pasien dischargedInpatient : dischargedInpatients) {
            dischargedInpatient.printPatientInfo(LocalDate.now());
            System.out.println("--------------------");
        }

        System.out.println("--- Pasien Rawat Jalan ---");
        for (Pasien dischargedOutpatient : dischargedOutpatients) {
            dischargedOutpatient.printPatientInfo(LocalDate.now());
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {
        HospitalManagementSystem hospital = new HospitalManagementSystem();
        hospital.run();
    }
}




