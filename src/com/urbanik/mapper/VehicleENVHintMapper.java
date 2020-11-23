package com.urbanik.mapper;

import com.urbanik.dto.VehicleENHintDto;
import com.urbanik.entity.VehicleENHint;

public class VehicleENVHintMapper implements BaseMapper<VehicleENHint, VehicleENHintDto> {
    AddressMapper addressMapper = new AddressMapper();

    @Override
    public VehicleENHintDto entityToDto(VehicleENHint entity) {
        VehicleENHintDto dto = new VehicleENHintDto();
        dto.setAddress(addressMapper.entityToDto(entity.getAddress()));
        dto.setEvidenceNumber(entity.getEvidenceNumber());
        return dto;
    }

    @Override
    public VehicleENHint dtoToEntity(VehicleENHintDto dto) {
        VehicleENHint entity = new VehicleENHint();
        entity.setAddress(addressMapper.dtoToEntity(dto.getAddress()));
        entity.setEvidenceNumber(dto.getEvidenceNumber());
        return entity;
    }
}
