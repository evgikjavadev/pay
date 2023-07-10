package ru.vtb.msa.rfrm.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseRepository extends JpaRepository<AccountsDatabaseEntity, Long> {
    @Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
    AccountsDatabaseEntity retrieveByName(@Param("name") String name);
}
