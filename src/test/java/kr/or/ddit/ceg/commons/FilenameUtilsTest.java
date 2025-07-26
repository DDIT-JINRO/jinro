package kr.or.ddit.ceg.commons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FilenameUtilsTest {
	public void test1() {
		String fullPath = "c:/temp/test/list.txt";

		System.out.println(FilenameUtils.getBaseName(fullPath));
		System.out.println(FilenameUtils.getExtension(fullPath));
		System.out.println(FilenameUtils.getFullPath(fullPath));
		System.out.println(FilenameUtils.getPrefix(fullPath));
		System.out.println(FilenameUtils.getPath(fullPath));
		System.out.println(FilenameUtils.getName(fullPath));

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("홍길동" + (i + 1));
		}
		try {
			FileUtils.writeLines(new File(fullPath), list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public static void main(String[] args) {
		for(int i=1; i<9;i++) {
			for(int j=1; j<(9-i); j++) {
				System.out.print(" ");
			}
			for(int j=1; j<=i;j++) {
				System.out.print("*");
			}
			System.out.println(" ");
		}
	}
}
