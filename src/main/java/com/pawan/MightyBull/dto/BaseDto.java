package com.pawan.MightyBull.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Pawan Saini
 * Created on 02/11/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto implements Serializable {

    private Long id;
    private Date createdTime;
    private Date updatedTime;
}
