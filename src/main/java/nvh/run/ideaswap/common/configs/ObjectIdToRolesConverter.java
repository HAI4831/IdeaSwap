//package nvh.run.ideaswap.common.configs;
//
//import nvh.run.ideaswap.data.entity.Roles;
//import org.bson.types.ObjectId;
//import org.springframework.stereotype.Component;
//import org.springframework.core.convert.converter.Converter;
//
//@Component
//public class ObjectIdToRolesConverter implements Converter<ObjectId, Roles> {
//    @Override
//    public Roles convert(ObjectId source) {
//        // Logic để ánh xạ ObjectId thành Roles
//        Roles roles = new Roles();
//        // Bạn cần điều chỉnh logic để lấy dữ liệu cần thiết từ MongoDB và map sang Roles
//        roles.setId(source.toString());  // Chuyển ObjectId sang String, nếu cần
//        return roles;
//    }
//}
