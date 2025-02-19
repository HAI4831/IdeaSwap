dù code không còn ProfileRespone nhưng vẫn xảy ra lỗi ép từ ProfileRespone sang ManagerProfileRespone
vì nó gọi lấy giá trị từ cache cũ và cache chưa thay đổi 
để dùng ổn thì cần gọi cacheEvict khi gọi crd để xóa toàn bộ giá trị của cacheable để nó lấy lại từ db
```java
authManagerService.getManagerProfile();
@Cacheable(value = "profile_response")
public ManagerProfileResponse getManagerProfile()

@GetMapping("/account")
public ResponseEntity<Object> getUserProfile() {
    log.info("Before AuthManagerController.getUserProfile was called");
    ManagerProfileResponse managerProfileResponse = authManagerService.getManagerProfile();
    log.info("After AuthManagerController.getUserProfile was called");
    if( !managerProfileResponse.isAuthenticated()){
        return ResponseEntity.status(401).body(
                Map.of(
                        "success", false,
                        "message", "Manager not authenticated"
                )
        );
    }
    return ResponseEntity.ok(
            Map.of(
                    "success", true,
                    "message", "Retrieve Manager Profile successfully",
                    "user", managerProfileResponse.getManager()
            )
    );
}
@Cacheable(value = "manager_profile_response")
public ManagerProfileResponse getManagerProfile() {
    log.info("start AuthManagerService.getManagerProfile was called ");
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Managers manager = managerService.findManagerByUsername(authentication.getName());
        log.info("AuthManagerService.getManagerProfile was called with manager {}", manager);
        if(manager!=null)return ManagerProfileResponse.builder().manager(manager).authenticated(true).build();


        return ManagerProfileResponse.builder()
                .manager(null)
                .authenticated(false)
                .build();
    } catch (Exception e) {
        throw new RuntimeException("Error retrieving user profile",e);
    }
}
```