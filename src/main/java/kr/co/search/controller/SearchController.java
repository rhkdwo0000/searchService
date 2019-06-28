package kr.co.search.controller;

import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.search.db.NewsArticleDAO2;
import kr.co.search.db.NewsArticleDTO2;

@Controller
public class SearchController {

	private Logger log = LoggerFactory.getLogger("kr.co.search.controller");
	
	@Autowired
	@Qualifier("newsarticleDAO")
	private NewsArticleDAO2 NewsArticleDAO;

	@RequestMapping("joe/paging") 
	public String name(@RequestParam("search") String text,
			@RequestParam(value = "content" ,required = false) String content,
			@RequestParam(value = "title" ,required = false) String title,
			@RequestParam(value ="year",required = false) String year,
			@RequestParam(value ="month",required = false) String month,
			@RequestParam(value ="day",required = false) String day,
			@RequestParam(value ="yearSecond",required = false) String yearSecond,
			@RequestParam(value ="monthSecond",required = false) String monthSecond,
			@RequestParam(value ="daySecond",required = false) String daySecond,
			@RequestParam("page") String page,
			@RequestParam(value ="newspaper",required = false) ArrayList<String> newspaper,
			HttpSession session,
			Model model) {
		
		System.out.println(text);
		System.out.println(content);
		System.out.println(title);
		System.out.println(year);
		System.out.println(month);
		
		
		
		
		
		model.addAttribute("text", text);
		model.addAttribute("content", content);
		model.addAttribute("title", title);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("yearSecond", yearSecond);
		model.addAttribute("monthSecond", monthSecond);
		model.addAttribute("daySecond", daySecond);
		model.addAttribute("newspaper", newspaper);
		model.addAttribute("page", page);
		model.addAttribute("list",session.getAttribute("list") );
		model.addAttribute("dto",session.getAttribute("dto") );
		model.addAttribute("total",session.getAttribute("total") );
		
		
		return "joe/search";
	}
	
	
	
	@RequestMapping("joe/search")
	public String searchResult(@RequestParam("search") String text,
			@RequestParam(value = "content" ,required = false) String content,
			@RequestParam(value = "title" ,required = false) String title,
			@RequestParam(value ="year",required = false) String year,
			@RequestParam(value ="month",required = false) String month,@RequestParam(value ="day",required = false) String day,
			@RequestParam(value ="yearSecond",required = false) String yearSecond,
			@RequestParam(value ="monthSecond",required = false) String monthSecond,
			@RequestParam("page") String page,
			@RequestParam(value ="daySecond",required = false) String daySecond,
			@RequestParam(value ="newspaper",required = false) ArrayList<String> newspaper,
			HttpSession session,
			Model model) throws Exception {

		System.out.println("내용 : "+text);
		System.out.println("내용 : "+content);
		System.out.println("제목 : "+title);
		System.out.println("시작년도 : "+year);
		System.out.println("시작월 : "+month);
		System.out.println("시작날 : "+day);
		System.out.println("끝년도 : "+yearSecond);
		System.out.println("끝월 : "+monthSecond);
		System.out.println("끝날 : "+daySecond);
		System.out.println("페이지 : "+page);
		
		 int newspaperTemp = NewsArticleDAO.newspaperCount();
		
//		log.debug("------------------- INDEX NEW ComplexContent -------------------");
//		log.debug("text["+text+"] , content["+content+"] , title["+title+"]");
		
		
		
		
		String start = null;
		String end = null;
		if (month.length()==1) {
			if (day.length()==1) {
				start = year +"-0"+month + "-0" +day;
			}else {
				start = year +"-0"+month + "-" +day;
			}
		}else {
			if (day.length()==1) {
				start = year +"-"+month + "-0" +day;
			}else {
				start = year +"-"+month + "-" +day;
			}
		}
		
		if (monthSecond.length()==1) {
			if (daySecond.length()==1) {
				end =yearSecond +"-0"+monthSecond + "-0" +daySecond;
			}else {
				end =yearSecond +"-0"+monthSecond + "-" +daySecond;
			}
		}else {
			if (daySecond.length()==1) {
				end =yearSecond +"-"+monthSecond + "-0" +daySecond;
			}else {
				end =yearSecond +"-"+monthSecond + "-" +daySecond;
			}
		}
		
	
		
		
		System.out.println(start);
		System.out.println(end);
		
		System.out.println(newspaper.toString());
		ArrayList<String> temp2 = new ArrayList<String>();
		for (int i = 0; i < newspaper.size(); i++) {
			
			String a = "\"";
			String b = a.concat(newspaper.get(i));
			System.out.println(b.concat(a));
			temp2.add(b.concat(a));
		}
	
		model.addAttribute("text", text);
		model.addAttribute("content", content);
		model.addAttribute("title", title);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("day", day);
		model.addAttribute("yearSecond", yearSecond);
		model.addAttribute("monthSecond", monthSecond);
		model.addAttribute("daySecond", daySecond);
		model.addAttribute("newspaper", newspaper);
		
		if (!(page.equals("null")) ) {
			model.addAttribute("page", page);
		}
		
		
		
		JSONObject e_jsonobject = new JSONObject();
		JSONParser e_jsonparser = new JSONParser();
		double startTime = System.currentTimeMillis();
		
		RestClient restClient = RestClient
				.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9205, "http")).build();

