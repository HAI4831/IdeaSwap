`@Validated` và `@Valid` đều được sử dụng để kích hoạt cơ chế **validation** trong Spring, nhưng có một số sự khác biệt quan trọng giữa chúng. Dưới đây là sự so sánh giữa hai annotation này:

### **1. `@Valid` (javax.validation)**
- **Nguồn gốc**: `@Valid` là một phần của Java Bean Validation API (JSR 303/JSR 380). Được cung cấp bởi Hibernate Validator, một thư viện tham chiếu cho Java Bean Validation.
- **Công dụng**: Được sử dụng để kích hoạt validation cho các đối tượng. Khi bạn áp dụng `@Valid` lên một trường, phương thức hoặc tham số của phương thức, nó sẽ kích hoạt quá trình validation dựa trên các annotation validation mà bạn đã sử dụng trong lớp DTO (ví dụ: `@NotNull`, `@Size`, `@Min`, v.v.).
- **Giới hạn**: `@Valid` chỉ kích hoạt validation cho đối tượng đã được đánh dấu. Nó không hỗ trợ nhóm validation (validation nhóm).

#### Ví dụ:
```java
@Valid
public class DocumentRequest {
    @NotNull
    private String title;
    @Min(0)
    private Double score;
}
```

### **2. `@Validated` (org.springframework.validation.annotation)**
- **Nguồn gốc**: `@Validated` là một extension của Spring, được thêm vào Spring Framework từ phiên bản 4.0 và được sử dụng để kích hoạt validation giống như `@Valid`, nhưng hỗ trợ tính năng nâng cao như **validation nhóm**.
- **Công dụng**: Giống như `@Valid`, nhưng hỗ trợ việc áp dụng các **nhóm validation**. Điều này có nghĩa là bạn có thể chỉ định các nhóm cụ thể mà bạn muốn áp dụng cho validation (ví dụ: nhóm kiểm tra đối tượng khi tạo mới và nhóm khi cập nhật).
- **Giới hạn**: Nếu bạn không sử dụng nhóm validation, `@Validated` và `@Valid` sẽ hoạt động giống nhau.

#### Ví dụ về nhóm validation với `@Validated`:
```java
public interface CreateGroup {}
public interface UpdateGroup {}

public class DocumentRequest {
    @NotNull(groups = CreateGroup.class)
    private String title;

    @Min(value = 0, groups = UpdateGroup.class)
    private Double score;
}
```
Trong controller:
```java
@PostMapping
public ResponseEntity<?> createDocument(@Validated(CreateGroup.class) @RequestBody DocumentRequest request) {
    // Chỉ validate theo nhóm CreateGroup
    return ResponseEntity.ok(request);
}

@PutMapping
public ResponseEntity<?> updateDocument(@Validated(UpdateGroup.class) @RequestBody DocumentRequest request) {
    // Chỉ validate theo nhóm UpdateGroup
    return ResponseEntity.ok(request);
}
```

### **Các điểm khác biệt chính:**
| Đặc điểm              | `@Valid`                            | `@Validated`                          |
|-----------------------|-------------------------------------|---------------------------------------|
| **Nguồn gốc**          | JSR 303/JSR 380 (Java Bean Validation) | Spring Framework (Spring Validation)  |
| **Hỗ trợ nhóm validation** | Không hỗ trợ nhóm validation        | Hỗ trợ nhóm validation                |
| **Ứng dụng**           | Dùng để kích hoạt validation cho đối tượng | Dùng để kích hoạt validation cho đối tượng, hỗ trợ nhóm validation |
| **Sử dụng**            | Thường dùng cho validation cơ bản   | Dùng khi cần validation với nhóm hoặc nâng cao hơn |

### **Khi nào nên sử dụng `@Valid` và khi nào sử dụng `@Validated`?**
- **`@Valid`**: Sử dụng khi bạn chỉ cần kích hoạt validation cơ bản cho các đối tượng mà không cần phải phân loại nhóm.
- **`@Validated`**: Sử dụng khi bạn cần kiểm tra nhóm validation hoặc muốn sử dụng các tính năng nâng cao của Spring Validation, ví dụ khi bạn muốn áp dụng các điều kiện validation khác nhau cho các tình huống khác nhau (như tạo mới hay cập nhật).

### **Tóm lại**:
- `@Valid` đơn giản và chủ yếu dùng để kích hoạt cơ chế validation cơ bản của JSR 303.
- `@Validated` là một phần mở rộng của Spring, cung cấp khả năng hỗ trợ nhóm validation, cho phép bạn xác định nhóm kiểm tra cho các tình huống khác nhau.