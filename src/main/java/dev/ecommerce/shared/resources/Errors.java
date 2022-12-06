package dev.ecommerce.shared.resources;

public enum Errors {
    PermissionDenied("permission_denied", "Không có quyền truy cập"),
    UserNotFound("user_not_found", "Không tìm thấy người dùng"),
    ProductAlreadyExist("product_already_exist", "Sản phẩm đã tồn tại!"),
    CategoriesAlreadyExist("categories_already_exist", "Danh mục đã tồn tại!"),
    OldPasswordNotCorrect("old_password_not_correct","Mật khẩu cũ không đúng"),
    ProductNotFound("product_not_found", "Không tìm thấy sản phẩm"),
    ShopNotFound("shop_not_found", "Không tìm thấy cửa hàng"),
    ProductNotEnoughOnStore("product_not_enough_on_store", "Sản phẩm trong kho không đủ");

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

