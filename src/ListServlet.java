package jp.co.staffnet.html5.photo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

@SuppressWarnings("serial")
public class ListServlet extends HttpServlet {

	public void doGet(HttpServletRequest req,
			HttpServletResponse resp)
			throws IOException,ServletException {

		//データストアから最新10件のエンティティ取得
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("PhotoLibrary");
		query.addSort("registDate", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(query);
		FetchOptions option = FetchOptions.Builder
							.withDefaults();
		option.offset(0);
		option.limit(10);
		List <Entity> entity = pq.asList(option);

		//取得データをArrayListへ変換
		ArrayList<PhotoData> list =
						new ArrayList<PhotoData>();
		for(Entity ent : entity){

			//エンティティからデータ抽出
			PhotoData data = new PhotoData();
			data.mailAddress = (String)ent
							.getProperty("mailAddress");
			data.title = (String)ent.getProperty("title");
			data.id = ent.getKey().getId();

			//日時データを文字列へ変換
			SimpleDateFormat dateFormat =
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.setTimeZone(
						TimeZone.getTimeZone("Asia/Tokyo"));
			String dateStr = dateFormat.format(
						(Date)ent.getProperty("registDate"));
			data.dateStr = dateStr;

			//イメージデータをBase64形式へ変換
			Blob blob = (Blob)ent.getProperty("thumnail");
			byte[] encoded = Base64
							.encodeBase64(blob.getBytes());
			data.thumnailStr = new String(encoded);

			//リストへ写真データを追加
			list.add(data);
		}

		//JSPへ渡すデータを設定
		req.setAttribute("photoData", list);

		//list.jspへ遷移
		getServletConfig().getServletContext()
				.getRequestDispatcher("/list.jsp")
				.forward(req, resp);

	}
}
