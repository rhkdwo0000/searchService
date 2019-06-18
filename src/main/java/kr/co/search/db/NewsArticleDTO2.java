package kr.co.search.db;

public class NewsArticleDTO2 {

	private int news_article_id;//기사별 고유아이디 
	private String title;// 기사제목 
	private String content;// 기사내용 
	private String image_url;// 이미지url
	private String crawling_url;// 실제크롤링url
	private int latest_news_article_flag;// 가장 최신기사 flag
	private int crawling_job_id;//크롤링 job테이블 아이
	private String newspaper;// 신문사 
	private String release_date;// 기사등록날짜 
	private String insert_time;// 데이터 등록 시간 
	private double score;
	private double timeTaken;

  
	public double getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	
	
	public NewsArticleDTO2(int news_article_id, String title, String content, String image_url, String crawling_url,
			int latest_news_article_flag, int crawling_job_id, String newspaper, String release_date,
			String insert_time) {
		super();
		this.news_article_id = news_article_id;
		this.title = title;
		this.content = content;
		this.image_url = image_url;
		this.crawling_url = crawling_url;
		this.latest_news_article_flag = latest_news_article_flag;
		this.crawling_job_id = crawling_job_id;
		this.newspaper = newspaper;
		this.release_date = release_date;
		this.insert_time = insert_time;
	}

	public NewsArticleDTO2(String title, String content, String image_url, String newspaper, String release_date) {
		super();
		this.title = title;
		this.content = content;
		this.image_url = image_url;
		this.newspaper = newspaper;
		this.release_date = release_date;
	}
	public NewsArticleDTO2() {
		super();
	}
	public int getNews_article_id() {
		return news_article_id;
	}
	public void setNews_article_id(int news_article_id) {
		this.news_article_id = news_article_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getCrawling_url() {
		return crawling_url;
	}
	public void setCrawling_url(String crawling_url) {
		this.crawling_url = crawling_url;
	}
	public int getLatest_news_article_flag() {
		return latest_news_article_flag;
	}
	public void setLatest_news_article_flag(int latest_news_article_flag) {
		this.latest_news_article_flag = latest_news_article_flag;
	}
	public int getCrawling_job_id() {
		return crawling_job_id;
	}
	public void setCrawling_job_id(int crawling_job_id) {
		this.crawling_job_id = crawling_job_id;
	}
	public String getNewspaper() {
		return newspaper;
	}
	public void setNewspaper(String newspaper) {
		this.newspaper = newspaper;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

	
	
}//NewsArticleDTO 끝 
 