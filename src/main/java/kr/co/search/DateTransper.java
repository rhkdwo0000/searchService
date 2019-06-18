package kr.co.search;

import kr.co.search.db.NewsArticleDAO;

public class DateTransper {

	public static void main(String[] args) throws Exception {
		NewsArticleDAO dao = new NewsArticleDAO();
		dao.dbTransferElastic();
	}
}
