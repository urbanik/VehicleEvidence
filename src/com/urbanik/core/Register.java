package com.urbanik.core;

import com.urbanik.dto.BlockDto;
import com.urbanik.dto.LicenseDto;
import com.urbanik.dto.VehicleDto;
import com.urbanik.entity.*;
import com.urbanik.generator.LicenseGenerator;
import com.urbanik.generator.VehicleGenerator;
import com.urbanik.mapper.BaseMapper;
import com.urbanik.mapper.BlockMapper;
import com.urbanik.mapper.LicenseMapper;
import com.urbanik.mapper.VehicleMapper;
import com.urbanik.structure.BTree;
import com.urbanik.structure.HeapFile;
import com.urbanik.test.BTreeTest;

import java.io.*;
import java.util.ArrayList;

public class Register {

    private static Register single_instance;
    private LicenseMapper licenseMapper;
    private VehicleMapper vehicleMapper;
    private BTree licenses;
    private BTree vehiclesByVin;
    private BTree vehiclesByEN;
    private HeapFile vehicles;

    private int licensesClusterSize;
    private int vehiclesByVinClusterSize;
    private int vehiclesByENClusterSize;
    private int vehiclesClusterSize;

    private LicenseGenerator licenseGenerator;
    private VehicleGenerator vehicleGenerator;

    public Register (int licensesClusterSize, int vehiclesByVinClusterSize, int vehiclesByENClusterSize,int vehiclesClusterSize) {

        this.licensesClusterSize = licensesClusterSize;
        this.vehiclesByVinClusterSize = vehiclesByVinClusterSize;
        this.vehiclesByENClusterSize = vehiclesByENClusterSize;
        this.vehiclesClusterSize = vehiclesByVinClusterSize;

        this.licenses = new BTree(new License(), "licenses", licensesClusterSize);
        this.vehiclesByVin = new BTree(new VehicleVINHint(), "vehiclesByVin", vehiclesByVinClusterSize);
        this.vehiclesByEN = new BTree(new VehicleENHint(), "vehiclesByEN", vehiclesByENClusterSize);
        this.vehicles = new HeapFile( "vehicles", new Vehicle(), vehiclesClusterSize);

        licenseMapper = new LicenseMapper();
        vehicleMapper = new VehicleMapper();

    }

    public static Register getInstance(int licensesClusterSize, int vehiclesByVinClusterSize, int vehiclesByENClusterSize,int vehiclesClusterSize){

        if(single_instance == null){
            single_instance = new Register(licensesClusterSize, vehiclesByVinClusterSize, vehiclesByENClusterSize, vehiclesClusterSize);
        }
        return single_instance;
    }

    public ArrayList<BlockDto> testBTree() {
        BTreeTest bTreeTest = new BTreeTest();
        //return entityBlocksToDtoBlocks(bTreeTest.getbTree().getBlocksByLevel());
        return entityBlocksToDtoBlocks(bTreeTest.getbTree().getBlocks());
    }

    public ArrayList<BlockDto> entityBlocksToDtoBlocks(ArrayList<Block> entityBlocks){
        ArrayList<BlockDto> blockDtos = new ArrayList<>();
        BaseMapper mapper = new BlockMapper();
        for (Block block : entityBlocks) {
            blockDtos.add((BlockDto) mapper.entityToDto(block));
        }
        return blockDtos;
    }

    public void addLicense(LicenseDto dto) {
        this.licenses.insert(licenseMapper.dtoToEntity(dto));
    }

    public LicenseDto findLicense(LicenseDto dto) {
        License license = licenseMapper.dtoToEntity(dto);
        License license2 = (License) licenses.find(license);
        return licenseMapper.entityToDto(license2);
    }

    public LicenseDto updateLicense(LicenseDto dto) {
        License license = licenseMapper.dtoToEntity(dto);
        License license2 = (License) licenses.update(license);
        if(license2 != null){
            return licenseMapper.entityToDto(license2);
        } else {
            return null;
        }


    }

    public ArrayList<BlockDto> getLicensesBlocks() {
        return entityBlocksToDtoBlocks(licenses.getBlocks());
    }

    public void addVehicle(VehicleDto dto) {
        AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock = this.vehicles.insert(vehicleMapper.dtoToEntity(dto));
        VehicleENHint vehicleENHint = new VehicleENHint(addressAndIndexOfRecordInBlock, dto.getEvidenceNumber());
        VehicleVINHint vehicleVINHint = new VehicleVINHint(addressAndIndexOfRecordInBlock, dto.getVIN());
        this.vehiclesByEN.insert(vehicleENHint);
        this.vehiclesByVin.insert(vehicleVINHint);
    }

    public VehicleDto findVehicleByEN(VehicleDto dto) {
        VehicleENHint vehicleENHint = new VehicleENHint(dto.getEvidenceNumber());
        VehicleENHint found = (VehicleENHint) this.vehiclesByEN.find(vehicleENHint);
        AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock = found.getAddress();
        VehicleDto vehicleDto = vehicleMapper.entityToDto((Vehicle) this.vehicles.find(addressAndIndexOfRecordInBlock));
        return vehicleDto;
    }


