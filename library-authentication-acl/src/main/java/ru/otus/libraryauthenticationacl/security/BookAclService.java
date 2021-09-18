package ru.otus.libraryauthenticationacl.security;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.libraryauthenticationacl.models.Book;

@Service
@AllArgsConstructor
public class BookAclService {
    private final JdbcMutableAclService aclService;

    /**
     * Выдает роли READER права на чтение, а ADMIN чтение, удаление, запись
     */
    @Transactional
    public void addPermissionsOnCreate(Book book) {
        ObjectIdentity oi = new ObjectIdentityImpl(book.getClass(), book.getId());
        MutableAcl acl = aclService.createAcl(oi);
        addAdminAcl(acl, oi);
        addReaderAcl(acl, oi);
        aclService.updateAcl(acl);
    }

    @Transactional
    public void removePermissionsOnDelete(Long id) {
        ObjectIdentity oi = new ObjectIdentityImpl(Book.class, id);
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
}
