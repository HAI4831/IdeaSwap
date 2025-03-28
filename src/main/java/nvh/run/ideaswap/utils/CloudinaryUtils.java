package nvh.run.ideaswap.utils;

public class CloudinaryUtils {
    public static String extractPublicId(String imageUrl) {
        String regex = ".*/upload/v\\d+/(.*?)\\..*";
        return imageUrl.replaceAll(regex, "$1");
    }
}
