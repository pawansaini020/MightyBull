package com.pawan.MightyBull.utils;

import com.pawan.MightyBull.exception.InternalServerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
public class StockUtils {

    public static String getStockId(Long bseCode, String nseCode) {
        if(StringUtils.isNotBlank(nseCode)) {
            return nseCode;
        } else if(bseCode!=null && bseCode!=0) {
            return String.valueOf(bseCode);
        }
        throw new InternalServerException(String.format("Invalid stock id for bseCode: %s, nseCode: %s", bseCode, nseCode));
    }

    public static Pageable getPageable(int pageNumber, int pageSize, Sort sort) {
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
