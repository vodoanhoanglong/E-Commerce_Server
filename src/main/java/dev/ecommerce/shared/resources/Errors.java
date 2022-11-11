package dev.ecommerce.shared.resources;

public enum Errors {
    PermissionDenied("permission_denied", "Không có quyền truy cập"),
    UserNotFound("user_not_found", "Không tìm thấy người dùng"),
    ProductAlreadyExist("product_already_exist", "Sản phẩm đã tồn tại!");

    private final String key;
    private final String value;

    Errors(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }
}

