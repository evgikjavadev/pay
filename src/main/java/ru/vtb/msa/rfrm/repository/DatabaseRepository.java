//package ru.vtb.msa.rfrm.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface DatabaseRepository extends JpaRepository<AccountsDatabaseEntity, Long> {
//    @Query("UPDATE AccountsDatabaseEntity f SET f.paymentTask f WHERE LOWER(f.name) = LOWER(:name)")
//    AccountsDatabaseEntity retrieveByName(@Param("name") String name);
//}
