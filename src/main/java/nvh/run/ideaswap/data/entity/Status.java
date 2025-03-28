package nvh.run.ideaswap.data.entity;

public enum Status {
   approved("approved"),
   pending("pending"),
   reject("reject");

   private final String value;

   Status() {
      this.value = name();  // Lấy tên enum làm giá trị mặc định
   }

   Status(String value) {
      this.value = value.toLowerCase();  // Gán giá trị, chuyển thành chữ thường
   }

   public void setValue(String value) {
      Status.valueOf(value.toLowerCase());  // Chuyển đổi chữ thường thành chữ hoa
   }

   public String getValue() {
      return value;
   }
}
