package com.urbanik.mapper;

import com.urbanik.dto.VehicleDto;
import com.urbanik.entity.Vehicle;

public class VehicleMapper implements BaseMapper<Vehicle, VehicleDto> {
    @Override
    public VehicleDto entityToDto(Vehicle entity) {
        VehicleDto dto = new VehicleDto();
        dto.setEvidenceNumber(entity.getEvidenceNumber());
        dto.setVIN(entity.getVIN());
        dto.setNumberOfWheelAxles(entity.getNumberOfWheelAxles());
        dto.setOperatingWeight(entity.getOperatingWeight());
        dto.setWanted(entity.isWanted());
        dto.setSTKValidityUntil(entity.getSTKValidityUntil());
        dto.setEKValidityUntil(entity.getEKValidityUntil());
        return dto;
    }

    @Override
    public Vehicle dtoToEntity(VehicleDto dto) {
        Vehicle entity = new Vehicle();
        entity.setEvidenceNumber(dto.getEvidenceNumber());
        entity.setVIN(dto.getVIN());

        entity.setNumberOfWheelAxles(dto.getNumberOfWheelAxles());
        entity.setOperatingWeight(dto.getOperatingWeight());
        entity.setWanted(dto.isWanted());
        entity.setSTKValidityUntil(dto.getSTKValidityUntil());
        entity.setEKValidityUntil(dto.getEKValidityUntil());
        return entity;
    }
}
