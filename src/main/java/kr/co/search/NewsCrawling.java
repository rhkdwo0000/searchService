package kr.co.search;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.co.search.db.NewsArticleDAO;
import kr.co.search.db.NewsArticleDTO;

public class NewsCrawling {
	static NewsArticleDAO dao2 = new NewsArticleDAO();

	public NewsCrawling() throws Exception {

		ElasticSearch elasticsearch = new ElasticSearch();
		NewsArticleDAO dao = new NewsArticleDAO();
		NewsArticleDTO dto = new NewsArticleDTO();

		String tempDate = null;
		int PageLimitflag = 0;
		int page = -1;
		String[] day;
		String[] time;
		String[] pmTime;
		String[] amTime;
		int pmHourBefore;
		int amHourBefore;
		int pmHourAfter;
		int amHourAfter;
		int latestNewsArticleFlag = 1;// 최신기사 플래그
		int newsInsertCount = 0;// 데이터 등록 횟수 .
		int breakFlag = 0;

		String title = null;
		String content = null;
		String newspaper = null;
		String releaseDate = null;
		String imageUrl = null;
		String crawlingUrl = null;

		elasticsearch.elasticSetting();

		if (dao.lastLowNewsIdSelect() != 0) {
			title = dao.flagNewsArticleSelect().getTitle();
			content = dao.flagNewsArticleSelect().getContent();
			newspaper = dao.flagNewsArticleSelect().getNewspaper();
			releaseDate = dao.flagNewsArticleSelect().getReleaseDate();
			imageUrl = dao.flagNewsArticleSelect().getImageUrl();
			crawlingUrl = dao.flagNewsArticleSelect().getCrawlingUrl();
		}

		Document document = null;
		Elements dateElement = null;
		
		
		while (true) {
			try {
				document = Jsoup.connect("https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001")
						.timeout(30000).get();
			} catch (Exception e) {
				e.printStackTrace();
				document = Jsoup.connect("https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001")
						.timeout(30000).get();
			}

			if (document != null) {
				break;
			}
		}	
		
		
		//다음날 전날기사 전부 크롤링 하는 방법 아직 적용
		dateElement = document.select(".pagenavi_day a");
		document = Jsoup.connect("https://news.naver.com/main/list.nhn" + dateElement.get(0).attr("href")).timeout(30000).get();
		
		
		
		
		dao.crawlingJobInsert("naver");
		dao.flagZeroUpdate();

		Elements pagingElement = document.select(".paging a");

		System.out.println("페이지사이즈 최초 : " + pagingElement.size());

		while (true) {

			if (0 != breakFlag) {
				break;
			}

			if (10 == pagingElement.size()) {
				if (page > pagingElement.size() - 1) {
					System.out.println("check2");

					while (true) {

						try {
							document = Jsoup.connect(
									"https://news.naver.com/main/list.nhn" + pagingElement.get(page - 1).attr("href"))
									.timeout(30000).get();
						} catch (Exception e) {
							e.printStackTrace();
							document = Jsoup.connect(
									"https://news.naver.com/main/list.nhn" + pagingElement.get(page - 1).attr("href"))
									.timeout(30000).get();
						}

						if (document != null) {
							break;
						}
					}

					pagingElement = document.select(".paging a");
					System.out.println("페이지사이즈 변환 : " + pagingElement.size());
					page = 1;
				}
			} else if (pagingElement.size() > 10) {
				if (page > pagingElement.size() - 1) {
					System.out.println("check3");

					while (true) {
						try {
							document = Jsoup.connect(
									"https://news.naver.com/main/list.nhn" + pagingElement.get(page - 1).attr("href"))
									.timeout(30000).get();
						} catch (Exception e) {
							e.printStackTrace();
							document = Jsoup.connect(
									"https://news.naver.com/main/list.nhn" + pagingElement.get(page - 1).attr("href"))
									.timeout(30000).get();
						}

						if (document != null) {
							break;
						}
					}

					pagingElement = document.select(".paging a");
					System.out.println("페이지사이즈 변환 : " + pagingElement.size());
					page = 1;
				}
			} else if (pagingElement.get(0).text().equals("이전") && pagingElement.size() - 1 < 10) {
				if (page > pagingElement.size() - 1) {
					System.out.println("check4");
					break;
				}
			}

			if (PageLimitflag != 0) {

				while (true) {
					try {
						document = Jsoup
								.connect("https://news.naver.com/main/list.nhn" + pagingElement.get(page).attr("href"))
								.timeout(30000).get();
					} catch (Exception e) {
						e.printStackTrace();
						document = Jsoup
								.connect("https://news.naver.com/main/list.nhn" + pagingElement.get(page).attr("href"))
								.timeout(30000).get();
					}

					if (document != null) {
						break;
					}
				}

			}
			Elements element = document.select(".type06_headline li");

			for (int i = 0; i < element.size(); i++) {
				System.out.println("1페이지 헤드라인");
				if (page != -1) {
					System.out.println("페이지 : " + pagingElement.get(page).text());
				}

				while (true) {

					try {
						document = Jsoup.connect(element.get(i).select("a").attr("href")).timeout(30000).get();
					} catch (Exception e) {
						e.printStackTrace();
						document = Jsoup.connect(element.get(i).select("a").attr("href")).timeout(30000).get();
					}

					if (document != null) {
						break;
					}
				}
				dto.setTitle(document.select(".article_info h3").text().replace("\"", "'"));
				dto.setContent(document.select("#articleBodyContents").text().replace("\"", "'"));
				dto.setImageUrl(element.get(i).select(".photo img").attr("src"));
				dto.setCrawlingUrl(element.get(i).select("a").attr("href"));

				if (document.select(".t11").text().contains("오전")) {
					day = document.select(".t11").text().split(" ");
					time = day[0].split("\\.");
//					System.out.println(time[0] + "-" + time[1] + "-" + time[2] + " " + day[2]);
					amTime = day[2].replaceAll(" ", "").split(":");
					amHourBefore = Integer.parseInt(amTime[0]);
					if (amHourBefore == 12) {
						amHourAfter = amHourBefore - 12;
					} else {
						amHourAfter = amHourBefore;
					}
					tempDate = time[0] + "-" + time[1] + "-" + time[2];

					dto.setReleaseDate(time[0] + "-" + time[1] + "-" + time[2] + " " + String.valueOf(amHourAfter) + ":"
							+ amTime[1]);
				} else if (document.select(".t11").text().contains("오후")) {
					day = document.select(".t11").text().split(" ");
					pmTime = day[2].replaceAll(" ", "").split(":");
					pmHourBefore = Integer.parseInt(pmTime[0]);
					if (pmHourBefore != 12) {
						pmHourAfter = pmHourBefore + 12;
					} else {
						pmHourAfter = pmHourBefore;
					}
					time = day[0].split("\\.");
//					System.out.println(time[0] + "-" + time[1] + "-" + time[2] + " " + String.valueOf(pmHourAfter) + ":"
//							+ pmTime[1]);// 기사 등록 날짜
//					tempDate = time[0] + "-" + time[1] + "-" + time[2];
					dto.setReleaseDate(time[0] + "-" + time[1] + "-" + time[2] + " " + String.valueOf(pmHourAfter) + ":"
							+ pmTime[1]);
				}

//				System.out.println();
				dto.setNewspaper(element.get(i).select(".writing").text());
				dto.setLatestNewsArticleFlag(latestNewsArticleFlag);
				dto.setCrawlingJobId(dao.crawlingJobIdSelect());

				System.out.println(title);
				System.out.println(document.select(".article_info h3").text().replace("\"", "'"));// title
				System.out.println(content);
				System.out.println(document.select("#articleBodyContents").text().replace("\"", "'"));// content
				System.out.println(newspaper);
				System.out.println(element.get(i).select(".writing").text());// 신문사
				System.out.println(imageUrl);
				System.out.println(element.get(i).select(".photo img").attr("src"));// image url
				System.out.println(crawlingUrl);
				System.out.println(element.get(i).select("a").attr("href"));
				System.out.println();
				System.out.println();

				if (title != null) {
					String[] tempReleaseDate2 = releaseDate.split("\\.");
					if (newspaper.equals(element.get(i).select(".writing").text())
							&& crawlingUrl.equals(element.get(i).select("a").attr("href"))
							&& imageUrl.equals(dto.getImageUrl())
							&& tempReleaseDate2[0].equals(dto.getReleaseDate()+":00")) {
						breakFlag++;
						break;
					} else {
						SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String[] tempReleaseDate = releaseDate.split("\\.");
						Date flagDate = transFormat.parse(tempReleaseDate[0]);
						System.out.println(tempReleaseDate[0]);
						System.out.println(dto.getReleaseDate()+":00");
						Date crawlingDate = transFormat.parse(dto.getReleaseDate()+":00");
						if (flagDate.compareTo(crawlingDate) > 0) {
							breakFlag++;
							break;
						}
					}

				}

				if (dao.newsArticleInsert(dto) == 1) {
//					System.out.println(dao.lastLowNewsIdSelect());
//					System.out.println(document.select(".article_info h3").text().replace("\"", "'"));
//					System.out.println(document.select("#articleBodyContents").text().replace("\"", "'"));
//					System.out.println(element.get(i).select(".writing").text());

//					elasticsearch.elasticInsert(dao.lastLowNewsIdSelect(), document.select(".article_info h3").text().replace("\"", "'"), document.select("#articleBodyContents").text().replace("\"", "'"), element.get(i).select(".writing").text());
//					elasticsearch.elasticInsertSecond(dao.lastLowNewsIdSelect(), document.select(".article_info h3").text().replace("\"", "'"), document.select("#articleBodyContents").text().replace("\"", "'"), element.get(i).select(".writing").text());
					elasticsearch.elasticLastInsert(dao.lastLowNewsIdSelect(),
							document.select(".article_info h3").text().replace("\"", "'"),
							document.select("#articleBodyContents").text().replace("\"", "'"),
							element.get(i).select(".writing").text(), tempDate);

				}

				latestNewsArticleFlag = 0;
				newsInsertCount++;

			} // 1페이지 헤드라인 끝

			if (PageLimitflag != 0) {

				while (true) {
					try {
						document = Jsoup
								.connect("https://news.naver.com/main/list.nhn" + pagingElement.get(page).attr("href"))
								.timeout(30000).get();
					} catch (Exception e) {
						e.printStackTrace();
						document = Jsoup
								.connect("https://news.naver.com/main/list.nhn" + pagingElement.get(page).attr("href"))
								.timeout(30000).get();
					}

					if (document != null) {
						break;
					}
				}

			} else {

				while (true) {
					try {
						document = Jsoup.connect("https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001")
								.timeout(30000).get();
					} catch (Exception e) {
						e.printStackTrace();
						document = Jsoup.connect("https://news.naver.com/main/list.nhn?mode=LSD&mid=sec&sid1=001")
								.timeout(30000).get();
					}
					if (document != null) {
						break;
					}
				}

			}

			element = document.select(".type06 li");

			for (int i = 0; i < element.size(); i++) {
				System.out.println("1페이지 헤드라인 제외");
				if (page != -1) {
					System.out.println("페이지 : " + pagingElement.get(page).text());
				}

				while (true) {

					try {
						document = Jsoup.connect(element.get(i).select("a").attr("href")).timeout(30000).get();

					} catch (Exception e) {
						e.printStackTrace();
						document = Jsoup.connect(element.get(i).select("a").attr("href")).timeout(30000).get();
					}
					if (document != null) {
						break;
					}
				}

//				System.out.println(element.get(i).select("a").attr("href"));// crawling url

				dto.setTitle(document.select(".article_info h3").text().replace("\"", "'"));
				dto.setContent(document.select("#articleBodyContents").text().replace("\"", "'"));
				dto.setImageUrl(element.get(i).select(".photo img").attr("src"));
				dto.setCrawlingUrl(element.get(i).select("a").attr("href"));

				if (document.select(".t11").text().contains("오전")) {
					day = document.select(".t11").text().split(" ");
					time = day[0].split("\\.");
//					System.out.println(time[0] + "-" + time[1] + "-" + time[2] + " " + day[2]);
					amTime = day[2].replaceAll(" ", "").split(":");
					amHourBefore = Integer.parseInt(amTime[0]);
					if (amHourBefore == 12) {
						amHourAfter = amHourBefore - 12;
					} else {
						amHourAfter = amHourBefore;
					}
					tempDate = time[0] + "-" + time[1] + "-" + time[2];

					dto.setReleaseDate(time[0] + "-" + time[1] + "-" + time[2] + " " + String.valueOf(amHourAfter) + ":"
							+ amTime[1]);
				} else if (document.select(".t11").text().contains("오후")) {
					day = document.select(".t11").text().split(" ");
					pmTime = day[2].replaceAll(" ", "").split(":");
					pmHourBefore = Integer.parseInt(pmTime[0]);
					if (pmHourBefore != 12) {
						pmHourAfter = pmHourBefore + 12;
					} else {
						pmHourAfter = pmHourBefore;
					}
					time = day[0].split("\\.");
//					System.out.println(time[0] + "-" + time[1] + "-" + time[2] + " " + String.valueOf(pmHourAfter) + ":"
//							+ pmTime[1]);// 기사 등록 날짜
//					tempDate = time[0] + "-" + time[1] + "-" + time[2];
					dto.setReleaseDate(time[0] + "-" + time[1] + "-" + time[2] + " " + String.valueOf(pmHourAfter) + ":"
							+ pmTime[1]);
				}

				dto.setNewspaper(element.get(i).select(".writing").text());
				dto.setLatestNewsArticleFlag(latestNewsArticleFlag);
				dto.setCrawlingJobId(dao.crawlingJobIdSelect());

				System.out.println(title);
				System.out.println(document.select(".article_info h3").text().replace("\"", "'"));// title
				System.out.println(content);
				System.out.println(document.select("#articleBodyContents").text().replace("\"", "'"));// content
				System.out.println(newspaper);
				System.out.println(element.get(i).select(".writing").text());// 신문사
				System.out.println(imageUrl);
				System.out.println(element.get(i).select(".photo img").attr("src"));// image url
				System.out.println(crawlingUrl);
				System.out.println(element.get(i).select("a").attr("href"));//본문링크
				System.out.println();
				System.out.println();

				if (title != null) {
					String[] tempReleaseDate2 = releaseDate.split("\\.");
					if (newspaper.equals(element.get(i).select(".writing").text())
							&& crawlingUrl.equals(element.get(i).select("a").attr("href"))
							&& imageUrl.equals(dto.getImageUrl())
							&& tempReleaseDate2[0].equals(dto.getReleaseDate()+":00")) {
						breakFlag++;
						break;
					} else {
						SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String[] tempReleaseDate = releaseDate.split("\\.");
						Date flagDate = transFormat.parse(tempReleaseDate[0]);
						System.out.println(tempReleaseDate[0]);
						System.out.println(dto.getReleaseDate()+":00");
						Date crawlingDate = transFormat.parse(dto.getReleaseDate()+":00");
						if (flagDate.compareTo(crawlingDate) > 0) {
							breakFlag++;
							break;
						}
					}
				}

				if (dao.newsArticleInsert(dto) == 1) {

//					elasticsearch.elasticInsert(dao.lastLowNewsIdSelect(), document.select(".article_info h3").text().replace("\"", "'"), document.select("#articleBodyContents").text().replace("\"", "'"), element.get(i).select(".writing").text());
//					elasticsearch.elasticInsertSecond(dao.lastLowNewsIdSelect(), document.select(".article_info h3").text().replace("\"", "'"), document.select("#articleBodyContents").text().replace("\"", "'"), element.get(i).select(".writing").text());
					elasticsearch.elasticLastInsert(dao.lastLowNewsIdSelect(),
							document.select(".article_info h3").text().replace("\"", "'"),
							document.select("#articleBodyContents").text().replace("\"", "'"),
							element.get(i).select(".writing").text(), tempDate);
				}

				newsInsertCount++;
			} // 헤드라인 제외한 기사

			PageLimitflag++;

			page++;

		} // while문

		dao.crawlingJobUpdate(dao.crawlingJobIdSelect(), newsInsertCount);

	}// 생성자 끝

	public static void main(String[] args) throws Exception {
		if (dao2.selectEndTimeCount() != 0) {

		} else {
			new NewsCrawling();
		}
	}

}// 크롤링 클래스
