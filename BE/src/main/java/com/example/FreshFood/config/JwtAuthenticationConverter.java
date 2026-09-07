package com.example.FreshFood.config;

import com.example.FreshFood.enums.Permission;
import com.example.FreshFood.enums.Role;
import com.example.FreshFood.mapper.RolePermissionMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        String roleValue = jwt.getClaimAsString("role");

        Role role;

        try {
            role = Role.valueOf(roleValue);
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Role không hợp lệ trong token: " + roleValue);
        }

        Set<Permission> permissions =
                RolePermissionMapper.getPermissions(role);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Role
        authorities.add(new SimpleGrantedAuthority(
                        "ROLE_" + role.name()
                )
        );

        // Permissions
        authorities.addAll(
                permissions.stream()
                        .map(permission -> new SimpleGrantedAuthority(
                                        permission.name()
                                )
                        )
                        .toList()
        );

        return new org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getSubject()
        );
    }
}
