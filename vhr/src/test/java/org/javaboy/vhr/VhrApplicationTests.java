package org.javaboy.vhr;

import org.javaboy.vhr.mapper.PositionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VhrApplicationTests {

    @Autowired
    PositionMapper mapper;
    @Test
    void contextLoads() {
    }

}
