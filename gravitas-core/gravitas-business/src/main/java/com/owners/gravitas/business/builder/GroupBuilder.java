package com.owners.gravitas.business.builder;

import static com.owners.gravitas.util.DateUtil.toDate;

import org.springframework.stereotype.Component;

import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.dto.request.UserGroupRequest;

/**
 * The Class GroupBuilder.
 *
 * @author raviz
 */
@Component( "groupBuilder" )
public class GroupBuilder extends AbstractBuilder< UserGroupRequest, Group > {

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Group convertTo( final UserGroupRequest source, final Group destination ) {
        Group group = destination;
        if (source != null) {
            if (destination == null) {
                group = new Group();
            }
            group.setName( source.getGroupName() );
            GroupOkr groupOkr = group.getGroupOkr();
            if (groupOkr == null) {
                groupOkr = new GroupOkr();
            }
            groupOkr.setRelatedOkr( source.getRelatedOkr() );
            groupOkr.setTestStartDate( toDate( source.getTestStartDate().getTime() ) );
            groupOkr.setTestEndDate( toDate( source.getTestEndDate().getTime() ) );
            groupOkr.setgroup( group );
            group.setGroupOkr( groupOkr );
        }
        return group;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public UserGroupRequest convertFrom( final Group source, final UserGroupRequest destination ) {
        throw new UnsupportedOperationException();
    }

}
