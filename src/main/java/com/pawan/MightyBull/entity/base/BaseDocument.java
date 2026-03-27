package com.pawan.MightyBull.entity.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseDocument<T extends Serializable> {

    @Id
    protected String id;

    private T sqlId;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;
}
