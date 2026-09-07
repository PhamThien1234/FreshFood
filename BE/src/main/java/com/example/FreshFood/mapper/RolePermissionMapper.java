package com.example.FreshFood.mapper;

import com.example.FreshFood.enums.Permission;
import com.example.FreshFood.enums.Role;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public final class RolePermissionMapper {
    private RolePermissionMapper() {
    }

    public static Set<Permission> getPermissions(Role role) {

        return switch (role) {

            case CUSTOMER -> Collections.unmodifiableSet(EnumSet.of(
                    Permission.PRODUCT_VIEW,
                    Permission.PRODUCT_SEARCH,
                    Permission.PRODUCT_FILTER,

                    Permission.FAVORITE_CREATE,
                    Permission.FAVORITE_DELETE,
                    Permission.FAVORITE_VIEW,

                    Permission.CART_VIEW,
                    Permission.CART_CREATE,
                    Permission.CART_UPDATE,
                    Permission.CART_DELETE,

                    Permission.ORDER_CREATE,
                    Permission.ORDER_VIEW_OWN
            ));

            case FARMER -> Collections.unmodifiableSet(EnumSet.of(
                    Permission.PRODUCT_VIEW,
                    Permission.PRODUCT_CREATE,
                    Permission.PRODUCT_UPDATE,
                    Permission.PRODUCT_DELETE,
                    Permission.PRODUCT_IMAGE_UPLOAD,

                    Permission.ORDER_VIEW_OWN,

                    Permission.REVENUE_VIEW_OWN
            ));

            case ADMIN -> Collections.unmodifiableSet(EnumSet.of(
                    Permission.PRODUCT_VIEW,
                    Permission.PRODUCT_SEARCH,
                    Permission.PRODUCT_FILTER,
                    Permission.PRODUCT_CREATE,
                    Permission.PRODUCT_UPDATE,
                    Permission.PRODUCT_DELETE,
                    Permission.PRODUCT_IMAGE_UPLOAD,
                    Permission.PRODUCT_APPROVE,

                    Permission.FAVORITE_CREATE,
                    Permission.FAVORITE_DELETE,
                    Permission.FAVORITE_VIEW,

                    Permission.CART_VIEW,
                    Permission.CART_CREATE,
                    Permission.CART_UPDATE,
                    Permission.CART_DELETE,

                    Permission.ORDER_CREATE,
                    Permission.ORDER_VIEW_OWN,
                    Permission.ORDER_VIEW_ALL,
                    Permission.ORDER_UPDATE,

                    Permission.USER_VIEW,
                    Permission.USER_LOCK,
                    Permission.USER_UNLOCK,

                    Permission.REVENUE_VIEW_OWN,
                    Permission.REVENUE_VIEW_ALL,

                    Permission.DASHBOARD_VIEW
            ));
        };
    }
}