    public VehicleDto findVehicleByVIN(VehicleDto dto) {
        VehicleVINHint vehicleVINHint = new VehicleVINHint(dto.getVIN());
        VehicleVINHint found = (VehicleVINHint) this.vehiclesByVin.find(vehicleVINHint);
        AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock = found.getAddress();
        VehicleDto vehicleDto = vehicleMapper.entityToDto((Vehicle) this.vehicles.find(addressAndIndexOfRecordInBlock));
        return vehicleDto;
    }

    public VehicleDto updateVehicle(VehicleDto dto) {
        VehicleENHint vehicleENHint = new VehicleENHint(dto.getEvidenceNumber());
        VehicleENHint found = (VehicleENHint) this.vehiclesByEN.find(vehicleENHint);
        AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock = found.getAddress();

        VehicleDto vehicleDto = vehicleMapper.entityToDto((Vehicle) this.vehicles.update(vehicleMapper.dtoToEntity(dto), addressAndIndexOfRecordInBlock));
        return vehicleDto;

    }

    public ArrayList<BlockDto> getVehiclesBlocks() {
        return entityBlocksToDtoBlocks(vehicles.getBlocks());
    }

    public ArrayList<BlockDto> getVehicelsByEnBlocks() {
        return entityBlocksToDtoBlocks(vehiclesByEN.getBlocks());
    }

    public ArrayList<BlockDto> getVehiclesByVinBlocks() {
        return entityBlocksToDtoBlocks(vehiclesByVin.getBlocks());
    }

    public void generateLicenses(int n) {
        licenseGenerator = new LicenseGenerator(n);
        for(int i = 0; i < n; i++){
            License license = (License) licenseGenerator.getRecords()[i];
            this.licenses.insert(license);
        }
    }

    public void generateVehicles(int n) {
        vehicleGenerator = new VehicleGenerator(n);

        for(int i = 0; i < n; i++){
            Vehicle vehicle = (Vehicle) vehicleGenerator.getRecords()[i];
            AddressAndIndexOfRecordInBlock addressAndIndexOfRecordInBlock = this.vehicles.insert(vehicle);
            VehicleENHint vehicleENHint = new VehicleENHint(addressAndIndexOfRecordInBlock, vehicle.getEvidenceNumber());
            VehicleVINHint vehicleVINHint = new VehicleVINHint(addressAndIndexOfRecordInBlock, vehicle.getVIN());
            this.vehiclesByEN.insert(vehicleENHint);
            this.vehiclesByVin.insert(vehicleVINHint);

        }

    }

    public void saveToFile() {
        FileWriter fw = null;
        try {
            fw = new FileWriter("data.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(this.licenses.getFileName() + "," + this.licenses.getNumberOfBlocks() + "," + this.licenses.getRootAddress() + "," + this.licenses.getHeight() + "\n");
            bw.write(this.vehiclesByVin.getFileName() + "," + this.vehiclesByVin.getNumberOfBlocks() + "," + this.vehiclesByVin.getRootAddress() + "," + this.vehiclesByVin.getHeight() + "\n");
            bw.write(this.vehiclesByEN.getFileName() + "," + this.vehiclesByEN.getNumberOfBlocks() + "," + this.vehiclesByEN.getRootAddress() + "," + this.vehiclesByEN.getHeight() + "\n");
            bw.write(this.vehicles.getFileName() + "," + this.vehicles.getNumberOfBlocks());

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadFromFile() {
        FileReader reader = null;
        try {
            reader = new FileReader("data.txt");
            BufferedReader br = new BufferedReader(reader);
            String licenses = br.readLine();
            String licensesString[] = licenses.split(",");
            this.licenses.setFileName(licensesString[0]);
            this.licenses.setNumberOfBlocks(Integer.parseInt(licensesString[1]));
            this.licenses.setRootAddress(Integer.parseInt(licensesString[2]));
            this.licenses.setHeight(Integer.parseInt(licensesString[3]));
            this.licenses.setRecordType(new License());
            this.licenses.setTmp(new Block(new License(), licensesClusterSize));

            String vbbin = br.readLine();
            String vbbinString[] = vbbin.split(",");
            this.vehiclesByVin.setFileName(vbbinString[0]);
            this.vehiclesByVin.setNumberOfBlocks(Integer.parseInt(vbbinString[1]));
            this.vehiclesByVin.setRootAddress(Integer.parseInt(vbbinString[2]));
            this.vehiclesByVin.setHeight(Integer.parseInt(vbbinString[3]));
            this.vehiclesByVin.setRecordType(new VehicleVINHint());
            this.vehiclesByVin.setTmp(new Block(new VehicleVINHint(), vehiclesByVinClusterSize));

            String vben = br.readLine();
            String vbenString[] = vben.split(",");
            this.vehiclesByEN.setFileName(vbenString[0]);
            this.vehiclesByEN.setNumberOfBlocks(Integer.parseInt(vbenString[1]));
            this.vehiclesByEN.setRootAddress(Integer.parseInt(vbenString[2]));
            this.vehiclesByEN.setHeight(Integer.parseInt(vbenString[3]));
            this.vehiclesByEN.setRecordType(new VehicleENHint());
            this.vehiclesByEN.setTmp(new Block(new VehicleENHint(), vehiclesByENClusterSize));

            String vehicles = br.readLine();
            String vehiclesString[] = vehicles.split(",");
            this.vehicles.setFileName(vehiclesString[0]);
            this.vehicles.setNumberOfBlocks(Integer.parseInt(vehiclesString[1]));
            this.vehicles.setRecordType(new Vehicle());

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
