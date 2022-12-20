package de.bugtracker.dao;

import de.bugtracker.domainobject.BugDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface BugTrackerRepository extends JpaRepository<BugDO, Long> {

  List<BugDO> findAllByCreatedAfter(ZonedDateTime endDate);
}
