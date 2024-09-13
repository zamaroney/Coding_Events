package com.example.codeevents.data;

import com.example.codeevents.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTagRepository extends CrudRepository<Tag, Integer> {

}
