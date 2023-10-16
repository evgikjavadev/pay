package ru.vtb.msa.rfrm.functions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FunctionPCImpl implements FunctionPC {

    @Override
    public void startProcess() {
        log.info("Старт функции ПС. Передача события о ручной смене статуса задания на оплату");  //todo

    }
}
