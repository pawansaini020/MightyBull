package com.pawan.MightyBull.dao;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * @author Pawan Saini
 * Created on 01/11/24.
 */
public interface Dao<T, ID> {

    Optional<T> get(@NonNull ID id);

    Optional<T> getByNseScriptCode(@NonNull String evenId);

    List<T> getAll();

    T save(@NonNull T t);

    List<T> saveAll(@NonNull List<T> tList);
}
