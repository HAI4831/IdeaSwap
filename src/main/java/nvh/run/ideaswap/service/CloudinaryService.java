package nvh.run.ideaswap.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import nvh.run.ideaswap.utils.CloudinaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(String base64Image, MultipartFile file,String folderName) {
        try {
            if (folderName == null || folderName.isEmpty()) {
                folderName = "image/png"; // Giá trị mặc định hợp lệ
            }

            // Khởi tạo các options chung cho cả Base64 và MultipartFile
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "image",
                    "folder", folderName
            );

            // Nếu có file dạng MultipartFile, ưu tiên upload file trước
            if (file != null && !file.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
                return uploadResult.get("secure_url").toString();
            }

            // Nếu không có file, kiểm tra Base64
            if (base64Image != null && !base64Image.isEmpty()) {
                // Loại bỏ tiền tố "data:image/png;base64," nếu có
                String base64Data = base64Image.replaceFirst("^data:image/\\w+;base64,", "");

                // Giải mã Base64 thành mảng byte
                byte[] decodedBytes = Base64.getDecoder().decode(base64Data);

                // Upload lên Cloudinary với cùng options
                Map uploadResult = cloudinary.uploader().upload(decodedBytes, options);
                return uploadResult.get("secure_url").toString();
            }

            return null;
//            throw new IllegalArgumentException("No valid image data provided");
        } catch (Exception e) {
            return null;
//            throw new RuntimeException("Upload image to Cloudinary failed", e);
        }
    }


//    public String uploadImage(String imageBase64,MultipartFile file) {
//
//        Map uploadResult=null;
//        try {
//            if(imageBase64!=null && !imageBase64.isEmpty()){
//                uploadResult = cloudinary.uploader().upload(base64ToByte(imageBase64),
//                        ObjectUtils.asMap("resource_type", "image"));
//            } else if(file!=null && !file.isEmpty()) {
//                uploadResult = cloudinary.uploader().upload(file.getBytes(),
//                        ObjectUtils.asMap("resource_type", "image")); // Chỉ định loại file là ảnh
//            }
//            return uploadResult.get("secure_url").toString(); // Trả về URL bảo mật của hình ảnh
//        } catch (Exception e) {
//            throw new RuntimeException("upload image failed",e);
////            return null;
//        }
//    }

    public String deleteImage(String url , String publicId) {
        if(!url.isEmpty()) publicId = CloudinaryUtils.extractPublicId(url);
        Map result;
        try {
            result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("delete image failed",e);
        }
        return result.get("result").toString(); // Trả về "ok" nếu xóa thành công
    }
    String byteToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }
    byte[] base64ToByte(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
