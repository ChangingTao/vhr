package org.javaboy.vhr.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.javaboy.vhr.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName PoiUtils
 * @Author 周某
 * @Date 2020-05-16 17:09
 **/
public class POIUtils {
    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        // xls,xlss 使用 2007版前
        // 1、创建一个 Excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2、创建文档摘要 配置文档的基本信息。eg：Excel文件的标记、类别、状态、类型、主题、备注……
        workbook.createInformationProperties();

        // 3、获取并配置文档信息
        DocumentSummaryInformation docInfo = new DocumentSummaryInformation();
        // 文档类别
        docInfo.setCategory("员工信息");
        // 文档管理员
        docInfo.setManager("ChangingTao");
        // 设置公司信息
        docInfo.setCompany("www.ChangingTao.com");

        // 4、获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        // 文档标题
        summInfo.setTitle("员工信息表");
        // 文档作者
        summInfo.setAuthor("ChangingTao");
        // 文档备注
        summInfo.setComments("本文由 ChangingTao 提供");

        // 5、创建样式
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        // 创建标题行的样式：背景颜色:黄色、填充模式:铺满
        hssfCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        hssfCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 日期格式化
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        // 6、创建工作表
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        // 设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 12 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 16 * 256);
        sheet.setColumnWidth(9, 12 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 16 * 256);
        sheet.setColumnWidth(13, 14 * 256);
        sheet.setColumnWidth(14, 14 * 256);
        sheet.setColumnWidth(15, 12 * 256);
        sheet.setColumnWidth(16, 8 * 256);
        sheet.setColumnWidth(17, 20 * 256);
        sheet.setColumnWidth(18, 20 * 256);
        sheet.setColumnWidth(19, 12 * 256);
        sheet.setColumnWidth(20, 8 * 256);
        sheet.setColumnWidth(21, 25 * 256);
        sheet.setColumnWidth(22, 14 * 256);
        sheet.setColumnWidth(23, 15 * 256);
        sheet.setColumnWidth(24, 15 * 256);
        // 创建标题行
        HSSFRow r0 = sheet.createRow(0);
        // 创建列
        HSSFCell c0 = r0.createCell(0);
        HSSFCell c1 = r0.createCell(1);
        HSSFCell c2 = r0.createCell(2);
        HSSFCell c3 = r0.createCell(3);
        HSSFCell c4 = r0.createCell(4);
        HSSFCell c5 = r0.createCell(5);
        HSSFCell c6 = r0.createCell(6);
        HSSFCell c7 = r0.createCell(7);
        HSSFCell c8 = r0.createCell(8);
        HSSFCell c9 = r0.createCell(9);
        HSSFCell c10 = r0.createCell(10);
        HSSFCell c11 = r0.createCell(11);
        HSSFCell c12 = r0.createCell(12);
        HSSFCell c13 = r0.createCell(13);
        HSSFCell c14 = r0.createCell(14);
        HSSFCell c15 = r0.createCell(15);
        HSSFCell c16 = r0.createCell(16);
        HSSFCell c17 = r0.createCell(17);
        HSSFCell c18 = r0.createCell(18);
        HSSFCell c19 = r0.createCell(19);
        HSSFCell c20 = r0.createCell(20);
        HSSFCell c21 = r0.createCell(21);
        HSSFCell c22 = r0.createCell(22);
        HSSFCell c23 = r0.createCell(23);
        HSSFCell c24 = r0.createCell(24);
        // 列样式
        c0.setCellStyle(hssfCellStyle);
        c1.setCellStyle(hssfCellStyle);
        c2.setCellStyle(hssfCellStyle);
        c3.setCellStyle(hssfCellStyle);
        c4.setCellStyle(hssfCellStyle);
        c5.setCellStyle(hssfCellStyle);
        c6.setCellStyle(hssfCellStyle);
        c7.setCellStyle(hssfCellStyle);
        c8.setCellStyle(hssfCellStyle);
        c9.setCellStyle(hssfCellStyle);
        c10.setCellStyle(hssfCellStyle);
        c11.setCellStyle(hssfCellStyle);
        c12.setCellStyle(hssfCellStyle);
        c13.setCellStyle(hssfCellStyle);
        c14.setCellStyle(hssfCellStyle);
        c15.setCellStyle(hssfCellStyle);
        c16.setCellStyle(hssfCellStyle);
        c17.setCellStyle(hssfCellStyle);
        c18.setCellStyle(hssfCellStyle);
        c19.setCellStyle(hssfCellStyle);
        c20.setCellStyle(hssfCellStyle);
        c21.setCellStyle(hssfCellStyle);
        c22.setCellStyle(hssfCellStyle);
        c23.setCellStyle(hssfCellStyle);
        c24.setCellStyle(hssfCellStyle);
        // 列名
        c0.setCellValue("编号");
        c1.setCellValue("姓名");
        c2.setCellValue("工号");
        c3.setCellValue("性别");
        c4.setCellValue("出生日期");
        c5.setCellValue("身份证号码");
        c6.setCellValue("婚姻状况");
        c7.setCellValue("民族");
        c8.setCellValue("籍贯");
        c9.setCellValue("政治面貌");
        c10.setCellValue("电话号码");
        c11.setCellValue("联系地址");
        c12.setCellValue("所属部门");
        c13.setCellValue("职位");
        c14.setCellValue("职称");
        c15.setCellValue("聘用形式");
        c16.setCellValue("最高学历");
        c17.setCellValue("专业");
        c18.setCellValue("毕业院校");
        c19.setCellValue("入职日期");
        c20.setCellValue("在职状态");
        c21.setCellValue("邮箱");
        c22.setCellValue("合同期限(年)");
        c23.setCellValue("合同起始日期");
        c24.setCellValue("合同终止日期");

