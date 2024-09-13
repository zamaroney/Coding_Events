package com.example.codeevents.data;

import com.example.codeevents.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Repository = DAO
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {
}
