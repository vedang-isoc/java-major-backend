package com.example.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Category;

public interface Categoryrepo extends JpaRepository<Category, Integer> {

}
