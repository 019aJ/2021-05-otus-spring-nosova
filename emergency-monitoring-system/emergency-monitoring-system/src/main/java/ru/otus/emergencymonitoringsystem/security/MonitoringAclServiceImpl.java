package ru.otus.emergencymonitoringsystem.security;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.mongodb.MongoDBMutableAclService;
import org.springframework.stereotype.Service;
import ru.otus.emergencymonitoringsystem.models.EMSUser;
import ru.otus.emergencymonitoringsystem.models.EmergencyMonitoring;
import ru.otus.emergencymonitoringsystem.models.WaterArea;

import java.util.List;

@Service
@AllArgsConstructor
public class MonitoringAclServiceImpl implements MonitoringAclService {
    private final MongoDBMutableAclService aclService;

    @Override
    public void addPermissionsForArea(WaterArea area, List<EMSUser> users) {
        ObjectIdentity oi = new ObjectIdentityImpl(area.getClass(), area.getId());
        MutableAcl acl = aclService.createAcl(oi);
        addAdminAcl(acl, oi);
        users.forEach(user -> {
            addUserAcl(acl, oi, user.getName());
        });
        aclService.updateAcl(acl);
    }

    @Override
    public void addPermissionsForMonitoring(EmergencyMonitoring emergencyMonitoring) {
        ObjectIdentity oi = new ObjectIdentityImpl(emergencyMonitoring.getClass(), emergencyMonitoring.getId());
        MutableAcl acl = aclService.createAcl(oi);
        addAdminAcl(acl, oi);
        addReaderAcl(acl, oi);
        aclService.updateAcl(acl);
    }

    @Override
    public void removePermissionsOnDelete(Long id) {
        ObjectIdentity oi = new ObjectIdentityImpl(EmergencyMonitoring.class, id);
        aclService.deleteAcl(oi, true);
    }

    private void addReaderAcl(MutableAcl acl, ObjectIdentity oi) {
        Sid sid = new GrantedAuthoritySid("ROLE_READER");
        Permission p = BasePermission.READ;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        acl.setOwner(sid);
    }

    private void addAdminAcl(MutableAcl acl, ObjectIdentity oi) {
        Sid sid = new GrantedAuthoritySid("ROLE_ADMIN");
        Permission p = BasePermission.READ;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        p = BasePermission.DELETE;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        p = BasePermission.WRITE;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        acl.setOwner(sid);
    }

    private void addUserAcl(MutableAcl acl, ObjectIdentity oi, String user) {
        Sid sid = new PrincipalSid(user);
        Permission p = BasePermission.READ;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        p = BasePermission.DELETE;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        p = BasePermission.WRITE;
        acl.insertAce(acl.getEntries().size(), p, sid, true);
        acl.setOwner(sid);
    }
}
