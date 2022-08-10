package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("==프로그램 시작==");
		
		Scanner sc = new Scanner(System.in);
		ArrayList<article> articlelist = new ArrayList<>();
		int num = 0;
		
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();
			
			if (cmd.equals("exit")) {
				break;
			} else if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요.");
			} else if (cmd.equals("article list") ) {
				if (num == 0) {
					System.out.println("게시글이 없습니다");					
				}
			} else if (cmd.equals("article write") ) {
				article newarticle = new article();
				System.out.printf("제목: ");
				String title = sc.nextLine();
				System.out.printf("내용: ");
				String body = sc.nextLine();
				articlelist.add(newarticle);
				num++;
				System.out.printf("%d번 글이 생성되었습니다.\n", num);
			} else {System.out.println("존재하지 않는 명령어입니다.");}
		}
		
		sc.close();
		
		System.out.println("==프로그램 끝==");
	}
}

class article {
	String title;
	String body;
}