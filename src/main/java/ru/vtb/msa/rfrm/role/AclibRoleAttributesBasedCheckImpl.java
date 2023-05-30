package ru.vtb.msa.rfrm.role;

import org.springframework.stereotype.Component;
import ru.vtb.omni.ac.lib.api.dto.service.AclibRoleKey;
import ru.vtb.omni.ac.lib.api.permissions.abac.AclibRoleAttributesBasedCheck;

@Component
public class AclibRoleAttributesBasedCheckImpl implements AclibRoleAttributesBasedCheck {
    @Override
    public boolean accessGranted(Object roleAttributes, String operation, AclibRoleKey roleKey) {
        return true;
    }
}
