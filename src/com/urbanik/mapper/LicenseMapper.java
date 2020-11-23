package com.urbanik.mapper;

import com.urbanik.dto.LicenseDto;
import com.urbanik.entity.License;

public class LicenseMapper implements BaseMapper<License, LicenseDto>{

    public LicenseMapper() {
    }

    @Override
    public LicenseDto entityToDto(License entity) {
        LicenseDto licenseDto = new LicenseDto();
        licenseDto.setDriverName(entity.getDriverName());
        licenseDto.setDriverSurname(entity.getDriverSurname());
        licenseDto.setLicenseNumber(entity.getLicenseNumber());
        licenseDto.setLicenseValidityUntil(entity.getLicenseValidityUntil());
        licenseDto.setProhibited(entity.isProhibited());
        licenseDto.setNumberOfViolationsInLast12Months(entity.getNumberOfViolationsInLast12Months());
        return licenseDto;
    }

    @Override
    public License dtoToEntity(LicenseDto dto) {
        License license = new License(dto.getDriverName(), dto.getDriverSurname(), dto.getLicenseNumber(), dto.getLicenseValidityUntil(), dto.isProhibited(), dto.getNumberOfViolationsInLast12Months());
        return license;
    }
}
