package jp.co.staffnet.html5.photo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req,
			HttpServletResponse resp)
			throws IOException,ServletException {

		//ブラウザからデータ受信
		Long id = Long.parseLong(req.getParameter("id"));

		//IDを使ったエンティティの検索
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key = KeyFactory.createKey("PhotoLibrary", id);
		Entity ent = null;
		try {
			ent = datastore.get(key);
		} catch (EntityNotFoundException e) {
			System.out.println(e);
		}

		//エンティティのデータ取得と加工
		String title = (String)ent.getProperty("title");
		String comment = (String)ent.getProperty("comment");
		Blob blob = (Blob)ent.getProperty("smallimage");
		byte[] encoded = Base64.encodeBase64(blob.getBytes());
		String imageStr = new String(encoded);

		//JSPへ渡すデータ(文字列)を設定
		req.setAttribute("title", title);
		req.setAttribute("comment", comment);
		req.setAttribute("imageStr", imageStr);

		//detail.jspへ遷移
		getServletConfig().getServletContext()
				.getRequestDispatcher("/detail.jsp")
				.forward(req, resp);

	}
}
