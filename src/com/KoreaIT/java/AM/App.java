package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {

	private static List<Article> articles;
	private static List<Member> members;

	static {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void run() {
		System.out.println("==프로그램 시작==");

		Scanner sc = new Scanner(System.in);

		makeTestData();
		int lastArticleId = 3;
		int lastMemberId = 0;

		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				break;
			}
			if (cmd.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}
			if (cmd.equals("article write")) {
				int id = lastArticleId + 1;
				lastArticleId = id;
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();
				Article article = new Article(id, Article.findDate(), title, body);
				articles.add(article);

				System.out.printf("%d번 글이 생성되었습니다.\n", id);

			} else if (cmd.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
					continue;
				} else {
					System.out.println("번호    |    제목    |    조회");
					for (int i = articles.size(); i > 0; i--) {
						Article article = articles.get(i - 1);
						System.out.printf("%4d   |    %s   |    %d\n", article.id, article.title, article.hit);
					}
				}
			} else if (cmd.startsWith("article detail")) {
				int num = Integer.parseInt(cmd.substring(15));

				if (Article.findArticle(articles, num) == -1) {
					System.out.println(num + "번 게시글은 존재하지 않습니다.");
				} else {
					Article article = articles.get(Article.findArticle(articles, num));
					article.increasehit();
					System.out.println("번호: " + article.id);
					System.out.println("날짜: " + article.date);
					System.out.println("제목: " + article.title);
					System.out.println("내용: " + article.body);
					System.out.println("조회: " + article.hit);
				}
			} else if (cmd.startsWith("article delete")) {
				int num = Integer.parseInt(cmd.substring(15));
				if (Article.findArticle(articles, num) == -1) {
					System.out.println(num + "번 게시글은 존재하지 않습니다.");
				} else {
					articles.remove(Article.findArticle(articles, num));
					System.out.printf("%d번 게시글이 삭제되었습니다.\n", num);
				}
			} else if (cmd.startsWith("article modify")) {
				int num = Integer.parseInt(cmd.substring(15));
				if (Article.findArticle(articles, num) == -1) {
					System.out.println(num + "번 게시글은 존재하지 않습니다.");
				} else {
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String body = sc.nextLine();
					Article article = new Article(num, Article.findDate(), title, body);
					articles.set(Article.findArticle(articles, num), article);
				}
			} else if (cmd.equals("member join")) {
				int id = lastMemberId + 1;
				lastMemberId = id;
				System.out.printf("이름: ");
				String name = sc.nextLine();
				String loginid = "";
				while (true) {
					boolean a = true;
					System.out.printf("아이디: ");
					String makeid = sc.nextLine();
					for (Member member : members) {
						if (member.loginid.equals(makeid)) {
							System.out.println("이미 존재하는 id 입니다.");
							a = false;
						}
					}
					if (a) {
						loginid = makeid;
						break;
					}
				}
				System.out.printf("비밀번호: ");
				String loginPw = sc.nextLine();
				Member member = new Member(id, name, Article.findDate(), loginid, loginPw);
				members.add(member);
			} else if (cmd.equals("login")) {
				Member memberlogin = null;
				while (true) {
					boolean a = false;
					System.out.printf("아이디: ");
					String makeid = sc.nextLine();
					for (Member member : members) {
						if (member.loginid.equals(makeid)) {
							memberlogin = member;
							a = true;
							break;
						}
					}
					if (a) {
						break;
					} else {
						System.out.println("아이디가 존재하지 않습니다.");
						continue;
					}
				}
				
				while (true) {
					System.out.printf("비밀번호: ");
					String loginPw = sc.nextLine();
					if (memberlogin.loginPw.equals(loginPw)) {
						System.out.println("로그인되었습니다.");
						break;
					} else {
						System.out.println("비밀번호가 일치하지 않습니다.");
						continue;
					}
				}
			} else {
				System.out.println("존재하지 않는 명령어입니다");
			}
		}
		sc.close();
		System.out.println("==프로그램 끝==");
	}

	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");

		articles.add(new Article(1, Article.findDate(), "제목1", "내용1"));
		articles.add(new Article(2, Article.findDate(), "제목2", "내용2"));
		articles.add(new Article(3, Article.findDate(), "제목3", "내용3"));
	}
}

class Article {
	int id;
	String date;
	String title;
	String body;
	int hit;

	Article(int id, String date, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.date = date;
		this.hit = 0;
	}

	public void increasehit() {
		hit++;
	}

	static int findArticle(List<Article> articles, int num) {
		int tf = -1;
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.id == num) {
				tf = i;
			}
		}
		return tf; // return index
	}

	static String findDate() {
		LocalDateTime now = LocalDateTime.now();
		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return date;
	}
}

class Member {
	int id;
	String name;
	String regDate;
	String loginid;
	String loginPw;

	Member(int id, String name, String regDate, String loginid, String loginPw) {
		this.id = id;
		this.name = name;
		this.regDate = regDate;
		this.loginid = loginid;
		this.loginPw = loginPw;
	}

	static boolean makeid(String makeid) {
		return true;
	}
}