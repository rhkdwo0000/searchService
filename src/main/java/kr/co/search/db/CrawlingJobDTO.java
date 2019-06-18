package kr.co.search.db;

public class CrawlingJobDTO {
	private int crawlingJobId;// 크롤링별 고유아이
	private String startTime;// 크롤링 시작시
	private String endTime;// 크롤링 종료시간 
	private int newsInsertCount;//뉴스기사 데이터가 현재크롤링에서 들어간 카운트 
	private String crawlingSite;//크롤링한 사이트 
	
	public CrawlingJobDTO(int crawlingJobId, String startTime, String endTime, int newsInsertCount,
			String crawlingSite) {//생성자 
		super();
		this.crawlingJobId = crawlingJobId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.newsInsertCount = newsInsertCount;
		this.crawlingSite = crawlingSite;
	}

	public int getCrawlingJobId() {
		return crawlingJobId;
	}

	public void setCrawlingJobId(int crawlingJobId) {
		this.crawlingJobId = crawlingJobId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getNewsInsertCount() {
		return newsInsertCount;
	}

	public void setNewsInsertCount(int newsInsertCount) {
		this.newsInsertCount = newsInsertCount;
	}

	public String getCrawlingSite() {
		return crawlingSite;
	}

	public void setCrawlingSite(String crawlingSite) {
		this.crawlingSite = crawlingSite;
	}
	
}//CrawlingJobDTO 클래스 끝 
