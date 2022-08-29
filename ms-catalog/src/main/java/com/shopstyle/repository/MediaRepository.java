package com.shopstyle.repository;

import com.shopstyle.domain.model.Category;
import com.shopstyle.domain.model.Media;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MediaRepository implements PanacheRepository<Media> {
}