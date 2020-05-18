package org.javaboy.vhr.serve;

import org.javaboy.vhr.mapper.NationMapper;
import org.javaboy.vhr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName NationService
 * @Author 周某
 * @Date 2020-05-15 11:39
 **/
@Service
public class NationService {

    @Autowired
    NationMapper nationMapper;


    public List<Nation> getAllNations() {
        return nationMapper.getAllNations();
    }
}
