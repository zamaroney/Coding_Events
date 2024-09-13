package com.example.codeevents.data;

import com.example.codeevents.models.EventCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EventCategoryRepository extends CrudRepository<EventCategory, Integer> {
}
