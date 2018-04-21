package com.ulrik.provatecnicamobile.repository;

import com.ulrik.provatecnicamobile.model.Resource;

import java.util.List;

public interface ResourceRepository<T extends Resource> {
    List<T> getResources();
}
