package com.urbanik.mapper;

import com.urbanik.dto.BlockDto;
import com.urbanik.dto.LicenseDto;
import com.urbanik.dto.RecordDto;
import com.urbanik.entity.Block;

public class BlockMapper implements BaseMapper<Block, BlockDto> {

    @Override
    public BlockDto entityToDto(Block block) {
        BlockDto blockDto = new BlockDto(block.getNumberOfRecords() + 1, new LicenseDto());
        blockDto.setAddress(block.getAddress());
        blockDto.setFatherAddress(block.getFatherAddress());
        for (int i = 0; i < block.getNumberOfRecords(); i++) {
            BaseMapper mapper = block.getTypeRecord().getMapper();
            blockDto.getRecords()[i] = (RecordDto) mapper.entityToDto(block.getRecords()[i]);
        }
        return blockDto;
    }

    @Override
    public Block dtoToEntity(BlockDto dto) {
        return null;
    }
}
