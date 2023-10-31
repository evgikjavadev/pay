package ru.vtb.msa.rfrm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.processingDatabase.enumentity.EntityDctStatusDetails;

import java.util.List;

public interface DctStatusDetailsRepo extends CrudRepository<EntityDctStatusDetails, Long>  {

    @Query("SELECT * FROM dct_status_details")
    List<DctStatusDetails> findAllRecordsFromDB();
}