        // 给列赋值
        for (int i = 0; i < list.size(); i++) {
            Employee emp = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellValue(emp.getBirthday());
            cell4.setCellStyle(dateCellStyle);
            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(emp.getPhone());
            row.createCell(11).setCellValue(emp.getAddress());
            row.createCell(12).setCellValue(emp.getDepartment().getName());
            row.createCell(13).setCellValue(emp.getJobLevel().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getEngageForm());
            row.createCell(16).setCellValue(emp.getTiptopDegree());
            row.createCell(17).setCellValue(emp.getSpecialty());
            row.createCell(18).setCellValue(emp.getSchool());
            HSSFCell cell19 = row.createCell(19);
            cell19.setCellValue(emp.getBeginDate());
            cell19.setCellStyle(dateCellStyle);
            row.createCell(20).setCellValue(emp.getWorkState());
            row.createCell(21).setCellValue(emp.getEmail());
            row.createCell(22).setCellValue(emp.getContractTerm());
            HSSFCell cell23 = row.createCell(23);
            cell23.setCellValue(emp.getBeginContract());
            cell23.setCellStyle(dateCellStyle);
            HSSFCell cell24 = row.createCell(24);
            cell24.setCellValue(emp.getEndContract());
            cell24.setCellStyle(dateCellStyle);
            HSSFCell cell25 = row.createCell(25);
            cell25.setCellValue(emp.getConversionTime());
            cell25.setCellStyle(dateCellStyle);

        }
        // workbook 转化为数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        try {
            // 将文件进行转码（支持中文）
            headers.setContentDispositionFormData("attachment", new String("员工表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            // 定义网络文件的类型和网页的编码，这里告诉浏览器返回的是二进制流数据
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 转换为字节流
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ResponseEntity参数：流数据、请求头、状态码
        return new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.CREATED);
    }

