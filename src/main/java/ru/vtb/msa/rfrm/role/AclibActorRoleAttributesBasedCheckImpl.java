package ru.vtb.msa.rfrm.role;

import org.springframework.stereotype.Component;
import ru.vtb.omni.ac.lib.api.dto.service.AclibActorRoleKey;
import ru.vtb.omni.ac.lib.api.permissions.abac.AclibActorRoleAttributesBasedCheck;

@Component
public class AclibActorRoleAttributesBasedCheckImpl implements AclibActorRoleAttributesBasedCheck {
    @Override
    public boolean accessGranted(Object actorRoleAttributes, String operation, AclibActorRoleKey actorRoleKey) {
        return true;
    }
}
