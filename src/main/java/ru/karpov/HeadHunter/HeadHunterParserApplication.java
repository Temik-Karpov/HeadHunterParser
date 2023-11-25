package ru.karpov.HeadHunter;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
public class HeadHunterParserApplication {
	public static void main(String[] args) throws IOException {
		String filename = "results.xls";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("FirstSheet");
		FileOutputStream fileOut = new FileOutputStream(filename);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		SpringApplication.run(HeadHunterParserApplication.class, args);
	}
}
