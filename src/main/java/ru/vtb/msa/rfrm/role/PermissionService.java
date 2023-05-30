package ru.vtb.msa.rfrm.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vtb.msa.rfrm.toggle.RoleModelToggle;
import ru.vtb.omni.ac.lib.api.permissions.abac.AclibActorAttributesBasedCheck;
import ru.vtb.omni.ac.lib.api.permissions.abac.AclibActorRoleAttributesBasedCheck;
import ru.vtb.omni.ac.lib.api.permissions.abac.AclibRoleAttributesBasedCheck;
import ru.vtb.omni.ac.lib.api.permissions.rbac.AclibRoleBasedPermissions;
import ru.vtb.omni.ac.lib.mapping.AclibActorMapper;
import ru.vtb.omni.ac.lib.mapping.AclibActorRoleMapper;
import ru.vtb.omni.ac.lib.mapping.AclibRoleMapper;
import ru.vtb.omni.ac.lib.service.AclibPropertiesService;
import ru.vtb.omni.ac.lib.service.PermissionCommonLogicService;
import ru.vtb.omni.ac.lib.service.data.AclibDataService;
import ru.vtb.omni.ac.lib.service.strategy.AbacStrategy;
import ru.vtb.omni.audit.lib.api.annotation.Audit;

/**
 * Сервис для проверки полномочий сотрудника, для доступа к указанной операции.
 * Наследник реализации Ролевой модели, в целях добавления аудирования события проверки полномочий.
 */

@Component
@Primary
public class PermissionService  extends PermissionCommonLogicService {

    private final RoleModelToggle toggle;

    public PermissionService(AclibPropertiesService aclibPropertiesService, AclibActorMapper actorMapper,
                             AclibRoleMapper roleMapper, AclibActorRoleMapper actorRoleMapper, AclibDataService aclibDataService,
                             AbacStrategy abacStrategy,   AclibRoleBasedPermissions roleBasedPermissions, AclibActorAttributesBasedCheck actorCheck,
                             AclibActorRoleAttributesBasedCheck actorRoleCheck, AclibRoleAttributesBasedCheck roleCheck, RoleModelToggle toggle) {
        super(aclibPropertiesService, actorMapper, roleMapper, actorRoleMapper, aclibDataService, abacStrategy, roleBasedPermissions, actorCheck, actorRoleCheck, roleCheck);
        this.toggle = toggle;
    }

    @Override
    @Audit("EXAMPLE_EVENT_CODE")
    public boolean permittedByRoleCommon(String operation, String uid, String channel) {
        if (toggle.isRoleOn()) {
            return super.permittedByRoleCommon(operation, uid, channel);
        }
        return true;
    }
}