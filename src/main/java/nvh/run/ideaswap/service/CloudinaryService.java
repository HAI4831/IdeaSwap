package nvh.run.ideaswap.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import nvh.run.ideaswap.common.utils.CloudinaryUtils;
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

    public String uploadImage(String imageBase64,MultipartFile file) {

        Map uploadResult=null;
        try {
            if(imageBase64!=null && !imageBase64.isEmpty()){
                uploadResult = cloudinary.uploader().upload(base64ToByte(imageBase64),
                        ObjectUtils.asMap("resource_type", "image"));
            } else if(file!=null && !file.isEmpty()) {
                uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "image")); // Chỉ định loại file là ảnh
            }
            return uploadResult.get("secure_url").toString(); // Trả về URL bảo mật của hình ảnh
        } catch (Exception e) {
            return null;
//            throw new RuntimeException("upload image failed",e);
        }
    }

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
