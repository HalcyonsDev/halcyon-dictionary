package com.halcyon.blog.service;

import com.halcyon.blog.entity.Package;

import java.util.List;

public interface IPackageService {
    List<Package> findAllPackages();
    Package findPackageById(int id);
    Package save(Package wordsPackage);
    void deletePackageById(int id);
}
