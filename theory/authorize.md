[](#cách-1)
[](#cách-2)
[](#cách-3)

## cách 1
Để phân quyền theo phương thức HTTP (GET, POST, PUT, DELETE...) thay vì chỉ dựa trên endpoint, bạn có thể sử dụng API của Spring Security kết hợp với `authorizeHttpRequests()` để chỉ định quyền cho các phương thức cụ thể.

Dưới đây là cách chỉnh sửa cấu hình của bạn để phân quyền theo phương thức HTTP:

---

### Cấu hình phân quyền theo phương thức
Bạn có thể sử dụng `.requestMatchers(HttpMethod.GET, "/api/v1/products/*").hasAuthority("user")` để chỉ định quyền truy cập cho phương thức HTTP cụ thể.

Dưới đây là một ví dụ hoàn chỉnh dựa trên cấu hình của bạn:

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    // Các endpoint công khai
                    .requestMatchers(
                            "/ping",
                            "/graphql",
                            "/graphiql",
                            "/user/register",
                            "/api/products",
                            "api/v1/auth/login",
                            "/api/v1/auth/register",
                            "/api/v1/auth/registerApi",
                            "/swagger-ui.html",
                            "/swagger*/*",
                            "/api/v1/admin/auth/register",
                            "/api/v1/admin/auth/login",
                            "/api/v1/admin/auth/refresh"
                    ).permitAll()

                    // Phân quyền theo phương thức HTTP
                    .requestMatchers(HttpMethod.GET, "/api/v1/products/*").hasAuthority("user")
                    .requestMatchers(HttpMethod.POST, "/api/v1/products/*").hasAuthority("manager")
                    .requestMatchers(HttpMethod.PUT, "/api/v1/products/*").hasAuthority("manager")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/products/*").hasAuthority("admin")

                    .requestMatchers(HttpMethod.GET, "/api/v1/banner/*").hasAuthority("user")
                    .requestMatchers(HttpMethod.POST, "/api/v1/banner/*").hasAuthority("manager")

                    .requestMatchers("/api/v1/admin/*").hasAuthority("manager")
                    .requestMatchers("/admin/*").hasAuthority("ADMIN")
                    .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
                    
                    // Các endpoint khác yêu cầu xác thực
                    .anyRequest().authenticated()
            );

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.authenticationProvider(customAuthenticationProvider);
    http.exceptionHandling(ex -> ex.authenticationEntryPoint(new AuthEntryPointJwt()));

    http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/home")
            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
            .addLogoutHandler(new CustomLogoutHandler())
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
    );

    return http.build();
}
```

---

### Giải thích:
1. **Phân quyền theo phương thức HTTP**:
    - Sử dụng `HttpMethod.GET`, `HttpMethod.POST`, `HttpMethod.PUT`, `HttpMethod.DELETE` để chỉ định phương thức.
    - Ví dụ:
        - `HttpMethod.GET`: Chỉ cho phép đọc dữ liệu.
        - `HttpMethod.POST`: Chỉ cho phép tạo mới dữ liệu.
        - `HttpMethod.PUT`: Chỉ cho phép cập nhật dữ liệu.
        - `HttpMethod.DELETE`: Chỉ cho phép xóa dữ liệu.

2. **Ví dụ cụ thể**:
    - `/api/v1/products/*`:
        - Người dùng có quyền `"user"` được phép **GET**.
        - Người dùng có quyền `"manager"` được phép **POST** và **PUT**.
        - Người dùng có quyền `"admin"` được phép **DELETE**.

3. **Kết hợp quyền**:
   Bạn có thể sử dụng `.hasAnyAuthority("role1", "role2")` nếu một endpoint cần nhiều quyền.

4. **Cấu hình mặc định**:
    - Sử dụng `.anyRequest().authenticated()` để yêu cầu xác thực với các endpoint khác không được chỉ định cụ thể.

---

### Debug và kiểm tra:
Để kiểm tra xem cấu hình có hoạt động như mong đợi hay không:
- Đảm bảo log mức `DEBUG` đã bật:
```properties
logging.level.org.springframework.security=DEBUG
```
- Gửi các request thử nghiệm qua Postman hoặc Curl:
    - Kiểm tra với các HTTP method khác nhau (GET, POST, PUT, DELETE).
    - Đảm bảo JWT token hoặc session đúng quyền được đính kèm trong request.

---

Nếu bạn gặp bất kỳ lỗi gì trong cấu hình hoặc logic, hãy gửi thông tin log để mình hỗ trợ thêm nhé! 😊

Ngoài cách phân quyền theo phương thức HTTP thông qua Spring Security như mình đã trình bày, bạn còn một số cách khác để kiểm soát quyền truy cập dựa trên phương thức, vai trò hoặc các yếu tố khác trong Spring Security:

---
## cách 2
các cách phân quyền khác
### 1. **Phân quyền với `@PreAuthorize` và `@PostAuthorize`**
Sử dụng annotation trên các phương thức của controller hoặc service.

#### Ví dụ:
```java
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        // Logic lấy sản phẩm
        return productService.getProductById(id);
    }

    @PreAuthorize("hasAuthority('manager')")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        // Logic tạo sản phẩm
        return productService.createProduct(product);
    }

    @PreAuthorize("hasAuthority('manager')")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        // Logic cập nhật sản phẩm
        return productService.updateProduct(id, product);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        // Logic xóa sản phẩm
        productService.deleteProduct(id);
    }
}
```

- **Ưu điểm**:
    - Dễ đọc và trực tiếp trên từng phương thức.
    - Có thể kết hợp với biểu thức Spring EL để kiểm tra điều kiện phức tạp (ví dụ: kiểm tra giá trị trong DTO hoặc tham số đầu vào).
- **Nhược điểm**:
    - Annotation dày đặc nếu có nhiều phương thức cần phân quyền khác nhau.

---

### 2. **Phân quyền bằng cách tạo `Custom Filter`**
Tự tạo một `Filter` để kiểm tra quyền dựa trên HTTP method, URL hoặc các yếu tố khác.

#### Ví dụ:
```java
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String method = request.getMethod();
        String path = request.getRequestURI();

        // Logic kiểm tra quyền dựa trên method và path
        if (method.equals("POST") && path.startsWith("/api/v1/products")) {
            // Kiểm tra quyền "manager"
            if (!request.isUserInRole("manager")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Access Denied");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

- **Ưu điểm**:
    - Linh hoạt cao, có thể kiểm soát chi tiết đến từng request.
- **Nhược điểm**:
    - Tự viết toàn bộ logic kiểm tra, dễ lỗi nếu không cẩn thận.
    - Phải tích hợp vào chuỗi `Filter` của Spring Security.

---

### 3. **Phân quyền bằng cách tùy chỉnh `AccessDecisionVoter`**
Tạo custom `AccessDecisionVoter` để quyết định quyền truy cập dựa trên logic của bạn.

#### Ví dụ:
```java
@Component
public class CustomAccessDecisionVoter implements AccessDecisionVoter<Object> {

    @Override
    public boolean supports(Class<?> clazz) {
        return true; // Hỗ trợ tất cả các loại request
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true; // Hỗ trợ tất cả các loại thuộc tính
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String method = request.getMethod();

        // Kiểm tra quyền dựa trên method
        if (method.equals("POST") && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("manager"))) {
            return ACCESS_DENIED;
        }
        return ACCESS_GRANTED;
    }
}
```

- **Ưu điểm**:
    - Có thể tái sử dụng trong nhiều tình huống.
    - Tích hợp tốt với hệ thống kiểm soát của Spring Security.
- **Nhược điểm**:
    - Tương đối phức tạp để triển khai ban đầu.

---

### 4. **Sử dụng `MethodSecurityExpressionHandler`**
Tùy chỉnh handler của Spring Security để cung cấp các điều kiện kiểm tra phức tạp hơn.

#### Ví dụ:
```java
@Bean
public MethodSecurityExpressionHandler expressionHandler() {
    DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
    handler.setPermissionEvaluator(new CustomPermissionEvaluator());
    return handler;
}

public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // Logic kiểm tra quyền tùy chỉnh
        if (permission.equals("EDIT_PRODUCT") && authentication.getAuthorities().contains(new SimpleGrantedAuthority("manager"))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false; // Không sử dụng
    }
}
```

- **Ưu điểm**:
    - Có thể kiểm tra quyền phức tạp (theo logic kinh doanh).
- **Nhược điểm**:
    - Tương đối khó hiểu đối với người mới làm quen.

---

### 5. **Phân quyền dựa trên dữ liệu trong Token**
Nếu bạn đang sử dụng JWT hoặc các loại token khác, bạn có thể mã hóa thông tin quyền trong token và kiểm tra chúng trước khi xử lý.

#### Ví dụ:
```java
if (jwtTokenUtil.getRolesFromToken(token).contains("manager") && method.equals("POST")) {
    // Logic xử lý nếu có quyền
}
```

- **Ưu điểm**:
    - Kiểm soát tốt hơn quyền truy cập thông qua token.
- **Nhược điểm**:
    - Cần triển khai thêm logic trong bộ giải mã token.

---

### Tóm tắt:
- **Nếu muốn đơn giản**: Sử dụng `@PreAuthorize` hoặc phân quyền trực tiếp trong `SecurityFilterChain`.
- **Nếu cần kiểm soát chi tiết**: Sử dụng `Custom Filter` hoặc `AccessDecisionVoter`.
- **Nếu cần logic phức tạp**: Sử dụng `PermissionEvaluator` hoặc kiểm tra quyền trong token.

Bạn có thể chọn cách phù hợp nhất tùy thuộc vào yêu cầu cụ thể của dự án. 😊

## cách 3
Ngoài các cách đã nêu ở trên, bạn có thể sử dụng những cách phân quyền khác trong Spring Security. Dưới đây là một số cách tiếp cận bổ sung, mỗi cách có ưu điểm và nhược điểm riêng:

---

### 1. **Phân quyền dựa trên dữ liệu động (`Data-Based Security`)**
Phân quyền không chỉ dựa trên role của user mà còn dựa trên dữ liệu được truy cập. Điều này thường được gọi là **Domain Object Security**.

#### Ví dụ:
Bạn muốn cho phép user chỉ được chỉnh sửa dữ liệu mà họ sở hữu.

```java
@PreAuthorize("#user.id == authentication.principal.id")
public User updateUser(User user) {
    // Logic cập nhật user
    return userRepository.save(user);
}
```

- **Cách hoạt động**:
    - Sử dụng Spring EL để kiểm tra quyền trên đối tượng dữ liệu (trong ví dụ trên là `user`).
    - So sánh dữ liệu của user với thông tin trong `Authentication`.

- **Ưu điểm**:
    - Kiểm soát chi tiết đến từng đối tượng dữ liệu.
- **Nhược điểm**:
    - Đòi hỏi tích hợp chặt chẽ giữa security và logic nghiệp vụ.

---

### 2. **Phân quyền theo nhóm (Role Grouping)**
Thay vì gán trực tiếp quyền vào từng endpoint hoặc role, bạn có thể tạo một cấu trúc nhóm quyền và phân quyền theo nhóm.

#### Cấu hình ví dụ:
```yaml
roles:
  USER:
    - READ_PRODUCT
    - WRITE_COMMENT
  ADMIN:
    - MANAGE_USER
    - MANAGE_PRODUCT
```

#### Áp dụng trong Spring:
```java
@PreAuthorize("hasAuthority('READ_PRODUCT')")
@GetMapping("/products")
public List<Product> getAllProducts() {
    return productService.findAll();
}
```

- **Ưu điểm**:
    - Dễ mở rộng và quản lý khi số lượng role tăng.
    - Tránh lặp lại logic phân quyền.
- **Nhược điểm**:
    - Cần thêm logic để ánh xạ role với quyền.

---

### 3. **Phân quyền với `FilterSecurityInterceptor`**
`FilterSecurityInterceptor` là filter được tích hợp sẵn trong Spring Security, cho phép bạn kiểm tra quyền truy cập từng request.

#### Ví dụ:
Tạo custom `FilterInvocationSecurityMetadataSource`:
```java
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String method = request.getMethod();
        String url = request.getRequestURI();

        if (method.equals("POST") && url.startsWith("/api/v1/products")) {
            return List.of(new SecurityConfig("ROLE_MANAGER"));
        }

        return null; // Không có quyền đặc biệt
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null; // Không cần sử dụng
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
```

- **Ưu điểm**:
    - Linh hoạt, có thể kiểm tra quyền trên từng request.
- **Nhược điểm**:
    - Cần viết nhiều code để cấu hình và triển khai.

---

### 4. **Phân quyền dựa trên event**
Bạn có thể lắng nghe các event của Spring Security để kiểm tra và xử lý quyền.

#### Ví dụ:
Lắng nghe event `AuthenticationSuccessEvent`:
```java
@Component
public class CustomAuthenticationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        System.out.println("User " + authentication.getName() + " đã đăng nhập thành công!");
    }
}
```

- **Ưu điểm**:
    - Phù hợp cho các hệ thống cần logging hoặc xử lý quyền động.
- **Nhược điểm**:
    - Không trực tiếp kiểm tra quyền mà chỉ theo dõi trạng thái.

---

### 5. **Phân quyền bằng `Custom GrantedAuthority`**
Bạn có thể mở rộng `GrantedAuthority` để tạo quyền riêng theo nhu cầu.

#### Ví dụ:
Tạo custom authority:
```java
public class CustomGrantedAuthority implements GrantedAuthority {
    private String permission;

    public CustomGrantedAuthority(String permission) {
        this.permission = permission;
    }

    @Override
    public String getAuthority() {
        return permission;
    }
}
```

Gán quyền trong `UserDetails`:
```java
public class CustomUserDetails implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new CustomGrantedAuthority("READ_PRODUCT"));
    }

    // Các phương thức khác
}
```

- **Ưu điểm**:
    - Phân quyền chi tiết hơn role thông thường.
- **Nhược điểm**:
    - Cần viết thêm code để hỗ trợ.

---

### 6. **Phân quyền dựa trên context (Context-Based Security)**
Sử dụng `SecurityContextHolder` để kiểm tra quyền trong logic nghiệp vụ.

#### Ví dụ:
```java
@Service
public class ProductService {

    public Product updateProduct(Long id, Product product) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("MANAGER"))) {
            return productRepository.save(product);
        } else {
            throw new AccessDeniedException("Bạn không có quyền cập nhật sản phẩm này");
        }
    }
}
```

- **Ưu điểm**:
    - Linh hoạt khi tích hợp với logic nghiệp vụ.
- **Nhược điểm**:
    - Dễ bị lạm dụng và gây khó khăn trong bảo trì.

---

### 7. **Phân quyền thông qua Policy Engine (ABAC)**
Sử dụng giải pháp Attribute-Based Access Control (ABAC) như **OPA (Open Policy Agent)** để quản lý quyền.

#### Cách làm:
1. Xây dựng policy bằng OPA.
2. Tích hợp Spring Security với OPA thông qua HTTP API.

#### Ví dụ:
```json
{
  "allow": true,
  "roles": ["user", "admin"]
}
```

- **Ưu điểm**:
    - Tách biệt rõ ràng logic phân quyền và mã nguồn ứng dụng.
    - Phù hợp với các hệ thống phức tạp.
- **Nhược điểm**:
    - Yêu cầu thêm công cụ và kiến thức để triển khai.

---

### Tóm tắt
- **Cách cơ bản**: Phân quyền dựa trên role hoặc endpoint với `@PreAuthorize` và `HttpSecurity`.
- **Cách nâng cao**: Dùng `Filter`, `AccessDecisionVoter`, hoặc kiểm tra dữ liệu động với Spring EL.
- **Giải pháp mở rộng**: Tích hợp với các công cụ bên ngoài như OPA để quản lý quyền theo chính sách (ABAC).

Bạn cần cân nhắc chọn cách triển khai phù hợp nhất với độ phức tạp và yêu cầu của dự án.