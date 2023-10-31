package ru.vtb.msa.rfrm.processingDatabase;

import org.springframework.stereotype.Component;
import ru.vtb.msa.rfrm.processingDatabase.model.DctStatusDetails;
import ru.vtb.msa.rfrm.repository.DctStatusDetailsRepo;

import java.util.List;

@Component
public class GetEnumsFromDBImpl implements GetEnumsFromDB {
    private final DctStatusDetailsRepo dctStatusDetailsRepo;
    private List<DctStatusDetails> allRecordsFromDB;
    //private Map<Integer, String> mapEnums;

    public GetEnumsFromDBImpl(DctStatusDetailsRepo dctStatusDetailsRepo, List<DctStatusDetails> allRecordsFromDB) {
        this.dctStatusDetailsRepo = dctStatusDetailsRepo;
        this.allRecordsFromDB = allRecordsFromDB;

    }

//    @PostConstruct
//    void init() {
//        getAllDataFromDB();
//    }

//    public List<DctStatusDetails> getAllDataFromDB() {
//        allRecordsFromDB = dctStatusDetailsRepo.findAllRecordsFromDB();
//        return allRecordsFromDB;
//    }

    @Override
    public String getOneRecord(Integer statusDetailsCode) {
        allRecordsFromDB = dctStatusDetailsRepo.findAllRecordsFromDB();

        for (DctStatusDetails elem: allRecordsFromDB) {
            Integer detailsCode = elem.getStatusDetailsCode();
            if (detailsCode.equals(202)) {
                DctStatusDetails.MASTER_ACCOUNT_ARRESTED.setDescription("Продуктовый Профиль ФЛ: Мастер-счет арестован");

                return elem.getDescription();
            }
        }

        return "";
    }
}
