package com.owners.gravitas.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.ContactAttribute;

/**
 * The Interface ContactRepository.
 *
 * @author shivamm
 */
public interface ContactAttributeRepository extends JpaRepository<ContactAttribute, String> {

    @Transactional
    @Modifying
    @Query(value = "update gr_contact_attr set value = :attributeValue where contact_id=:contactId and object_attr_conf_id=:attributeConfId", nativeQuery = true)
    void updateContactAttribute(@Param("contactId") final String contactId,
            @Param("attributeConfId") final String attributeConfId,
            @Param("attributeValue") final String attributeValue);
    
    @Query(value = "select c.* from GR_CONTACT_ATTR c where c.contact_id=:contactId and c.object_attr_conf_id=:attributeConfId", nativeQuery = true)
    ContactAttribute getContactAttribute(@Param("contactId") final String contactId,
            @Param("attributeConfId") final String attributeConfId);
    
    @Query(value = "select c.* from GR_CONTACT_ATTR c where c.contact_id=:contactId", nativeQuery = true)
    Set<ContactAttribute> getContactAttributeSet(@Param("contactId") final String contactId);
    
    @Transactional
    @Modifying
    @Query(value = "insert into GR_CONTACT_ATTR values (:id,:contactId,:objectAttributeConfig,:value,:createdBy,now(),:lastModifiedBy,now())", nativeQuery = true)
    void insertContactAttribute(@Param("id") final String id, @Param("contactId") final String contactId, @Param("objectAttributeConfig") final String objectAttributeConfig,
            @Param("value") final String value, @Param("createdBy") final String createdBy, @Param("lastModifiedBy") final String lastModifiedBy);

}
