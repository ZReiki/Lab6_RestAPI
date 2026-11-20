package ua.edu.nuos.lab6_restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.nuos.lab6_restapi.model.Watch;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Long> {}
