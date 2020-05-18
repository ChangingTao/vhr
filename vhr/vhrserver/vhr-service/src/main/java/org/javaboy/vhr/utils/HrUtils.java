package org.javaboy.vhr.utils;

import org.javaboy.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @ClassName HrUtils
 * @Author 周某
 * @Date 2020-05-13 17:50
 **/
public class HrUtils {

    /**
     * 用来获取当前登录用户的Id
     *
     * @return
     */
    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


}