    /**
     * Excel 解析成员工集合
     *
     * @param file
     * @param allNations        民族
     * @param allPoliticsstatus
     * @param allDepartments
     * @param allPositions
     * @param allJobLevels
     * @return
     */
    public static List<Employee> excel2Employee(MultipartFile file, List<Nation> allNations, List<Politicsstatus> allPoliticsstatus, List<Department> allDepartments, List<Position> allPositions, List<JobLevel> allJobLevels) {
        List<Employee> list = new ArrayList<>();
        Employee employee = null;
        try {
            // 1、创建一个workbook对象，获取文件流
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            // 2、获取Excel文件表单的长度
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                // 3、获取表单
                HSSFSheet sheet = workbook.getSheetAt(i);
                // 4、获取表单中的行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    // 5、跳过标题行
                    if (j == 0) {
                        continue; // 跳过解析标题行
                    }
                    // 6、获取行
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue; // 防止数据中间有空行
                    }
                    // 7、获取列
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    employee = new Employee();
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()) {
                            // 如果单行格是字符串
                            case STRING:
                                String cellValue = cell.getStringCellValue();
                                switch (k) {
                                    case 1:
                                        employee.setName(cellValue);
                                        break;
                                    case 2:
                                        employee.setWorkID(cellValue);
                                        break;
                                    case 3:
                                        employee.setGender(cellValue);
                                        break;
                                    case 5:
                                        employee.setIdCard(cellValue);
                                        break;
                                    case 6:
                                        employee.setWedlock(cellValue);
                                        break;
                                    case 7:
                                        int nationIndex = allNations.indexOf(new Nation(cellValue));
                                        employee.setNationId(allNations.get(nationIndex).getId());
                                        break;
                                    case 8:
                                        employee.setNativePlace(cellValue);
                                        break;
                                    case 9:
                                        int politicstatusIndex = allPoliticsstatus.indexOf(new Politicsstatus(cellValue));
                                        employee.setPoliticId(allPoliticsstatus.get(politicstatusIndex).getId());
                                        break;
                                    case 10:
                                        employee.setPhone(cellValue);
                                        break;
                                    case 11:
                                        employee.setAddress(cellValue);
                                        break;
                                    case 12:
                                        int departmentIndex = allDepartments.indexOf(new Department(cellValue));
                                        employee.setDepartmentId(allDepartments.get(departmentIndex).getId());
                                        break;
                                    case 13:
                                        int jobLevelIndex = allJobLevels.indexOf(new JobLevel(cellValue));
                                        employee.setJobLevelId(allJobLevels.get(jobLevelIndex).getId());
                                        break;
                                    case 14:
                                        int positionIndex = allPositions.indexOf(new Position(cellValue));
                                        employee.setPosId(allPositions.get(positionIndex).getId());
                                        break;
                                    case 15:
                                        employee.setEngageForm(cellValue);
                                        break;
                                    case 16:
                                        employee.setTiptopDegree(cellValue);
                                        break;
                                    case 17:
                                        employee.setSpecialty(cellValue);
                                        break;
                                    case 18:
                                        employee.setSchool(cellValue);
                                        break;
                                    case 20:
                                        employee.setWorkState(cellValue);
                                        break;
                                    case 21:
                                        employee.setEmail(cellValue);
                                        break;
                                }
                                break;
                            // 如果不是字符串信息，则说明为日期
                            default: {
                                switch (k) {
                                    case 4:
                                        employee.setBirthday(cell.getDateCellValue());
                                        break;
                                    case 19:
                                        employee.setBeginDate(cell.getDateCellValue());
                                        break;
                                    case 22:
                                        employee.setContractTerm(cell.getNumericCellValue());
                                        break;
                                    case 23:
                                        employee.setBeginContract(cell.getDateCellValue());
                                        break;
                                    case 24:
                                        employee.setEndContract(cell.getDateCellValue());
                                        break;
                                    case 25:
                                        employee.setConversionTime(cell.getDateCellValue());
                                        break;
                                }
                            }
                            break;
                        }
                    }
                    list.add(employee);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}


























