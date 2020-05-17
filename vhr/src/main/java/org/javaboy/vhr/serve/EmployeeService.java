package org.javaboy.vhr.serve;

import io.swagger.models.auth.In;
import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.mapper.EmployeeecMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.RespPageBean;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName EmployeeService
 * @Author 周某
 * @Date 2020-05-14 10:17
 **/
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");


    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {
        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, beginDateScope);
        Long total = employeeMapper.getTotal(employee, beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {
        // 在添加员工的时候将合同期限计算出来，并存入数据库
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double month = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12
                + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));

        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));

        return employeeMapper.insertSelective(employee);
    }

    public Integer maxWorkID() {
        return employeeMapper.maxWorkID();
    }

    public Integer deleteEmpByEid(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }
}
