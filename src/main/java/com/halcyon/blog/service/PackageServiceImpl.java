package com.halcyon.blog.service;

import com.halcyon.blog.entity.Package;
import com.halcyon.blog.repository.IPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements IPackageService {
    private IPackageRepository packageRepository;

    @Override
    public List<Package> findAllPackages() {
        return packageRepository.findAll();
    }

    @Override
    public Package findPackageById(int id) {
        Optional<Package> packageOptional = packageRepository.findById(id);
        Package wordsPackage = null;

        if (packageOptional.isPresent()) wordsPackage = packageOptional.get();

        return wordsPackage;
    }

    @Override
    public Package save(Package wordsPackage) {
        return packageRepository.save(wordsPackage);
    }

    @Override
    public void deletePackageById(int id) {
        packageRepository.deleteById(id);
    }

    @Autowired
    public void setPackageRepository(IPackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }
}
