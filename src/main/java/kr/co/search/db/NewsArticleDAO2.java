package kr.co.search.db;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("newsarticleDAO")
public class NewsArticleDAO2 {
	
	@Autowired
	private SqlSession session;

	public NewsArticleDTO2 searchSelect(String id) {
		return session.selectOne("news.select", id);
	}
	
	public int newspaperCount() {
		return session.selectOne("news.newspaperCountSelect");
	}

}