//		System.out.println(text);
//		"{\n" 
//		+ "    \"from\" : 0," + "    \"size\" : 10," 
//		+ "    \"query\" : {\n"
//		+ "    \"multi_match\": { \"query\":\"" + text + "\","
//		+ " 	\"fields\": [ \"title\", \"content\", \"newspaper\"]"
//		+ "} \n" 
//		+ "} \n" 
//		+ "}",
//		
		HttpEntity entity1 = null;
		
		if (content != null && title !=null && newspaper.size() == newspaperTemp ) {
			System.out.println("1");
			entity1 = new NStringEntity(
					"{\n" +
							"    \"from\" : 0," +
							"    \"size\" : 1000," +
							"    \"query\" : {\n" +
							"    \"bool\" : {\n" +
							"    \"must\" : [{\n" +
							"    \"term\" : {\n" +
							"    \"title\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"term\" : {\n" +
							"    \"content\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"terms\" : {\n" +
							"    \"newspaper\" : " + temp2.toString() + "}\n" +
							"}],\n"+
							" \"filter\": \n"+
							"{   \"range\" : {\n" +
						    "    \"release_date\" : {\n" +
						    "    \"gte\" : \"" + start + "\"," +
						    "    \"lte\" : \"" + end + "\"" +
						    "}\n"+
						    "}\n"+
						    "}\n"+
							"}\n"+
							"}\n"+
							"}",
				ContentType.APPLICATION_JSON);
		}else if(content != null && title ==null && newspaper.size() == newspaperTemp){
			System.out.println("2");
				entity1 = new NStringEntity(				
						"{\n" +
								"    \"from\" : 0," +
								"    \"size\" : 1000," +
								"    \"query\" : {\n" +
								"    \"bool\" : {\n" +
								"    \"must\" : [{\n" +
								"    \"term\" : {\n" +
								"    \"content\" : \"" +text+ "\"}\n"+
								"},\n"+
								"{   \"terms\" : {\n" +
								"    \"newspaper\" : " + temp2.toString() + "}\n" +
								"}],\n"+
								" \"filter\": \n"+
								"{   \"range\" : {\n" +
							    "    \"release_date\" : {\n" +
							    "    \"gte\" : \"" + start + "\"," +
							    "    \"lte\" : \"" + end + "\"" +
							    "}\n"+
							    "}\n"+
							    "}\n"+
								"}\n"+
								"}\n"+
								"}",
				ContentType.APPLICATION_JSON);
		}else if(content == null && title !=null && newspaper.size() == newspaperTemp) {
			System.out.println("3");
			entity1 = new NStringEntity(
					"{\n" +
							"    \"from\" : 0," +
							"    \"size\" : 1000," +
							"    \"query\" : {\n" +
							"    \"bool\" : {\n" +
							"    \"must\" : [{\n" +
							"    \"term\" : {\n" +
							"    \"title\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"terms\" : {\n" +
							"    \"newspaper\" : " + temp2.toString() + "}\n" +
							"}],\n"+
							" \"filter\": \n"+
							"{   \"range\" : {\n" +
						    "    \"release_date\" : {\n" +
						    "    \"gte\" : \"" + start + "\"," +
						    "    \"lte\" : \"" + end + "\"" +
						    "}\n"+
						    "}\n"+
						    "}\n"+
							"}\n"+
							"}\n"+
							"}",
				ContentType.APPLICATION_JSON);
		}else if(content != null && title !=null && newspaper.size() != newspaperTemp && !(title.equals("null")) && !(content.equals("null"))) {
		System.out.println("4");
//			entity1 = new NStringEntity(
//					"{\n" +
//					"    \"from\" : 0," +
//				    "    \"size\" : 100," +
//				    "    \"query\" : {\n" +
//				    "    \"bool\" : {\n" +
//				    "    \"must\" : [{\n" +
//				    "    \"term\" : {\n" +
//				    "    \"title\" : \"" +text+ "\"}\n"+
//				    "},\n"+
//				    "{   \"term\" : {\n" +
//				    "    \"content\" : \"" +text+ "\"}\n"+
//				    "},\n"+
//				    "{   \"terms\" : {\n" +
//				    "    \"newspaper\" : " + temp2.toString() + "}\n" +
//				    "}]\n"+
//				    "}\n"+
//				    "}\n"+
//				    "}",
//				    ContentType.APPLICATION_JSON);     
			entity1 = new NStringEntity(
					"{\n" +
							"    \"from\" : 0," +
							"    \"size\" : 1000," +
							"    \"query\" : {\n" +
							"    \"bool\" : {\n" +
							"    \"must\" : [{\n" +
							"    \"term\" : {\n" +
							"    \"title\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"term\" : {\n" +
							"    \"content\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"terms\" : {\n" +
							"    \"newspaper\" : " + temp2.toString() + "}\n" +
							"}],\n"+
							" \"filter\": \n"+
							"{   \"range\" : {\n" +
						    "    \"release_date\" : {\n" +
						    "    \"gte\" : \"" + start + "\"," +
						    "    \"lte\" : \"" + end + "\"" +
						    "}\n"+
						    "}\n"+
						    "}\n"+
							"}\n"+
							"}\n"+
							"}",
							ContentType.APPLICATION_JSON);     
			
		
//		entity1 = new NStringEntity(
//					"{\n" +
//							"    \"from\" : 0," +
//							"    \"size\" : 1000," +
//							"    \"query\" : {\n" +
//							"    \"range\" : {\n" +
//							"    \"release_date\" : {\n" +
//							"    \"gte\" : \"" + start + "\"," +
//							"    \"lte\" : \"" + end + "\"" +
//							"}\n"+
//							"}\n"+
//							"}\n"+
//							"}",
//							ContentType.APPLICATION_JSON);     
//			
    
			
		}else if((content != null && title ==null && newspaper.size() != newspaperTemp) || (content != null && title.equals("null") && newspaper.size() !=112)) {
			System.out.println("5");
			entity1 = new NStringEntity(
					"{\n" +
							"    \"from\" : 0," +
							"    \"size\" : 1000," +
							"    \"query\" : {\n" +
							"    \"bool\" : {\n" +
							"    \"must\" : [{\n" +
							"    \"term\" : {\n" +
							"    \"content\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"terms\" : {\n" +
							"    \"newspaper\" : " + temp2.toString() + "}\n" +
							"}],\n"+
							" \"filter\": \n"+
							"{   \"range\" : {\n" +
						    "    \"release_date\" : {\n" +
						    "    \"gte\" : \"" + start + "\"," +
						    "    \"lte\" : \"" + end + "\"" +
						    "}\n"+
						    "}\n"+
						    "}\n"+
							"}\n"+
							"}\n"+
							"}",
				    ContentType.APPLICATION_JSON); 
			
		}else if((content == null && title !=null && newspaper.size() != newspaperTemp) || (content.equals("null") && title !=null && newspaper.size() !=112)) {
			System.out.println("6");
			entity1 = new NStringEntity(
					"{\n" +
							"    \"from\" : 0," +
							"    \"size\" : 1000," +
							"    \"query\" : {\n" +
							"    \"bool\" : {\n" +
							"    \"must\" : [{\n" +
							"    \"term\" : {\n" +
							"    \"title\" : \"" +text+ "\"}\n"+
							"},\n"+
							"{   \"terms\" : {\n" +
							"    \"newspaper\" : " + temp2.toString() + "}\n" +
							"}],\n"+
							" \"filter\": \n"+
							"{   \"range\" : {\n" +
						    "    \"release_date\" : {\n" +
						    "    \"gte\" : \"" + start + "\"," +
						    "    \"lte\" : \"" + end + "\"" +
						    "}\n"+
						    "}\n"+
						    "}\n"+
							"}\n"+
							"}\n"+
							"}",
				    ContentType.APPLICATION_JSON); 
			
		}
		
		Response response = restClient.performRequest("post", "/news_article_openkorea/news_article_openkorea/_search",
				Collections.singletonMap("pretty", "true"), entity1);

		restClient.close();
		
		String temp = EntityUtils.toString(response.getEntity());// getEntity는 스트림형태로 가져오기 때문에 딱 한번만 읽고 다시 돌아갈 수 없다.

		e_jsonobject = (JSONObject) e_jsonparser.parse(temp);

		JSONArray jsonarray = ((JSONArray) ((JSONObject) (e_jsonobject.get("hits"))).get("hits"));

		session.setAttribute("total", jsonarray.size());
		 model.addAttribute("total", jsonarray.size());
		 System.out.println("사이즈 : "+jsonarray.size());
		ArrayList<NewsArticleDTO2> list = new ArrayList<NewsArticleDTO2>();
		NewsArticleDTO2 dto = null;
		for (int i = 0; i < jsonarray.size(); i++) {

//     		System.out.println(((JSONObject)jsonarray.get(i)).get("_id"));

			dto = NewsArticleDAO.searchSelect((String) ((JSONObject) jsonarray.get(i)).get("_id"));
			
			
//			System.out.println(dto.getCrawling_url());
//			System.out.println(dto.getTitle());
//			System.out.println(dto.getNewspaper());
			
			if (dto.getContent().length()>50) {
				dto.setContent(dto.getContent().substring(0, 49));
			}else {
				dto.setContent(dto.getContent().substring(0, dto.getContent().length()-1));
			}
			
			dto.setScore((Double)((JSONObject) jsonarray.get(i)).get("_score"));
			list.add(dto);
		}

		double endTime = System.currentTimeMillis();
		
		NewsArticleDTO2 dto2 =new NewsArticleDTO2();
		dto2.setTimeTaken((endTime-startTime)/1000.0);
		session.setAttribute("list", list);
		session.setAttribute("dto", dto2);
		
		model.addAttribute("list", session.getAttribute("list"));
		model.addAttribute("dto", dto2);
		
		return "joe/search";
	}

}
