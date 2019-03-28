package com.ectocyst.service.impl;

import com.ectocyst.mapper.EmployeeMapper;
import com.ectocyst.model.Employee;
import com.ectocyst.service.EmployeeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author Seagull_gby
 * @date 2019/3/12 16:42
 * Description: 人员接口实现
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public JSONObject getReservationEmployee(long jobId) {
        JSONObject returnEmployee = new JSONObject();

        returnEmployee.put("code", 200);
        returnEmployee.put("msg", "success");

        JSONObject data = new JSONObject();
        JSONArray list = new JSONArray();
        Employee employee = new Employee();
        employee = employeeMapper.queryEmployeeByJobId(jobId);

        String[] e = new String[] {"姓名", "工号", "部门", "组别", "联系方式"};
        for(int i=0; i<5; i++) {
            JSONObject d = new JSONObject();
            d.put("id", i+1);
            d.put("item", e[i]);

            switch (i) {
                case 0:
                    d.put("infor", employee.getName());
                    break;
                case 1:
                    d.put("infor", employee.getJobId());
                    break;
                case 2:
                    d.put("infor", employee.getDepartment());
                    break;
                case 3:
                    d.put("infor", employee.getPosition());
                    break;
                case 4:
                    d.put("infor", employee.getPhone());
                    break;
                default:
                    d.put("error", "error");
            }

            list.add(d);
        }

        data.put("list", list);

        JSONArray peopleList = new JSONArray();
        List<Employee> employees = employeeMapper.queryAllEmployeeExceptReservationPeople(jobId);

        /* 迭代删除所有未认证的人员 */
        Iterator<Employee> it = employees.iterator();
        while(it.hasNext()){
            Employee em = it.next();
            if(em.getImgUrl()==null){
                it.remove();
            }
        }

        employees.forEach(ee -> {
            JSONObject peopleListUnit = new JSONObject();
            peopleListUnit.put("id", ee.getJobId());
            peopleListUnit.put("name", ee.getName());
            peopleListUnit.put("department", ee.getDepartment());
            peopleListUnit.put("position", ee.getPosition());

            peopleList.add(peopleListUnit);
        });

        data.put("peopleList", peopleList);
        returnEmployee.put("data", data);

        return returnEmployee;
    }
}
