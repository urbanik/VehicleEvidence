package com.urbanik.mapper;

import com.urbanik.dto.AddressAndIndexOfRecordInBlockDto;
import com.urbanik.dto.VehicleVINHintDto;
import com.urbanik.entity.AddressAndIndexOfRecordInBlock;
import com.urbanik.entity.VehicleVINHint;

public class AddressMapper implements BaseMapper<AddressAndIndexOfRecordInBlock, AddressAndIndexOfRecordInBlockDto> {
    @Override
    public AddressAndIndexOfRecordInBlockDto entityToDto(AddressAndIndexOfRecordInBlock entity) {
        AddressAndIndexOfRecordInBlockDto dto = new AddressAndIndexOfRecordInBlockDto();
        dto.setAddress(entity.getAddress());
        dto.setIndex(entity.getIndex());
        return dto;
    }

    @Override
    public AddressAndIndexOfRecordInBlock dtoToEntity(AddressAndIndexOfRecordInBlockDto dto) {
        AddressAndIndexOfRecordInBlock entity = new AddressAndIndexOfRecordInBlock();
        entity.setAddress(dto.getAddress());
        entity.setIndex(dto.getIndex());
        return entity;

    }
}
