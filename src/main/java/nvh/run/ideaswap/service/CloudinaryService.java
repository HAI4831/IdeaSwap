package nvh.run.ideaswap.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import nvh.run.ideaswap.common.utils.CloudinaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        Map uploadResult;
        try {
            uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "image")); // Chỉ định loại file là ảnh
        } catch (IOException e) {
            throw new RuntimeException("upload image failed",e);
        }
        return uploadResult.get("secure_url").toString(); // Trả về URL bảo mật của hình ảnh
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
}
