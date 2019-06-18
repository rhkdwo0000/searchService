package kr.co.search.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.co.search.ElasticSearch;

public class NewsArticleDAO {
//	tom-mysql.c8xsfxnysoko.ap-northeast-2.rds.amazonaws.com
	// newssite

	private String url = "jdbc:mysql://tom-mysql.c8xsfxnysoko.ap-northeast-2.rds.amazonaws.com:3306/newssite?useSSL=false&characterEncoding=utf8";
	// 데이터 베이스 url
	private String user = "newssite";
	// mysql root 계정 아이디
	private String password = "newssitenewssite";
	// mysql root 계정 비밀번호
	private String sql;
	// sql문 문자열

	private Connection con;
	private PreparedStatement ps;

	int count;

	public void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connectDB:메서드 끝


	
	public int selectEndTimeCount() {
		ResultSet rs = null;
		int count = 0;
		try {
			connectDB();
			sql = "select end_time,count(*) from crawling_job where end_time is null";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
		
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return count;
	}
	
	
	
	
	public NewsArticleDTO selectId(String id) {
		ResultSet rs = null;
		NewsArticleDTO dto = null;
		try {
			connectDB();
			sql = "select * from news_article where news_article_id ='"+id+ "'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int newsArticleId = rs.getInt("news_article_id");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String imageUrl = rs.getString("image_url");
				String newspaper = rs.getString("newspaper");
				String crawlingUrl = rs.getString("crawling_url");
				String releaseDate = rs.getString("release_date");
				 dto = new NewsArticleDTO(title, content, imageUrl, crawlingUrl, newspaper, releaseDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;
	}
	
	
	
	
	
	public ArrayList<String> newspaperSelect() {
		ArrayList<String> list = new ArrayList<String>();
		ResultSet rs = null;
		try {
			connectDB();
			sql = "select newspaper from news_article group by newspaper";
			ps =con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("newspaper"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
		
	}
	
	
	
	
	
	
	
	public int newsArticleInsert(NewsArticleDTO dto) {// 뉴스기사 등록 메서드
		int flag = 0;
		try {
			connectDB();
			sql = "insert into news_article value(default,?,?,?,?,?,?,?,?,NOW())";
			ps = con.prepareStatement(sql);

			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setString(3, dto.getImageUrl());
			ps.setString(4, dto.getCrawlingUrl());
			ps.setInt(5, dto.getLatestNewsArticleFlag());
			ps.setInt(6, dto.getCrawlingJobId());
			ps.setString(7, dto.getNewspaper());
			ps.setString(8, dto.getReleaseDate());

			flag = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return flag;

	}

	public int lastLowNewsIdSelect() {
		ResultSet rs = null;
		int id = 0;
		try {
			connectDB();
			sql = "select news_article_id from news_article order by news_article_id desc limit 1";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				id = rs.getInt("news_article_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return id;

	}

	public NewsArticleDTO flagNewsArticleSelect() {
		ResultSet rs = null;
		NewsArticleDTO dto = null;
		try {
			connectDB();
			sql = "select * from news_article where latest_news_article_flag ='1'";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("content");
				String newspaper = rs.getString("newspaper");
				String releaseDate = rs.getString("release_date");
				String imageUrl = rs.getString("image_url");
				String crawlingUrl =rs.getString("crawling_url");

				dto = new NewsArticleDTO(title, content, imageUrl, crawlingUrl, newspaper, releaseDate);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return dto;

	}

	public void flagZeroUpdate() {// 크롤링 job 업데이트 메서드

		try {
			connectDB();
			sql = "update news_article set latest_news_article_flag ='0' ";
			ps = con.prepareStatement(sql);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}// 크롤링 job 업데이트 메서드 끝

	public void crawlingJobInsert(String crawlingSite) {// 크롤링 job 등록 메서

		try {
			connectDB();
			sql = "insert into crawling_job value(default,NOW(),default,default,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, crawlingSite);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void crawlingJobUpdate(int crawlingJobId, int newsInsertCount) {// 크롤링 job 업데이트 메서드

		try {
			connectDB();
			sql = "update crawling_job set end_time = NOW() , news_insert_count = ? where crawling_job_id =?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, newsInsertCount);
			ps.setInt(2, crawlingJobId);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}// 크롤링 job 업데이트 메서드 끝

	public int crawlingJobIdSelect() {// 크롤링 job 아이디 select 메서
		ResultSet rs = null;
		int crawlingJobId = 0;
		try {
			connectDB();
			sql = "select crawling_job_id from crawling_job where  news_insert_count is NULL";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				crawlingJobId = rs.getInt("crawling_job_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return crawlingJobId;

	}

	public void dbTransferElastic() throws Exception {

		ElasticSearch elastic = new ElasticSearch();
		ResultSet rs = null;
		elastic.elasticSetting();

		try {
			connectDB();
			sql = "select * from news_article";
			ps = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setFetchSize(Integer.MIN_VALUE);

			rs = ps.executeQuery();
			while (rs.next()) {

				int newsArticleId = 0;
				String title = null;
				String content = null;
				String newspaper = null;
				String date = null;

				newsArticleId = rs.getInt("news_article_id");
				title = rs.getString("title");
				content = rs.getString("content");
				newspaper = rs.getString("newspaper");
				date = rs.getString("release_date");
				String[] date2= date.split(" ");
				
				elastic.elasticLastInsert(newsArticleId, title, content, newspaper,date2[0]);// e.s type 별로 메서드 바꿔줘야
			}

			elastic.close();
			System.out.println("finish");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
