package kr.co.search;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearch {

	private static TransportClient client;
	Map<String, Object> json = new HashMap<String, Object>();

	public void elasticSetting() throws Exception {
		Settings settings = Settings.builder().put("client.transport.sniff", false)
//				.put("client.transport.ignore_cluster_name", false)
				.put("cluster.name", "elastic") // cx-cluster
				.build();
		client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
//		        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.100.8"), 9300));
	}
	
	public void elasticLastInsert(int newsArticleId, String title, String content,String newspaper,String date ) {//defualt 분석기
		json.put("title", title);
		json.put("content", content);
		json.put("newspaper", newspaper);
		json.put("release_date", date);
		insert(json, "news_article_openkorea", "news_article_openkorea", String.valueOf(newsArticleId));
	}
	
	public void elasticInsert(int newsArticleId, String title, String content,String newspaper ) {//defualt 분석기
		json.put("title", title);
		json.put("content", content);
		json.put("newspaper", newspaper);
		insert(json, "news_article", "news_article", String.valueOf(newsArticleId));
	}
	
	public void elasticInsertFirst(int newsArticleId,  String content ) {//defualt 분석기
//		json.put("title", title);
		json.put("content", content);
//		json.put("newspaper", newspaper);
		insert(json, "news_article_first", "news_article_first", String.valueOf(newsArticleId));
	}
	
	public void elasticInsertSecond(int newsArticleId, String title, String content,String newspaper ) {//ngram 1~3
		json.put("title", title);
		json.put("content", content);
		json.put("newspaper", newspaper);
		insert(json, "news_article_second", "news_article_second", String.valueOf(newsArticleId));
	}
	
	public void elasticInsertThird(int newsArticleId, String title, String content,String newspaper ) {//ngram 1~4
		json.put("title", title);
		json.put("content", content);
		json.put("newspaper", newspaper);
		insert(json, "news_article_third", "news_article_third", String.valueOf(newsArticleId));
	}
	
	public void elasticInsertFour(int newsArticleId,  String content ) {//ngram 2~3
//		json.put("title", title);
		json.put("content", content);
//		json.put("newspaper", newspaper);
		insert(json, "news_article_four", "news_article_four", String.valueOf(newsArticleId));
	}
	public void elasticInsertFive(int newsArticleId,  String content ) {//ngram 2~3
//		json.put("title", title);
		json.put("content", content);
//		json.put("newspaper", newspaper);
		insert(json, "news_article_five", "news_article_five", String.valueOf(newsArticleId));
	}
	
	public void elasticInsertAnalyzer(int newsArticleId, String title, String content,String newspaper ) {
		json.put("title", title);
		json.put("content", content);
		json.put("newspaper", newspaper);
		insert(json, "analyzer_test", "analyzer_test", String.valueOf(newsArticleId));
	}
	
	public void testInsert(int newsArticleId,  String content ) {
//		json.put("title", title);
		json.put("content", content);
//		json.put("newspaper", newspaper);
		insert(json, "analyzer_test", "analyzer_test", String.valueOf(newsArticleId));
	}
	
	public void tsetUpdate(int newsArticleId , String title) throws InterruptedException, ExecutionException {
		json.put("title", title);
		update(json, "analyzer_test", "analyzer_test", String.valueOf(newsArticleId));
	}
	
	public void close() {
		// on shutdown
		client.close();
	}

	public static IndexResponse insert(Map<String, Object> json, String index, String type, String id) {
		if (id == null || "".equals(id)) {
			return client.prepareIndex(index, type).setSource(json).get();
		}
		return client.prepareIndex(index, type, id).setSource(json).get();
	}

	public void upsert(Map<String, Object> exist, Map<String, Object> notExist, String index, String type, String id)
			throws InterruptedException, ExecutionException {
		IndexRequest indexRequest = new IndexRequest(index, type, id).source(notExist);
		UpdateRequest updateRequest = new UpdateRequest(index, type, id).doc(exist).upsert(indexRequest);
		client.update(updateRequest);
	}

	UpdateRequest updateRequest = new UpdateRequest();
	public void update(Map<String, Object> json, String index, String type, String id)
			throws InterruptedException, ExecutionException {
		updateRequest.index(index);
		updateRequest.type(type);
		updateRequest.id(id);
		updateRequest.doc(json);
		client.update(updateRequest).get();
	}
	
	public GetResponse search(String index, String type, String id) {
		return client.prepareGet(index, type, id).get();
	}
	
	public DeleteResponse remove(String index, String type, String id) {
		return client.prepareDelete(index, type, id).get();
	}
	
}
