### **📌 Sự khác biệt giữa kiểu dữ liệu nguyên thủy (primitive) và không nguyên thủy (non-primitive) trong Java**

| Đặc điểm | Kiểu nguyên thủy (Primitive) | Kiểu không nguyên thủy (Non-Primitive) |
|----------|-----------------------------|----------------------------------------|
| **Định nghĩa** | Các kiểu dữ liệu cơ bản có sẵn trong Java | Các kiểu dữ liệu phức tạp được tạo ra từ các lớp |
| **Lưu trữ trong bộ nhớ** | Stack (ngăn xếp) | Heap (bộ nhớ động) |
| **Giá trị mặc định** | Có (ví dụ: `int` mặc định là `0`) | `null` (vì là đối tượng) |
| **Có thể là `null` không?** | ❌ Không thể | ✅ Có thể (vì là Object) |
| **Hỗ trợ phương thức (methods)** | ❌ Không có | ✅ Có (vì là Object) |
| **Tốc độ xử lý** | Nhanh hơn do lưu trữ trên Stack | Chậm hơn do cần tham chiếu đến bộ nhớ Heap |
| **So sánh bằng `==`** | So sánh giá trị thực | So sánh địa chỉ tham chiếu (trừ khi override `equals()`) |
| **Có thể sử dụng với Generics không?** | ❌ Không thể (ví dụ: `List<int>` không hợp lệ) | ✅ Có thể (ví dụ: `List<Integer>` hợp lệ) |

---

## **1️⃣ Kiểu nguyên thủy (Primitive types)**
Các kiểu dữ liệu nguyên thủy là những kiểu dữ liệu cơ bản, không có phương thức đi kèm.

| Kiểu dữ liệu | Kích thước | Giá trị mặc định |
|-------------|-----------|----------------|
| `byte` | 8 bit | `0` |
| `short` | 16 bit | `0` |
| `int` | 32 bit | `0` |
| `long` | 64 bit | `0L` |
| `float` | 32 bit | `0.0f` |
| `double` | 64 bit | `0.0d` |
| `char` | 16 bit | `'\u0000'` (ký tự rỗng) |
| `boolean` | 1 bit | `false` |

🔹 **Ví dụ về kiểu nguyên thủy**:
```java
int a = 10;
double b = 5.5;
boolean isActive = true;
```
🔹 **Không thể gán `null` cho kiểu nguyên thủy**:
```java
int x = null; // ❌ Lỗi biên dịch
```

---

## **2️⃣ Kiểu không nguyên thủy (Non-primitive types)**
Các kiểu dữ liệu không nguyên thủy là **đối tượng** hoặc **mảng**, do đó chúng có nhiều phương thức đi kèm.

Ví dụ: `String`, `Integer`, `Double`, `Boolean`, `List`, `Map`, `Array`, `Class`, v.v.

🔹 **Ví dụ về kiểu không nguyên thủy**:
```java
Integer num = 100;       // Lớp wrapper của int
Double pi = 3.14159;     // Lớp wrapper của double
String text = "Hello";   // String là một đối tượng
List<Integer> numbers = new ArrayList<>();
```
🔹 **Có thể gán `null` cho kiểu không nguyên thủy**:
```java
Integer x = null; // ✅ Hợp lệ
```

---

## **3️⃣ Boxing & Unboxing (Chuyển đổi giữa primitive và non-primitive)**
Java hỗ trợ tự động chuyển đổi giữa kiểu nguyên thủy và đối tượng của nó (gọi là **Autoboxing** và **Unboxing**).

🔹 **Autoboxing**: Chuyển từ **primitive** → **non-primitive**
```java
int a = 10;
Integer b = a; // Autoboxing (int -> Integer)
```

🔹 **Unboxing**: Chuyển từ **non-primitive** → **primitive**
```java
Integer c = 20;
int d = c; // Unboxing (Integer -> int)
```

---

## **🚀 Khi nào dùng Primitive và Non-Primitive?**
✅ **Dùng kiểu nguyên thủy khi:**
- Cần hiệu suất cao hơn (vì nó nhanh hơn do lưu trên Stack).
- Dữ liệu không cần hỗ trợ các phương thức nâng cao.
- Không cần gán `null`.

✅ **Dùng kiểu không nguyên thủy khi:**
- Cần sử dụng Generics (ví dụ: `List<Integer>`, không thể dùng `List<int>`).
- Cần phương thức đi kèm (`Integer.parseInt()`, `Double.toString()`...).
- Có khả năng giá trị có thể `null`.

---

### **🔎 Ví dụ thực tế về lỗi khi dùng kiểu nguyên thủy**
Bạn gặp lỗi khi request thiếu trường `score`, vì:
```java
private double score; // ❌ Không thể nhận null nếu thiếu trong request
```
➡ **Cách khắc phục:**
```java
private Double score; // ✅ Có thể nhận null nếu request thiếu
```

---

## **🎯 Kết luận**
| Thuộc tính | Kiểu nguyên thủy (Primitive) | Kiểu không nguyên thủy (Non-Primitive) |
|------------|-----------------------------|----------------------------------------|
| Bộ nhớ | Stack | Heap |
| Hiệu suất | Nhanh hơn | Chậm hơn |
| Giá trị mặc định | Có (0, `false`, `\u0000`) | `null` |
| Hỗ trợ Generics | ❌ Không | ✅ Có |
| Có thể là `null` không? | ❌ Không thể | ✅ Có thể |
| Hỗ trợ phương thức | ❌ Không | ✅ Có |

Bạn có muốn mình giải thích thêm điểm nào không? 🚀