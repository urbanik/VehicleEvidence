package com.urbanik.mapper;

import com.urbanik.dto.VehicleVINHintDto;
import com.urbanik.entity.VehicleVINHint;

public class VehicleVINHintMapper implements BaseMapper<VehicleVINHint, VehicleVINHintDto> {

    AddressMapper addressMapper = new AddressMapper();
    @Override
    public VehicleVINHintDto entityToDto(VehicleVINHint entity) {
        VehicleVINHintDto dto = new VehicleVINHintDto();
        dto.setAddress(addressMapper.entityToDto(entity.getAddress()));
        dto.setVin(entity.getVin());
        return dto;
    }

    @Override
    public VehicleVINHint dtoToEntity(VehicleVINHintDto dto) {
        VehicleVINHint entity = new VehicleVINHint();
        entity.setAddress(addressMapper.dtoToEntity(dto.getAddress()));
        entity.setVin(dto.getVin());
        return entity;
    }
}
