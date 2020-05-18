package org.javaboy.vhr.serve;

import org.javaboy.vhr.mapper.PoliticsstatusMapper;
import org.javaboy.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PoliticsstatusService
 * @Author 周某
 * @Date 2020-05-15 11:40
 **/
@Service
public class PoliticsstatusService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
