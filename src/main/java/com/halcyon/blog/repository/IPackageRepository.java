package com.halcyon.blog.repository;

import com.halcyon.blog.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPackageRepository extends JpaRepository<Package, Integer> {
}
