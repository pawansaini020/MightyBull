package com.pawan.MightyBull.entity;

import com.pawan.MightyBull.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * @author Pawan Saini
 * Created on 03/01/24.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "constances")
public class ConstanceEntity extends BaseEntity<Long> {

    @Column(name = "key")
    private String key;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "value")
    private Map<String, Object> value;
}
