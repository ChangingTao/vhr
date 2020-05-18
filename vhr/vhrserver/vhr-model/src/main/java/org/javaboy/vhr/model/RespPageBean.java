package org.javaboy.vhr.model;

import java.util.List;

/**
 * @ClassName RespPageBean 分页Bean
 * @Author 周某
 * @Date 2020-05-14 10:14
 **/
public class RespPageBean {
    /* 总记录数 */
    private Long total;
    /* 分页内容 */
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
