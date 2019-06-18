package kr.co.search.db;

public class NewsArticleDTO {

	private int newsArticleId;//기사별 고유아이디 
	private String title;// 기사제목 
	private String content;// 기사내용 
	private String imageUrl;// 이미지url
	private String crawlingUrl;// 실제크롤링url
	private int latestNewsArticleFlag;// 가장 최신기사 flag
	private int crawlingJobId;//크롤링 job테이블 아이
	private String newspaper;// 신문사 
	private String releaseDate;// 기사등록날짜 
	private String insertTime;// 데이터 등록 시간 
	private double score;
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public NewsArticleDTO(int newsArticleId, String title, String content, String imageUrl, String crawlingUrl,
			int latestNewsArticleFlag, int crawlingJobId, String newspaper, String releaseDate, String insertTime) {//생성자 
		super();
		this.newsArticleId = newsArticleId;
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.crawlingUrl = crawlingUrl;
		this.latestNewsArticleFlag = latestNewsArticleFlag;
		this.crawlingJobId = crawlingJobId;
		this.newspaper = newspaper;
		this.releaseDate = releaseDate;
		this.insertTime = insertTime;
	}
	
	
	
	
	public NewsArticleDTO(String title, String content, String imageUrl, String crawlingUrl, String newspaper,
			String releaseDate) {
		super();
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.crawlingUrl = crawlingUrl;
		this.newspaper = newspaper;
		this.releaseDate = releaseDate;
	}
	public NewsArticleDTO(String title, String content, String imageUrl, String newspaper, String releaseDate) {
		super();
		this.title = title;
		this.content = content;
		this.imageUrl = imageUrl;
		this.newspaper = newspaper;
		this.releaseDate = releaseDate;
	}

	public NewsArticleDTO() {
		super();
	}

	public int getNewsArticleId() {
		return newsArticleId;
	}

	public void setNewsArticleId(int newsArticleId) {
		this.newsArticleId = newsArticleId;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCrawlingUrl() {
		return crawlingUrl;
	}

	public void setCrawlingUrl(String crawlingUrl) {
		this.crawlingUrl = crawlingUrl;
	}

	public int getLatestNewsArticleFlag() {
		return latestNewsArticleFlag;
	}

	public void setLatestNewsArticleFlag(int latestNewsArticleFlag) {
		this.latestNewsArticleFlag = latestNewsArticleFlag;
	}

	public int getCrawlingJobId() {
		return crawlingJobId;
	}

	public void setCrawlingJobId(int crawlingJobId) {
		this.crawlingJobId = crawlingJobId;
	}

	public String getNewspaper() {
		return newspaper;
	}

	public void setNewspaper(String newspaper) {
		this.newspaper = newspaper;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	
}//NewsArticleDTO 끝 
 