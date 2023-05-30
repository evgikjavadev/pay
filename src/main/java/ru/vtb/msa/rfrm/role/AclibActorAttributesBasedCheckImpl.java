package ru.vtb.msa.rfrm.role;

import org.springframework.stereotype.Component;
import ru.vtb.omni.ac.lib.api.dto.service.AclibActorKey;
import ru.vtb.omni.ac.lib.api.permissions.abac.AclibActorAttributesBasedCheck;

@Component
public class AclibActorAttributesBasedCheckImpl implements AclibActorAttributesBasedCheck {
    @Override
    public boolean accessGranted(Object actorAttributes, String operation, AclibActorKey actorKey) {
        return true;
    }
}
