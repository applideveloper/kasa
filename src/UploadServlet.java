package jp.co.staffnet.html5.photo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {

	public void doPost(HttpServletRequest req,
		HttpServletResponse resp)
        throws ServletException, IOException {

		//ブラウザからデータ受信
		String title = sanitize(req.getParameter("title"));
		String comment = sanitize(req.getParameter("comment"));

		//Blobストアから写真の保存ID取得
		BlobstoreService blobstoreService =
				BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> blobs =
			blobstoreService.getUploads(req);
		BlobKey blobKey = blobs.get("photo").get(0);

		//画像処理の準備
		ImagesService imagesService =
			ImagesServiceFactory.getImagesService();
		Image originalImage =
			ImagesServiceFactory.makeImageFromBlob(blobKey);

		//縮小画像データ作成(800x800)
		Transform resize = ImagesServiceFactory
						.makeResize(800, 800);
		Image newImage = imagesService
						.applyTransform(resize, originalImage,
										OutputEncoding.JPEG);
		byte[] smallimage = newImage.getImageData();

		//サムネイルデータ作成(80x80)
		resize = ImagesServiceFactory.makeResize(80, 80);
		newImage = imagesService
				.applyTransform(resize, originalImage,
									OutputEncoding.JPEG);
		byte[] thumnail = newImage.getImageData();

		//登録者の情報取得
		UserService userService = UserServiceFactory
							.getUserService();
		User user = userService.getCurrentUser();
		String mailAddress = user.getEmail();

		//登録日の取得
		Date registDate = new Date();

		//データストアへ保存
		DatastoreService datastore = DatastoreServiceFactory
								.getDatastoreService();
		Entity entity = new Entity("PhotoLibrary");
		entity.setProperty("title",title);
		entity.setProperty("comment",comment);
		entity.setProperty("smallimage",new Blob(smallimage));
		entity.setProperty("thumnail",new Blob(thumnail));
		entity.setProperty("mailAddress",mailAddress);
		entity.setProperty("registDate",registDate);
		datastore.put(entity);

		//end.jspへ画面遷移
		resp.sendRedirect("/end.jsp");

	}


	//サニタイズ処理
	private static String sanitize(String str){
		if(str == null){
			return str;
		}
		str = str.replaceAll("&" , "&amp;" );
		str = str.replaceAll("<" , "&lt;"  );
		str = str.replaceAll(">" , "&gt;"  );
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("'" , "&#39;" );
		return str;
	}


}
