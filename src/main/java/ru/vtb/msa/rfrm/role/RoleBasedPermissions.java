package ru.vtb.msa.rfrm.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vtb.omni.ac.lib.api.permissions.rbac.AclibRoleBasedPermissions;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *    Класс для мапинга ролей операциям
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RoleBasedPermissions implements AclibRoleBasedPermissions {

    private final Map<String, Set<String>> rolesMap = new HashMap<>();

    @PostConstruct
    private void init() {
        rolesMap.put(Constants.READ, Set.of(Constants.ADMIN, Constants.OPERATOR));
        rolesMap.put(Constants.WRITE, Set.of(Constants.ADMIN));
    }
    @Override
    public Map<String, Set<String>> getRoleBasedPermissions() {
        return rolesMap;
    }
}