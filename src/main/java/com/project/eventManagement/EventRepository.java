package com.project.eventManagement;

import org.springframework.data.jpa.repository.JpaRepository;

// the data in the db will be of type Event and the primary key is of type Long
// 
public interface EventRepository extends JpaRepository<Event, Long> {

}
