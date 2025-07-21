package handler;

import entity.Book;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class bookApiHandler {
  String clientID = "tqo7kxREe4GFC4lx0fAK";
  String secret = "jcYW9V3xwB";
	static int counter;

	public String call(String query) throws IOException {
		String encoded = URLEncoder.encode(query, "UTF-8");
		String url = "https://openapi.naver.com/v1/search/book.json?query=" + encoded + "&display=100";

		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("X-Naver-Client-Id", clientID);
		conn.setRequestProperty("X-Naver-Client-Secret", secret);

		int code = conn.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code: " + code);
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}
	}

	public List<Book> fetchBooks(String query) throws IOException {
		String json = call(query);
		return StringJSON2List(json);
	}

  public List<Book> StringJSON2List(String str) {
		JSONObject jObject = new JSONObject(str);
		JSONArray jArray = (JSONArray) jObject.get("items");
		List<Book> list = new ArrayList<>();

		for (int i = 0; i < jArray.length(); i++) {
			JSONObject item = (JSONObject) jArray.get(i);
			String title = item.getString("title");
			String author = item.getString("author");
			String isbn = item.getString("isbn");
			String publisher = item.getString("publisher");
			String description = item.getString("description");
			Integer discount = item.getInt("discount");

			Book book = new Book(String.format("%04d", ++counter), title, author, publisher, isbn, description, discount,0);
			list.add(book);
			System.out.println(list);
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		bookApiHandler bah = new bookApiHandler();
		String[] queries = { "전래동화", "서양고전", "철학", "개발" };
		List<Book> results = new ArrayList<>();

		for (String q : queries) {
			try {
				results.addAll(bah.fetchBooks(q)); // 여기서 Book 리스트 반환 받음
			} catch (IOException e) {
				System.err.println("API 호출 실패: " + q);
				e.printStackTrace();
			}
		}
		results.forEach(System.out::println);

		File file = new File("src/main/resources/data.ser");
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(results);
			System.out.println("SYSTEM :: data.ser 저장 완료 -> " + file.getAbsolutePath());
		}
	}

}
