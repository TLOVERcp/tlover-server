package com.example.tlover.domain.region.exception;

import com.example.tlover.domain.thema.exception.ThemaException;
import com.example.tlover.domain.thema.exception.ThemaExceptionList;

public class NotFoundRegionNameException extends RegionException {
    public NotFoundRegionNameException() {
        super(RegionExceptionList.NOT_FOUND_REGION_NAME.getCODE(),
                RegionExceptionList.NOT_FOUND_REGION_NAME.getHttpStatus(),
                RegionExceptionList.NOT_FOUND_REGION_NAME.getMESSAGE()
        );
    }
}
