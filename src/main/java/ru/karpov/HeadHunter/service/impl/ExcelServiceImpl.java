package ru.karpov.HeadHunter.service.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import ru.karpov.HeadHunter.service.ExcelService;

import java.io.*;
import java.util.Map;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void addVacancyTown(final Integer countSaint, final Integer countMoscow) throws IOException {
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.createRow(2).createCell(0).setCellValue("Кол-во вакансий по городам");

        sheet.createRow(3).createCell(0).setCellValue("Санкт-Петербург");
        sheet.getRow(3).createCell(1).setCellValue(countSaint);

        sheet.createRow(4).createCell(0).setCellValue("Москва");
        sheet.getRow(4).createCell(1).setCellValue(countMoscow);


        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }

    @Override
    public void addVacancyLanguages(final Map<String, Integer> countLanguages) throws IOException {
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.createRow(13);
        row.createCell(0).setCellValue("Кол-во вакансий по языкам");

        int rowCount = 14;
        for (String key: countLanguages.keySet())
        {
            row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(key);
            row.createCell(1).setCellValue(countLanguages.get(key));
            rowCount++;
        }
        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }

    @Override
    public void addVacancyAvgSalary(final Integer avg1, final String town1,
                                    final Integer avg2, final String town2) throws IOException {
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.createRow(6).createCell(0).setCellValue("Средние зарпалыт по городам");

        sheet.createRow(7).createCell(0).setCellValue(town1);
        sheet.getRow(7).createCell(1).setCellValue(avg1);
        sheet.createRow(8).createCell(0).setCellValue(town2);
        sheet.getRow(8).createCell(1).setCellValue(avg2);

        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }

    @Override
    public void addVacancyCompany(final Integer max, final String company) throws IOException {
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        sheet.createRow(10).createCell(0).setCellValue("Лидер среди компаний по вакансиям");

        int rowCount = 11;
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue(company);
        row.createCell(1).setCellValue(max);
        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }

    @Override
    public void addVacancyAvgSalaryLanguage(final Map<String, Integer> sumLanguages1,
                                            final Map<String, Integer> sumLanguages2) throws IOException {
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.createRow(24);
        row.createCell(0).setCellValue("Средняя зарплата по языкам Санкт-Петербурга");
        int rowCount = 25;
        for (String key: sumLanguages1.keySet())
        {
            row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(key);
            row.createCell(1).setCellValue(sumLanguages1.get(key));
            rowCount++;
        }
        row = sheet.createRow(35);
        row.createCell(0).setCellValue("Средняя зарплата по языкам Москвы");
        rowCount = 36;
        for (String key: sumLanguages2.keySet())
        {
            row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(key);
            row.createCell(1).setCellValue(sumLanguages2.get(key));
            rowCount++;
        }

        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }

    @Override
    public void addVacancyMetro(final Map<String, Integer> countMetro) throws IOException{
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        sheet.createRow(46).createCell(0).setCellValue("Кол-во вакансий по станциям метро Питера");
        int rowCount = 47;
        for (String key: countMetro.keySet())
        {
            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(key);
            row.createCell(1).setCellValue(countMetro.get(key));
            rowCount++;
        }
        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }

    @Override
    public void addVacancyCount(final long count) throws IOException {
        FileInputStream fis = new FileInputStream(new File("test.xls"));
        Workbook workbook = new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = 0;
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue("Всего вакансий:");
        row.createCell(1).setCellValue(count);
        fis.close();
        try (FileOutputStream fos = new FileOutputStream("test.xls")) {
            workbook.write(fos);
        }
    }
}
